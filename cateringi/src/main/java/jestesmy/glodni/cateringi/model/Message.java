package jestesmy.glodni.cateringi.model;

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
    @JoinColumn(name="clientID")
    @MapsId
    @JsonIgnore
    private User to;

    @ManyToOne
    @JoinColumn(name="clientID")
    @MapsId
    @JsonIgnore
    private User from;

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
