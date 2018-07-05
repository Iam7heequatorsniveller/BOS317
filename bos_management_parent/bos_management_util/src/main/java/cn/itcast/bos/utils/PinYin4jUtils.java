package cn.itcast.bos.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYin4jUtils {
	
	private static Map<String, List<String>> pinyinMap = new HashMap<String, List<String>>();    
	private static long count = 0;    
	
	static{
		initPinyin("/duoyinzi_dic.txt");    
	}
	
	/**  
     * 初始化 所有的多音字词组  
     *   
     * @param fileName  
     */    
    public static void initPinyin(String fileName) {    
        // 读取多音字的全部拼音表;    
        InputStream file = PinyinHelper.class.getResourceAsStream(fileName);    
    
        BufferedReader br = new BufferedReader(new InputStreamReader(file));    
    
        String s = null;    
        try {    
            while ((s = br.readLine()) != null) {    
    
                if (s != null) {    
                    String[] arr = s.split("#");    
                    String pinyin = arr[0];    
                    String hanzi = arr[1];    
    
                    if(hanzi!=null){    
                        String[] strs = hanzi.split(" ");    
                        List<String> list = Arrays.asList(strs);    
                        pinyinMap.put(pinyin, list);    
                    }    
                }    
            }    
    
        } catch (IOException e) {    
            e.printStackTrace();    
        }finally{    
            try {    
                br.close();    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        }    
    }    
    
	/**
	 * 将字符串转换成拼音数组
	 * 
	 * @param src
	 * @return
	 */
	public static String[] stringToPinyin(String src) {
		return stringToPinyin(src, false, null);
	}

	/**
	 * 将字符串转换成拼音数组
	 * 
	 * @param src
	 * @return
	 */
	public static String[] stringToPinyin(String src, String separator) {

		return stringToPinyin(src, true, separator);
	}

	/**
	 * 将字符串转换成拼音数组
	 * 
	 * @param src
	 * @param isPolyphone
	 *            是否查出多音字的所有拼音
	 * @param separator
	 *            多音字拼音之间的分隔符
	 * @return
	 */
	public static String[] stringToPinyin(String src, boolean isPolyphone,
			String separator) {
		// 判断字符串是否为空
		if ("".equals(src) || null == src) {
			return null;
		}
		char[] srcChar = src.toCharArray();
		int srcCount = srcChar.length;
		String[] srcStr = new String[srcCount];

		for (int i = 0; i < srcCount; i++) {
			srcStr[i] = charToPinyin(srcChar[i], isPolyphone, separator);
		}
		return srcStr;
	}

	/**
	 * 将单个字符转换成拼音
	 * 
	 * @param src
	 * @return
	 */
	public static String charToPinyin(char src, boolean isPolyphone,
			String separator) {
		// 创建汉语拼音处理类
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出设置，大小写，音标方式
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		StringBuffer tempPinying = new StringBuffer();

		// 如果是中文
		if (src > 128) {
			try {
				// 转换得出结果
				String[] strs = PinyinHelper.toHanyuPinyinStringArray(src,
						defaultFormat);

				// 是否查出多音字，默认是查出多音字的第一个字符
				if (isPolyphone && null != separator) {
					for (int i = 0; i < strs.length; i++) {
						tempPinying.append(strs[i]);
						if (strs.length != (i + 1)) {
							// 多音字之间用特殊符号间隔起来
							tempPinying.append(separator);
						}
					}
				} else {
					tempPinying.append(strs[0]);
				}

			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} else {
			tempPinying.append(src);
		}

		return tempPinying.toString();

	}

	public static String hanziToPinyin(String hanzi) {
		return hanziToPinyin(hanzi, " ");
	}

	 /**  
     * 将某个字符串的首字母 大写  
     * @param str  
     * @return  
     */    
    public static String convertInitialToUpperCase(String str){    
        if(str==null){    
            return null;    
        }    
        StringBuffer sb = new StringBuffer();    
        char[] arr = str.toCharArray();    
        for(int i=0;i<arr.length;i++){    
            char ch = arr[i];    
            if(i==0){    
                sb.append(String.valueOf(ch).toUpperCase());    
            }else{    
                sb.append(ch);    
            }    
        }    
            
        return sb.toString();    
    } 
        
            
	
	
	/**
	 * 将汉字转换成拼音
	 * 
	 * @param hanzi
	 * @param separator
	 * @return
	 */
	public static String hanziToPinyin(String hanzi, String separator) {    
    
        StringBuffer pinyin = new StringBuffer();    
    
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();    
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);    
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);    
    
        char[] arr = hanzi.toCharArray();    
    
        for (int i = 0; i < arr.length; i++) {    
    
            char ch = arr[i];    
    
            if (ch > 128) { // 非ASCII码    
                // 取得当前汉字的所有全拼    
                try {    
    
                    String[] results = PinyinHelper.toHanyuPinyinStringArray(    
                            ch, defaultFormat);    
    
                    if (results == null) {  //非中文    
    
                        return "";    
                    } else {    
    
                        int len = results.length;    
    
                        if (len == 1) { // 不是多音字    
    
//                          pinyin.append(results[0]);    
                            String py = results[0];         
                            if(py.contains("u:")){  //过滤 u:    
                                py = py.replace("u:", "v");    
                                System.out.println("filter u:"+py);    
                            }    
                            pinyin.append(convertInitialToUpperCase(py));    
                                
                        }else if(results[0].equals(results[1])){    //非多音字 有多个音，取第一个    
                                
//                          pinyin.append(results[0]);    
                            pinyin.append(convertInitialToUpperCase(results[0]));    
                                
                        }else { // 多音字    
                                
//                            System.out.println("多音字："+ch);    
                                
                            int length = hanzi.length();    
                                
                            boolean flag = false;    
                                
                            String s = null;    
                                
                            List<String> keyList =null;    
                                
                            for (int x = 0; x < len; x++) {    
                                    
                                String py = results[x];    
                                    
                                if(py.contains("u:")){  //过滤 u:    
                                    py = py.replace("u:", "v");    
                                    System.out.println("filter u:"+py);    
                                }    
    
                                keyList = pinyinMap.get(py);    
                                    
                                if (i + 3 <= length) {   //后向匹配2个汉字  大西洋     
                                    s = hanzi.substring(i, i + 3);    
                                    if (keyList != null && (keyList.contains(s))) {    
//                                  if (value != null && value.contains(s)) {    
    
                                        System.out.println("last 2 > " + py);    
//                                      pinyin.append(results[x]);    
                                        pinyin.append(convertInitialToUpperCase(py));    
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                                    
                                if (i + 2 <= length) {   //后向匹配 1个汉字  大西    
                                    s = hanzi.substring(i, i + 2);    
                                    if (keyList != null && (keyList.contains(s))) {    
    
                                        System.out.println("last 1 > " + py);    
//                                      pinyin.append(results[x]);    
                                        pinyin.append(convertInitialToUpperCase(py));    
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                                    
                                if ((i - 2 >= 0) && (i+1<=length)) {  // 前向匹配2个汉字 龙固大    
                                    s = hanzi.substring(i - 2, i+1);    
                                    if (keyList != null && (keyList.contains(s))) {    
                                            
                                        System.out.println("before 2 < " + py);    
//                                      pinyin.append(results[x]);    
                                        pinyin.append(convertInitialToUpperCase(py));    
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                                    
                                if ((i - 1 >= 0) && (i+1<=length)) {  // 前向匹配1个汉字   固大    
                                    s = hanzi.substring(i - 1, i+1);    
                                    if (keyList != null && (keyList.contains(s))) {    
                                            
                                        System.out.println("before 1 < " + py);    
//                                      pinyin.append(results[x]);    
                                        pinyin.append(convertInitialToUpperCase(py));    
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                                    
                                if ((i - 1 >= 0) && (i+2<=length)) {  //前向1个，后向1个      固大西    
                                    s = hanzi.substring(i - 1, i+2);    
                                    if (keyList != null && (keyList.contains(s))) {    
                                            
                                        System.out.println("before last 1 <> " + py);    
//                                      pinyin.append(results[x]);    
                                        pinyin.append(convertInitialToUpperCase(py));    
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                            }    
                                
                            if (!flag) {    //都没有找到，匹配默认的 读音  大     
                                    
                                s = String.valueOf(ch);    
                                    
                                for (int x = 0; x < len; x++) {    
                                        
                                    String py = results[x];    
                                        
                                    if(py.contains("u:")){  //过滤 u:    
                                        py = py.replace("u:", "v");    
                                        System.out.println("filter u:");    
                                    }    
                                        
                                    keyList = pinyinMap.get(py);    
                                        
                                    if (keyList != null && (keyList.contains(s))) {    
                                            
//                                        System.out.println("default = " + py);    
//                                      pinyin.append(results[x]);  //如果不需要拼音首字母大写 ，直接返回即可    
                                        pinyin.append(convertInitialToUpperCase(py));//拼音首字母 大写    
                                        break;    
                                    }    
                                }    
                            }    
                        }    
                    }    
    
                } catch (BadHanyuPinyinOutputFormatCombination e) {    
                    e.printStackTrace();    
                }    
            } else {    
                pinyin.append(arr[i]);    
            }    
        }    
        return pinyin.toString();    
    }

	/**
	 * 将字符串数组转换成字符串
	 * 
	 * @param str
	 * @param separator
	 *            各个字符串之间的分隔符
	 * @return
	 */
	public static String stringArrayToString(String[] str, String separator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			sb.append(str[i]);
			if (str.length != (i + 1)) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 简单的将各个字符数组之间连接起来
	 * 
	 * @param str
	 * @return
	 */
	public static String stringArrayToString(String[] str) {
		return stringArrayToString(str, "");
	}

	/**
	 * 将字符数组转换成字符串
	 * 
	 * @param str
	 * @param separator
	 *            各个字符串之间的分隔符
	 * @return
	 */
	public static String charArrayToString(char[] ch, String separator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
			if (ch.length != (i + 1)) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符数组转换成字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String charArrayToString(char[] ch) {
		return charArrayToString(ch, " ");
	}

	/**
	 * 取汉字的首字母
	 * 
	 * @param src
	 * @param isCapital
	 *            是否是大写
	 * @return
	 */
	public static char[] getHeadByChar(char src, boolean isCapital) {
		// 如果不是汉字直接返回
		if (src <= 128) {
			return new char[] { src };
		}
		// 获取所有的拼音
		String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);

		// 创建返回对象
		int polyphoneSize = pinyingStr.length;
		char[] headChars = new char[polyphoneSize];
		int i = 0;
		// 截取首字符
		for (String s : pinyingStr) {
			char headChar = s.charAt(0);
			// 首字母是否大写，默认是小写
			if (isCapital) {
				headChars[i] = Character.toUpperCase(headChar);
			} else {
				headChars[i] = headChar;
			}
			i++;
		}

		return headChars;
	}

	/**
	 * 取汉字的首字母(默认是大写)
	 * 
	 * @param src
	 * @return
	 */
	public static char[] getHeadByChar(char src) {
		return getHeadByChar(src, true);
	}

	/**
	 * 查找字符串首字母
	 * 
	 * @param src
	 * @return
	 */
	public static String[] getHeadByString(String src) {
		return getHeadByString(src, true);
	}

	/**
	 * 查找字符串首字母
	 * 
	 * @param src
	 * @param isCapital
	 *            是否大写
	 * @return
	 */
	public static String[] getHeadByString(String src, boolean isCapital) {
		return getHeadByString(src, isCapital, null);
	}

	/**
	 * 查找字符串首字母
	 * 
	 * @param src
	 * @param isCapital
	 *            是否大写
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String[] getHeadByString(String src, boolean isCapital,
			String separator) {
		char[] chars = src.toCharArray();
		String[] headString = new String[chars.length];
		int i = 0;
		for (char ch : chars) {

			char[] chs = getHeadByChar(ch, isCapital);
			StringBuffer sb = new StringBuffer();
			if (null != separator) {
				int j = 1;

				for (char ch1 : chs) {
					sb.append(ch1);
					if (j != chs.length) {
						sb.append(separator);
					}
					j++;
				}
			} else {
				sb.append(chs[0]);
			}
			headString[i] = sb.toString();
			i++;
		}
		return headString;
	}
	
	public static void main(String[] args) {
		// pin4j 简码 和 城市编码 
//		StriNG S1 = "中华人民共和国"; 
//		STRING[] HEADARRAY = GETHEADBYSTRING(S1); // 获得每个汉字拼音首字母
//		SYSTEM.OUT.PRINTLN(ARRAYS.TOSTRING(HEADARRAY));
//		
//		STRING S2 ="长城" ; 
//		SYSTEM.OUT.println(Arrays.toString(stringToPinyin(s2,true,",")));
		
//		String s3 ="长";
//		System.out.println(Arrays.toString(stringToPinyin(s3,true,",")));
		
		String ret = hanziToPinyin("长安");
		System.out.println(ret);
		
//		String[] headByString = getHeadByString("长安");
//		String ret1 = StringUtils.join(headByString);
//		System.out.println(ret1);
//		String ret = hanziToPinyin("长");
//		System.out.println(ret);
	}
}
