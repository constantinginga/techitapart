package model;

import java.util.ArrayList;

public class UserList
{
    private ArrayList<User> users;


    public UserList()
    {
        this.users = new ArrayList<>();
    }


    public ArrayList<User> getUsers(){
        return users;
    }


    public int size()
    {
        return users.size();
    }


    public User getUser(int index)
    {
        return users.get(index);
    }

    public User getUserByName(String name)
    {
        for (int i = 0; i < users.size(); i++)
        {
            if (users.get(i).getUserName().getName().equalsIgnoreCase(name))
            {
                return users.get(i);
            }
        }
        return null;
    }


    public void addUser(User user)
    {
        users.add(user);
    }



    public void addUser(String fName, String lName, String email, UserName userName, Password password)
    {
        if (!contains(new User(fName, lName, email, userName, password))) {

            addUser(new User(fName, lName, email, userName, password));

        }else {
            throw new IllegalArgumentException("User already exist");
        }

    }


    public boolean contains(User user)
    {
        if (user == null)
        {
            return false;
        }
        return getUserByName(user.getUserName().getName()) != null;
    }


    @Override public String toString()
    {
        return "UserList{" + "users=" + users + '}';
    }


}
