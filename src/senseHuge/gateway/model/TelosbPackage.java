package senseHuge.gateway.model;

//import java.sql.Time;
import java.util.Date;

public class TelosbPackage {
	public int 	id;
	public String message;
    public String Ctype;  //C1 C2 C3 C4
	public String status;   // true ÉÏ´«³É
	public Date receivetime;
	public String nodeID;
	
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCtype() {
		return Ctype;
	}
	public void setCtype(String ctype) {
		Ctype = ctype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReceivetime() {
		return receivetime;
	}
	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}
}
