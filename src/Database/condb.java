package Database;

import Generators.Chromosome;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class condb {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    public static Connection conn = null;
    public static Statement stmt = null;

    public static void setConnection() {

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }

            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public static void insertSG(String sgname) {
        PreparedStatement insertPreparedStatement = null;

        String query = "SELECT TOP 1 * FROM TTSTUDENTGROUP ORDER BY ID DESC";

        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            int i = 0;
            while (rs.next()) {
                i++;
                if (i > 0) {
                    break;
                }
            }
            int id;
            if (i > 0) {
                id = rs.getInt("id");
                id++;
            } else {
                id = 1;
            }


            String sql = "INSERT INTO STUDENTGROUP(NAME, TTID) VALUES" + "(?,?)";

            insertPreparedStatement = conn.prepareStatement(sql);
            insertPreparedStatement.setString(1, sgname);
            insertPreparedStatement.setInt(2, id);
            insertPreparedStatement.executeUpdate();

            stmt.close();
            insertPreparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertTTSG() {

        int days = 5, hours = 7;
        String day = null, one, two, three, four, five, six, seven;

        PreparedStatement insertPreparedStatement = null;

        String query = "SELECT TOP 1 * FROM TTSTUDENTGROUP ORDER BY ID DESC";

        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            int i = 0;
            while (rs.next()) {
                i++;
                if (i > 0) {
                    break;
                }
            }
            int id;
            if (i > 0) {
                id = rs.getInt("id");
                id++;
            } else {
                id = 1;
            }


            //looping for each day
            for (int j = 0; j < days; j++) {

                switch (j) {
                    case 0:
                        day = "MONDAY";
                        break;
                    case 1:
                        day = "TUESDAY";
                        break;
                    case 2:
                        day = "WEDNESDAY";
                        break;
                    case 3:
                        day = "THURSDAY";
                        break;
                    case 4:
                        day = "FRIDAY";
                        break;
                }

                one = Chromosome.tt[j][0];
                two = Chromosome.tt[j][1];
                three = Chromosome.tt[j][2];
                four = Chromosome.tt[j][3];
                five = Chromosome.tt[j][4];
                six = Chromosome.tt[j][5];
                seven = Chromosome.tt[j][6];

                String sql = "INSERT INTO TTSTUDENTGROUP(ID, DAY, ONE,TWO,THREE, FOUR, FIVE, SIX, SEVEN) VALUES" + "(?,?,?,?,?,?,?,?,?)";
                insertPreparedStatement = conn.prepareStatement(sql);
                insertPreparedStatement.setInt(1, id);
                insertPreparedStatement.setString(2, day);
                insertPreparedStatement.setString(3, one);
                insertPreparedStatement.setString(4, two);
                insertPreparedStatement.setString(5, three);
                insertPreparedStatement.setString(6, four);
                insertPreparedStatement.setString(7, five);
                insertPreparedStatement.setString(8, six);
                insertPreparedStatement.setString(9, seven);
                insertPreparedStatement.executeUpdate();


            }

            // STEP 4: Clean-up environment
            stmt.close();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> viewBranchName() {
        ArrayList S = new ArrayList();
        String s = new String();

        String query = "SELECT name FROM STUDENTGROUP ";

        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                s = rs.getString("name");
                S.add(s);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return S;
    }

    public static String[][] showBranchTT(String branch) {
        String TT[][] = new String[5][7];
        int ttid=0;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT ttid FROM STUDENTGROUP WHERE name = ? ");

            ((PreparedStatement) stmt).setString(1,branch);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ttid = rs.getInt("TTID");
            }


            stmt = conn.prepareStatement("SELECT * FROM TTSTUDENTGROUP WHERE id = ? ");
            ((PreparedStatement) stmt).setInt(1,ttid);
            ResultSet r = stmt.executeQuery();

            int i = 0;
            while (r.next()) {
                TT[i][0] = r.getString("one");
                TT[i][1] = r.getString("two");
                TT[i][2] = r.getString("three");
                TT[i][3] = r.getString("four");
                TT[i][4] = r.getString("five");
                TT[i][5] = r.getString("six");
                TT[i][6] = r.getString("seven");
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return TT;

    }

    public static int branchname(String branch) {

        int flag=0;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT ttid FROM STUDENTGROUP WHERE name = ? ");

            ((PreparedStatement) stmt).setString(1,branch);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                flag = 1;
            }
        }
        catch (SQLException e){

        }
        return flag;
    }

}