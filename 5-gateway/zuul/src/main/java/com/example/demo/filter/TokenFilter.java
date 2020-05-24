package com.example.demo.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 身份认证
 */
@Component
public class TokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // 生命周期的过滤器类型
        // pre：可以在请求被路由之前调用
        // route：在路由请求时候被调用
        // post：在route和error过滤器之后被调用
        // error：处理请求时发生错误时被调用
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 执行顺序
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        // 可添加调试，是否启用
        return true;
    }

    @Override
    public Object run() {
        // 实际逻辑
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 这里从url参数里获取, 也可以从cookie, header里获取
//        String token = request.getHeader("token");
//        Cookie[] cookies =  request.getCookies();

        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token) || !token.equals("123")) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
