  package edu.wtbu.servlet;

  import com.alibaba.fastjson.JSON;
  import com.alibaba.fastjson.serializer.SerializerFeature;
  import edu.wtbu.pojo.Result;
  import edu.wtbu.service.ScheduleService;
  import java.io.IOException;
  import javax.servlet.ServletException;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;

  @WebServlet({"/getFlightStatus"})
  public class GetFlightStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      int startPage = 1;
      int pageSize = 10;
      String departureDate = request.getParameter("departureDate");
      String startDate = String.valueOf(departureDate) + " 00:00:00";
      String endDate = String.valueOf(departureDate) + " 23:59:59";
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

      Result result = ScheduleService.getFlightStatus(startDate, endDate, startPage, pageSize);
      String msg = JSON.toJSONString(result, new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat });
      response.getWriter().append(msg);
    }
  }


