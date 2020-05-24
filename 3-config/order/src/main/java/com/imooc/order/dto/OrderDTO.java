package com.imooc.order.dto;

import com.imooc.order.data.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 将订单主表和订单商品表进行关联，封装，便于传输和管理；
 */

@Data
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    // 1:N  关联
    private List<OrderDetail> orderDetailList;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
}
