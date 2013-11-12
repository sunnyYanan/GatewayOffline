package senseHuge.gateway.model;

public class State {
	public boolean serialState;
	public boolean httpclientState;
	public boolean havedataforLocal;
	public boolean havedataforRemote;
	public State() {
		super();
	}
	public boolean isSerialState() {
		return serialState;
	}
	public void setSerialState(boolean serialState) {
		this.serialState = serialState;
	}
	public boolean isHttpclientState() {
		return httpclientState;
	}
	public void setHttpclientState(boolean httpclientState) {
		this.httpclientState = httpclientState;
	}
	public boolean isHavedataforLocal() {
		return havedataforLocal;
	}
	public void setHavedataforLocal(boolean havedataforLocal) {
		this.havedataforLocal = havedataforLocal;
	}
	public boolean isHavedataforRemote() {
		return havedataforRemote;
	}
	public void setHavedataforRemote(boolean havedataforRemote) {
		this.havedataforRemote = havedataforRemote;
	}
}
