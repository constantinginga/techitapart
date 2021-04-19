package model;

public enum Role {
    Consumer("Consumer"),Admin("Admin");
    private String roleType;

    private Role(String roleType){
        this.roleType = roleType;
    }

    public String getRole(){
        return roleType;
    }
}
