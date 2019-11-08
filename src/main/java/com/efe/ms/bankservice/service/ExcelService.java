package com.efe.ms.bankservice.service;

import org.springframework.web.multipart.MultipartFile;

import com.efe.ms.bankservice.model.ExcelImp;
import com.efe.ms.bankservice.model.ExcelImpDetail;
import com.efe.ms.bankservice.model.User;
import com.efe.ms.bankservice.vo.PageInfoVO;

/**
 * excel业务
 *
 * @author TianLong Liu
 * @date 2019年11月6日 下午5:17:28
 */
public interface ExcelService {
	
	/**
	 * 
	 * <p>上传excel: </p>
	 * @param
	 * @author TianLong Liu
	 * @date 2019年11月7日 上午11:21:42
	 * @return ExcelImp
	 */
	ExcelImp uploadExcel(MultipartFile file,User user) throws Exception;
	
	/**
	 * 
	 * <p>分页获取excel导入信息: </p>
	 * @param
	 * @author TianLong Liu
	 * @date 2019年11月6日 下午5:20:18
	 * @return PageInfoVO<?>
	 */
	PageInfoVO<?> findExcelImpList(ExcelImp imp,PageInfoVO<?> page);
	
	/**
	 * 分页获取excel导入明细信息
	 * 
	 * @param
	 * @author TianLong Liu
	 * @date 2019年11月6日 下午5:20:30
	 * @return PageInfoVO<?>
	 */
	PageInfoVO<?> findExcelImpDetailList(ExcelImpDetail impDetail,PageInfoVO<?> page);

}
