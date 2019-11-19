package com.efe.ms.bankservice.service;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.efe.ms.bankservice.config.EnvironmentProperties;
import com.efe.ms.bankservice.dao.ExcelImpDao;
import com.efe.ms.bankservice.dao.ExcelImpDetailDao;
import com.efe.ms.bankservice.excel.ExcelFactory;
import com.efe.ms.bankservice.model.ExcelImp;
import com.efe.ms.bankservice.model.ExcelImpDetail;
import com.efe.ms.bankservice.model.User;
import com.efe.ms.bankservice.vo.ExcelImpDetailVO;
import com.efe.ms.bankservice.vo.PageInfoVO;

/**
 * exxcel业务实现类
 *
 * @author TianLong Liu
 * @date 2019年11月6日 下午5:18:13
 */
@Service
public class ExcelServiceImpl extends BaseServiceImpl implements ExcelService {

	@Autowired
	private EnvironmentProperties envProps;

	@Autowired
	private ExcelImpDao excelImpDao;

	@Autowired
	private ExcelImpDetailDao excelImpDetailDao;

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ExcelImp uploadExcel(MultipartFile file, User user) throws Exception {
		if (file == null) {
			throw new RuntimeException("没有获取到excel文件信息");
		}
		// 保存文件并记录数据
		String absPath = saveFileToDisk(file);
		String fileName = absPath
				.substring(absPath.lastIndexOf(File.separator) + 1);
		// 解析excel
		List<ExcelImpDetail> details = null;
		int status = ExcelImp.Status.SUCCESS;
		try {
			Object obj = ExcelFactory.getInstance().getExcelImpDetailParser()
					.parse(absPath, null);
			details = obj == null ? null : (List<ExcelImpDetail>) obj;
		} catch (Exception e) {
			status = ExcelImp.Status.FAIL;
			logger.error("解析文件失败", e);
		}
		// 保存上传记录
		ExcelImp imp = createExcelImp(file, user, fileName, absPath, status);
		excelImpDao.add(imp);
		// 保存明细
		Optional.ofNullable(details).orElse(Collections.emptyList())
				.forEach(detail -> {
					detail.setFileLowerName(imp.getFileLowerName());
					detail.setImpId(Long.valueOf(imp.getId()));
					detail.setUserId(imp.getUserId());
					detail.setUserName(imp.getUserName());
					detail.setImportTime(imp.getImportTime());
					excelImpDetailDao.add(detail);
				});
		return imp;
	}

	@Transactional
	public ExcelImp uploadExcel2(MultipartFile file, User user)
			throws Exception {
		if (file == null) {
			throw new RuntimeException("没有获取到excel文件信息");
		}
		// 保存文件并记录数据
		String absPath = saveFileToDisk(file);
		String fileName = absPath
				.substring(absPath.lastIndexOf(File.separator) + 1);
		// 解析excel
		List<ExcelImpDetailVO> details = null;
		int status = ExcelImp.Status.SUCCESS;
		try {
			// details = readExcel(file);
			details = readExcel(new File(absPath));
		} catch (Exception e) {
			status = ExcelImp.Status.FAIL;
			logger.error("解析文件失败", e);
		}
		// 保存上传记录
		ExcelImp imp = createExcelImp(file, user, fileName, absPath, status);
		excelImpDao.add(imp);
		// 保存明细
		Optional.ofNullable(details)
				.orElse(Collections.emptyList())
				.forEach(vo -> {
					ExcelImpDetail detail = new ExcelImpDetail();
					// BeanUtils.copyProperties(vo, detail);
						BeanCopier.create(ExcelImpDetailVO.class,
								ExcelImpDetail.class, false).copy(vo, detail,
								null);
						detail.setImpId(Long.valueOf(imp.getId()));
						detail.setUserId(imp.getUserId());
						detail.setUserName(imp.getUserName());
						detail.setImportTime(imp.getImportTime());
						excelImpDetailDao.add(detail);
					});
		return imp;
	}

	@Override
	public PageInfoVO<?> findExcelImpList(ExcelImp imp, PageInfoVO<?> page) {
		List<ExcelImp> list = excelImpDao.getListPage(imp, page.toRowBounds());
		return page.with(list);
	}

