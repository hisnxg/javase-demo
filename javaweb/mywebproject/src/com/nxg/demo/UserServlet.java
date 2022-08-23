package com.nxg.demo;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author nxg
 * date 2022/3/6
 * @apiNote
 */
public class UserServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        System.out.println("初始化方法------"+"mycontext="+servletConfig.getServletContext().getInitParameter("mycontext"));
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("销毁的方法");
    }
}
