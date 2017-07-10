package com.example.recruitdemo.updateversion;

import java.io.Serializable;

/**
 * @项目名: 	gdmsaec-app
 * @包名:	com.winfo.gdmsaec.app.domain
 * @类名:	VersionInfo
 * @创建者:	wenjie
 * @创建时间:	2015-10-14	上午11:06:08 
 * @描述:	app版本信息封装类
 * 
 * @svn版本:	$Rev: 1304 $
 * @更新人:	$Author: wenjie $
 * @更新时间:	$Date: 2016-02-27 14:58:42 +0800 (Sat, 27 Feb 2016) $
 * @更新描述:	TODO
 */
public class VersionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String	name;//版本名
	private String		version;//版本号
	private String	notes;//版本描述信息内容

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	private String	downloadUrl;//新版本的下载路径
//	private String versionSize;//版本大小
	
//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getVersionSize() {
//		return versionSize;
//	}
//	public void setVersionSize(String versionSize) {
//		this.versionSize = versionSize;
//	}
//	public String getVersionName() {
//		return versionName;
//	}
//	public void setVersionName(String versionName) {
//		this.versionName = versionName;
//	}
//	public int getVersionCode() {
//		return versionCode;
//	}
//	public void setVersionCode(int versionCode) {
//		this.versionCode = versionCode;
//	}
//	public String getVersionDesc() {
//		return versionDesc;
//	}
//	public void setVersionDesc(String versionDesc) {
//		this.versionDesc = versionDesc;
//	}
//	public String getDownloadUrl() {
//		return downloadUrl;
//	}
//	public void setDownloadUrl(String downloadUrl) {
//		this.downloadUrl = downloadUrl;
//	}
	
}
