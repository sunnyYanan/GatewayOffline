package senseHuge.gateway.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class PackagePattern {
	public String Head = "00FFFF";
	public String DestinationAddr = "FFFF";
	public String SourceAddr = "0000";
	public int MessageLength = 0;
	public String NetworkNum = "00";
	public String AMtype = "00";
	public String ctype = "C1";
	public String nodeID ;
	public Map<String, String> DataField = new LinkedHashMap<String, String>();
	public Map<String, Map<String, String>> TelosbDataField = new LinkedHashMap<String, Map<String,String>>();
	
	public PackagePattern() {
		super();
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getHead() {
		return Head;
	}
	public void setHead(String head) {
		Head = head;
	}
	public String getDestinationAddr() {
		return DestinationAddr;
	}
	public void setDestinationAddr(String destinationAddr) {
		DestinationAddr = destinationAddr;
	}
	public String getSourceAddr() {
		return SourceAddr;
	}
	public void setSourceAddr(String sourceAddr) {
		SourceAddr = sourceAddr;
	}
	public int getMessageLength() {
		return MessageLength;
	}
	public void setMessageLength(int messageLength) {
		MessageLength = messageLength;
	}
	public String getNetworkNum() {
		return NetworkNum;
	}
	public void setNetworkNum(String networkNum) {
		NetworkNum = networkNum;
	}
	public String getAMtype() {
		return AMtype;
	}
	public void setAMtype(String aMtype) {
		AMtype = aMtype;
	}
	public Map<String, String> getDataField() {
		return DataField;
	}
	public void setDataField(Map<String, String> dataField) {
		DataField = dataField;
	}
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public Map<String, Map<String, String>> getTelosbDataField() {
		return TelosbDataField;
	}
	public void setTelosbDataField(Map<String, Map<String, String>> telosbDataField) {
		TelosbDataField = telosbDataField;
	}
}
