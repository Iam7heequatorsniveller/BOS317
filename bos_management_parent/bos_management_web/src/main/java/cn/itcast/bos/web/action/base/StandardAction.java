package cn.itcast.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.service.Customer;
import cn.itcast.crm.service.CustomerService;

@Controller
@Scope("prototype")
//package  action  result
@Namespace("/")
@ParentPackage("struts-default")
@Results({
	@Result(name="list",type="redirect",location="/pages/base/standard.jsp"),
	@Result(name="unauthorized", location="/unauthorized.jsp")
})
@ExceptionMappings({
	@ExceptionMapping(exception="org.apache.shiro.authz.UnauthorizedException",result="unauthorized")
})
public class StandardAction extends BaseAction<Standard> {

	/*private Standard model = new Standard();
	public Standard getModel() {
		return model;
	}*/
	
	
	@Autowired
	private StandardService standardService;
	
	
	/**
	  * @Description: 收派标准保存
	 */
	@Action("standardAction_save")
	public String save() throws Exception {
		standardService.save(model);
		return "list";
	}
	
	@Autowired
	private CustomerService customerProxy;
	
	/*//接收提交当前页；每页显示记录数
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}*/


	/**
	  * @Description: 页面中datagrid组件发出ajax请求-获取收派标准数据（分页）
	  * @return json 对象
	  {
	     total:100,
	     rows:[]
	  }
	 */
	@Action("standardAction_pageQuery")
	public String pageQuery() throws Exception {
		//TODO 远程调用CRM系统查询所有的客户记录 - 为测试
//		List<Customer> list = customerProxy.findAll();
//		System.out.println(list);
		
		//封装PageBean对象
		//调用dao中方法：Page<T> findAll(Pageable pageable);
		//pageable对象封装页面提交当前页，每页记录数
		Pageable pageable = new PageRequest(page-1, rows);
		//page对象中包含：总记录数 ，当前页记录
		Page<Standard> page  = standardService.pageQuery(pageable);
		this.java2Json(page, null);
		
		/*System.out.println("总记录数："+page.getTotalElements());
		System.out.println("当前页记录："+page.getContent());
		//将java对象转为json格式
		//常见转josn工具包：fastjson,json-lib,gson,jackson
		//转集合，数组 使用:JSONArray
		//转对象使用:JSONObject
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		
		String json = JSONObject.fromObject(map).toString();
		System.out.println(json);
		
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		*/
		return NONE;
	}
	
	/**
	  * @Description: easyui组件comobobox下拉列表发出ajax请求
	  * @return json数组
	  *	  
	 */
	@Action("standardAction_findAll")
	public String findAll() throws Exception {
		List<Standard> list = standardService.findAll();
		
		this.java2Json(list, null);
//		String json = JSONArray.fromObject(list).toString();
//		System.out.println(json);
//		
//		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
//		ServletActionContext.getResponse().getWriter().write(json);
		return NONE;
	}

}
