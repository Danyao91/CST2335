package com.example.androidlabs;

public class Message {
    private String message;
    private Role role;

    Message(String message, Role role) {
        this.message = message;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    enum Role {
        SEND, RECIEVE
    }
}
