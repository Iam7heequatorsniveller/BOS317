package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;
import cn.itcast.bos.utils.FileUtils;
import cn.itcast.bos.web.action.common.BaseAction;
import net.sf.json.JSONArray;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
	@Result(name="list",type="redirect",location="/pages/base/sub_area.jsp")
})
public class SubAreaAction extends BaseAction<SubArea> {

	@Autowired
	private SubAreaService subAreaService;
	
	/**
	  * @Description: 分区保存
	  * @return
	  * @throws Exception
	  *	  
	 */
	@Action("subareaAction_save")
	public String save() throws Exception {
		subAreaService.save(model);
		return "list";
	}
	
	@Action("subAreaAction_pageQuery")
	public String pageQuery() throws Exception {
		Pageable pageable = new PageRequest(page-1, rows);
		Page<SubArea> page = subAreaService.pageQuery(pageable);
		//noSession-转SubArea对象（对象中包含区域对象Area,在区域对象中包含subareas集合）
		//TODO 后期分区记录跟定区关联，还有有no-session
		this.java2Json(page, new String[]{"subareas", "fixedArea"});
		return NONE;
	}
	
	/**
	  * @Description: 查询未关联到定区分区记录
	  * @return json数组
	  * @throws Exception
	  *	  
	 */
	@Action("subAreaAction_listajax")
	public String listajax() throws Exception {
		List<SubArea> list = subAreaService.findNotAssocaiton();
		this.java2Json(list, new String[]{"subareas", "fixedArea"});
		return NONE;
	}
	
	/**
	  * @Description: 1、查询分区数据  2、通过POI将数据写入excel文件   3、将文件作为附件下载
	 */
	@Action("subAreaAction_exportXls")
	public String exportXls() throws Exception {
		List<SubArea> list = subAreaService.findAll();
		
		//第一步：创建excel文件对象  注意目标文件excel版本 --空白
		HSSFWorkbook workbook = new HSSFWorkbook();
		//第二步：创建标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		
		//第三步：创建行--先创建标题行（查询结果决定）创建数据行
		//第四步：创建单元格/给单元格赋值
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("编号");
		headRow.createCell(1).setCellValue("关键字");
		headRow.createCell(2).setCellValue("辅助关键字");
		headRow.createCell(3).setCellValue("区域");
		headRow.createCell(4).setCellValue("定区");
		
		
		HSSFRow dataRow = null;
		for (SubArea subArea : list) {
			dataRow = sheet.createRow(sheet.getLastRowNum()+1);;
			dataRow.createCell(0).setCellValue(subArea.getId());
			dataRow.createCell(1).setCellValue(subArea.getKeyWords());
			dataRow.createCell(2).setCellValue(subArea.getAssistKeyWords());
			dataRow.createCell(3).setCellValue(subArea.getArea().getText());
			dataRow.createCell(4).setCellValue(subArea.getFixedArea().getFixedAreaName());
		}
		//定义文件名称
		String filename = "分区.xls";
		
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//文件名问题
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		
		//文件下载：一个流两个头（文件MIME类型 ，文件打开方式（浏览器内部打开，附件下载））
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("content-type", "application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment;filename="+filename);
		
		workbook.write(response.getOutputStream());
		return NONE;
	}
	
	/**
	  * @Description: 根据省份统计每个省份分区个数
	  * @return json 二维数组
	  [
			['北京市',   45],
			['河北省',   268],
			['山东省',   85]
	  ]
	 */
	@Action("subAreaAction_showChart")
	public String showChart() throws Exception {
		List<Object[]> list = subAreaService.showChart();
		this.java2Json(list, null);
		return NONE;
	}
	
	public static void main(String[] args) {
		List<Object[]> list = new ArrayList<>();
		Object[] obj1 = new Object[2];
		obj1[0] = "北京市";
		obj1[1] = 45;
		
		Object[] obj2 = new Object[2];
		obj2[0] = "河北省";
		obj2[1] = 268;
		
		list.add(obj1);
		list.add(obj2);
		String json = JSONArray.fromObject(list).toString();
		System.out.println(json);
	}
}
