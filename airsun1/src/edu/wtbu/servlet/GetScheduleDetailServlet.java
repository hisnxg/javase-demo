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


 @WebServlet({"/getScheduleDetail"})
 public class GetScheduleDetailServlet
   extends HttpServlet
 {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      int scheduleId = 0;
     try {
        scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
      } catch (Exception e) {
        scheduleId = 0;
     }
      Result result = ScheduleService.getScheduleDetail(scheduleId);
      String msg = JSON.toJSONString(result, new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat });
      response.getWriter().append(msg);
   }
 }

