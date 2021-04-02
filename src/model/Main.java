package model;


public class Main {


    public static void main(String[] args) {
        try {
            User user = new User("Farouk", " ", "farouk@gmail.com",new UserName("farouk"), new Password("Farouk_"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
