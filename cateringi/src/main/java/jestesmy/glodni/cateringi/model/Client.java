package jestesmy.glodni.cateringi.model;

import javax.persistence.*;

@Entity
@Table(name = "Clients")
public class Client {

    @Id
    private int clientID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

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
}
