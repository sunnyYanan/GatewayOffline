package senseHuge.gateway.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import senseHuge.gateway.model.PackagePattern;
import senseHuge.gateway.service.PackageParserService;
import senseHuge.gateway.service.PackagePatternService;

public class XmlTelosbPackagePatternUtil {
	public PackageParserService packageParserService = new PackageParserService();;
	public String path;
	public PackagePattern packagePattern = null;

	public XmlTelosbPackagePatternUtil(String path) {
		super();
		this.path = path;
		this.inite();
		packagePattern = this.getPackagePattern();
	}

	public PackagePattern getPackagePattern() {
		if (this.packagePattern == null) {
			return PackagePattern();
		} else {
			return this.packagePattern;
		}
	}

	public PackagePattern PackagePattern() {
		// String path;
		// path = getFilesDir().toString();
		PackagePatternService packagePatternService = new PackagePatternService();
		File f = new File(path + "package.xml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PackagePattern packagePattern = null;
		try {
			packagePattern = packagePatternService.getPackage(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packagePattern;
	}

	public void inite() {
		/*
		 * String path; path = getFilesDir().toString();
		 */
		PackagePatternService packagePatternService = new PackagePatternService();
		PackagePattern packagePattern = new PackagePattern();
		File f = new File(path + "package.xml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			return;
		}
		OutputStream outPut = null;
		try {
			outPut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> C1 = new LinkedHashMap<String, String>();
		Map<String, String> C2 = new LinkedHashMap<String, String>();
		Map<String, String> C3 = new LinkedHashMap<String, String>();
		Map<String, String> C4 = new LinkedHashMap<String, String>();

		C1.put("数据包类型", "019019");
		C1.put("源节点编号 ", "020021");
		C1.put("sink节点编号", "022023");
		C1.put("发包时间戳", "024027");
		C1.put("sink收包时间戳", "028031");
		C1.put("数据包序列号", "032033");
		C1.put("周期", "034035");
		C1.put("下一跳点编号", "036037");
		C1.put("跳数", "038039");
		C1.put("发包功率", "040040");
		C1.put("执行任务时间", "041044");
		C1.put("执行任务时间ms", "045048");
		C1.put("下一跳节点", "049050");
		C1.put("采样频率", "051052");
		C1.put("telosb采样温度", "053054");
		C1.put("节点电压", "055056");
		C1.put("传感器采样湿度", "057058");
		C1.put("传感器采样温度", "059060");
		C1.put("光照强度", "061062");
		C1.put("二氧化碳浓度", "065066");
		C1.put("跃阶次数", "075075");
		C1.put("链路长度", "076076");
		C1.put("每跳节点", "077096");

		packagePattern.TelosbDataField.put("C1", C1);

		C2.put("数据包类型", "019019");
		C2.put("源节点号", "020021");
		C2.put("sink节点号", "022023");
		C2.put("发包时间戳", "024027");
		C2.put("收包时间戳", "028031");
		C2.put("序列号", "032033");
		C2.put("周期", "034035");
		C2.put("邻居节点数量", "036036");
		C2.put("邻居节点编号", "037096");
		C2.put("父节点", "097098");

		packagePattern.TelosbDataField.put("C2", C2);

		C3.put("数据包类型", "019019");
		C3.put("源节点号", "020021");
		C3.put("sink节点号", "022023");
		C3.put("发包时间戳", "024027");
		C3.put("收包时间戳", "028031");
		C3.put("序列号", "032033");
		C3.put("周期", "034035");
		C3.put("天线打开次数", "036039");
		C3.put("成功收到包数", "040043");
		C3.put("成功发送包数", "044047");
		C3.put("接收缓冲溢出导致的丢包数", "048051");
		C3.put("节点自己发出的数据包总数", "052055");
		C3.put("发送失败无ACK但重传次数未达上限", "056059");
		C3.put("发送失败无ACK重传次数已达上限丢包", "060063");
		C3.put("重传次数", "064067");
		C3.put("路由出现回路次数", "068069");
		C3.put("重复收到包数", "070071");
		C3.put("成功收到ACK数量", "072075");
		C3.put("父节点变更次数", "076077");
		C3.put("Taskpost的数量", "078081");
		C3.put("Task执行的数量", "082085");
		C3.put("找不到父节点次数", "086087");
		C3.put("CTP发送失败次数", "088091");
		C3.put("CTP除去一个路由表项次数", "092095");
		C3.put("MAC层Initialbackoff的次数", "096099");
		C3.put("MAC层Congestionbackoff的次数", "100103");

		packagePattern.TelosbDataField.put("C3", C3);
		C4.put("数据包类型", "019019");
		C4.put("源节点号", "020021");
		C4.put("sink节点", "022023");
		C4.put("发包时间戳", "024027");
		C4.put("收包时间戳", "028031");
		C4.put("数据包序列号", "032033");
		C4.put("周期", "034035");
		C4.put("路径队伍长度和", "036037");
		C4.put("路径退避次数总和", "038039");
		C4.put("路径重传次数", "040041");
		C4.put("跳数", "042042");
		C4.put("邻居大小", "043043");
		C4.put("路径节点", "044093");

		packagePattern.TelosbDataField.put("C4", C4);

		try {
			//存入xml文件中
			packagePatternService.save2(packagePattern, outPut);
			outPut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				outPut.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public Map<String, String> Parse(PackagePattern packagePattern,
			String Ctype, String telsobData) {
		Map<String, String> map = new LinkedHashMap<String, String>();

		packagePattern.TelosbDataField.get(Ctype);

		if (packagePattern.getDataField() != null) {
			// System.out.println("packagePattern .getDataField() is null!!!");
			System.out.println("packagePattern.getTelosbDataField() size= "
					+ packagePattern.getTelosbDataField().size());
			System.out
					.println("packagePattern.getTelosbDataField() is containsKey :"
							+ packagePattern.getTelosbDataField().containsKey(
									"C1"));
		} else if (Ctype != null) {
			System.out.println("Ctype =" + Ctype);
			// System.out.println("packagePattern .getDataField().Ctype is null!!!");
		} else if (packagePattern.TelosbDataField.get(Ctype) == null) {
			System.out
					.println("packagePattern.TelosbDataField.get(Ctype) == null!!!");
		}

		Iterator<?> it = packagePattern.TelosbDataField.get(Ctype).entrySet()
				.iterator();

		int num = 0;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			// String data = packageParserService.parser(telsobData,
			// pairs.getValue().toString());
			String value = packageParserService.parseDatafield(telsobData,
					pairs.getValue().toString());

			/*
			 * int begin = Integer.valueOf(value.substring(0, 3),10); int end =
			 * Integer.valueOf(value.substring(3, 6),10);
			 */
			map.put(pairs.getKey().toString(), value);
			if (value.length() > 10) {
				num = 0; // 数据长度过长 无法转换： 如C2包的邻居节点编号
			} else {

				// num = Integer.valueOf(value, 16);
			}
			// System.out.println("data  =  "+value + ";result ="+num);
		}

		return map;
	}

	public Map<String, String> Parse(String Ctype, String telsobData) {
		Map<String, String> map = new LinkedHashMap<String, String>();

		packagePattern.TelosbDataField.get(Ctype);

	
		Iterator it = packagePattern.TelosbDataField.get(Ctype).entrySet()
				.iterator();

		int num = 0;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
//			System.out.println("解析包："+pairs.getKey() + " = " + pairs.getValue());
			// String data = packageParserService.parser(telsobData,
			// pairs.getValue().toString());
			String value = packageParserService.parseDatafield(telsobData,
					pairs.getValue().toString());

			/*
			 * int begin = Integer.valueOf(value.substring(0, 3),10); int end =
			 * Integer.valueOf(value.substring(3, 6),10);
			 */
			map.put(pairs.getKey().toString(), value);
			if (value.length() > 10) {
				num = 0; // 数据长度过长 无法转换： 如C2包的邻居节点编号
			} else {

				// num = Integer.valueOf(value, 16);
			}
			// System.out.println("data  =  "+value + ";result ="+num);
		}

		return map;
	}

	public PackagePattern parseTelosbPackage(String telsobData)
			throws Exception {
		PackagePattern telosbPackage = new PackagePattern();
		telosbPackage.setAMtype(packageParserService.parseDatafield(telsobData,
				"008008"));
		telosbPackage.setCtype(packageParserService.parseDatafield(telsobData,
				"019019"));
		telosbPackage.setMessageLength(Integer.valueOf(
				packageParserService.parseDatafield(telsobData, "006006"), 16));
		telosbPackage.setNetworkNum(packageParserService.parseDatafield(
				telsobData, "007007"));
		telosbPackage.setSourceAddr(packageParserService.parseDatafield(
				telsobData, "004005"));
		telosbPackage.setHead(packageParserService.parseDatafield(telsobData,
				"001003"));
		telosbPackage.setNodeID(packageParserService.parseDatafield(telsobData,
				"020021"));
		if (packagePattern == null) {

			System.out.println("packagePattern == null");
		}
		String type = "C1C2C3C4";
		String ctype = packageParserService
				.parseDatafield(telsobData, "019019");
		if (!type.contains(ctype)) {
			System.out.println("包类型^^^^^^^^^^^^^^^^^^^error:");
			throw new RuntimeException("demo");

		}
		telosbPackage.setDataField(this.Parse(
				packageParserService.parseDatafield(telsobData, "019019"),
				telsobData));

//		testTelosbDatafield(telosbPackage);

		return telosbPackage;
	}
}
