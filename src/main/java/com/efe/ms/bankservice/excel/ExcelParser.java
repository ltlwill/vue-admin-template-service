package com.efe.ms.bankservice.excel;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel解析器接口
 *
 * @author TianLong Liu
 * @date 2019年11月19日 下午3:50:16
 */
public interface ExcelParser {

	/**
	 * 打开excel文件
	 * 
	 * @param path
	 * @return
	 */
	public Workbook open(String path) throws Exception;

	/**
	 * 打开excel文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Workbook open(File file) throws Exception;

	/**
	 * 打开输入流
	 * 
	 * @param ins
	 * @return
	 * @throws Exception
	 */
	public Workbook open(InputStream ins) throws Exception;

	/**
	 * 关闭流
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception;

	/**
	 * 解析excel文件（具体解析逻辑有子类实现）
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Object parse(String path, Map<String, Object> params)
			throws Exception;

	/**
	 * 解析excel文件（具体解析逻辑有子类实现）
	 * 
	 * @param ins
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Object parse(InputStream ins, Map<String, Object> params)
			throws Exception;
}
