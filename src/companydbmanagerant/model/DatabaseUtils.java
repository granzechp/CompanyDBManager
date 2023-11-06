/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companydbmanagerant.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import raven.toast.Notifications;

/**
 *
 * @author PHC
 */
public class DatabaseUtils {

    private static String URL = "localhost:3306";
    private static String USER = "root";
    private static String PASS = "qwer123";
    private static String DBNAME = "companydb";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + URL + "/" + DBNAME + "?zeroDateTimeBehavior=CONVERT_TO_NULL", USER, PASS);
        } catch (Exception e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, e.getMessage());
            //JOptionPane.showMessageDialog(null, e);
        }
        return conn;  // 이제 연결 객체를 반환.
    }

    public static boolean try_login(LoginFormDataDTO logindata) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String temp = String.valueOf(logindata.getPassword());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + logindata.getUrl() + "/" + logindata.getDbName() + "?zeroDateTimeBehavior=CONVERT_TO_NULL", logindata.getUserId(), temp);
            USER = logindata.getUserId();
            PASS = temp;
            DBNAME = logindata.getDbName();
            URL = logindata.getUrl();

            String checkTablesQuery = "SELECT GROUP_CONCAT(table_name) FROM information_schema.tables WHERE table_schema = ? AND table_name IN ('employee', 'department', 'dependent', 'works_on', 'project', 'dept_locations')";
            pstmt = conn.prepareStatement(checkTablesQuery);
            pstmt.setString(1, logindata.getDbName()); // 데이터베이스 이름으로 테이블 스키마를 설정합니다.
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String foundTablesConcatenated = rs.getString(1);
                if (foundTablesConcatenated != null) {
                    List<String> missingTables = new ArrayList<>();
                    String[] requiredTables = {"employee", "department", "dependent", "works_on", "project", "dept_locations"};
                    List<String> foundTables = Arrays.asList(foundTablesConcatenated.split(","));

                    for (String table : requiredTables) {
                        if (!foundTables.contains(table)) {
                            missingTables.add(table);
                        }
                    }

                    if (!missingTables.isEmpty()) {
                        // 존재하지 않는 모든 테이블을 사용자에게 한 번에 알림
                        String missingTablesString = String.join(", ", missingTables);
                        Notifications.getInstance().show(Notifications.Type.ERROR, "The following tables do not exist: " + missingTablesString);
                        return false;
                    }
                } else {
                    // 필요한 테이블이 하나도 없는 경우
                    Notifications.getInstance().show(Notifications.Type.ERROR, "No required tables found in the database.");
                    return false;
                }
            }
        } catch (Exception e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, e.getMessage());
            System.err.println(e);
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                Notifications.getInstance().show(Notifications.Type.ERROR, e.getMessage());
                System.err.println(e);
            }
        }
        return true;
    }

//    public static boolean try_login(LoginFormDataDTO logindata) {
//
//        
//        Connection conn = null;
//        String temp = String.valueOf(logindata.getPassword());
//        try {
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://"+ logindata.getUrl() + "/"+ logindata.getDbName() +"?zeroDateTimeBehavior=CONVERT_TO_NULL", logindata.getUserId(), temp);
//            USER = logindata.getUserId();
//            PASS = temp;
//            DBNAME = logindata.getDbName();
//            URL = logindata.getUrl();
//        } catch (Exception e) {
//            //JOptionPane.showMessageDialog(null, e);
//            Notifications.getInstance().show(Notifications.Type.ERROR,e.getMessage());
//            System.err.println(e);
//
//            return false;
//        }  finally {
//            // 사용한 자원 반환
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//            }
//        }
//        
//        return true;  
//    }
}
