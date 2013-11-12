package senseHuge.gateway.service;

import android.content.Context;
import senseHuge.gateway.util.LocalConfigUtil;

public class LocalConfigService {
	private static Context context = null;
	public static LocalConfigUtil localConfigUtil;
     public LocalConfigService(Context context) {
		super();
		if (getContext() ==null) {
			
			setContext(context);
		}
		if (localConfigUtil == null) {
			localConfigUtil = new LocalConfigUtil(context);
			
		}
	}
	public String getConfig(String configName){
    	return localConfigUtil.read(configName);
     }
     public void setConfig(String configName,String value){
    	 localConfigUtil.write(configName, value);
     }
	public static Context getContext() {
		return context;
	}
	public static void setContext(Context context) {
		LocalConfigService.context = context;
	}
}
