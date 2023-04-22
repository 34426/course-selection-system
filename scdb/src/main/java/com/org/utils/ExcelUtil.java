package com.org.utils;
import com.org.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//excel的读取工具类
public class ExcelUtil {

    public static Workbook getWorkbook(InputStream in, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));//获得后缀
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(in);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(in);
        } else {
            throw new Exception("请上传excel文件！");
        }
        return workbook;
    }

    public static List<Student> getMultipleStudent(InputStream in, String fileName){
        List<Student> students=new ArrayList<>();
        try{
            Workbook wb = getWorkbook(in, fileName);//获得正确的流
            Sheet sheet=wb.getSheetAt(0);
            for(int r = 1; r <= sheet.getLastRowNum(); r++) {   //遍历行
                Row row = sheet.getRow(r);
                if(row ==null){
                    continue;
                }
                row.getCell(0).setCellType(CellType.STRING);
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                row.getCell(3).setCellType(CellType.STRING);
                row.getCell(5).setCellType(CellType.STRING);
                row.getCell(6).setCellType(CellType.STRING);
                row.getCell(7).setCellType(CellType.STRING);
                Student student=new Student();
                student.setSno(row.getCell(0).getStringCellValue());
                student.setSpassword(row.getCell(1).getStringCellValue());
                student.setSname(row.getCell(2).getStringCellValue());
                student.setSsex(row.getCell(3).getStringCellValue());
                student.setSage((int)row.getCell(4).getNumericCellValue());
                student.setSdept(row.getCell(5).getStringCellValue());
                student.setSid(row.getCell(6).getStringCellValue());
                student.setPower(row.getCell(7).getStringCellValue());
                students.add(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return students;
    }

    /*public static Message testStudent(InputStream in, String fileName){
        try{
            Workbook wb = getWorkbook(in, fileName);//获得正确的流
            Sheet sheet=wb.getSheetAt(0);
            for(int r = 1; r <= sheet.getLastRowNum(); r++) {   //遍历行
                Row row = sheet.getRow(r);
                if(row ==null){
                    continue;
                }
                row.getCell(0).setCellType(CellType.STRING);
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                row.getCell(3).setCellType(CellType.STRING);
                row.getCell(5).setCellType(CellType.STRING);
                row.getCell(6).setCellType(CellType.STRING);
                row.getCell(7).setCellType(CellType.STRING);
                Student student=new Student();
                student.setSno(row.getCell(0).getStringCellValue());
                student.setSpassword(row.getCell(1).getStringCellValue());
                student.setSname(row.getCell(2).getStringCellValue());
                student.setSsex(row.getCell(3).getStringCellValue());
                student.setSage((int)row.getCell(4).getNumericCellValue());
                student.setSdept(row.getCell(5).getStringCellValue());
                student.setSid(row.getCell(6).getStringCellValue());
                student.setPower(row.getCell(7).getStringCellValue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/
    public static List<Map<String,Object>> getMultipleTeacher(InputStream in, String fileName){
        List<Map<String,Object>> teachers=new ArrayList<>();
        try{
            Workbook wb = getWorkbook(in, fileName);//获得正确的流
            Sheet sheet=wb.getSheetAt(0);
            for(int r = 1; r <= sheet.getLastRowNum(); r++) {   //遍历行
                Row row = sheet.getRow(r);
                if(row ==null){
                    continue;
                }
                row.getCell(0).setCellType(CellType.STRING);
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                row.getCell(3).setCellType(CellType.STRING);
                row.getCell(5).setCellType(CellType.STRING);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return teachers;
    }

}
