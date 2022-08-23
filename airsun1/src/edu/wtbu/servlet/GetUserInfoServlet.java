  package edu.wtbu.servlet;

  import com.alibaba.fastjson.JSON;
  import edu.wtbu.pojo.Result;
  import edu.wtbu.service.UsersService;
  import java.io.IOException;
  import javax.servlet.ServletException;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;

  @WebServlet({"/getUserInfo"})
  public class GetUserInfoServlet
    extends HttpServlet
  {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      int userId = 0;
      try {
        userId = Integer.parseInt(request.getParameter("userId"));
      } catch (Exception e) {
        userId = 0;
      }
      Result result = UsersService.findByUserId(userId);
      String msg = JSON.toJSONString(result);
      response.getWriter().append(msg);
    }
  }


