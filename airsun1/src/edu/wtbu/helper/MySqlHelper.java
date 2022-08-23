 package edu.wtbu.helper;

 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;

 public class MySqlHelper {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    private static String url = "jdbc:mysql://localhost:3306/session1?serverTimezone=GMT%2B8&useOldAliasMetadataBehavior=true";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String userName = "root";
    private static String password = "MyMysql511.";

   static {
     try {
        Class.forName(driver);
      } catch (Exception e) {
        e.printStackTrace();
     }
   }


   public static Connection getConnection() {
     try {
        conn = DriverManager.getConnection(url, userName, password);
      } catch (Exception e) {
        e.printStackTrace();
     }
      return conn;
   }


   public static List<HashMap<String, Object>> executeQueryReturnMap(String sql, Object[] parameters) {
      List<HashMap<String, Object>> list = null;
     try {
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        if (parameters != null) {
          for (int i = 0; i < parameters.length; i++) {
            String className = parameters[i].getClass().getName();
            if (className.contains("String")) {
              pstmt.setString(i + 1, parameters[i].toString());
           }
            if (className.contains("Integer")) {
             pstmt.setInt(i + 1, Integer.parseInt(parameters[i].toString()));
           }
         }
       }
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        list = new ArrayList<>();
        int columnNum = rsmd.getColumnCount();
        while (rs.next()) {
          HashMap<String, Object> map = new HashMap<>();
          for (int i = 0; i < columnNum; i++) {
          String columnName = rsmd.getColumnName(i + 1);
            Object value = rs.getObject(i + 1);
           map.put(columnName, value);
         }
          list.add(map);
       }
    } catch (Exception e) {
      e.printStackTrace();
     } finally {
       close(rs, pstmt, conn);
     }
     return list;
   }


   public static int executeUpdate(String sql, Object[] parameters) {
      int result = 0;
     try {
        conn = getConnection();
       pstmt = conn.prepareStatement(sql);
      if (parameters != null) {
         for (int i = 0; i < parameters.length; i++) {
          String className = parameters[i].getClass().getName();
           if (className.contains("String")) {
             pstmt.setString(i + 1, parameters[i].toString());
           }
           if (className.contains("Integer")) {
             pstmt.setInt(i + 1, Integer.parseInt(parameters[i].toString()));
           }
         }
       }

        result = pstmt.executeUpdate();
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       close(rs, pstmt, conn);
     }
     return result;
   }


   public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
     if (rs != null) {
       try {
        rs.close();
        } catch (Exception e) {
         e.printStackTrace();
       }
     }
      if (pstmt != null) {
       try {
          pstmt.close();
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
     if (conn != null)
       try {
        conn.close();
      } catch (Exception e) {
          e.printStackTrace();
       }
   }
 }
