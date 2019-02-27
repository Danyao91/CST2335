package com.example.androidlabs;

public class Message {
    private String message;
    //private Role role;
    private long id;
    private boolean isSent;

    Message(long id, String message, boolean isSent) {
        this.message = message;
        //this.role = role;
        this.id = id;
        this.isSent = isSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public boolean getIsSent(){
        return isSent;
    }
    public void setIsSent(boolean isSent){
        this.isSent = isSent;
    }

/*
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
*/

 /*   enum Role {
        SEND, RECIEVE
    }*/
}
