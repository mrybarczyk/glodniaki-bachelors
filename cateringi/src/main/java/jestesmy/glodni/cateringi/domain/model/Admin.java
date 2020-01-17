package jestesmy.glodni.cateringi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Admins")
public class Admin {
    @Id
    private int adminID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @OneToOne
    @JoinColumn(name="adminID")
    @MapsId
    @JsonIgnore
    User user;

    public Admin() {
    }

    public Admin(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
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
