package com.nxg.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author nxg
 * date 2022/3/6
 * @apiNote
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("error.jsp");//重定向
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.setContentType("text/html;charset=utf-8");
       String username = req.getParameter("username");
       String pass = req.getParameter("pass");
      //判断是否登录成功
       if(username.equals("admin")&&pass.equals("123456")){
        //登录成功
           HttpSession session = req.getSession();
           session.setAttribute("username",username);
           session.setAttribute("password",pass);
           session.setMaxInactiveInterval(60*30);
           req.getRequestDispatcher("/success.jsp").forward(req,resp);//转发
       }else{//登录失败
           //一般不使用cookie存密码等重要信息
           Cookie cookie = new Cookie("uname",username);
           resp.addCookie(cookie);
           resp.sendRedirect("index.jsp");
       }

    }
}
