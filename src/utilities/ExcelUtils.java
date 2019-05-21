package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.LIGHT_GREEN;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;

public class ExcelUtils {

	public static Workbook wb;
	public static Sheet sh;
	public static Row rowNumber;
	public static Cell cellNumber;

	public static void SetExcelFile(String path, String sheetName) throws Exception {

		try {
			// Opening Excel File
			HSSFWorkbook wb = new HSSFWorkbook();
			
			HSSFSheet sh = wb.createSheet(sheetName);
			
			 sh = wb.getSheet(sheetName);

			
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
            fileOut.close();
            wb.close();
            System.out.println("Your excel file has been generated!");

		} catch (Exception e) {
			throw (e);
		}

	}

	public static void GetExcelFile(String path, String sheetName) throws Exception {

		try {
			// Opening Excel File

			FileInputStream ExcelFile = new FileInputStream(path);

			HSSFWorkbook wb = new HSSFWorkbook(ExcelFile);
			
			 sh = wb.getSheet(sheetName);

		} catch (Exception e) {
			throw (e);
		}

	}

	public static Row GetRow(int rowNum) throws Exception {

		Row row;
		try {
			row = sh.getRow(rowNum);
		} catch (Exception e) {
			throw (e);
		}
		return row;
	}

	public static String GetCellData(int rowNum, int colNum) throws Exception {

		try {

			Row r = sh.getRow(rowNum);
			if (r == null) {
				return "1234";
			}
			String cellData = new DataFormatter().formatCellValue(sh.getRow(rowNum).getCell(colNum));
			return cellData;

		} catch (Exception e) {
			throw (e);
		}
	}

	public static int GetIntCellData(int rowNum, int colNum) throws Exception {

		try {
			Row r = sh.getRow(rowNum);
			if (r == null) {
				return 1234;
			}

			double cellData = Math.round(sh.getRow(rowNum).getCell(colNum).getNumericCellValue());
			int cellData1 = (int) cellData;
			return cellData1;

		} catch (Exception e) {
			throw (e);
		}

	}

	public static void GetSheet(String sheetName) {

		try {

			wb.getSheet(sheetName);

		} catch (Exception e) {
			throw (e);
		}

	}

	public static int GetCurrentSheetIndex() {

		int sheetInd = 0;
		try {

			sheetInd = wb.getActiveSheetIndex();

		} catch (Exception e) {
			throw (e);
		}

		return sheetInd;
	}

	public static void SetCellData(String filePath, String sheetName, String[][] result) throws Exception {

		Workbook wb2 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName); // Will
																			// convert
																			// invalid
																			// characters
																			// to
																			// space
																			// '
																			// '.

		Sheet resultSheet = wb2.createSheet(safeSheetName);

		Row row0 = resultSheet.createRow(0);

		row0.createCell(0).setCellValue("S.No.");
		row0.createCell(1).setCellValue("Proposal");
		row0.createCell(2).setCellValue("Policy No");
		row0.createCell(3).setCellValue("Name");
		row0.createCell(4).setCellValue("Status");
		row0.createCell(5).setCellValue("Initiated From");
		row0.createCell(6).setCellValue("Schedule");
		row0.createCell(7).setCellValue("View");
		row0.createCell(8).setCellValue("Cust Code");
		row0.createCell(9).setCellValue("Receipt No");
		row0.createCell(10).setCellValue("Receipt Date");
		row0.createCell(11).setCellValue("Status");

