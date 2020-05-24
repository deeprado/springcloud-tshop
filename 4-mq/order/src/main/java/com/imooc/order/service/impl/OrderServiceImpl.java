package com.imooc.order.service.impl;


import com.imooc.order.client.ProductClient;
import com.imooc.order.data.OrderDetail;
import com.imooc.order.data.OrderMaster;
import com.imooc.order.data.ProductInfo;
import com.imooc.order.dto.CartDTO;
import com.imooc.order.dto.OrderDTO;
import com.imooc.order.enums.OrderStatusEnum;
import com.imooc.order.enums.PayStatusEnum;
import com.imooc.order.enums.ResultEnum;
import com.imooc.order.exception.OrderException;
import com.imooc.order.repository.OrderDetailRepository;
import com.imooc.order.repository.OrderMasterRepository;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        List<OrderDetail> newOrderDetailList = new ArrayList<>();

        // 订单详情列表
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();

        log.info("订单详细 = {}", orderDetailList);

        // 查询商品信息(调用商品服务)
        List<String> productIdList = orderDetailList.stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        log.info("商品ID列表 = {}", productIdList);

        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);

        log.info("商品列表 = {}", productInfoList);

        // 计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail: orderDetailList) {
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    // 单价*数量
                    orderAmount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);

                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    // 订单详情入库
//                    orderDetailRepository.save(orderDetail);
                    newOrderDetailList.add(orderDetail);
                }
            }
        }

       // 扣库存(调用商品服务)
        List<CartDTO> cartDTOS = orderDetailList.stream().map(
                e-> new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productClient.decreaseStock(cartDTOS);

        // 订单详情入库
        orderDetailRepository.saveAll(newOrderDetailList);

        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);

        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        // 1. 先查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if (!orderMasterOptional.isPresent()) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 2. 判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if (OrderStatusEnum.NEW.getCode() != orderMaster.getOrderStatus()) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 3. 修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);

        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }


}
