package model;


import java.io.Serializable;
import java.util.regex.Pattern;

public class User implements Serializable
{
  private String fName;
  private String lName;
  private UserName userName;
  private Password password;
  private String email;
  private Role role;
  private OrderList orderList;

  private static final String regex = "^(.+)@(.+)$";


  public User(String fName,String lName,String email, UserName userName, Password password)
  {
    setlName(lName);
    setfName(fName);
    setEmail(email);
    setPassword(password);
    setUserName(userName);
    this.role = Role.Consumer;
    orderList = new OrderList();
  }


  public void addOrder(Order order){
    orderList.addOrder(order);
  }

  public Order getOrderById(int id){
    return orderList.getOrder(id);
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

  public void setUserName(UserName userName) {
    if (userName == null){
      throw new IllegalArgumentException("username is null");
    }
    this.userName = userName;
  }

  public void setPassword(Password password) {
    if (password == null){
      throw new IllegalArgumentException("password is null");
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

  public void setRole(Role role){
    this.role = role;
  }

  public boolean isAdmin(){
    return Role.Admin == role;
  }

  public String getlName() {
    return lName;
  }

  public String getEmail() {
    return email;
  }


  public Role getRole() {
    return role;
  }

  public UserName getUserName()
  {
    return userName;
  }

  public Password getPassword()
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
