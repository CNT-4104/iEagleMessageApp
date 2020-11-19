package sample;

public class Contact {

  String Name;
  String Username;
  String contact_type;

  public Contact(String name, String username, String contact_type) {
    Name = name;
    Username = username;
    this.contact_type = contact_type;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getUsername() {
    return Username;
  }

  public void setUsername(String username) {
    Username = username;
  }

  public String getContact_type() {
    return contact_type;
  }

  public void setContact_type(String contact_type) {
    this.contact_type = contact_type;
  }
}
