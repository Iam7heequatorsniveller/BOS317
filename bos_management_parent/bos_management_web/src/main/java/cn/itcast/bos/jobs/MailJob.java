package cn.itcast.bos.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;

import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.take_delivery.WorkBill;

@Component("mailJob")
public class MailJob {
	
	@Autowired
	private WorkBillDao workBillDao;
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	/**
	  * @Description: 定时发送当日工单数据
	 */
	public void sendMail(){
		
		try {
			//TODO 下去改为查询当日
			List<WorkBill> list = workBillDao.findAll();
			
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
			helper.setFrom(mailSender.getUsername());
			helper.setTo("lvhonglong816@126.com");
			helper.setSubject(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "日工单数据");
			// 第二个参数true，表示text的内容为html
			// <img/>标签，src='cid:file'，'cid'是contentId的缩写，'file'是一个标记，需要在后面的代码中调用MimeMessageHelper的addInline方法替代成文件
			String domHtml = "";
			for (WorkBill workBill : list) {
				domHtml += "<tr><td>" + workBill.getId() + "</td><td>" + workBill.getPickstate() + "</td>" + "<td>"
						+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(workBill.getBuildtime()) + "</td>" + "<td>"
						+ workBill.getCourier().getName() + "</td></tr>";
			}
			helper.setText("<!DOCTYPE html><html><body>" + "<table border='1' bordercolor='#000000' style='width: 99%'>"
					+ "<tr>" + "<th colspan='4'>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</th>"
					+ "</tr><tr>" + "<td>工单编号</td>" + "<td>取件状态</td>" + "<td>生成时间</td>" + "<td>快递员</td></tr>" 
					+ domHtml
					// + "<tr><td>0100001</td><td>取件中</td><td>2018.01.04 11:22:34</td><td>张三</td></tr>"
					+ "</table>" + "<br><br><br>" + "<img src='cid:file'/></body></html>", true);
			// 获取当前的spring容器，任何java类中适用
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			ServletContext servletContext = webApplicationContext.getServletContext();
			ServletContextResource resource = new ServletContextResource(servletContext, "/mail/logo.png");
			// FileSystemResource file = new
			// FileSystemResource("H:\\test\\logo.png");
			helper.addInline("file", resource);
			mailSender.send(msg);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
