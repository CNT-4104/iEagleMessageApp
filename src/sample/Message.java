package sample;

import java.time.LocalDate;
import java.time.LocalTime;


public class Message {
  String message_type;
  LocalTime time_of_message;
  String chat_contact;
  LocalDate date;
  String message_context;
  int message_id;



  public Message(String message_type, LocalTime time_of_message, String chat_contact,
      LocalDate date, String message_context, int message_id) {
    this.message_type = message_type;
    this.time_of_message = time_of_message;
    this.chat_contact = chat_contact;
    this.date = date;
    this.message_context = message_context;
    this.message_id = message_id;
  }

  public int getMessage_id() {
    return message_id;
  }

  public void setMessage_id(int message_id) {
    this.message_id = message_id;
  }

  public String getMessage_type() {
    return message_type;
  }

  public LocalTime getTime_of_message() {
    return LocalTime.now();
  }

  public String getChat_contact() {
    return chat_contact;
  }

  public LocalDate getDate() {
    return LocalDate.now();
  }

  public String getMessage_context() {
    return message_context;
  }

  public void setMessage_type(String message_type) {
    this.message_type = message_type;
  }

  public void setTime_of_message(LocalTime time_of_message) {
    this.time_of_message = time_of_message;
  }

  public void setChat_contact(String chat_contact) {
    this.chat_contact = chat_contact;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setMessage_context(String message_context) {
    this.message_context = message_context;
  }
}
