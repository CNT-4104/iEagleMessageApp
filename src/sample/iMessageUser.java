package sample;

public class iMessageUser {
  String firstName, lastName, username;
  public iMessageUser(String firstName, String lastName, String username){
    this.firstName = getFirstName();
    this.lastName = getLastName();
    this.username = getUsername();
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getUsername() {
    return username;
  }




}
