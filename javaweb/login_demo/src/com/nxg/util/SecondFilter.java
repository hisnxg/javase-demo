package com.nxg.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author nxg
 * date 2022/3/6
 * @apiNote
 */
public class SecondFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter过滤器2初始化");
    }

    @Override //request和response,请求和响应都会执行该方法
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        System.out.println("过滤器2开始");
        //调取下一个过滤器，或者serlet
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("过滤器2结束");


    }

    @Override
    public void destroy() {
        System.out.println("过滤器2销毁");
    }
}
