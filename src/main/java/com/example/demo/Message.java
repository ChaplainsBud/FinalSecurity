package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
//    @Size(min = 3)
    private String content;

    //    private String postedDate;
    @NotNull
//    @Size(min = 3)
    private String title;

    private String postedBy;

    private String pic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public Message(@NotNull String content, @NotNull String title, String postedBy) {
        this.content = content;
        this.title = title;
        this.postedBy = postedBy;
    }

    public Message(@NotNull String content, @NotNull String title, String pic, String postedBy) {
        this.content = content;
        this.title = title;
        this.pic = pic;
        this.postedBy = postedBy;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Message() {

    }
//    public Message(@NotNull @Size(max = 280) String content, Date postedDate,
//                   @NotNull @Size(min = 2) String sentBy) {
//        this.content = content;
//        this.postedDate = postedDate;
//        this.sentBy = sentBy;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