	@Override
	public PageInfoVO<?> findExcelImpDetailList(ExcelImpDetail impDetail,
			PageInfoVO<?> page) {
		List<ExcelImpDetail> list = excelImpDetailDao.getListPage(impDetail,
				page.toRowBounds());
		return page.with(list);
	}

	private ExcelImp createExcelImp(final MultipartFile file, User user,
			String fileName, String absPath, int status) {
		ExcelImp imp = new ExcelImp();
		imp.setFileName(fileName);
		imp.setFileLowerName(fileName.toLowerCase());
		imp.setFileExt(getExtensionName(fileName));
		imp.setAbsPath(absPath);
		imp.setStatus(status);
		imp.setUserId(user == null ? null : user.getId());
		imp.setUserName(user == null ? null : user.getName());
		imp.setImportTime(new Date());
		return imp;
	}

	/*
	 * 获取文件名称的扩展名
	 */
	private String getExtensionName(String name) {
		return name.substring(name.lastIndexOf(".") + 1);
	}

	/*
	 * 读取excel数据
	 */
	@SuppressWarnings("unused")
	private List<ExcelImpDetailVO> readExcel(final MultipartFile file)
			throws Exception {
		List<ExcelImpDetailVO> list = EasyExcel.read(file.getInputStream())
				.head(ExcelImpDetailVO.class).sheet(0).doReadSync();
		return list;
	}

	/*
	 * 读取excel数据
	 */
	private List<ExcelImpDetailVO> readExcel(final File file) throws Exception {
		List<ExcelImpDetailVO> list = EasyExcel.read(file)
				.head(ExcelImpDetailVO.class).sheet(0).doReadSync();
		return list;
	}

	/*
	 * 保存文件
	 */
	private String saveFileToDisk(MultipartFile file) {
		String baseDir = this.envProps.getVirtualDir(), fileName = file
				.getOriginalFilename(), absPath = getPath4ExistsPath(baseDir
				+ File.separator + fileName);
		File destFile = new File(absPath);
		try {
			// 如果父路径不存在
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			file.transferTo(destFile);
		} catch (Exception e) {
			// 删除文件
			try {
				FileUtils.forceDelete(destFile);
			} catch (Exception ex) {
				logger.error("强制删除文件失败");
			}
			throw new RuntimeException("上传文件时保存文件到服务器出错", e);
		}
		return absPath;
	}

	/**
	 * 获取文件目录（如果文件已存在，则会自动生成 (数字递增规则)的路径）
	 * 
	 * @param path
	 * @return
	 */
	private String getPath4ExistsPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return path;
		}
		String prefix = path.substring(0, path.lastIndexOf(File.separator) + 1), name = path
				.substring(path.lastIndexOf(File.separator) + 1), ext = name
				.substring(name.lastIndexOf(".")), sortName = name.substring(0,
				name.lastIndexOf("."));
		Pattern pattern = Pattern.compile("^(.*)\\(\\d+\\)$"); // 匹配以"(数字)"字符串结尾
		int num = 1;
		if (pattern.matcher(sortName).matches()) {
			String numStr = sortName.substring(sortName.lastIndexOf("(") + 1,
					sortName.lastIndexOf(")"));
			sortName = sortName.substring(0, sortName.lastIndexOf("("));
			num = Integer.parseInt(numStr);
		}
		path = prefix + sortName + "(" + (num + 1) + ")" + ext;
		// 递归
		return getPath4ExistsPath(path);
	}

	@Transactional
	@Override
	public void deleteExcelImpByIds(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new RuntimeException("无效的参数");
		}
		excelImpDao.deleteByIds(ids);
	}

	@Transactional
	@Override
	public void deleteExcelImpDetailByIds(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new RuntimeException("无效的参数");
		}
		excelImpDetailDao.deleteByIds(ids);
	}

	@Transactional
	@Override
	public void deleteExcelImpDetailByImpIds(List<String> impIds) {
		if (CollectionUtils.isEmpty(impIds)) {
			throw new RuntimeException("无效的参数");
		}
		excelImpDetailDao.deleteByImpIds(impIds);
	}

	@Transactional
	@Override
	public void deleteExcelImpInfo(List<String> ids) {
		deleteExcelImpByIds(ids);
		deleteExcelImpDetailByImpIds(ids);
	}
}
