package com.nxg.demo;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author nxg
 * date 2022/3/6
 * @apiNote
 */
public class TestServlet implements Servlet {

    public TestServlet() {
        System.out.println("起始");

    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("servlet配置的方法"+servletConfig.getInitParameter("myparam"));
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.getWriter().append("hello");

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("销毁的方法--------------");
    }

}
