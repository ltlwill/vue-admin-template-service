package com.efe.ms.bankservice.web.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efe.ms.bankservice.model.ExcelImp;
import com.efe.ms.bankservice.model.ExcelImpDetail;
import com.efe.ms.bankservice.model.User;
import com.efe.ms.bankservice.service.ExcelService;
import com.efe.ms.bankservice.vo.BusinessResult;
import com.efe.ms.bankservice.vo.PageInfoVO;
import com.efe.ms.bankservice.web.BaseController;

/**
 * excel业务控制器
 *
 * @author TianLong Liu
 * @date 2019年11月6日 下午5:24:46
 */
@RestController
@RequestMapping("/excel")
public class ExcelController extends BaseController {

	@Autowired
	private ExcelService excelService;

	@PostMapping("/upload")
	public BusinessResult uploadExcel(
			@RequestParam(value = "file", required = false) MultipartFile file,
			String id, String name) throws Exception {
		return jsonSuccess(excelService.uploadExcel(file, new User(id, name)));
	}

	/**
	 * 
	 * <p>
	 * 分页获取excel导入记录信息:
	 * </p>
	 * 
	 * @param
	 * @author TianLong Liu
	 * @date 2019年11月6日 下午5:27:39
	 * @return PageInfoVO<?>
	 */
	@GetMapping("/imp/list")
	public BusinessResult findExcelImpList(ExcelImp imp, PageInfoVO<?> page) {
		return jsonSuccess(excelService.findExcelImpList(imp, page));
	}

	/**
	 * 
	 * <p>
	 * 分页获取excel导入记录信息:
	 * </p>
	 * 
	 * @param
	 * @author TianLong Liu
	 * @date 2019年11月6日 下午5:27:39
	 * @return PageInfoVO<?>
	 */
	@GetMapping("/detail/list")
	public BusinessResult findExcelImpDetailList(ExcelImpDetail impDetail,
			PageInfoVO<?> page) {
		return jsonSuccess(excelService.findExcelImpDetailList(impDetail, page));
	}

}
