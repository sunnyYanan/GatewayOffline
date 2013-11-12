package senseHuge.gateway.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import senseHuge.gateway.model.TelosbPackage;
import senseHuge.gateway.service.OfflineBackupService;

public class OfflineBackupUtil {
	
  public static	OfflineBackupService   offlineBackupService = new OfflineBackupService();
  public  boolean CreatBackUpFile(List<TelosbPackage> list,String path){
	  File f = new File(path + "TelosbBackup.xml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}/* else {

			return false;
		}*/
		OutputStream outPut = null;
		try {
			outPut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return offlineBackupService.save(list, outPut);
  }
}
