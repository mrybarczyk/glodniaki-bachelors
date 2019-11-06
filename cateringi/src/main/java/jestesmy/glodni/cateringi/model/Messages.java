package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Messages")
public class Messages {

    @Id
    @Column(name = "MessageID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int messageID;

    @ManyToOne
    @JoinColumn(name = "Sender")
    private Accounts sender;

    @ManyToOne
    @JoinColumn(name = "Receiver")
    private Accounts receiver;

    private String subject;

    private String description;

    private Timestamp dateSent;

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

    public Accounts getSender() {
        return sender;
    }

    public void setSender(Accounts sender) {
        this.sender = sender;
    }

    public Accounts getReceiver() {
        return receiver;
    }

    public void setReceiver(Accounts receiver) {
        this.receiver = receiver;
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

    public Timestamp getDateSent() {
        return dateSent;
    }

    public void setDateSent(Timestamp dateSent) {
        this.dateSent = dateSent;
    }
}
