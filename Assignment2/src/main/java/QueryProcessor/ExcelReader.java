package QueryProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	
	public String readexcel(int index_position){
		 FileInputStream file;
		 try {
			ClassLoader classLoader = getClass().getClassLoader();
			file = new FileInputStream(new File(classLoader.getResource("/data.xlsx").getFile()));
	
         //Create Workbook instance holding reference to .xlsx file
         XSSFWorkbook workbook = new XSSFWorkbook(file);

         //Get first/desired sheet from the workbook
         XSSFSheet sheet = workbook.getSheetAt(0);

         //Iterate through each rows one by one
         Iterator<Row> rowIterator = sheet.iterator();
         while (rowIterator.hasNext()) 
         {
        	 
             Row row = rowIterator.next();
             if(row.getRowNum()==index_position){
	 			Cell url=row.getCell(1);
	 			String url_value=url.toString();
	 			return url_value;
        		 
        	 }
             
         }
         file.close();
	}
         catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOT FOUND";
		
	}

}
