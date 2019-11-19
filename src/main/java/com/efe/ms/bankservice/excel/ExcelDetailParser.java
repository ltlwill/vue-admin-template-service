package com.efe.ms.bankservice.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;

import com.efe.ms.bankservice.model.ExcelImpDetail;

/**
 * 模板字典文件解析
 * 
 * @author liutianlong
 *
 */
public class ExcelDetailParser extends BaseExcelParser {
	// 标题行索引
	private final static int TITLE_ROW_INDEX = 0;
	// 数据行开始索引
	private final static int DATA_ROW_BEGIN_INDEX = 1;

	@Override
	protected Object parseLogic(Map<String, Object> params) throws Exception {
		logger.debug("-----开始解析（文件名：" + srcFile.getName() + "）-----");
		List<ExcelImpDetail> result = new ArrayList<ExcelImpDetail>();
		Sheet sheet = workbook.getSheetAt(0);
		parseSheet(sheet, result);
		logger.debug("-----结束解析（文件名：" + srcFile.getName() + "）-----");
		return result;
	}

	private void parseSheet(Sheet sheet, List<ExcelImpDetail> result)
			throws Exception {
		int continueNullNum = 0;
		Row row = null;
		Map<String, Integer> titleIndexMap = getTitleIndexMap(sheet
				.getRow(TITLE_ROW_INDEX));
		for (int rowIndex = DATA_ROW_BEGIN_INDEX; rowIndex <= sheet
				.getLastRowNum(); rowIndex++) {
			if (continueNullNum > MAX_NULL_COL_NUM) {
				break;
			}
			row = sheet.getRow(rowIndex);
			if (row == null) {
				continueNullNum++;
				continue;
			}
			createDetail(row, result, titleIndexMap);
		}
	}

	/*
	 * 获取标题名称和对应的列索引位置
	 */
	private Map<String, Integer> getTitleIndexMap(Row row) {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		int begin = row.getFirstCellNum(), last = row.getLastCellNum();
		Cell cell = null;
		Object cellVal = null;
		String title = null;
		for (int idx = begin; idx <= last; idx++) {
			cell = row.getCell(idx, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			cellVal = getCellValue(cell);
			title = cellVal == null ? null : String.valueOf(cellVal);
			if(StringUtils.isBlank(title)){
				continue;
			}
			map.put(title.trim(), idx);
		}
		return map;
	}

	private void createDetail(Row row, List<ExcelImpDetail> result,
			Map<String, Integer> titleIndexMap) throws Exception {
		Cell cell = null;
		Object cellVal = null;

		Iterator<Map.Entry<String, Integer>> iterator = titleIndexMap
				.entrySet().iterator();
		Map.Entry<String, Integer> entry = null;
		String title = null;
		String fieldName = null;
		Integer index = null;
		ExcelImpDetail detail = new ExcelImpDetail();
		while (iterator.hasNext()) {
			entry = iterator.next();
			title = entry.getKey();
			index = entry.getValue();
			fieldName = ExcelImpDetail.TitleFieldNameMap.getFieldName(title);
			if (StringUtils.isBlank(title) || StringUtils.isBlank(fieldName)) {
				continue;
			}
			cell = row.getCell(index, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			cellVal = getCellValue(cell);
			BeanUtils.setProperty(detail, fieldName, cellVal);
		}
		result.add(detail);
	}
}
