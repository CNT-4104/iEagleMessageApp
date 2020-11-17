package sample;

public interface UserStatusListener {
  void isOnline(String email);
  void isOffline(String email);
}
