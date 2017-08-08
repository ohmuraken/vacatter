package com.fernandocejas.android10.sample.presentation.model;

public class OnishiModel {
    private final int onishiId;

    public OnishiModel(int onishiId) {
        this.onishiId = onishiId;
    }

    private String message;

    public int getOnishiId() {
        return this.onishiId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
