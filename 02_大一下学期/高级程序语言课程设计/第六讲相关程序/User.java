package SocketTest;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String password;
 
    public User(int id, String name, String password) {
    super();
    this.id = id;
    this.name = name;
    this.password = password;
    }
 
    @Override
    public String toString() {
    return "User [id=" + id + ", name=" + name + ", password=" + password+ "]";
    }
 
}
