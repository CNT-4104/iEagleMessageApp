package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {

  private final int serverPort;
  // I tried using an array but it didn't work out, so I used ArrayList
  // Basically holds list of threads aka online users
  private ArrayList<ServerThread> serverThreadAL = new ArrayList<>();

  // CONSTRUCTOR ---------------------------------------------------------------
  public Server(int serverPort) {
    this.serverPort = serverPort;
  }

  // THREAD START --------------------------------------------------------------
  @Override
  public void run() {
    try {
      ServerSocket serverSocket = new ServerSocket(serverPort);

      // Infinite loop because we're always accepting new clients
      while (true) {
        // Accept new client connection
        System.out.println("Accepting new clients");
        Socket clientSocket = serverSocket.accept();
        System.out.println(serverThreadAL.size());
        System.out.println("Client connected: " + clientSocket);
        ServerThread serverThread = new ServerThread(this, clientSocket);
        serverThreadAL.add(serverThread); // Adds new client to list
        System.out.println(serverThreadAL.size());
        serverThread.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // REMOVE USER ---------------------------------------------------------------
  // Remove offline user from online user list
  public void removeServerThread(ServerThread serverThread) {
    serverThreadAL.remove(serverThread);
  }

  // GET LIST OF ONLINE USERS --------------------------------------------------
  public ArrayList<ServerThread> getServerThreadAL() {
    return serverThreadAL;
  }
}
