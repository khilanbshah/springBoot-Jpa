package com.example.vne.model;

import javax.persistence.*;

@Entity
public class Vne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String host;
    String user;
    String password;

    public Vne(){
    }

    public Vne(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Vne{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
