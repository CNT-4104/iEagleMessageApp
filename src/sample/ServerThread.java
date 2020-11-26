package sample;

import java.lang.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

// !!! IMPORTANT !!!
// ALL 'OUTPUTSTREAM.WRITE' *MUST* END IN '\N' !!!
// Clients are threads
// One ServerThread object is made for every client

public class ServerThread extends Thread {

  private final Socket clientSocket;
  private final Server server;
  private String email = null;
  private OutputStream outputStream;

  // CONSTRUCTOR ---------------------------------------------------------------
  public ServerThread(Server server, Socket clientSocket) {
    this.server = server;
    this.clientSocket = clientSocket;
  }

  // THREAD START --------------------------------------------------------------
  // When thread.start(); is called, this is what runs.
  @Override
  public void run() {
    try {
      manageClientSocket();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // MANAGE ACTIONS MADE BY CLIENT ---------------------------------------------
  // Allows us to have multiple threads aka clients at the same time
  private void manageClientSocket() throws IOException {
    // Along with bufferedReader, this reads commands sent from client
    InputStream inputStream = clientSocket.getInputStream();
    BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(inputStream));
    // Writes to clients
    this.outputStream = clientSocket.getOutputStream();
    String input;

    // Read commands
    while ((input = bufferedReader.readLine()) != null) {
      // Split & tokenize input to read each word of command independently
      String[] tokens = input.split(" ");

      /*       Formats of commands:
       * exit
       * signin ourEmail ourPassword
       * msg recipientEmail messageContent
       */
      if (tokens != null && tokens.length > 0) {
        // First token tells to either exit, signin, or msg
        String command = tokens[0];

        if ("exit".equalsIgnoreCase(command)) {
          manageSignOut();
          break;
        } else if ("signin".equalsIgnoreCase(command)) {
          manageSignIn(outputStream, tokens);
        } else if ("msg".equalsIgnoreCase(command)) {
          manageMessage(tokens);
        } else {
          outputStream.write("Incorrect Command\n".getBytes());
          System.out.println("Incorrect Command");
        }
      }
    }
    clientSocket.close();
  }

  // MANAGE MESSAGES -----------------------------------------------------------
  // Manage messages sent from one client to another
  private void manageMessage(String[] tokens) throws IOException {
    // Assign to appropriate Strings based on command format
    String recipient = tokens[1];
    String messageContent = null;
    // List of online users
    ArrayList<ServerThread> serverThreadAL = server.getServerThreadAL();

    // Loop to read the message contents
    // i starts at 2 since the third token is the first letter of the message
    for (int i = 2; i < tokens.length; i++) {
      messageContent += (tokens[i] + " ");
    }
    // For some reason I'm getting 'null' at the beginning of the message
    // This removes that. Just a quick fix.
    messageContent = messageContent.substring(4);

    // Iterate through every online user to find the recipient
    for (ServerThread serverThread : serverThreadAL) {
      if (recipient.equalsIgnoreCase(serverThread.getEmail())) {
        // Sending this.getEmail() because the recipient client needs to know
        // who it came from
        serverThread.send(
            "msg " + this.getEmail() + " " + messageContent + "\n");
      }
    }
  }

  // MANAGE SIGN-OUT------------------------------------------------------------
  // Notifies user that guest has signed out
  private void manageSignOut() throws IOException {
    server.removeServerThread(this); // Remove current user from online user list
    // Get list of online users
    ArrayList<ServerThread> serverThreadAL =
        server.getServerThreadAL();

    // Iterate through all online users to notify of offline status
    for (ServerThread serverThread : serverThreadAL) {
      // Don't need to know if your own account is offline
      if (!email.equalsIgnoreCase(serverThread.getEmail())) {
        serverThread.send("offline " + email + "\n");
      }
    }
    clientSocket.close();
  }

  // MANAGE SIGN-IN -----------------------------------------------------------
  private void manageSignIn(OutputStream outputStream, String[] tokens)
      throws IOException {
    // If number tokens is more than 3 then there's an unnecessary word
    if (tokens.length == 3) {
      String email = tokens[1]; // First token gives email
      String password = tokens[2]; // Third token gives password

      // Hard coded user(s)
      // TO-DO ################################################################
      // In future must check if combination of email and password present in
      // local database
      // ######################################################################
      if (Database_Accessor.verifyAccount(email, password).equals(email)) {
        this.email = email;
        outputStream.write("Sign-In Successful!\n".getBytes());
        System.out.println("Sign-In Successful: " + email);

        // List of online users
        ArrayList<ServerThread> serverThreadAL = server.getServerThreadAL();

        // For each thread aka online client in serverThreadAL
        // Remember Python for loops
        // Iterating through list of online users
        for (ServerThread serverThread : serverThreadAL) {
          if (serverThread.getEmail() != null) {
            // Don't need to know if your own account is online
            if (!email.equalsIgnoreCase(serverThread.getEmail())) {
              // Send to current client what users online
              send("online " + serverThread.getEmail() + "\n");
              // Send to other clients that you're online
              serverThread.send("online " + email + "\n");
            }
          }
        }
      } else {
        outputStream.write("Sign-In Failed!\n".getBytes());
        System.out.println("Sign-In Failed!");
      }
    }
  }

  // SEND DATA TO CLIENT -------------------------------------------------------
  // Sends client message from server
  private void send(String serverMessage) throws IOException {
    if (email != null) {
      outputStream.write(serverMessage.getBytes());
    }
  }

  // GET EMAIL -----------------------------------------------------------------
  // Retrieves email from current user
  public String getEmail() {
    return email;
  }
}
