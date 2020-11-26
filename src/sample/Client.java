package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

  //Global objects will be updated in other classes.

  private final String serverIP;
  private final int serverPort;
  private Socket socket;
  private InputStream serverIn;
  private OutputStream serverOut;
  private BufferedReader bufferedReader;
  private ArrayList<UserStatusListener> userStatusListenerArrayList =
      new ArrayList<>();
  private static ArrayList<MessageListener> messageListenerArrayList =
      new ArrayList<>();



  // CONSTRUCTOR ---------------------------------------------------------------
  public Client(String serverIP, int serverPort) {
    this.serverIP = serverIP;
    this.serverPort = serverPort;
  }

  // MAIN ----------------------------------------------------------------------
  public static void main(String[] args) throws IOException {
    // IMPORTANT ###############################################################
    // Replace 'localhost' with server's IPv4 address
    // The displayed IPv4 address is my address, yours will be different
    // Port number is pretty arbitrary, so no reason to change it
    // #########################################################################

    iMessageUser onlineUser = Main.currentiMessageUser;
    Message currentOutgoingMessage = Main.currentMessage;

    String username = "";
    String password = "";
    Client client = new Client("10.0.0.43", 139);

    // Creates listener from interface
    // Listens for whether users are online or offline and prints them
    client.addUserStatusListener(new UserStatusListener() {
      @Override
      public void isOnline(String username) {
        System.out.println(username + " is online.");
      }

      @Override
      public void isOffline(String username) {
        System.out.println(username + " is offline.");
      }
    });



    // Creates listener from interface
    // Listens for incoming messages and prints them
    client.addMessageListener(new MessageListener() {
      @Override
      public void uponReceivingMessage(String sender, String messageContent) {
        System.out.println(sender + ": " + messageContent);
      }
    });

    // If client is able to connect to the server
    if (client.connectToServer()) {
      // TO-DO #################################################################
      // Basically everything in here needs to be controlled by the GUI
      // Currently the software just asks you to input things in order

      // Scanner currently used for input
      // Replace with GUI textfield
      //Scanner scanner = new Scanner(System.in);
      username = onlineUser.getUsername();
      password = onlineUser.getPassword();



      System.out.println("Connected!");
      System.out.println("Your Username: "+  username);
      //email = scanner.nextLine();
      System.out.println("Password: "+ password);
     // password = scanner.nextLine();

      // If client.SignIn returns true (meaning sign-in was successful)
      if (client.signIn(username, password)) {
        String recipient = currentOutgoingMessage.getChat_contact();
        String messageContent = currentOutgoingMessage.getMessage_context();
        System.out.println("Sign-In Successful! :)");

        // Currently can only send one message since this isn't being
        // controlled by a GUI

        System.out.println("Send message to: " + recipient);
        //recipient = scanner.nextLine();
        System.out.println("Compose message: " + messageContent);
        //messageContent = scanner.nextLine();
        client.sendMessage(recipient, messageContent);

      } else {
        System.out.println("Incorrect email or password");
      }
      // #######################################################################
    } else {
      System.out.println("Failed to connect!");
    }
  }

  // SEND MESSAGE --------------------------------------------------------------
  // Will send message to server, which relays to recipient client
  private void sendMessage(String recipient, String messageContent)
      throws IOException {
    // Must follow format shown in manageClientSocket in ServerThread
    // msg recipientEmail messageContent
    serverOut
        .write(("msg " + recipient + " " + messageContent + "\n").getBytes());
  }

  // SIGN-OUT ------------------------------------------------------------------
  private void signOut() throws IOException {
    // Must follow format shown in manageClientSocket in ServerThread
    // exit
    serverOut.write("exit\n".getBytes());
  }

  // SIGN-IN -------------------------------------------------------------------
  private boolean signIn(String username, String password) throws IOException {
    String serverInput;

    // Must follow format shown in manageClientSocket in ServerThread
    // signin email password
    serverOut.write(("signin " + username + " " + password + "\n").getBytes());
    serverInput = bufferedReader.readLine(); // read response from server
    // If response reads "Sign-In Successful!"
    if (serverInput.equals("Sign-In Successful!")) {
      startMessageReader();
      return true;
    } else {
      return false;
    }
  }

  // READ FROM SERVER ----------------------------------------------------------
  // Reads any data send from server
  // Including: new messages, who's online, and who's offline
  private void startMessageReader() {
    Thread thread = new Thread(() -> readMessage());
    thread.start();
  }

  private void readMessage() {
    try {
      String input;
      while ((input = bufferedReader.readLine()) != null) {
        // Split and tokenize by spaces
        String[] tokens = input.split(" ");
        if (tokens != null && tokens.length > 0) {
          // According to format, first word always holds desired action
          String command = tokens[0];
          // If incoming message from server starts with "online" then it's
          // telling you that a certain user is online
          if ("online".equalsIgnoreCase(command)) {
            manageOnline(tokens);
          }
          // If incoming message from server starts with "offline" then it's
          // telling you that a certain user us offline
          else if ("offline".equalsIgnoreCase(command)) {
            manageOffline(tokens);
          }
          // If incoming message from server starts with "msg" then it's
          // telling you a user has sent you a message
          else if ("msg".equalsIgnoreCase(command)) {
            manageMessage(tokens);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      try {
        socket.close();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
  }

  // MANAGE INCOMING MESSAGES FROM USERS ---------------------------------------
  private void manageMessage(String[] tokens) {
    String password = Main.currentiMessageUser.getPassword();
    String username = Main.currentiMessageUser.getUsername();
    String messageContent = null;

    // i starts at 2 since the third token is the first letter of the message
    for (int i = 2; i < tokens.length; i++) {
      messageContent += (tokens[i] + " ");
    }
    // For some reason I'm getting 'null' at the beginning of the message
    // This removes that. Just a quick fix.
    messageContent = messageContent.substring(4);

    // Send message to each listener
    // tokens[1] is email (remember format is: msg email messageContent)
    for (MessageListener listener : messageListenerArrayList) {
      listener.uponReceivingMessage(tokens[1], messageContent);
    }
  }

  // MANAGE ONLINE/OFFLINE STATUS ----------------------------------------------
  private void manageOffline(String[] tokens) {
    String username = tokens[1];
    for (UserStatusListener listener : userStatusListenerArrayList) {
      listener.isOffline(username);
    }
  }

  private void manageOnline(String[] tokens) {
    String username = tokens[1];
    for (UserStatusListener listener : userStatusListenerArrayList) {
      listener.isOnline(username);
    }
  }

  // CONNECT TO SERVER ---------------------------------------------------------
  private boolean connectToServer() {
    // If can connect: return true
    // If can't connect: return false
    try {
      this.socket = new Socket(serverIP, serverPort);
      this.serverIn = socket.getInputStream();
      this.serverOut = socket.getOutputStream();
      this.bufferedReader = new BufferedReader(new InputStreamReader(serverIn));
      System.out.println("Client Port: " + socket.getLocalPort());
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    // It's only get here if the try fails (aka isn't able to connect)
    return false;
  }

  // ADD & REMOVE LISTENERS ----------------------------------------------------
  // User Status
  public void addUserStatusListener(UserStatusListener listener) {
    userStatusListenerArrayList.add(listener);
  }
  public void removeUserStatusListener(UserStatusListener listener) {
    userStatusListenerArrayList.remove(listener);
  }

  // Message
  public void addMessageListener(MessageListener listener) {
    messageListenerArrayList.add(listener);

  }
  public void removeMessageListener(MessageListener listener) {
    messageListenerArrayList.remove(listener);
  }
}
