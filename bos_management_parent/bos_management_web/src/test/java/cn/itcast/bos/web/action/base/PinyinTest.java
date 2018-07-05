package cn.itcast.bos.web.action.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Code;
import org.junit.Test;

import cn.itcast.bos.utils.PinYin4jUtils;

public class PinyinTest {

	/**
	  * @Description: 18	QY018	石家庄市		桥东区	130103	河北省	

	  *	  
	 */
	@Test
	public void test() {
		String province = "河北省";
		String city = "石家庄市";
		String district = "桥东区";
		
		//简码：HBSJZQD
		province = province.substring(0, province.length()-1);
		city = city.substring(0, city.length()-1);
		district = district.substring(0, district.length()-1);
		
		String all = province+city+district;
		System.out.println(all);
		
		String[] strings = PinYin4jUtils.getHeadByString(all);
		String shortcode = StringUtils.join(strings);
		System.out.println(shortcode);
		
		//城市编码：shijiazhuang
		String citycode = PinYin4jUtils.hanziToPinyin(city, "");
		System.out.println(citycode);
		
	}

}
