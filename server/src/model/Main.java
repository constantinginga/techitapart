package model;

import persistence.AccountDB;

public class Main {
    public static void main(String[] args) {
        Model model = new ModelManager();
        System.out.println(model.login("Farouk12","Farouk_12"));
        AccountDB accountDB = new AccountDB();

    }
}
