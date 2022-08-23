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


  @WebServlet({"/addUser"})
// @SuppressWarnings("deprecation")
// @WebServlet(urlPatterns = "/addUser",asyncSupported = true)
 public class AddUserServlet extends HttpServlet {
     private static final long serialVersionUID = 1L;

//      protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
//          response.setHeader("Access-Control-Allow-Origin", "*");
//
//          response.setHeader("Access-Control-Allow-Credentials", "true");
//
//          response.setHeader("Access-Control-Allow-Methods", "*");
//
//          response.setHeader("Access-Control-Max-Age", "3600");
//
//          response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept,"
//
//                  + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
//
//          response.setHeader("Access-Control-Request-Headers", "Authorization,Origin, X-Requested-With,content-Type,Accept");
//
//          response.setHeader("Access-Control-Expose-Headers", "*");
//
//      }
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request, response);
     }



      protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
       String email = request.getParameter("email");
       String password = "";
       String firstName = request.getParameter("firstName");
       String lastName = request.getParameter("lastName");
       String gender = request.getParameter("gender");
       String dateOfBirth = request.getParameter("dateOfBirth");
       String phone = request.getParameter("phone");
       String photo = request.getParameter("photo");
       String address = request.getParameter("address");
       int roleId = 0;
       try {
         roleId = Integer.parseInt(request.getParameter("roleId"));
       } catch (Exception e) {
         roleId = 0;
       }
       try {
         password = email.split("@")[0];
         password = (password.length() > 6) ? password.substring(0, 6) : password;
       } catch (Exception e) {
         password = "123456";
       }
       HashMap<String, Object> map = new HashMap<>();
      map.put("email", email);
      map.put("firstName", firstName);
       map.put("lastName", lastName);
       map.put("dateOfBirth", dateOfBirth);
       map.put("address", address);
       map.put("gender", gender);
     map.put("password", password);
      map.put("phone", phone);
     map.put("photo", photo);
     map.put("roleId", Integer.valueOf(roleId));

   Result result = UsersService.addUser(map);
     String msg = JSON.toJSONString(result);
     response.getWriter().append(msg);
   }
  }


