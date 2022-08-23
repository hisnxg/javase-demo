  package edu.wtbu.servlet;

  import com.alibaba.fastjson.JSON;
  import edu.wtbu.pojo.Result;
  import edu.wtbu.service.CityService;
  import java.io.IOException;
  import javax.servlet.ServletException;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;

  @WebServlet({"/getCityNames"})
//  @SuppressWarnings("deprecation")
//  @WebServlet(urlPatterns = "/getCityNames",asyncSupported = true)
  public class GetCityNamesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
//    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
//      response.setHeader("Access-Control-Allow-Origin", "*");
//
//      response.setHeader("Access-Control-Allow-Credentials", "true");
//
//      response.setHeader("Access-Control-Allow-Methods", "*");
//
//      response.setHeader("Access-Control-Max-Age", "3600");
//
//      response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept,"
//
//              + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
//
//      response.setHeader("Access-Control-Request-Headers", "Authorization,Origin, X-Requested-With,content-Type,Accept");
//
//      response.setHeader("Access-Control-Expose-Headers", "*");
//
//    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      Result result = CityService.getCityNames();
      String msg = JSON.toJSONString(result);
    response.getWriter().append(msg);
    }
  }

