 package edu.wtbu.dao;

 import edu.wtbu.helper.MySqlHelper;

 import java.util.HashMap;
 import java.util.List;

 public class CityDao
 {
   public static List<HashMap<String, Object>> getCityNames() {
    String sql = "select * from City";
    return MySqlHelper.executeQueryReturnMap(sql, null);
   }
 }

