package com.voiceup.sumantbhandari.social;

/**
 * Created by sumantbhandari on 12/27/16.
 */

public class Topic {

    private String Description;
    private int ID;
    private String Rules;
    private String Text;
    private int max_time;
    private int min_time;



    public Topic(){

    }

    public Topic(String Description, int ID, String Rules,  String Text, int max_time, int min_time){
        this.Description = Description;
        this.Rules = Rules;
        this.Text = Text;
        this.ID = ID;
        this.min_time = min_time;
        this.max_time = max_time;

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRules() {
        return Rules;
    }

    public void setRules(String rules) {
        Rules = rules;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getMax_time() {
        return max_time;
    }

    public void setMax_time(int max_time) {
        this.max_time = max_time;
    }

    public int getMin_time() {
        return min_time;
    }

    public void setMin_time(int min_time) {
        this.min_time = min_time;
    }
}
