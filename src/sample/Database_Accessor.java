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
    private static String JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:C:/Users/feesh/OneDrive/intelliJCOP/iEagleMessageApp_2/res/iEagle_Database";
    private final static String pass = "";
    private final static String user = "";
    private static Connection conn = null;
    private static Statement stmt = null;


  public static String verifyAccount(String username, String password) {
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();

      String sql =
          "SELECT USERNAME FROM USER_INFO WHERE USERNAME = '"
              + username
              + "' AND PASSWORD='"
              + password
              + "'";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        username = rs.getString(1);
        System.out.println("Found a user\nusername: " + username);
      } else {
        username = "not found";
        System.out.println("User not found\nUSERNAME: null");
      }

      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return username;
  }

  public static iMessageUser getiMessageUser(String username) {
    iMessageUser currentUser = new iMessageUser("", " ", "");
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql = "SELECT * FROM USER_INFO WHERE USERNAME='" + username + "'";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        currentUser = new iMessageUser(rs.getString(1), rs.getString(2), rs.getString(3));
      } else {
        System.out.println("No account was found");
      }
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return currentUser;
    }

    public static void addMessage(Message message) {
      try {
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql =
            "INSERT INTO MESSAGE_TABLE(MESSAGE_TYPE, TIME_OF_MESSAGE, CHAT_CONTACT, DATE_OF_MESSAGE, MESSAGE_CONTEXT)"
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
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    public static void updateMessageContent(String messageContent){

      Connection conn = null;
      Statement stmt = null;
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, user, pass);

        stmt = conn.createStatement();
        String sql =
            "UPDATE MESSAGE_TABLE "
                + " SET MESSAGE_CONTEXT = '" + messageContent + "'"
                + " WHERE MESSAGE_ID = (SELECT max(MESSAGE_ID)from MESSAGE_TABLE)";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }


    public static Message getLiveMessage(){
      Message message = new Message("", LocalTime.MIDNIGHT, "", LocalDate.now(), "", 0);
      ArrayList<Message> messageArrayList = new ArrayList<>();
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM MESSAGE_TABLE WHERE MESSAGE_ID = (SELECT max(MESSAGE_ID)from MESSAGE_TABLE)";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
          message = new Message(                  rs.getString(2),
              LocalTime.parse(rs.getString(3)),
              rs.getString(4),
              LocalDate.parse(rs.getString(5)),
              rs.getString(6),
              rs.getInt(1));
        }
        stmt.close();
        conn.close();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return message;
    }


    public static ArrayList<Message> getMessages(String currentUser) {
      Message message = new Message("", LocalTime.MIDNIGHT, "", LocalDate.now(), "", 0);
      ArrayList<Message> messageArrayList = new ArrayList<>();
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM MESSAGE_TABLE WHERE CHAT_CONTACT='" + currentUser + "'";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
          System.out.println("Gathering Messages...");
          messageArrayList.add(
              new Message(
                  rs.getString(2),
                  LocalTime.parse(rs.getString(3)),
                  rs.getString(4),
                  LocalDate.parse(rs.getString(5)),
                  rs.getString(6),
                  rs.getInt(1)));
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
        conn = DriverManager.getConnection(DB_URL, user, pass);
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
      String currentUsername = "bjrhodes855";
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM CONTACT_LIST WHERE CURRENTUSER='" + currentUsername + "'";
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

    public static void deleteContact(String contact_username){
      Connection conn = null;
      Statement stmt = null;
      try{
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql = "DELETE FROM CONTACT_LIST WHERE USERNAME='"+contact_username+"'";
        stmt.executeUpdate(sql);
        System.out.println("Contact "+ contact_username + " has been deleted");
        stmt.close();
        conn.close();


      }catch(SQLException e){
        e.printStackTrace();
      }
    }

  }



