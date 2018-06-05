package com.comcast.vde.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Ebif {

    private Integer programNumber;
    private String srcUrl;
    private Integer dpiPid;
    private String timingMode;
    private Boolean enable;
    private String delay;
    private String bandwidthConfigName;
    private List<ElementaryStreams> elementaryStreams = new ArrayList<ElementaryStreams>();

    public Integer getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(Integer programNumber) {
        this.programNumber = programNumber;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public Integer getDpiPid() {
        return dpiPid;
    }

    public void setDpiPid(Integer dpiPid) {
        this.dpiPid = dpiPid;
    }

    public String getTimingMode() {
        return timingMode;
    }

    public void setTimingMode(String timingMode) {
        this.timingMode = timingMode;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getBandwidthConfigName() {
        return bandwidthConfigName;
    }

    public void setBandwidthConfigName(String bandwidthConfigName) {
        this.bandwidthConfigName = bandwidthConfigName;
    }

    public List<ElementaryStreams> getElementaryStreams() {
        return elementaryStreams;
    }

    public void setElementaryStreams(List<ElementaryStreams> elementaryStreams) {
        this.elementaryStreams = elementaryStreams;
    }



}
