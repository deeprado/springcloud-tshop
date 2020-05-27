package com.example.demo.filter;

import com.example.demo.constant.RedisConstant;
import com.example.demo.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.ZuulFilterResult;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限拦截（区分卖家和买家）
 */
@Component
@Slf4j
public class AuthFilter extends ZuulFilter{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean isStaticFilter() {
        return super.isStaticFilter();
    }

    @Override
    public boolean shouldFilter() {
        // 可添加调试，是否启用
        return false;
    }

    @Override
    public boolean isFilterDisabled() {
        return super.isFilterDisabled();
    }

    @Override
    public ZuulFilterResult runFilter() {
        return super.runFilter();
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 2;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //  /order/create 买家 (cookie -> openid)
        //  /order/finish 卖家 (cookie -> token = redis )
        //  /product/list 全部

        // TODO 代码冗余且不好维护 -> AuthBuyerFilter

        String uri =  request.getRequestURI();
        log.info("uri =  {}", uri);
        if ("/order/order/create".equals(uri)) {
            Cookie cookie = CookieUtil.get(request, "openid");
            if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
        if ("/order/order/finish".equals(uri)) {
            Cookie cookie = CookieUtil.get(request, "token");
            log.info("cookie token = {}", cookie);
            if (cookie == null
                    || StringUtils.isEmpty(cookie.getValue())
                    || StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
                log.info("token = {}", cookie.getValue());
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        return  null;
    }
}
