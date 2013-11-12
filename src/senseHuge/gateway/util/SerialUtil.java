package senseHuge.gateway.util;


public class SerialUtil {
	
	public String headString = "00FFFF";
	public int headLength;
	public int Length;
	public int type;
	public String SegmentData;
	public StringBuffer stringBuffer = new StringBuffer();
	public int stringBufferLength ;

	public SerialUtil() {
		super();
	//	stringBuffer.append(string);
	}
	
	public SerialUtil(String headString, int headLength) {
		super();
		this.headString = headString;
		this.headLength = headLength;
	}

	public String getFirstData(){
		int start = findhead(headString);
		int packageLength = Integer.parseInt(stringBuffer.substring(start+10, start+12),16);
		int length =2*packageLength+16;
		String string = "";
		if (packageLength == 0) {
			stringBuffer.delete(0, start+16);
		} else {

			if (start+length > stringBuffer.length()) {
				return null;
			}
			string = stringBuffer.substring(start, start+length);
		}
		stringBuffer.delete(0, start+length);
		return 	string;
	}
	
	public int findhead(String headString){
		return stringBuffer.indexOf(headString);
	}
	
	public boolean delete(int start,int end)
	{
		if (end>stringBuffer.capacity()) {
			return false;
		} else {

			stringBuffer.delete(start,end);
			return true;
		}
	}
	public boolean delete(int end)
	{
		if (end>stringBuffer.length()) {
			return false;
		} else {
         System.out.println("delete end:"+ end + "buffer length:"+stringBuffer.length() );
			stringBuffer.delete(0,end);
			return true;
		}
	}
	

}
