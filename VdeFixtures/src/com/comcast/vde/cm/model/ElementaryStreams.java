package com.comcast.vde.cm.model;

public class ElementaryStreams {
	
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	public String getDescriptorList() {
		return descriptorList;
	}
	public void setDescriptorList(String descriptorList) {
		this.descriptorList = descriptorList;
	}
	private long pid;
	private long type;
	private String descriptorList;
}
