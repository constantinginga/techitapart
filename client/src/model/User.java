package model;


import java.io.Serializable;
import java.util.regex.Pattern;

public class User implements Serializable
{
  private String fName;
  private String lName;
  private String userName;
  private String password;
  private String email;
  private String role;

  private static final String regex = "^(.+)@(.+)$";


  public User(String fName,String lName,String email, String userName, String password)
  {
    setlName(lName);
    setfName(fName);
    setEmail(email);
    setPassword(password);
    setUserName(userName);
    this.role = "consumer";

  }

  public User(String userName, String role) {
    this.userName = userName;
    this.role = role;
  }


  public void setfName(String fName) {
    if (fName == null || fName.equals("")){
      throw new  IllegalArgumentException("Please enter first name");
    }
    this.fName = fName;
  }


  public void setlName(String lName) {
    if (lName == null || lName.equals("")){
      throw new IllegalArgumentException("Please enter last name");
    }
    this.lName = lName;
  }

  public void setUserName(String userName) {
    if (userName == null){
      throw new IllegalArgumentException("Please enter username");
    }
    this.userName = userName;
  }

  public void setPassword(String password) {
    if (password == null){
      throw new IllegalArgumentException("Please enter password");
    }
    this.password = password;
  }


  public void setEmail(String email) {
    Pattern pattern = Pattern.compile(regex);

    if (email == null) {
      throw new IllegalArgumentException("Please enter email address");
    }

    if(!pattern.matcher(email).matches()){
      throw new IllegalArgumentException("Invalid email address");
    }
    this.email = email;
  }

  public void setRole(String role){
    this.role = role;
  }

  public boolean isAdmin(){
    return role.equalsIgnoreCase("admin");
  }

  public String getlName() {
    return lName;
  }

  public String getEmail() {
    return email;
  }


  public String  getRole() {
    return role;
  }

  public String getUsername()
  {
    return userName;
  }

  public String getPassword()
  {
    return password;
  }

  public String getfName() {
    return fName;
  }

  public String toString()
  {
    return userName + ", password = " + password + ", " ;
  }

}
