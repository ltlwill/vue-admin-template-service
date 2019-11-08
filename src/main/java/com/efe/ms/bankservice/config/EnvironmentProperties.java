package com.efe.ms.bankservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义环境
 * 
 * @author liutianlong
 *
 */

@Component
@ConfigurationProperties(prefix = "custom-env")
public class EnvironmentProperties {
	// 映射虚拟目录的url模式
	private String virtualPathPattern;
	// 虚拟目录资源访问上下文
	private String virtualPath;
	// 虚拟目录路径映射模式
	private String virtualDirPattern;

	private String linuxVirtualDirPattern;
	// 虚拟目录路径
	private String virtualDir;
	// 应用入口
	private String appEntrance;

	public String getVirtualPathPattern() {
		return virtualPathPattern;
	}

	public void setVirtualPathPattern(String virtualPathPattern) {
		this.virtualPathPattern = virtualPathPattern;
		this.virtualPath = getVirtualPathFromVirtualPathPattern();
	}

	public String getVirtualPath() {
		return virtualPath;
	}

	public String getVirtualDirPattern() {
		return virtualDirPattern;
	}

	public void setVirtualDirPattern(String virtualDirPattern) {
		this.virtualDirPattern = virtualDirPattern;
		this.virtualDir = getVirtualDirFromVirtualDirPattern();
	}

	public String getVirtualDir() {
		return virtualDir;
	}

	private String getVirtualPathFromVirtualPathPattern() {
		if (this.virtualPathPattern == null
				|| "".equals(this.virtualPathPattern.trim())) {
			return null;
		}
		return this.virtualPathPattern.substring(0,
				this.virtualPathPattern.lastIndexOf("/"));
	}

	private String getVirtualDirFromVirtualDirPattern() {
		if (this.virtualDirPattern == null
				|| "".equals(this.virtualDirPattern.trim())) {
			return null;
		}
		return this.virtualDirPattern
				.substring(virtualDirPattern.indexOf(":") + 1);
	}

	public String getAppEntrance() {
		return appEntrance;
	}

	public void setAppEntrance(String appEntrance) {
		this.appEntrance = appEntrance;
	}

	public String getLinuxVirtualDirPattern() {
		return linuxVirtualDirPattern;
	}

	public void setLinuxVirtualDirPattern(String linuxVirtualDirPattern) {
		this.linuxVirtualDirPattern = linuxVirtualDirPattern;
	}

	public void setVirtualPath(String virtualPath) {
		this.virtualPath = virtualPath;
	}

	public void setVirtualDir(String virtualDir) {
		this.virtualDir = virtualDir;
	}

}
