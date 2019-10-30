package jestesmy.glodni.cateringi.model;

import javax.persistence.*;

@Entity
@Table(name = "Messages")
public class Messages {

    @Id
    @Column(name = "MessageID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int messageID;

    @ManyToOne
    @JoinColumn(name = "CompanyDataID")
    private CompanyData companyData;

    @ManyToOne
    @JoinColumn(name = "UserDataID")
    private UserData userData;

    private String subject;

    private String description;

    public Messages() { }

    public Messages(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public CompanyData getCompanyData() {
        return companyData;
    }

    public void setCompanyData(CompanyData companyData) {
        this.companyData = companyData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
