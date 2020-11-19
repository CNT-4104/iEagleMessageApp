package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class Database_Accessor {
    public static String JDBC_DRIVER = "org.h2.Driver";
    public static String DB_URL = "jdbc:h2:C:/Users/feesh/OneDrive/intelliJCOP/iEagleMessageApp/res/iEagle_Database";
    public static String PASSWORD = "";
    public static String USERNAME = "";
    public static Connection conn = null;
    public static Statement stmt = null;

    // The sql statement and type (execute or update) are passed
    public static void addMessage(Message message) {
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        stmt = conn.createStatement();
        String sql =
            "INSERT INTO MESSAGE_TABLE(MESSAGE_TYPE, TIME_OF_MESSAGE, CHAT_CONTACT, DATE, MESSAGE_CONTEXT)"
                + "VALUES('"
                + message.getMessage_type()
                + "','"
                + message.getTime_of_message()
                + "','"
                + message.getChat_contact()
                + "','"
                + message.getDate()
                + "','"
                + message.getMessage_context()
                + "');";

        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    public static ArrayList<Message> getMessages(iMessageUser currentUser) {
      Message message = new Message("", LocalTime.MIDNIGHT, "", LocalDate.now(), "");
      ArrayList<Message> messageArrayList = new ArrayList<>();
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM MESSAGE_TABLE WHERE ='" + currentUser.getUsername() + "'";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
          System.out.println("Gathering Messages...");
          messageArrayList.add(
              new Message(
                  rs.getString(1),
                  LocalTime.parse(rs.getString(2)),
                  rs.getString(3),
                  LocalDate.parse(rs.getString(4)),
                  rs.getString(5)));
        }
        // STEP 4: Clean-up environment
        stmt.close();
        conn.close();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return messageArrayList;
    }

    public static void addContact(Contact contact) {
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        stmt = conn.createStatement();
        String sql =
            "INSERT INTO CONTACT_LIST(currentuser, name, username, type_of_contact)"
                + "VALUES('"
                + Main.currentUser.getUsername()
                + "','"
                + contact.getName()
                + "','"
                + contact.getUsername()
                + "','"
                + contact.getContact_type()
                + "');";

        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    public static ArrayList<Contact> getContacts() {
      ArrayList<Contact> contactArrayList = new ArrayList<>();
      String currentUsername = Main.currentUser.getUsername();
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        stmt = conn.createStatement();
        String sql = "SELECT NAME, USERNAME, TYPE_OF_CONTACT FROM CONTACT_LIST WHERE ='" + currentUsername + "'";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("Gathering Contacts...");
        while (rs.next()) {
          contactArrayList.add(
              new Contact(
                  rs.getString(2),
                  rs.getString(3),
                  rs.getString(4)));
        }
        // STEP 4: Clean-up environment
        stmt.close();
        conn.close();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return contactArrayList;
    }
  }



