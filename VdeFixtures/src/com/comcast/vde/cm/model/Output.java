package com.comcast.vde.cm.model;

import com.google.gson.annotations.SerializedName;

public class Output {

    private Boolean sdtEnable;
    private Integer udpSrcPort;
    private Boolean enable;
    private String dstAddress;
    private Integer tsRate;
    @SerializedName("interface")
    private String _interface;
    private Integer tsid;
    private Integer udpDstPort;
    private Integer timeToLive;

    public Boolean getSdtEnable() {
        return sdtEnable;
    }

    public void setSdtEnable(Boolean sdtEnable) {
        this.sdtEnable = sdtEnable;
    }

    public Integer getUdpSrcPort() {
        return udpSrcPort;
    }

    public void setUdpSrcPort(Integer udpSrcPort) {
        this.udpSrcPort = udpSrcPort;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDstAddress() {
        return dstAddress;
    }

    public void setDstAddress(String dstAddress) {
        this.dstAddress = dstAddress;
    }

    public Integer getTsRate() {
        return tsRate;
    }

    public void setTsRate(Integer tsRate) {
        this.tsRate = tsRate;
    }

    public String getInterface() {
        return _interface;
    }

    public void setInterface(String _interface) {
        this._interface = _interface;
    }

    public Integer getTsid() {
        return tsid;
    }

    public void setTsid(Integer tsid) {
        this.tsid = tsid;
    }

    public Integer getUdpDstPort() {
        return udpDstPort;
    }

    public void setUdpDstPort(Integer udpDstPort) {
        this.udpDstPort = udpDstPort;
    }

    public Integer getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
    }

 

}
