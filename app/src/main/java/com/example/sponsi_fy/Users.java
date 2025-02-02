package com.example.sponsi_fy;
class Users{
String name,number,email,username,pass;

public Users() {

}

public Users(String name, String number, String email, String username, String pass) {
    this.name = name;
    this.number = number;
    this.email = email;
    this.username = username;
    this.pass = pass;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getNumber() {
    return number;
}

public void setNumber(String number) {
    this.number = number;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
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
}
