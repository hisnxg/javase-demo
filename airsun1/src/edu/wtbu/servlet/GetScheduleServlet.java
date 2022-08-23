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

 @WebServlet({"/getSchedule"})
 public class GetScheduleServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      String fromCity = request.getParameter("fromCity");
      String toCity = request.getParameter("toCity");
      String startDate = String.valueOf(request.getParameter("startDate")) + " 00:00:00";
      String endDate = String.valueOf(request.getParameter("endDate")) + " 23:59:59";
       Result result = ScheduleService.getSchedule(fromCity, toCity, startDate, endDate);
      String msg = JSON.toJSONString(result, new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat });
      response.getWriter().append(msg);
   }
 }


