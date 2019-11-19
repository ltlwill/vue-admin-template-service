package com.efe.ms.bankservice.excel;

/**
 * Excel实例工厂
 * 
 * @author liutianlong
 *
 */
public final class ExcelFactory {

	private static ExcelFactory instance = null;

	private ExcelFactory() {
	}

	/**
	 * 后去EBAY brand解析器
	 * 
	 * @return
	 */
	public ExcelParser getExcelImpDetailParser() {
		return new ExcelDetailParser();
	}

	public static ExcelFactory getInstance() {
		if (instance == null) {
			synchronized (ExcelFactory.class) {
				if (instance == null) {
					instance = new ExcelFactory();
				}
			}
		}
		return instance;
	}
}
