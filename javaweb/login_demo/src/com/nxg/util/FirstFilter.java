package com.nxg.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nxg
 * date 2022/3/6
 * @apiNote
 */
public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter过滤器初始化");
    }

    @Override //request和response,请求和响应都会执行该方法
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器开始");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //防止乱码
        request.setCharacterEncoding("utf-8");

        //在登录的情况下才能访问其他页面
        String requestURI = request.getRequestURI();
        Object username = request.getSession().getAttribute("username");
        if(requestURI.endsWith("testsession.jsp")&&username==null){
            response.sendRedirect("/index.jsp");
        }
        //调取下一个过滤器，或者serlet
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("过滤器结束");


    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }
}
