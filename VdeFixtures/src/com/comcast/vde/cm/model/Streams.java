package com.comcast.vde.cm.model;

public class Streams {
	
	 private String url;
	    private String streamName ;
	    private Integer streamPid;
	    private Integer streamIndex;
	    private Boolean enable;
	    private Integer bitrate;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getStreamName() {
			return streamName;
		}
		public void setStreamName(String streamName) {
			this.streamName = streamName;
		}
		public Integer getStreamPid() {
			return streamPid;
		}
		public void setStreamPid(Integer streamPid) {
			this.streamPid = streamPid;
		}
		public Integer getStreamIndex() {
			return streamIndex;
		}
		public void setStreamIndex(Integer streamIndex) {
			this.streamIndex = streamIndex;
		}
		public Boolean getEnable() {
			return enable;
		}
		public void setEnable(Boolean enable) {
			this.enable = enable;
		}
		public Integer getBitRate() {
			return bitrate;
		}
		public void setBitRate(Integer bitRate) {
			this.bitrate = bitRate;
		}
	    
	    

}
