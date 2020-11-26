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


    //Searches the database for the users credentials that were inputted, and verifies credentials.
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

  public static iMessageUser getiMessageUser(String currentUsername) {
    iMessageUser currentUser = new iMessageUser(0,"", "", "");
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql = "SELECT * FROM USER_INFO WHERE USERNAME='" + currentUsername + "'";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        currentUser = new iMessageUser(
            Integer.parseInt(rs.getString(1)),    //user_id
            rs.getString(2),       //username
            rs.getString(3),       //password
            rs.getString(4));      //email
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

    //Adds a message to the database when a message object is passed.
    public static void addMessage(Message message) {
      try {
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql =
            "INSERT INTO MESSAGE_TABLE(CURRENT_USER_ID, MESSAGE_TYPE, TIME_OF_MESSAGE, CHAT_CONTACT, DATE_OF_MESSAGE, MESSAGE_CONTEXT)"
                + "VALUES('"
                +Main.currentiMessageUser.getUser_id()
                +"','"
                + message.getMessage_type()
                + "','"
                + message.getTime_of_message().toString()
                + "','"
                + message.getChat_contact()
                + "','"
                + message.getDate().toString()
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


  public static iMessageUser lookup_iMessage_user(int currentUserID) {
    iMessageUser currentUser = new iMessageUser(0,"", "", "");
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql = "SELECT * FROM USER_INFO WHERE USER_ID='" + currentUserID + "'";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        currentUser = new iMessageUser(
            Integer.parseInt(rs.getString(1)),    //user_id
            rs.getString(2),       //username
            rs.getString(3),       //password
            rs.getString(4));      //email
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


    public static void updateMessageType(int messageID, String type){
      Connection conn = null;
      Statement stmt = null;
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, user, pass);

        stmt = conn.createStatement();
      String sql =
          "UPDATE MESSAGE_TABLE "
              + " SET MESSAGE_TYPE = '"
              + type
              + "'"
              + " WHERE MESSAGE_ID = '" + messageID + "'";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

  //When a contact is selected to begin a chat with, this will get the messages
    public static ArrayList<Message> getLiveMessage(String chatContact, String currentUsername){
      Message message = new Message(0,0,"",
          LocalTime.MIDNIGHT, "", LocalDate.now(), "");
      ArrayList<Message> messageArrayList = new ArrayList<>();
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql ="SELECT * FROM MESSAGE_TABLE WHERE CHAT_CONTACT = '"
            + chatContact
            + "' OR CHAT_CONTACT='"
            + currentUsername
            + "'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
          messageArrayList.add(new Message(Integer.parseInt(rs.getString(1)),  //message_id
              Integer.parseInt(rs.getString(2)),        //current_user_id
              rs.getString(3),                     //type
              LocalTime.parse(rs.getString(4)),      //time_of_message
              rs.getString(5),      //chat_contact
              LocalDate.parse(rs.getString(6)),  //date_of_message
              rs.getString(7)          //message_content
              ));
        }
        stmt.close();
        conn.close();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return messageArrayList;
    }

    //Gets a list of unread messages for the current user from the database.
  public static ArrayList<Message> getUnreadMessage(int userID){
    String unread = "unread";
    Message message = new Message(0,0,"",
        LocalTime.MIDNIGHT, "", LocalDate.now(), "");
    ArrayList<Message> unreadMessagesList = new ArrayList<>();
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql ="SELECT * FROM MESSAGE_TABLE WHERE MESSAGE_TYPE = '"
          + unread
          + "' AND CURRENT_USER_ID='"
          + Main.currentiMessageUser.getUser_id()
          + "'";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()){
        unreadMessagesList.add(new Message(Integer.parseInt(rs.getString(1)),  //message_id
            Integer.parseInt(rs.getString(2)),        //current_user_id
            rs.getString(3),                     //type
            LocalTime.parse(rs.getString(4)),      //time_of_message
            rs.getString(5),      //chat_contact
            LocalDate.parse(rs.getString(6)),  //date_of_message
            rs.getString(7)          //message_content
        ));
      }
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return unreadMessagesList;
  }


  //Gets the messages the current user has deleted
  public static ArrayList<Message> getDeletedMessage(int userID){
    String deleted = "deleted";
    Message message = new Message(0,0,"",
        LocalTime.MIDNIGHT, "", LocalDate.now(), "");
    ArrayList<Message> deletedMessagesList = new ArrayList<>();
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql ="SELECT * FROM MESSAGE_TABLE WHERE MESSAGE_TYPE = '"
          + deleted
          + "' AND CURRENT_USER_ID='"
          + Main.currentiMessageUser.getUser_id()
          + "'";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()){
        deletedMessagesList.add(new Message(Integer.parseInt(rs.getString(1)),  //message_id
            Integer.parseInt(rs.getString(2)),        //current_user_id
            rs.getString(3),                     //type
            LocalTime.parse(rs.getString(4)),      //time_of_message
            rs.getString(5),      //chat_contact
            LocalDate.parse(rs.getString(6)),  //date_of_message
            rs.getString(7)          //message_content
        ));
      }
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return deletedMessagesList;
  }


  // Adds a contact to the database in the current user's CONTACT_LIST database
    public static void addContact(Contact contact) {
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql =
            "INSERT INTO CONTACT_LIST(CURRENT_USER_ID, name, username, type_of_contact)"
                + "VALUES('"
                + Main.currentiMessageUser.getUser_id()
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

    public static ArrayList<Contact> getContacts(int currentUserID) {
      ArrayList<Contact> contactArrayList = new ArrayList<>();
      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, user, pass);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM CONTACT_LIST WHERE CURRENT_USER_ID='" + currentUserID + "'";
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



