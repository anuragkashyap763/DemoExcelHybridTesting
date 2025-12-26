package com.ibm.webApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	static XSSFWorkbook wb;
	
	static XSSFSheet sh;
	
	static FileInputStream fin;
	
	static File fileDataSheet;
	
	public static void loadData() throws IOException {
		fileDataSheet = new File(""C:\Users\AnuragKashyap\OneDrive - IBM\Desktop\Test using excel.xlsx"");
		fin = new FileInputStream(fileDataSheet);
		
		wb = new XSSFWorkbook(fin);
		
		sh = wb.getSheet("keywords");
	}
	public static int rowCount() {
	    return sh.getLastRowNum();
	}

	public static String getCellValue(int row, int col) {
	    DataFormatter formatter = new DataFormatter();
	    return formatter.formatCellValue(sh.getRow(row).getCell(col));
	}

}
