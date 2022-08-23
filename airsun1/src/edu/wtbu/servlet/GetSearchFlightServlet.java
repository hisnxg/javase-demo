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

 @WebServlet({"/getSearchFlight"})
 public class GetSearchFlightServlet
   extends HttpServlet
 {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String fromCity = request.getParameter("fromCity");
      String toCity = request.getParameter("toCity");
      String flightType = request.getParameter("flightType");
      String departureDate = request.getParameter("departureDate");
      String startDate = String.valueOf(departureDate) + " 00:00:00";
      String endDate = String.valueOf(departureDate) + " 23:59:59";
      int cabinTypeId = 0;
     try {
        cabinTypeId = Integer.parseInt(request.getParameter("cabinTypeId"));
      } catch (Exception e) {
        cabinTypeId = 0;
     }

      Result result = ScheduleService.getSearchFlight(fromCity, toCity, startDate, endDate, cabinTypeId, flightType);
      String msg = JSON.toJSONString(result, new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat });
      response.getWriter().append(msg);
   }
 }

