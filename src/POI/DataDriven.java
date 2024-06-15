package POI;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Files.Payload;

public class DataDriven {

	public static void main(String[] args) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(Payload.getExcel());
		int numOfSheets = workbook.getNumberOfSheets();
		System.out.println(numOfSheets);
		
		for (int i=0; i < numOfSheets; i++) {
			if(workbook.getSheetName(i).equalsIgnoreCase("testdata")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				System.out.println(sheet.getSheetName()+"**********");
				Iterator<Row> rows =  sheet.iterator();
				Row row = rows.next();
				//for (Iterator iterator = rows; iterator.hasNext();) {
					//Row row = (Row) iterator.next();
					Iterator<Cell> cell =  row.cellIterator();
					while (cell.hasNext()) {
						System.out.println(cell.next().toString());;
					}
					
					System.out.println("*****************");
				//}
				break;
			}
						
		}

	}

}
