package com.comcast.vde.cm.model;


public class Audio {

    private String bufferDelay;
    private String representationId;
    private String adaptationSetId;
    private Boolean enable;
    private Integer audioIndex;

    public String getBufferDelay() {
        return bufferDelay;
    }

    public void setBufferDelay(String bufferDelay) {
        this.bufferDelay = bufferDelay;
    }

    public String getRepresentationId() {
        return representationId;
    }

    public void setRepresentationId(String representationId) {
        this.representationId = representationId;
    }

    public String getAdaptationSetId() {
        return adaptationSetId;
    }

    public void setAdaptationSetId(String adaptationSetId) {
        this.adaptationSetId = adaptationSetId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getAudioIndex() {
        return audioIndex;
    }

    public void setAudioIndex(Integer audioIndex) {
        this.audioIndex = audioIndex;
    }

    
}
