package com.fernandocejas.android10.sample.domain;


/**
 * Class that represents a Post in the domain layer
 */
public class Onishi {

    private final int onishiId;

    public Onishi(int onishiId) {
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
