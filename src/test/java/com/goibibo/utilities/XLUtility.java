package com.goibibo.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XLUtility {
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static HSSFWorkbook wb;
	public static HSSFSheet ws;
	public static HSSFRow row;
	public static HSSFCell cell;

	public static int getRowCount(String xlFile, String xlSheet) throws IOException {
		fi = new FileInputStream(xlFile);
		wb = new HSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		int rowCount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowCount;
	}

	public static int getCellCount(String xlFile, String xlSheet, int rowNum) throws IOException {
		fi = new FileInputStream(xlFile);
		wb = new HSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		int columnCount = row.getLastCellNum();
		wb.close();
		fi.close();
		return columnCount;
	}

	public static String getCellData(String xlFile, String xlSheet, int rowNum, int columnNum) throws IOException {
		fi = new FileInputStream(xlFile);
		wb = new HSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.getCell(columnNum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public static void setCellData(String xlFile, String xlSheet, int rowNum, int columnNum, String data)
			throws IOException {
		fi = new FileInputStream(xlFile);
		wb = new HSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.createCell(columnNum);
		cell.setCellValue(data);
		fo = new FileOutputStream(xlFile);
		wb.write(fo);
		wb.close();
		fo.close();
		fi.close();
	}

	public static String getCellDatafactory(String filePath, String sheetName, int rowNum, int colNum)
			throws EncryptedDocumentException, IOException {
		String data;
		File f = new File(filePath);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheetName);
		Row row = sh.getRow(rowNum);
		Cell cell = row.getCell(colNum);
		data = cell.getStringCellValue().toString();
		return data;
	}
}
