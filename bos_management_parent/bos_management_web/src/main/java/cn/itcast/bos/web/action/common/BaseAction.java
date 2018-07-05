package cn.itcast.bos.web.action.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model;
	
	public T getModel() {
		return model;
	}
	
	
	/**
	     子类action对象创建，父类BaseAction无参构造执行---目的获取实际类型（class）参数
	  1、T:代表任意类型。一般写大写字母T (大写字母即可)
	  2、BaseAction<Standard>:参数化类型(class)
	  3、<>中：实际类型参数
	 */
	public BaseAction() {
		try {
			//第一步：获取当前运行class（子类class）
			Class clzz = this.getClass();  //  cn.itcast.bos.web.action.base.StandardAction
			System.out.println(clzz);
			
			//第二步：获取父类参数化class(BaseAction的calss)
			/*Type getGenericSuperclass() 
			返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。 */
			Type type = clzz.getGenericSuperclass();  //cn.itcast.bos.web.action.common.BaseAction<cn.itcast.bos.domain.base.Standard>
			System.out.println(type);
			
			
			//type是顶级接口--将顶级接口转为子接口
			ParameterizedType pt = (ParameterizedType) type;
			
			//第三步：获取实际类型(class)参数
			 /*Type[] getActualTypeArguments() 
			 返回表示此类型实际类型参数的 Type 对象的数组。 */
			Type[] types = pt.getActualTypeArguments(); // [cn.itcast.bos.domain.base.Standard]
			//将Standard的type转为standard的Class
			Class clzzzzzzzzzzzzzzz = (Class) types[0];  //cn.itcast.bos.domain.base.Standard
			System.out.println(clzzzzzzzzzzzzzzz);
			
			//第四步：将实际class实例化
			model = (T) clzzzzzzzzzzzzzzz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	//接收提交当前页；每页显示记录数
	protected int page;
	protected int rows;
	public void setPage(int page) {
		this.page = page;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	/**
	  * @Description: 将分页查询到page对象转为json字符串
	  * @param page:查询得到page对象
	  * @param excludes:不需要转json属性
	  *	  
	 */
	public void java2Json(Page<T> page,  String[]excludes){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("total", page.getTotalElements());
			map.put("rows", page.getContent());
			
			//将不需要转为json字符串排除掉；由于快递员实体中fixedAreas集合属性会引起no-Session问题，将引起问题属性fixedAreas排除掉
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(excludes);
			
			String json = JSONObject.fromObject(map, jsonConfig).toString();
			System.out.println(json);
			
			ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 将list对象转为json字符串
	 * @param page:查询得到page对象
	 * @param excludes:不需要转json属性
	 *	  
	 */
	public void java2Json(List list,  String[]excludes){
		try {
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(excludes);
			
			String json = JSONArray.fromObject(list, jsonConfig).toString();
			System.out.println(json);
			
			ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
