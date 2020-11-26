package sample;

public class iMessageUser {
  String password, email, username;
  int user_id;

  public iMessageUser(String username, String password, String email) {
    this.password = password;
    this.email = email;
    this.username = username;
  }

  public iMessageUser(int user_id, String username, String password, String email) {
    this.user_id = user_id;
    this.password = password;
    this.email = email;
    this.username = username;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public iMessageUser(String username){
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
