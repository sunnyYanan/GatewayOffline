package senseHuge.gateway.model;

public class HttpClient {
	public String server;
	public String info;
	
	public HttpClient(String server) {
		super();
		this.server = server;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	

}