		// TODO give max i length as result.length
		for (int i = 0; i < result.length; i++) {

			Row row2 = resultSheet.createRow(i + 2);

			row2.createCell(0).setCellValue(i + 1);
			row2.createCell(1).setCellValue(result[i][0]);
			row2.createCell(2).setCellValue(result[i][1]);
			row2.createCell(3).setCellValue(result[i][2]);
			row2.createCell(4).setCellValue(result[i][3]);
			row2.createCell(5).setCellValue(result[i][4]);

		}
		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		wb2.close();

	}
	
	public static void SetCellData1(String filePath, String sheetName, String[][] result, int row) throws Exception{

	Workbook wb2 = new HSSFWorkbook();

	String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName); // Will
																		// convert
																		// invalid
																		// characters
																		// to
																		// space
																		// '
																		// '.

	Sheet resultSheet = wb2.createSheet(safeSheetName);

	if(row ==1){
		
		System.out.println("Deleting Rows");
		for (int i = 1; i<=100; i++){
			
			Row rowDel = resultSheet.createRow(i);
			
			resultSheet.removeRow(rowDel);
			
		}
	}
	
	Row row0 = resultSheet.createRow(0);

	row0.createCell(0).setCellValue("S.No.");
	row0.createCell(1).setCellValue("Proposal");
	row0.createCell(2).setCellValue("Policy No");
	row0.createCell(3).setCellValue("Name");
	row0.createCell(4).setCellValue("Status");
	row0.createCell(5).setCellValue("Initiated From");
	row0.createCell(6).setCellValue("Schedule");
	row0.createCell(7).setCellValue("View");
	row0.createCell(8).setCellValue("Cust Code");
	row0.createCell(9).setCellValue("Receipt No");
	row0.createCell(10).setCellValue("Receipt Date");
	row0.createCell(11).setCellValue("Status");

	// TODO give max i length as result.length
	for (int i = 0; i < result.length; i++) {

		Row row2 = resultSheet.createRow(row + i);

		System.out.println("Row Created :"+(row + i));
		
		row2.createCell(0).setCellValue(i + 1);
		row2.createCell(1).setCellValue(result[i][0]);
		row2.createCell(2).setCellValue(result[i][1]);
		row2.createCell(3).setCellValue(result[i][2]);
		row2.createCell(4).setCellValue(result[i][3]);
		row2.createCell(5).setCellValue(result[i][4]);
		row2.createCell(6).setCellValue(result[i][5]);
		row2.createCell(7).setCellValue(result[i][6]);
		row2.createCell(8).setCellValue(result[i][7]);
		row2.createCell(9).setCellValue(result[i][8]);
		row2.createCell(10).setCellValue(result[i][9]);
		row2.createCell(10).setCellValue(result[i][10]);

	}
	
	try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
		wb2.write(fileOut);
		fileOut.close();
	} catch (Exception e) {
		System.out.println(e);
	}
	wb2.close();

}

	public static void SetInputData(String filePath, String sheetName, String value, int cell) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		Row row0 = inputSheet.getRow(0);
		if (row0 == null) {
			row0 = inputSheet.createRow(0);
		}

		Row row1 = inputSheet.getRow(1);
		if (row1 == null) {
			row1 = inputSheet.createRow(1);
		}

		row0.createCell(cell).setCellValue("Value");
		row1.createCell(cell).setCellValue(value);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, String[] bookings) throws Exception {

		Workbook Wb3 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);

		Sheet inputSheet = Wb3.createSheet(safeSheetName);

		for (int i = 0; i < bookings.length; i++) {

			Row row1 = inputSheet.createRow(i);

			row1.createCell(0).setCellValue("Product");

			row1.createCell(1).setCellValue(bookings[i]);

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, long mobile, HashMap<String, String> data,
			int cell) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3;

		// String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);

		Sheet inputSheet;

		if (cell == 1) {

			Wb3 = new HSSFWorkbook();
			System.out.println("ExcelUtils creating new sheet");
			inputSheet = Wb3.createSheet(sheetName);

		} else {

			Wb3 = new HSSFWorkbook(fin);
			inputSheet = Wb3.getSheet(sheetName);

		}

		Row row0 = inputSheet.getRow(0);

		if (row0 == null) {

			row0 = inputSheet.createRow(0);
		}

		// Set Customer Mobile Number
		row0.createCell(cell).setCellValue(mobile);

		for (String key : data.keySet()) {

			System.out.println("ExcelUtils : " + key);

			Row row1 = inputSheet.getRow(Integer.parseInt(key));

			if (row1 == null) {

				row1 = inputSheet.createRow(Integer.parseInt(key));
			}

			row1.createCell(0).setCellValue(Integer.parseInt(key));

			row1.createCell(cell).setCellValue(data.get(key));

		}

		fin.close();

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, ArrayList<String> bookings) throws Exception {

		Workbook Wb3 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);

		Sheet inputSheet = Wb3.createSheet(safeSheetName);

		Row row0 = inputSheet.createRow(0);

		row0.createCell(0).setCellValue("S.No.");

		row0.createCell(1).setCellValue("Data");

		for (int i = 1; i <= bookings.size(); i++) {

			Row row1 = inputSheet.createRow(i);

			row1.createCell(0).setCellValue(i);

			row1.createCell(1).setCellValue(bookings.get(i - 1));

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, long mobile, ArrayList<String> bookings,
			ArrayList<String> bookingsWithoutStatus, String searchedProduct, String notification, int cell)
			throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3;
		Sheet inputSheet;

		if (cell == 1) {

			Wb3 = new HSSFWorkbook();

			System.out.println("Rogue ExcelUtils - Creating new Sheet:");

			inputSheet = Wb3.createSheet(sheetName);

		} else {

			Wb3 = new HSSFWorkbook(fin);

			System.out.println("Rogue ExcelUtils - Using Existing Sheet:");

			inputSheet = Wb3.getSheet(sheetName);

		}

		Row row0 = inputSheet.getRow(0);

		if (row0 == null) {
			row0 = inputSheet.createRow(0);
		}

		row0.createCell(0).setCellValue("S.No.");

		// Setting Rogue Bookings
		for (int i = 1; i <= bookings.size(); i++) {

			Row row1 = inputSheet.getRow(i);

			if (row1 == null) {
				row1 = inputSheet.createRow(i);
			}

			row1.createCell(0).setCellValue(i);

			row0.createCell(cell + 1).setCellValue("RogueBookings");

			row1.createCell(cell + 1).setCellValue(bookings.get(i - 1));

		}

		// Setting Bookings without Status
		for (int j = 1; j <= bookingsWithoutStatus.size(); j++) {

			Row row1 = inputSheet.getRow(j);

			row0.createCell(cell + 2).setCellValue("BookingsWithoutStatus");
			row1.createCell(cell + 2).setCellValue(bookingsWithoutStatus.get(j - 1));

		}

		Row row1 = inputSheet.getRow(1);

		if (row1 == null) {
			row1 = inputSheet.createRow(1);
		}

		// Setting Customer Mobile number
		row0.createCell(cell).setCellValue("Customer Mobile");
		row1.createCell(cell).setCellValue(mobile);

		// Setting the Searched Product and Notification
		row0.createCell(cell + 3).setCellValue("Searched Product");
		row0.createCell(cell + 4).setCellValue("Notification");
		row1.createCell(cell + 3).setCellValue(searchedProduct);
		row1.createCell(cell + 4).setCellValue(notification);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, String[] vehicle, String[][] vehicleField,
			String[][] vehicleFieldValue) throws Exception {

		Workbook Wb3 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);

		Sheet inputSheet = Wb3.createSheet(safeSheetName);

		for (int i = 0; i < vehicle.length; i++) {

			Row newRow = inputSheet.createRow(i + 1);

			Row row0 = inputSheet.createRow(0);

			newRow.createCell(0).setCellValue(vehicle[i]);

			row0.createCell(0).setCellValue("Vehicle Name");

			for (int j = 0; j < vehicleField[i].length; j++) {

				row0.createCell(j + 1).setCellValue(vehicleField[i][j]);

				newRow.createCell(j + 1).setCellValue(vehicleFieldValue[i][j]);

			}
		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();

	}

	// Proc to Generate a new Report Sheet every Time. Below it is another proc
	// to update the already existing Report Sheet. (So that the formatting is
	// not to be done by POI).
	// To Reuse it, just change the proc name in
	// GenerateReport.GenerateReportSheet from GenerateReport2 to
	// GenerateReport. And pass Arraylist parameters - rogueBookings,
	// vehicleNames instead of Array paramenters as used in GenerateReport2.

	public static void GenerateReport(String filePath, String sheetName, ArrayList<String> vehicles,
			ArrayList<String> rogueBookings, ArrayList<String> rawResult, long mobile) throws Exception {

		Workbook wb2 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName); // Will
																			// convert
																			// invalid
																			// characters
																			// to
																			// space
																			// '
																			// '.

		Sheet resultSheet = wb2.createSheet(safeSheetName);

		Row row0 = resultSheet.createRow(0);

		row0.createCell(0).setCellValue("Module");
		row0.createCell(1).setCellValue("Scenario");
		row0.createCell(2).setCellValue("Result");
		row0.createCell(3).setCellValue("Customer Data");
		row0.createCell(4).setCellValue("Complexity");
		row0.createCell(5).setCellValue("Tester");
		row0.createCell(6).setCellValue("Defect ID");
		row0.createCell(7).setCellValue("Severity");
		row0.createCell(8).setCellValue("Status");
		row0.createCell(9).setCellValue("Customer Vehicles");
		row0.createCell(10).setCellValue("Rogue Bookings");

		row0.setHeight((short) 700);

		CellStyle my_style = wb2.createCellStyle();
		my_style.setFillForegroundColor(new HSSFColor.ORANGE().getIndex());
		my_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		for (int i = 0; i <= 10; i++) {
			row0.getCell(i).setCellStyle(my_style);
		}
		// row0.setRowStyle(my_style);

		Row row1 = resultSheet.getRow(2);

		if (row1 == null) {
			row1 = resultSheet.createRow(2);
		}

		row1.createCell(3).setCellValue(mobile);

		for (int i = 0; i < vehicles.size(); i++) {

			Row row2 = resultSheet.getRow(i + 2);

			if (row2 == null) {
				row2 = resultSheet.createRow(i + 2);
			}

			row2.createCell(9).setCellValue(vehicles.get(i));

		}

		for (int j = 0; j < rogueBookings.size(); j++) {

			Row row2 = resultSheet.getRow(j + 2);

			if (row2 == null) {
				row2 = resultSheet.createRow(j + 2);
			}

			row2.createCell(10).setCellValue(rogueBookings.get(j));

		}

		String[] Modules = { "Log In", "Customer Bookings", "Booking Details", "My Searches", "My Vehicles", "Get Help",
				"Customer Notifications", "Log Out" };
		String[] Scenarios = { "Log In using customer Credentials", "Identify Rogue Bookings",
				"Verify Booking Details Link", "Get the Latest searched product", "Identify all Customer Vehicles",
				"Verify Get Help link", "Get first Notification", "Log Out Successfully" };

		for (int k = 0; k <= 7; k++) {

			Row row2 = resultSheet.getRow(k + 2);
			if (row2 == null) {
				row2 = resultSheet.createRow(k + 2);
			}

			row2.createCell(0).setCellValue(Modules[k]);

			row2.createCell(1).setCellValue(Scenarios[k]);

			row2.createCell(2).setCellValue(rawResult.get(k));

			resultSheet.setColumnWidth(3, resultSheet.getColumnWidth(3) + 300);
			resultSheet.setColumnWidth(0, resultSheet.getColumnWidth(0) + 400);
			resultSheet.setColumnWidth(1, resultSheet.getColumnWidth(1) + 800);
			resultSheet.setColumnWidth(2, resultSheet.getColumnWidth(2) + 300);
			resultSheet.setColumnWidth(9, resultSheet.getColumnWidth(9) + 800);
			resultSheet.setColumnWidth(10, resultSheet.getColumnWidth(10) + 600);

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		wb2.close();

	}


	public static void SetSearchSummarySheet(String reportPath, String reportSummarySheetName,
			HashMap<String, String> searchCount) throws Exception {

		System.out.println("Setting Summary");

		FileInputStream fin = new FileInputStream(reportPath);

		Workbook wb2 = new HSSFWorkbook(fin);

		Sheet reportSummarySheet = wb2.getSheet(reportSummarySheetName);

		reportSummarySheet.getRow(1).createCell(7).setCellValue(Integer.parseInt(searchCount.get("Car")));
		reportSummarySheet.getRow(2).createCell(7).setCellValue(Integer.parseInt(searchCount.get("Health")));
		reportSummarySheet.getRow(3).createCell(7).setCellValue(Integer.parseInt(searchCount.get("Term")));
		reportSummarySheet.getRow(4).createCell(7).setCellValue(Integer.parseInt(searchCount.get("Investment")));
		
		
		/*for(int i =1; i<=4; i++){
		
			String prod = GetCellData(i, 0);
			
			Row r1 = reportSummarySheet.getRow(i);
			//Cell c1 = r1.getCell(6);
			//if(c1 == null){
			Cell	c1 = r1.createCell(6);
			//}

			if(prod.equals("Car")){
				c1.setCellValue(Integer.parseInt(searchCount.get("Car")));
			}
			if(prod.equals("Health")){
				c1.setCellValue(Integer.parseInt(searchCount.get("Health")));
			}
			if(prod.equals("Investment")){
				c1.setCellValue(Integer.parseInt(searchCount.get("Investment")));
			}
			if(prod.equals("Term")){
				c1.setCellValue(Integer.parseInt(searchCount.get("Term")));
			}
		} */
		
		fin.close();

		// Write the File
		try (FileOutputStream fileOut = new FileOutputStream(reportPath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		wb2.close();

	}
		
	
	@SuppressWarnings("deprecation")
	public static boolean isRowEmpty(Row row) {

		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);

			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}

	public static void UpdateSheetDemo(String file, String SheetName, String data) throws IOException {

		FileInputStream fin = new FileInputStream(file);

		HSSFWorkbook wb = new HSSFWorkbook(fin);

		HSSFSheet sh = wb.createSheet(SheetName);

		HSSFRow row = sh.getRow(1);

		if (row == null) {
			sh.createRow(1).createCell(1).setCellValue(data);
		} else {
			row.createCell(1).setCellValue(data);
		}

		fin.close();

		FileOutputStream fout = new FileOutputStream(file);

		wb.write(fout);

		fout.close();
		wb.close();

	}

	public static void CloseFile() throws Exception {

		try {
			wb.close();
		} catch (Exception e) {
			throw (e);
		}

	}

}
