package com.fernandocejas.android10.sample.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Post Entity used in the data layer.
 */
public class PostEntity {

    @SerializedName("id")
    private int postId;

    @SerializedName("message")
    private String message;

    public PostEntity() {
        //empty
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message;}
}
