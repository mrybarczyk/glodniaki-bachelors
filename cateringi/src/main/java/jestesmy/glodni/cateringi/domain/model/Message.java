package jestesmy.glodni.cateringi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageID;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name="userId")
    @MapsId
    @JsonIgnore
    private User to;

    @ManyToOne
    @JoinColumn(name="userId")
    @MapsId
    @JsonIgnore
    private User from;

    // czy nadawca usunął swoją kopię wiadomości?
    //@Column(nullable = false)
    //private boolean deletedFrom;

    // czy odbiorca usunął swoją kopię wiadomości?
    //@Column(nullable = false)
    //private boolean deletedTo;

    //public boolean isDeletedFrom() {
    //    return deletedFrom;
    //}

    //public void setDeletedFrom(boolean deletedFrom) {
     //   this.deletedFrom = deletedFrom;
    //}

    //public boolean isDeletedTo() {
    //    return deletedTo;
    //}

    //public void setDeletedTo(boolean deletedTo) {
    //    this.deletedTo = deletedTo;
    //}

    // Jeżeli usunęli ją zarówno odbiorca jak i nadawca to można wywalić z bazy?
    //public boolean isDeletedOnBothSides(){
    //    return this.deletedFrom && this.deletedTo;
    //}

    public Message(){ }

    public Message(String subject, String contents, User to, User from) {
        this.subject = subject;
        this.contents = contents;
        this.to = to;
        this.from = from;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }
}
