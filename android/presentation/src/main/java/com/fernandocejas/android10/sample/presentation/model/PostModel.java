package com.fernandocejas.android10.sample.presentation.model;

public class PostModel {
    private final int postId;

    public PostModel(int postId) {
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
