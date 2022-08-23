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

  @WebServlet({"/userList"})
  public class UserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      String name = request.getParameter("name");
      int roleId = 0;
      int startPage = 1;
       int pageSize = 10;
      try {
        roleId = Integer.parseInt(request.getParameter("roleId"));
      } catch (Exception e) {
        roleId = 0;
      }
      try {
          startPage = Integer.parseInt(request.getParameter("startPage"));
      } catch (Exception e) {
        startPage = 1;
      }
      try {
        pageSize = Integer.parseInt(request.getParameter("pageSize"));
      } catch (Exception e) {
        pageSize = 10;
      }
      Result result = UsersService.userList(name, roleId, startPage, pageSize);
      String msg = JSON.toJSONString(result);
      response.getWriter().append(msg);
    }
  }
