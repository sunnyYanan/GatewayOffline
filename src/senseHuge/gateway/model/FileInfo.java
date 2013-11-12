package senseHuge.gateway.model;

import senseHuge.gateway.util.FileChooserAdapter;

enum FileType {
	FILE , DIRECTORY;
}
public class FileInfo {
	
	private FileType fileType;
	private String fileName;
	private String filePath;

	public FileInfo(String filePath, String fileName, boolean isDirectory) {
		this.filePath = filePath;
		this.fileName = fileName;
		fileType = isDirectory ? FileType.DIRECTORY : FileType.FILE;
	}

	public boolean isMp3File() {
		if (fileName.lastIndexOf(".") < 0) // Don't have the suffix
			return false;
		String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
		if (!isDirectory() && FileChooserAdapter.PPT_SUFFIX.contains(fileSuffix))
			return true;
		else
			return false;
	}

	public boolean isDirectory() {
		if (fileType == FileType.DIRECTORY)
			return true;
		else
			return false;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "FileInfo [fileType=" + fileType + ", fileName=" + fileName
				+ ", filePath=" + filePath + "]";
	}
}
