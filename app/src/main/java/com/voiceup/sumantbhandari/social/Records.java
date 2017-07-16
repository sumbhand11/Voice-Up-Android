package com.voiceup.sumantbhandari.social;

/**
 * Created by sumantbhandari on 12/23/16.
 */

public class Records {

    private  String Recording;
    private int Topic;
    private String Uid;
    private Long NumberLikes;
    private Long duration;

    public Records(){

    }

    public Records(String Recording, int Topic,  String Uid, Long NumberLikes, Long duration){
        this.Recording = Recording;
        this.Topic = Topic;
        this.Uid = Uid;
        this.NumberLikes = NumberLikes;
        this.duration = duration;

    }

    public String getRecording() {
        return Recording;
    }

    public void setRecording(String recording) {
        Recording = recording;
    }

    public int getTopic() {
        return Topic;
    }

    public void setTopic(int topic) {
        Topic = topic;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {Uid = uid;}

    public Long getNumberLikes() { return NumberLikes; }

    public void setNumberLikes(Long NumberLikes) {
        NumberLikes = NumberLikes;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
