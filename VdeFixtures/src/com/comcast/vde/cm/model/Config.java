package com.comcast.vde.cm.model;

import java.util.ArrayList;
import java.util.List;


public class Config {

    private String segmentDuration;
 private List<Streams> stream = new ArrayList<Streams>();
    private String fanoutProxy;
    private List<Program> program = new ArrayList<Program>();
    private String networkLatency;
    private String systemId;
    private Output output;
    private Scte30Server scte30Server;

    public String getSegmentDuration() {
        return segmentDuration;
    }

    public void setSegmentDuration(String segmentDuration) {
        this.segmentDuration = segmentDuration;
    }

    public List<Streams> getStream() {
        return stream;
    }

    public void setStream(List<Streams> stream) {
        this.stream = stream;
    }

    public String getFanoutProxy() {
        return fanoutProxy;
    }

    public void setFanoutProxy(String fanoutProxy) {
        this.fanoutProxy = fanoutProxy;
    }

    public List<Program> getProgram() {
        return program;
    }

    public void setProgram(List<Program> program) {
        this.program = program;
    }

    public String getNetworkLatency() {
        return networkLatency;
    }

    public void setNetworkLatency(String networkLatency) {
        this.networkLatency = networkLatency;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Scte30Server getScte30Server() {
        return scte30Server;
    }

    public void setScte30Server(Scte30Server scte30Server) {
        this.scte30Server = scte30Server;
    }


}
