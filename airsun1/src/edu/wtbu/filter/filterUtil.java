package edu.wtbu.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName="filterUtil", urlPatterns="/")
public class filterUtil implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) resp;
//        response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.
//        response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0");
//        chain.doFilter(req, resp);


   //  HttpServletResponse response = (HttpServletResponse) resp;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        chain.doFilter(req, resp);

//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setDateHeader("Expires", 0);


        //设置跨域请求
        HttpServletResponse response = (HttpServletResponse) resp;
        //此处ip地址为需要访问服务器的ip及端口号
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type,Token,Accept, Connection, User-Agent, Cookie");
        response.setHeader("Access-Control-Max-Age", "3628800");

//        System.out.println("设置跨域请求");
        chain.doFilter(req, response);

//        chain.doFilter(req, res);这里注释掉这句话，因为前面已经将修改后的请求头发送了，这句话会使请求发送两次

    }

    @Override
    public void destroy() {

    }
}
