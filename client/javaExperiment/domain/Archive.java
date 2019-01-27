package javaExperiment.domain;

import java.io.Serializable;
import java.io.ObjectInputStream.GetField;
import java.sql.Timestamp;

import com.mysql.cj.xdevapi.DatabaseObjectDescription;

import javaExperiment.common.*;

public class Archive implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8781005320460274471L;
	private int id;                //唯一标识符
	private String description;
	private Timestamp timestamp;   //更新时间戳
	private String title;           //标题
	private String keyword;   //关键词
	private String catalogue; //目录
	private SecurityClassfication securityClassfication;   //档案密级
	private String fileName;  //文件名
	private String absolutePath;  //附件在后台的绝对路径
	private String sourcePath; //前台的绝对路径，不持久化
	private User user;    //上传用户
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description ;
	}
	public String getDescription() {
		return description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCatalogue() {
		return catalogue;
	}
	public void setCatalogue(String catalogue) {
		this.catalogue = catalogue;
	}
	public SecurityClassfication getSecurityClassfication() {
		return securityClassfication;
	}
	public void setSecurityClassfication(SecurityClassfication securityClassfication) {
		this.securityClassfication = securityClassfication;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Archive [id=" + id + ", timestamp=" + timestamp + ", title=" + title + ", keyword=" + keyword
				+ ", catalogue=" + catalogue + ", securityClassfication=" + securityClassfication + ", fileName="
				+ fileName + ", absolutePath=" + absolutePath + ", sourcePath=" + sourcePath + ", user=" + user + "]";
	}

}
