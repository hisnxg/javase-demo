 package edu.wtbu.service;

 import edu.wtbu.dao.ScheduleDao;
 import edu.wtbu.pojo.Page;
 import edu.wtbu.pojo.Result;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;


 public class ScheduleService {
   public static Result getFlightStatus(String startDate, String endDate, int startPage, int pageSize) {
      List<HashMap<String, Object>> list = ScheduleDao.findScheduleByDate(startDate, endDate, startPage, pageSize);
      int total = ScheduleDao.findScheduleCountByDate(startDate, endDate);
      Page page = new Page(total, startPage, pageSize);
      Result result = new Result("success", page, list);
      return result;
   }




   public static Result getSchedule(String fromCity, String toCity, String startDate, String endDate) {
      List<HashMap<String, Object>> list = ScheduleDao.findScheduleByCityAndDate(fromCity, toCity, startDate, endDate);
      Result result = new Result("success", null, list);
      return result;
   }





   public static Result getScheduleDetail(int scheduleId) {
      Result result = new Result("fail", null, null);

      HashMap<String, Object> scheduleInfo = ScheduleDao.findByScheduleId(scheduleId);
      if (scheduleInfo == null) {
        result.setData("航班计划不存在");
        return result;
     }

      int aircraftId = Integer.parseInt(scheduleInfo.get("AircraftId").toString());
      List<HashMap<String, Object>> ticketInfoList = ScheduleDao.findTicketInfoList(scheduleId);
      List<HashMap<String, Object>> selectedSeatList = ScheduleDao.findSelectedSeatList(scheduleId);
      List<HashMap<String, Object>> seatLayoutList = ScheduleDao.findSeatLayoutList(aircraftId);

      HashMap<String, Object> map = new HashMap<>();
      map.put("ScheduleInfo", scheduleInfo);
      map.put("TicketInfoList", ticketInfoList);
      map.put("SelectedSeatList", selectedSeatList);
      map.put("SeatLayoutList", seatLayoutList);

       result.setFlag("success");
      result.setData(map);
      return result;
   }




   public static Result updateSchedule(int scheduleId, String status) {
      Result result = new Result("fail", null, null);
      HashMap<String, Object> scheduleInfo = ScheduleDao.findByScheduleId(scheduleId);
      if (scheduleInfo == null) {
        result.setData("航班计划不存在");
        return result;
     }
      int updateResult = ScheduleDao.updateSchedule(scheduleId, status);
      if (updateResult > 0) {
        result.setFlag("success");
     }
      return result;
   }





   public static Result getSearchFlight(String fromCity, String toCity, String startDate, String endDate, int cabinTypeId, String flightType) {
      Result result = new Result("success", null, null);

      if (flightType.equals("Non-stop")) {
        List<HashMap<String, Object>> list = getNonstop(fromCity, toCity, startDate, endDate, cabinTypeId);
        result.setData(list);
      } else if (flightType.equals("1-stop")) {
        List<HashMap<String, Object>> list = getOnestop(fromCity, toCity, startDate, endDate, cabinTypeId);
        result.setData(list);
        } else if (flightType.equals("All")) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        List<HashMap<String, Object>> nonStopList = getNonstop(fromCity, toCity, startDate, endDate, cabinTypeId);
        List<HashMap<String, Object>> oneStopList = getOnestop(fromCity, toCity, startDate, endDate, cabinTypeId);
        list.addAll(nonStopList);
        list.addAll(oneStopList);
        result.setData(list);
     }

      return result;
   }




   protected static List<HashMap<String, Object>> getNonstop(String fromCity, String toCity, String startDate, String endDate, int cabinTypeId) {
      List<HashMap<String, Object>> list = ScheduleDao.findNonStopScheduleList(fromCity, toCity, startDate, endDate);
      if (list != null) {
        for (int i = 0; i < list.size(); i++) {
          HashMap<String, Object> map = list.get(i);
          int scheduleId = Integer.parseInt(map.get("ScheduleId").toString());
          int soldTickets = ScheduleDao.findSoldTicketsCount(scheduleId, cabinTypeId);
          int allTickets = 0;
          if (cabinTypeId == 1) {
            allTickets = Integer.parseInt(map.get("EconomySeatsAmount").toString());
          } else if (cabinTypeId == 2) {
            allTickets = Integer.parseInt(map.get("BusinessSeatsAmount").toString());
          } else if (cabinTypeId == 3) {
            allTickets = Integer.parseInt(map.get("FirstSeatsAmount").toString());
         }
          int residueTickets = allTickets - soldTickets;
          map.put("ResidueTickets", Integer.valueOf(residueTickets));
          map.put("FlightType", "Non-stop");
       }
     }
      return list;
   }




   protected static List<HashMap<String, Object>> getOnestop(String fromCity, String toCity, String startDate, String endDate, int cabinTypeId) {
      List<HashMap<String, Object>> list = ScheduleDao.findOneStopScheduleList(fromCity, toCity, startDate, endDate);
      List<HashMap<String, Object>> delayInfoList = ScheduleDao.findDelayInfoList(startDate);
      if (list != null) {
        for (int i = 0; i < list.size(); i++) {
          HashMap<String, Object> map = list.get(i);
          int s1ScheduleId = Integer.parseInt(map.get("S1ScheduleId").toString());
          int s2ScheduleId = Integer.parseInt(map.get("S2ScheduleId").toString());
          String s1FlightNumber = map.get("S1FlightNumber").toString();
          String s2FlightNumber = map.get("S2FlightNumber").toString();
          if (delayInfoList != null) {
            for (int j = 0; j < delayInfoList.size(); j++) {
              HashMap<String, Object> delayInfoMap = delayInfoList.get(j);
              String flightNumber = delayInfoMap.get("FlightNumber").toString();
              if (flightNumber.equals(s1FlightNumber)) {
                map.put("S1AllCount", delayInfoMap.get("AllCount"));
                map.put("S1DelayCount", delayInfoMap.get("DelayCount"));
                map.put("S1NotDelay", delayInfoMap.get("NotDelay"));
              } else if (flightNumber.equals(s2FlightNumber)) {
                map.put("S2AllCount", delayInfoMap.get("AllCount"));
                map.put("S2DelayCount", delayInfoMap.get("DelayCount"));
                map.put("S2NotDelay", delayInfoMap.get("NotDelay"));
             }
           }
         }

          int s1SoldTickets = ScheduleDao.findSoldTicketsCount(s1ScheduleId, cabinTypeId);
          int s2SoldTickets = ScheduleDao.findSoldTicketsCount(s2ScheduleId, cabinTypeId);
          int s1AllTickets = 0;
          int s2AllTickets = 0;
          if (cabinTypeId == 1) {
            s1AllTickets = Integer.parseInt(map.get("S1EconomySeatsAmount").toString());
            s2AllTickets = Integer.parseInt(map.get("S2EconomySeatsAmount").toString());
          } else if (cabinTypeId == 2) {
            s1AllTickets = Integer.parseInt(map.get("S1BusinessSeatsAmount").toString());
            s2AllTickets = Integer.parseInt(map.get("S2BusinessSeatsAmount").toString());
          } else if (cabinTypeId == 3) {
            s1AllTickets = Integer.parseInt(map.get("S1FirstSeatsAmount").toString());
            s2AllTickets = Integer.parseInt(map.get("S2FirstSeatsAmount").toString());
         }
          int s1ResidueTickets = s1AllTickets - s1SoldTickets;
          int s2ResidueTickets = s2AllTickets - s2SoldTickets;
          map.put("S1ResidueTickets", Integer.valueOf(s1ResidueTickets));
          map.put("S2ResidueTickets", Integer.valueOf(s2ResidueTickets));
        map.put("FlightType", "1-stop");
       }
     }

    return list;
   }
 }

