package com.walkinradius.beacon.networking.model;

import com.google.gson.annotations.SerializedName;

public class Note {

    //@SerializedName("title")
    //@Expose
    private String title1;

    @SerializedName("body")
    //@Expose
    private String body;

    @SerializedName("userId")
    //@Expose
    private Integer userId;

    @SerializedName("id")
    //@Expose
    private Integer id;

    public String getTitle() {
        return title1;
    }

    public void setTitle(String title) {
        this.title1 = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title1 + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }

}
