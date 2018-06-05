package com.comcast.vde.cm.model;

public class Input {

    private String mpdLatency;
    private String contentType;
    private String url;
    public boolean isCspEnable() {
		return cspEnable;
	}

	public void setCspEnable(boolean cspEnable) {
		this.cspEnable = cspEnable;
	}

	public boolean isRevLocationOrder() {
		return revLocationOrder;
	}

	public void setRevLocationOrder(boolean revLocationOrder) {
		this.revLocationOrder = revLocationOrder;
	}

	private boolean cspEnable;
    private boolean revLocationOrder;
  

    public String getMpdLatency() {
        return mpdLatency;
    }

    public void setMpdLatency(String mpdLatency) {
        this.mpdLatency = mpdLatency;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



}
