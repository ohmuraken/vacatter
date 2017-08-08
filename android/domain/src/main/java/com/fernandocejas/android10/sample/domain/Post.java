package com.fernandocejas.android10.sample.domain;


/**
 * Class that represents a Post in the domain layer
 */
public class Post {

    private final int postId;

    public Post(int postId) {
        this.postId = postId;
    }

    private String message;

    public int getPostId() {
        return this.postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
