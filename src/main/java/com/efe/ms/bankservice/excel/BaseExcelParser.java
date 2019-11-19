package com.efe.ms.bankservice.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel 文件基础解析器
 *
 * @author TianLong Liu
 * @date 2019年11月19日 下午3:51:23
 */
public abstract class BaseExcelParser implements ExcelParser {
	protected final static Logger logger = LoggerFactory
			.getLogger(BaseExcelParser.class);

	protected Workbook workbook;
	protected File srcFile;
	protected String filePath;

	// 允许最大连续空的数量
	protected final static int MAX_NULL_COL_NUM = 10;

	/**
	 * 打开excel文件
	 * 
	 * @param path
	 * @return
	 */
	@Override
	public Workbook open(String path) throws Exception {
		return open(new File(path));
	}

	public Workbook open(InputStream ins) throws Exception {
		try {
			this.workbook = WorkbookFactory.create(ins);
		} catch (Exception e) {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException ie) {
				}
			}
		}
		return this.workbook;
	}

	/**
	 * 打开excel文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Override
	public Workbook open(File file) throws Exception {
		Workbook workbook = WorkbookFactory.create(file);
		this.workbook = workbook;
		this.srcFile = file;
		this.filePath = file.getAbsolutePath();
		return workbook;
	}

	public void close() throws Exception {
		if (this.workbook != null) {
			this.workbook.close();
		}
	}

	/**
	 * 解析excel文件（具体解析逻辑有子类实现）
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object parse(String path, Map<String, Object> params)
			throws Exception {
		try {
			// 打开文件
			open(path);
			// 具体的解析业务逻辑
			return parseLogic(params);
		} catch (Exception e) {
			logger.error("Parse excel file error,cause:", e);
			throw e;
		} finally {
			try {
				close();
			} catch (Exception e) {
				logger.error("Colse excel file stream error,cause:", e);
				throw e;
			}
		}
	}

	/**
	 * 解析excel文件（输入流）
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Object parse(InputStream ins, Map<String, Object> params)
			throws Exception {
		try {
			// 打开文件
			open(ins);
			// 具体的解析业务逻辑
			return parseLogic(params);
		} catch (Exception e) {
			logger.error("Parse excel file error,cause:", e);
			throw e;
		} finally {
			try {
				close();
			} catch (Exception e) {
				logger.error("Colse excel file stream error,cause:", e);
				throw e;
			}
		}
	}

	/**
	 * 具体的解析逻辑
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected abstract Object parseLogic(Map<String, Object> params)
			throws Exception;

	protected Sheet getSheet(String name) {
		return this.workbook.getSheet(name);
	}

	protected Sheet getSheet(int index) {
		return this.workbook.getSheetAt(index);
	}

	protected Row getRow(Sheet sheet, int rowIndex) {
		return sheet.getRow(rowIndex);
	}

	protected Cell getCell(Row row, int cellIndex) {
		return row.getCell(cellIndex);
	}

	@SuppressWarnings("deprecation")
	protected Object getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING); // 强制设置为String类型，否则在读取数字时会出现多余的小数点(如:11,读取后为11.0)
				// return cell.getNumericCellValue();
				return cell.getStringCellValue();
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		default:
			return cell.getStringCellValue();
		}
	}

}
