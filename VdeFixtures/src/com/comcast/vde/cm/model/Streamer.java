package com.comcast.vde.cm.model;

public class Streamer {

    private String id;
    private Integer rev;
    private Data data;
    private String revTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRev() {
        return rev;
    }

    public void setRev(Integer rev) {
        this.rev = rev;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getRevTime() {
        return revTime;
    }

    public void setRevTime(String revTime) {
        this.revTime = revTime;
    }



}
