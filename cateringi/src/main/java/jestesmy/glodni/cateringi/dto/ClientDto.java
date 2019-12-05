package jestesmy.glodni.cateringi.dto;

public class ClientDto {

    private int clientID;

    private String name;

    private String lastName;

    private int userID;

    public ClientDto() { }

    public ClientDto(int clientID, String name, String lastName, int userID) {
        this.clientID = clientID;
        this.name = name;
        this.lastName = lastName;
        this.userID = userID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

