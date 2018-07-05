package cn.itcast.bos.realms;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.dao.system.UserDao;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;

/**
 * @Description: 查询安全数据（用户认证过程查询用户信息；用户授权过程查询用户权限信息）
 * 1、认证      2、授权
 * @Title: BosRelam.java
 * @date 2018年6月26日 下午12:12:26
 */
@Component("bosRealm")
public class BosRelam extends AuthorizingRealm{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PermissionDao permissionDao;

	/**
	  * @Description: 返回当前用户认证信息（判断用户是否合法）
	  * @param token :Subject调用login方法参数
	  * 认证逻辑：1、根据用户名查询数据库中真实密码   2、比对密码是否正确框架实现
	*/
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		//用户在登陆表单输入值
		String username = usernamePasswordToken.getUsername();
		User user = userDao.findByUsername(username);
		if(user==null){
			//用户名错误
			return null; //shiro框架抛出异常：未知账户异常
		}
		//参数一：主角信息    参数二：用户真实密码  
		//如果密码比对失败shiro框架抛异常
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
	}


	
	/**
	  * @Description: 授权（查询当前用户是否有权限）
	  * 1、当使用shiro框架提供给url验证方式，使用权限，角色过滤器调用此方法查询用户权限。
	  * 2、当使用shiro框架提供给注解验证方式，代理对象调用此方法查询用户权限。
	  * 3、当使用shiro框架提供页面标签方式验证权限，使用验证权限，验证角色的标识，调用此方法
	  * 4、当使用代码级别验证权限
	*/
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		System.out.println("开始授权");
		List<Role> roleList = null;
		List<Permission> permissionList=null;
		//创建授权信息对象（封装当前用户拥有角色，权限）
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//动态根据用户ID查询用户权限，角色
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//如果是管理员用户，拥有所有的角色，权限
		if(user.getUsername().equals("admin")){
			roleList = roleDao.findAll();
			permissionList = permissionDao.findAll();
		}else{
			//其他用户根据用户ID查询
			roleList = roleDao.findByUserId(user.getId());
			//注意：对查询到权限数据去重
			permissionList = permissionDao.findByUserId(user.getId());
		}
		
		if(roleList!=null && !roleList.isEmpty()){
			for (Role role : roleList) {
				info.addRole(role.getKeyword());  //简单授权信息对象中不能添加null,""
			}
		}
		
		if(permissionList!=null && !permissionList.isEmpty()){
			for (Permission permission : permissionList) {
				info.addStringPermission(permission.getKeyword());
			}
		}
		return info;
	}

	
}
