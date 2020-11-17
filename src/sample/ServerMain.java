package sample;

public class ServerMain {
  public static void main(String[] args) {
    // Make sure your Windows Firewall has inbound and outbound rules accepting
    // connections from socket 6177.
    // Also make sure that Telnet is activated

    int port = 6177; // Which socket you choose is arbitrary I'm pretty sure
    Server server = new Server(port);
    server.start(); // Runs code that's in 'run', aka starts server
  }
}
