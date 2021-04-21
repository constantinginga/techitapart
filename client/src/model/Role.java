package model;

import java.io.Serializable;

public enum Role implements Serializable {
    Consumer("Consumer"),Admin("Admin");
    private String roleType;

    private Role(String roleType){
        this.roleType = roleType;
    }

    public String getRole(){
        return roleType;
    }
}
