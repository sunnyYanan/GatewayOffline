package senseHuge.gateway.util;

public class ParseTelosbPackageUtil {
	public static String parse(String string,String StartAndEnd){
		   if (StartAndEnd.length()!=6) {
			return null;
		} else {
			int start = Integer.parseInt(StartAndEnd.substring(0, 3));
			int end = Integer.parseInt(StartAndEnd.substring(3, 6));
	        return parse(string,2*(start-1), 2*(end-1));
		}
	   }
	   public static String parse(String string,int start,int end){
		 if (start == end) {
			 return  string.substring(start, end+2);
			
		}else {
			
			return  string.substring(start, end);
		}
	   }

}
