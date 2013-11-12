package senseHuge.gateway.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;
import senseHuge.gateway.model.TelosbPackage;

public class OfflineBackupService {
    public boolean  save(List<TelosbPackage> list,OutputStream out){
    	
    	try {
			XmlSerializer xmlSerializer = Xml.newSerializer();
			xmlSerializer.setOutput(out, "UTF-8");
			xmlSerializer.startDocument("UTF-8", true);

			xmlSerializer.startTag(null, "TelosbPackage");

			for (TelosbPackage telosbPackage : list) {
				xmlSerializer.startTag(null, "TELOSB");
				
				xmlSerializer.startTag(null, "PID");
			    xmlSerializer.text(String.valueOf(telosbPackage.id));
			    xmlSerializer.endTag(null, "PID");
			    
			    xmlSerializer.startTag(null, "MESSAGE");
			    xmlSerializer.text(telosbPackage.message);
			    xmlSerializer.endTag(null, "MESSAGE");
			    
			    xmlSerializer.startTag(null, "RECEIVETIME");
			    xmlSerializer.text(telosbPackage.receivetime.toString());
			    xmlSerializer.endTag(null, "RECEIVETIME");
			    
			    xmlSerializer.startTag(null, "CTYPE");
			    xmlSerializer.text(telosbPackage.Ctype);
			    xmlSerializer.endTag(null, "CTYPE");
			    
			    xmlSerializer.startTag(null, "NODEID");
			    xmlSerializer.text(telosbPackage.nodeID);
			    xmlSerializer.endTag(null, "NODEID");
			    xmlSerializer.endTag(null, "TELOSB");
			    
			    
			    
			}
			


			/*
			xmlSerializer.startTag(null, "DataField");
			for (Map.Entry<String, String> m : packagePattern.getDataField()
					.entrySet()) {

				// logger.info("email-" + m.getKey() + ":" + m.getValue());
				xmlSerializer.startTag(null, m.getKey());
				xmlSerializer.text(m.getValue());
				xmlSerializer.endTag(null, m.getKey());
			}
			xmlSerializer.endTag(null, "DataField");*/

			xmlSerializer.endTag(null, "TelosbPackage");
			xmlSerializer.endDocument();

			out.flush();
			out.close();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    	
    	return true;
    }
}
