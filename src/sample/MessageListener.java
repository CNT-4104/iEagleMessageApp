package sample;

public interface MessageListener {
  void uponReceivingMessage(String sender, String messageContent);
}
