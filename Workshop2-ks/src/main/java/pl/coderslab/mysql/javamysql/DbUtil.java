package pl.coderslab.mysql.javamysql;

import pl.coderslab.entity.User;

import java.sql.*;


public class DbUtil {
//nie wykorzystano haszowania hasła oraz metod proponowanych w zadaniu

    //Stałe które są wykorzystywane w metodzie getConnection
    private static final String DB_URL = "jdbc:mysql://localhost:3306/"
            + "workshop2" + //NAZWA BAZY Z KTÓRĄ SIĘ ŁĄCZYMY
            "?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";

    //---------------------------------------------------------------------------------------
    // Statyczna metoda getConnection z klasy DbUtil która udostępnia połączenie z database
    public static Connection connect() throws SQLException {  //TEN THROWS MUSI BYĆ - ale jak jest TRY CATCH to nie musi
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        return connection;
    }

    //----------------------------------------------------------------------------------------
    //INSERT
    public static void insert(Connection conn, String query, String... params) { //to jest szablon KTÓRY WSTAWIA DO TABELI
        try (PreparedStatement preStmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preStmt.setString(i + 1, params[i]); //iteruję po wszystkich Params i ustawiam jako String
            }
            preStmt.executeUpdate(); // to jest taki SUBMIT
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //SELECT
    public static void printData(Connection conn, String query, int id) throws SQLException {
        PreparedStatement preStmt = conn.prepareStatement(query); // Przekazuje w parametrze kwerendę
        preStmt.setInt(1, id); // Podmieniam "?" z kwerendy parametrem metody. Pierwszy parametr to wskazanie, który znak zapytania podmieniam
        ResultSet rs = preStmt.executeQuery();  // Wywołuje kwerendę
        ResultSetMetaData rsmd = rs.getMetaData(); // Zbieram wyniki wywołania
        int columnsNumber = rsmd.getColumnCount(); // Pobieram ilość kolumn z tabeli
        while (rs.next()) { // Dopóki jest jakaś wartość w polu tabeli ->
            for (int i = 1; i <= columnsNumber; i++) { // Przechodzi przez każde pole od 1 do ostatniej kolumny
                System.out.print(rs.getString(i) + " "); // Wyświetla zawartość pola w kolumnie "i"
            }
        }
    }

    public static void printAllData(Connection conn, String query) { // TO JEST SZABLON METODY DO WYPISYWANIA W KONSOLI - TRZEBA WYWOŁAĆ JĄ STRINGIEM Z SELECT
        try (PreparedStatement preStmt = conn.prepareStatement(query);
             ResultSet rs = preStmt.executeQuery()) {   //RESULTSET ->zawiera wyniki zapytania
            ResultSetMetaData rsmd = rs.getMetaData(); //dodatkowe metody dla resultSet np. columnsCount
            int columnsNumber = rsmd.getColumnCount();
// Iterate through the data in the result set and display it.
            while (rs.next()) {
//Print one row
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rs.getString(i) + " "); //Print one element of a row
                }
                System.out.println();//Move to the next line to print the next row.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //UPDATE
    public static void updateData(Connection conn, String query, int id, String email, String username, String password) {

        try (PreparedStatement preStmt = // jest pS bo z zewnątrz
                     conn.prepareStatement(query)) {
            preStmt.setString(1, username); //to są pozycje znaków zapytania w query (1 dla username itp)
            preStmt.setString(2, email);
            preStmt.setString(3, password);
            preStmt.setInt(4, id); // id = 4 bo jest na końcu zapytania WHERE id = ?
            preStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //----------------------------------------------------------------------------------------
    //DELETE
    public static void remove(Connection conn, String query, int id) { //SZABLON DO KASOWANIA
        try (PreparedStatement preStmt = // jest pS bo z zewnątrz
                     conn.prepareStatement(query)) { //ten replace nie był potrzebny bo jest w kwerendzie zapisane co zmieniamy na sztywno / bo jest tylko 1 tabela do wyboru
            preStmt.setInt(1, id);
            preStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



