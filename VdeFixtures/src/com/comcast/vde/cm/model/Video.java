package com.comcast.vde.cm.model;


public class Video {

    private String bufferDelay;
    private String representationId;
    private String adaptationSetId;

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



}
