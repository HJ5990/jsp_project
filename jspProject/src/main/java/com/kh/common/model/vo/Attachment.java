package com.kh.common.model.vo;

import java.sql.Date;

public class Attachment {
	
	private int FileNo;
	private int refBoardNo;
	private String originName;
	private String changeName;
	private String filePath;
	private Date uploadDate; 
	private String fileLevel;
	private String status;


	public Attachment() {}


	public Attachment(int fileNo, int refBoardNo, String originName, String changeName, String filePath,
			Date uploadDate, String fileLevel, String status) {
		super();
		FileNo = fileNo;
		this.refBoardNo = refBoardNo;
		this.originName = originName;
		this.changeName = changeName;
		this.filePath = filePath;
		this.uploadDate = uploadDate;
		this.fileLevel = fileLevel;
		this.status = status;
	}


	public int getFileNo() {
		return FileNo;
	}


	public void setFileNo(int fileNo) {
		FileNo = fileNo;
	}


	public int getRefBoardNo() {
		return refBoardNo;
	}


	public void setRefBoardNo(int refBoardNo) {
		this.refBoardNo = refBoardNo;
	}


	public String getOriginName() {
		return originName;
	}


	public void setOriginName(String originName) {
		this.originName = originName;
	}


	public String getChangeName() {
		return changeName;
	}


	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public Date getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}


	public String getFileLevel() {
		return fileLevel;
	}


	public void setFileLevel(String fileLevel) {
		this.fileLevel = fileLevel;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Attachment [FileNo=" + FileNo + ", refBoardNo=" + refBoardNo + ", originName=" + originName
				+ ", changeName=" + changeName + ", filePath=" + filePath + ", uploadDate=" + uploadDate
				+ ", fileLevel=" + fileLevel + ", status=" + status + "]";
	}
	
	
	
	
}
