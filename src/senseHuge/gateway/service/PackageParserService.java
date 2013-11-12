package senseHuge.gateway.service;

public class PackageParserService {
	    public String parserString;
	    public int begin;
	    public int end;
	
      public String parser(String string,int beginIndex,int endIndex){
    	 return string.substring(2*beginIndex,2* endIndex);
      }
      public String parseDatafield(String string,int beginIndex,int endIndex){
    	  return string.substring(2*beginIndex - 2,2* endIndex);
      }
      
      public String parser(String string,String datafield){
    	//  String string2=datafield.substring(1, 2);
    	  begin = Integer.valueOf(datafield.substring(0, 2),16);
    	  end = Integer.valueOf(datafield.substring(2, 4),16);
    	  return parser(string, begin, end);
      }
 
      public String parseDatafield(String string,String datafield){
    	/*  String firString = datafield.substring(0, 2);
    	  String second = datafield.substring(2, 4);*/
    		int  begin = Integer.valueOf(datafield.substring(0, 3),10);
	    	int  end = Integer.valueOf(datafield.substring(3, 6),10);
	    	if (string == "") {
				System.out.println("no data:");
			}
	    	//System.out.println(string+"MMMMMMMMMMMMM");
    	  return parseDatafield(string, begin, end);
      }
	public String getParserString() {
		return parserString;
	}

	public void setParserString(String parserString) {
		this.parserString = parserString;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
      
      
}
