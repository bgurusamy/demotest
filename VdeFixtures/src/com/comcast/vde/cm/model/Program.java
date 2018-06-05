package com.comcast.vde.cm.model;

import java.util.ArrayList;
import java.util.List;


public class Program {

    private Integer programNumber;
    private Integer programIndex;
    private Input input;
    private List<Audio> audio = new ArrayList<Audio>();
    private Boolean enable;
    private Decryption decryption;
    private Ads ads;
    private Integer maxRate;
    private Video video;
    private String programName;
    private Ebif ebif;

    public Integer getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(Integer programNumber) {
        this.programNumber = programNumber;
    }

    public Integer getProgramIndex() {
        return programIndex;
    }

    public void setProgramIndex(Integer programIndex) {
        this.programIndex = programIndex;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public List<Audio> getAudio() {
        return audio;
    }

    public void setAudio(List<Audio> audio) {
        this.audio = audio;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Decryption getDecryption() {
        return decryption;
    }

    public void setDecryption(Decryption decryption) {
        this.decryption = decryption;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public Integer getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(Integer maxRate) {
        this.maxRate = maxRate;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Ebif getEbif() {
        return ebif;
    }

    public void setEbif(Ebif ebif) {
        this.ebif = ebif;
    }



}
