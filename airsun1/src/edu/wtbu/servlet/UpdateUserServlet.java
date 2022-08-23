  package edu.wtbu.servlet;

  import com.alibaba.fastjson.JSON;
  import edu.wtbu.pojo.Result;
  import edu.wtbu.service.UsersService;
  import java.io.IOException;
  import java.util.HashMap;
  import javax.servlet.ServletException;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;

  @WebServlet({"/updateUser"})
  public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
    }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");

      String email = request.getParameter("email");
     String firstName = request.getParameter("firstName");
      String lastName = request.getParameter("lastName");
      String gender = request.getParameter("gender");
      String dateOfBirth = request.getParameter("dateOfBirth");
      String phone = request.getParameter("phone");
      String photo = request.getParameter("photo");
       String address = request.getParameter("address");
      int userId = 0;
      try {
        userId = Integer.parseInt(request.getParameter("userId"));
      } catch (Exception e) {
        userId = 0;
      }
      int roleId = 0;
      try {
        roleId = Integer.parseInt(request.getParameter("roleId"));
      } catch (Exception e) {
        roleId = 0;
      }

      HashMap<String, Object> map = new HashMap<>();
      map.put("userId", Integer.valueOf(userId));
      map.put("email", email);
      map.put("firstName", firstName);
      map.put("lastName", lastName);
      map.put("dateOfBirth", dateOfBirth);
      map.put("address", address);
      map.put("gender", gender);
     map.put("phone", phone);
      map.put("photo", photo);
      map.put("roleId", Integer.valueOf(roleId));

      Result result = UsersService.updateUser(map);
      String msg = JSON.toJSONString(result);
      response.getWriter().append(msg);
    }
  }


/* Location:              C:\Users\nxg\Desktop\SunshineAirlines.war!\WEB-INF\classes\edu\wtbu\servlet\UpdateUserServlet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */