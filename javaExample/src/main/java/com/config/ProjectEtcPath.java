package com.config;

/**
 * @Description:项目配置文件路径
 * @author xiangrandy E-mail:351615708@qq.com
 * @Time:2017年1月3日下午4:10:29
 */
public class ProjectEtcPath {

	private static ProjectEtcPath Instance = new ProjectEtcPath();

	private String ROOT_PATH = "";

	private static final String CONF_PATH = "conf.properties";

	private ProjectEtcPath() {
	}

	public static ProjectEtcPath getInstance() {
		return Instance;
	}

	public boolean setEtcRootPath(String path) {
		ROOT_PATH = path;
		return true;
	}

	public String getConfPath() {
		if (ROOT_PATH.endsWith("/")) {
			return ROOT_PATH + CONF_PATH;
		} else {
			return ROOT_PATH + "/" + CONF_PATH;
		}
	}
}
