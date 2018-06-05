package com.comcast.vde.cm.model;


import java.util.ArrayList;
import java.util.List;

public class Data {

    private String location;
    private String name;
    private String sourceCIDR;
    public String getSourceCIDR() {
		return sourceCIDR;
	}

	public void setSourceCIDR(String sourceCIDR) {
		this.sourceCIDR = sourceCIDR;
	}

	private Integer deployCount;
    private Mux mux;
	
	 private List<Streams> stream = new ArrayList<Streams>();
   

	public List<Streams> getStream() {
		return stream;
	}

	public void setStream(List<Streams> stream) {
		this.stream = stream;
	}
	
	
    private Boolean deploy;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeployCount() {
        return deployCount;
    }

    public void setDeployCount(Integer deployCount) {
        this.deployCount = deployCount;
    }

    public Mux getMux() {
        return mux;
    }

    public void setMux(Mux mux) {
        this.mux = mux;
    }

    public Boolean getDeploy() {
        return deploy;
    }

    public void setDeploy(Boolean deploy) {
        this.deploy = deploy;
    }


}
