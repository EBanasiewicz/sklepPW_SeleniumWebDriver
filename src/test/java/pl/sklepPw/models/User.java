package pl.sklepPw.models;

import pl.sklepPw.utils.PropertiesLoader;

import java.io.IOException;

public class User {

    //w przypadku braku pliku userdata.properties w folderze resources należy go utworzyć, a następnie dodać:
    //username = {login}
    //password = {hasło}

    private String emailAddress = PropertiesLoader.loadData("username");

    private String password = PropertiesLoader.loadData("password");
    ;

    public User() throws IOException {
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
