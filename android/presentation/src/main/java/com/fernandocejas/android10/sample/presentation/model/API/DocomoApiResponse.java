package com.fernandocejas.android10.sample.presentation.model.API;

import android.content.SharedPreferences;
import javax.annotation.Generated;

/**
 * Created by apple on 2017/07/29.
 */

@Generated("org.jsonschema2pojo")
public class DocomoApiResponse {

    private String utt;
    private String yomi;
    private String mode;
    private String da;
    private String context;

    public String getUtt()
    {
        return utt;
    }
    public String getYomi(){
        return yomi;
    }
    public String getMode(){
        return mode;
    }
    public String getDa(){
        return da;
    }
    public String getContext(){
        return context;
    }

    public void setUtt(String utt){
        this.utt = utt;
    }
    public void setYomi(String yomi){this.yomi = yomi;}
    public void setMode(String mode){this.mode = mode;}
    public void setDa(String da){this.da = da;}
    public void setContext(String context){this.context = context;}
}