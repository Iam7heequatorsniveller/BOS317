package cn.itcast.bos.dao.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class POITest {
	//需求：读取本地磁盘xls格式excel文件，将区域数据输出在控制台
	//H:\\北京Java317期_速运快递\\速运快递项目-day04\\资料\\03_区域测试数据\\区域导入测试数据.xls
	public static void main(String[] args) throws Exception{
		String pathname = "H:\\北京Java317期_速运快递\\速运快递项目-day04\\资料\\03_区域测试数据\\区域导入测试数据.xls";
		//		HSSFWorkbook--03版本
		//		XSSFWorkbook--07版本
		//創建excel文件對象
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(pathname )));
		
		//获取标签页
		HSSFSheet sheet = workbook.getSheetAt(0);
		//获取行
		for (Row row : sheet) {
			System.out.println();
			//获取单元格    获取单元格中内容
			for (Cell cell : row) {
				System.out.print(cell.getStringCellValue() +" ");
			}
		}
		workbook.close();
	}
}
