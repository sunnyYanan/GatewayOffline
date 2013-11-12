package senseHuge.gateway.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import senseHuge.gateway.model.PackagePattern;
import android.util.Xml;

public class PackagePatternService {
	// 项目Xml文件生成后就执行的部分
	public PackagePattern getPackage(InputStream xml)
			throws XmlPullParserException, IOException {
		PackagePattern packagePattern = new PackagePattern();
		XmlPullParser xmlPullParser = Xml.newPullParser();
		xmlPullParser.setInput(xml, "UTF-8");
		int event = xmlPullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				// persons = new ArrayList<Person>();
				break;
			case XmlPullParser.START_TAG:
				if ("Head".equals(xmlPullParser.getName())) {
					packagePattern.setHead((String) xmlPullParser.nextText());
					break;
				}
				if ("DestinationAddr".equals(xmlPullParser.getName())) {
					packagePattern.setDestinationAddr((String) xmlPullParser
							.nextText());
				}

				if ("SourceAddr".equals(xmlPullParser.getName())) {

					packagePattern.setSourceAddr((String) xmlPullParser
							.nextText());
				}
				if ("MessageLength".equals(xmlPullParser.getName())) {
					packagePattern.setMessageLength(Integer
							.parseInt((xmlPullParser.nextText())));
				}
				if ("NetworkNum".equals(xmlPullParser.getName())) {
					packagePattern.setNetworkNum((String) xmlPullParser
							.nextText());
				}
				if ("AMtype".equals(xmlPullParser.getName())) {
					packagePattern.setAMtype((String) xmlPullParser.nextText());
				}
				if ("Ctype".equals(xmlPullParser.getName())) {
					packagePattern.setCtype((String) xmlPullParser.nextText());
				}
				if ("DataField".equals(xmlPullParser.getName())) {
					Map<String, String> DataField = new LinkedHashMap<String, String>();
					String key = xmlPullParser.getAttributeValue(0);
					int i = 0;
					while (event != XmlPullParser.END_DOCUMENT) {

						event = xmlPullParser.next();
						switch (event) {
						case XmlPullParser.START_DOCUMENT:
							// persons = new ArrayList<Person>();
							break;
						case XmlPullParser.START_TAG:
							// packagePattern.DataField.put(xmlPullParser.getName(),
							// (String) xmlPullParser.nextText());
							DataField.put(xmlPullParser.getName(),
									(String) xmlPullParser.nextText());
							break;
						case XmlPullParser.END_TAG:
							if ("DataField".equals(xmlPullParser.getName())) {
								i = 1;
								System.out.println("parse end !!");
							}

						}
						if (i == 1) {
							break;
						}

					}

					packagePattern.TelosbDataField.put(key, DataField);
				}
				break;

			case XmlPullParser.END_TAG:
				if ("DataPackage".equals(xmlPullParser.getName())) {
					System.out.println("parse end !!");
				}
			}

			event = xmlPullParser.next();
		}

		return packagePattern;
	}

	public void save(PackagePattern packagePattern, OutputStream out)
			throws Exception {
		XmlSerializer xmlSerializer = Xml.newSerializer();
		xmlSerializer.setOutput(out, "UTF-8");
		xmlSerializer.startDocument("UTF-8", true);

		xmlSerializer.startTag(null, "DataPackage");

		xmlSerializer.startTag(null, "Head");
		xmlSerializer.text(packagePattern.getHead());
		xmlSerializer.endTag(null, "Head");

		xmlSerializer.startTag(null, "DestinationAddr");
		xmlSerializer.text(packagePattern.getDestinationAddr());
		xmlSerializer.endTag(null, "DestinationAddr");

		xmlSerializer.startTag(null, "SourceAddr");
		xmlSerializer.text(packagePattern.getSourceAddr());
		xmlSerializer.endTag(null, "SourceAddr");

		xmlSerializer.startTag(null, "MessageLength");
		xmlSerializer.text(String.valueOf(packagePattern.getMessageLength()));
		xmlSerializer.endTag(null, "MessageLength");

		xmlSerializer.startTag(null, "NetworkNum");
		xmlSerializer.text(packagePattern.getNetworkNum());
		xmlSerializer.endTag(null, "NetworkNum");

		xmlSerializer.startTag(null, "AMtype");
		xmlSerializer.text(packagePattern.getAMtype());
		xmlSerializer.endTag(null, "AMtype");
		xmlSerializer.startTag(null, "DataField");
		for (Map.Entry<String, String> m : packagePattern.getDataField()
				.entrySet()) {

			// logger.info("email-" + m.getKey() + ":" + m.getValue());
			xmlSerializer.startTag(null, m.getKey());
			xmlSerializer.text(m.getValue());
			xmlSerializer.endTag(null, m.getKey());
		}
		xmlSerializer.endTag(null, "DataField");

		xmlSerializer.endTag(null, "DataPackage");
		xmlSerializer.endDocument();

		out.flush();
		out.close();
	}

	/**
	 * 初始化xml文件，将包的格式存入
	 */
	public void save2(PackagePattern packagePattern, OutputStream out)
			throws Exception {
		XmlSerializer xmlSerializer = Xml.newSerializer();
		xmlSerializer.setOutput(out, "UTF-8");
		xmlSerializer.startDocument("UTF-8", true);

		xmlSerializer.startTag(null, "DataPackage");

		xmlSerializer.startTag(null, "Head");
		xmlSerializer.text(packagePattern.getHead());
		xmlSerializer.endTag(null, "Head");

		xmlSerializer.startTag(null, "DestinationAddr");
		xmlSerializer.text(packagePattern.getDestinationAddr());
		xmlSerializer.endTag(null, "DestinationAddr");

		xmlSerializer.startTag(null, "SourceAddr");
		xmlSerializer.text(packagePattern.getSourceAddr());
		xmlSerializer.endTag(null, "SourceAddr");

		xmlSerializer.startTag(null, "MessageLength");
		xmlSerializer.text(String.valueOf(packagePattern.getMessageLength()));
		xmlSerializer.endTag(null, "MessageLength");

		xmlSerializer.startTag(null, "NetworkNum");
		xmlSerializer.text(packagePattern.getNetworkNum());
		xmlSerializer.endTag(null, "NetworkNum");

		xmlSerializer.startTag(null, "AMtype");
		xmlSerializer.text(packagePattern.getAMtype());
		xmlSerializer.endTag(null, "AMtype");

		xmlSerializer.startTag(null, "Ctype");
		xmlSerializer.text(packagePattern.getCtype());
		xmlSerializer.endTag(null, "Ctype");

		int i = 0;
		for (Entry<String, Map<String, String>> l : packagePattern.TelosbDataField
				.entrySet()) {

			xmlSerializer.startTag(null, "DataField");
			xmlSerializer.attribute(null, "Ctype", l.getKey());
			for (Map.Entry<String, String> m : l.getValue().entrySet()) {

				// logger.info("email-" + m.getKey() + ":" + m.getValue());
				xmlSerializer.startTag(null, m.getKey());
				xmlSerializer.text(m.getValue());
				xmlSerializer.endTag(null, m.getKey());
			}
			xmlSerializer.endTag(null, "DataField");
			i++;

		}

		xmlSerializer.endTag(null, "DataPackage");
		xmlSerializer.endDocument();

		out.flush();
		out.close();
	}
}
