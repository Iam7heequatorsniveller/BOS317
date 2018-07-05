
package cn.itcast.bos.web.action.system;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;
import cn.itcast.bos.utils.Md5Util;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
	@Result(name="index",type="redirect",location="/index.jsp"),
	@Result(name="list",type="redirect",location="/pages/system/user.jsp"),
	@Result(name="login",location="/login.jsp")
})
public class UserAction extends BaseAction<User> {

	@Autowired
	private UserService userService;
	
	//页面提交验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}


	/**
	  * @Description: 基于shiro框架实现登陆
	 */
	@Action("userAction_login")
	public String login() throws Exception {
//		if(StringUtils.isNotBlank(checkcode)){
//			String  realChecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
//			if(checkcode.equals(realChecode)){
				//获取当前登陆“用户”
				Subject subject = SecurityUtils.getSubject();
				//判断当前用户登陆状态
				if(subject.isAuthenticated()){
					return "index";
				}else{
					//开始认证  认证状态：未认证
					//创建认证令牌-用户名密码令牌
					AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), Md5Util.encode(model.getPassword()));
					try {
						subject.login(token);
						//用户认证通过 subject对象变为 认证通过
						//将用户登陆信息存在session
						User user = (User) subject.getPrincipal();
						ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
						return "index";
						
					} catch (Exception e) {
						if(e instanceof UnknownAccountException){
							this.addActionError("用户名输入错误");   //login.jsp中 通过struts2标签展示错误信息  <s:actionerror>
						}
						if(e instanceof IncorrectCredentialsException){
							
						}
						//用户名密码有误
						e.printStackTrace();
					}
				}
//			}
//		}
		return "login";
	}
	
	/**
	  * @Description: 用户退出
	  * @return
	  * @throws Exception
	  *	  
	 */
	@Action("userAction_logout")
	public String logout() throws Exception {
		//获取当前登陆“用户”
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
//		HttpSession session = ServletActionContext.getRequest().getSession();
//		Object attribute = session.getAttribute("loginUser");
		return "login";
	}
	
	private Integer[]roleIds;
	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}


	/**
	  * @Description: 保存用户，用户关联角色
	 */
	@Action("userAction_save")
	public String save() throws Exception {
		userService.save(model, roleIds);
		return "list";
	}
}
