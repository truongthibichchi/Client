package connection;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String pass;
    private String nickname;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User(String username, String nickname){
        this.username=username;
        this.nickname=nickname;
    }


    public User(String username, String pass, String nickname){
        this.username=username;
        this.pass=pass;
        this.nickname=nickname;
    }

    public User(String username, String pass, String nickname, Status status) {
        this.username = username;
        this.pass = pass;
        this.nickname = nickname;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}