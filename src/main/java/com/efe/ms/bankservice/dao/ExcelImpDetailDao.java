package com.efe.ms.bankservice.dao;

import java.util.List;

import com.efe.ms.bankservice.model.ExcelImpDetail;

/**
 * excel导入明细dao
 *
 * @author TianLong Liu
 * @date 2019年11月6日 下午3:40:19
 */
public interface ExcelImpDetailDao extends BaseDao<ExcelImpDetail>{
	
	void deleteByIds(List<String> ids);
	
	void deleteByImpIds(List<String> impIds);

}
