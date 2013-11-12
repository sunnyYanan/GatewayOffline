package senseHuge.gateway.model;

public class Serial {
	private String filePath;
	private int bandrate;
	private Boolean state;
	
	public Serial() {
		super();
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getBuandrate() {
		return bandrate;
	}
	public void setBuandrate(int buandrate) {
		this.bandrate = buandrate;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
}
