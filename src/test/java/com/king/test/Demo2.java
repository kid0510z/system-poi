
package com.king.test;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @Description: java类作用描述
 * @Author: Bsea
 * @CreateDate: 2019/8/26$ 20:58$
 */
public class Demo2 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        HSSFWorkbook wb = new HSSFWorkbook();    //建立新HSSFWorkbook对象
        HSSFSheet sheet = wb.createSheet("java10");  //建立新的sheet对象
        HSSFRow row = sheet.createRow((short) 0);
        //在sheet里创建一行，参数为行号（第一行，此处可想象成数组）
        HSSFCell cell = row.createCell((short) 0);
        //在row里建立新cell（单元格），参数为列号（第一列）
        cell.setCellValue("2019");
        //cell.set
        //设置cell的整数类型的值
        row.createCell((short) 1).setCellValue(1.2);     //设置cell浮点类型的值
        row.createCell((short) 2).setCellValue("test");   //设置cell字符类型的值
        row.createCell((short) 3).setCellValue(true);    //设置cell布尔类型的值
        HSSFCellStyle cellStyle = wb.createCellStyle(); //建立新的cell样式
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        //设置cell样式为定制的日期格式
        HSSFCell dCell = row.createCell((short) 4);
        dCell.setCellValue(new Date());            //设置cell为日期类型的值
        dCell.setCellStyle(cellStyle);              //设置该cell日期的显示格式
        HSSFCell csCell = row.createCell((short) 5);
        //csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
        //设置cell编码解决中文高位字节截断
        csCell.setCellValue("中文测试_Chinese Words Test");  //设置中西文结合字符串
//        row.createCell((short)6).setCellType(HSSFCell.CELL_TYPE_ERROR);
        //建立错误cell

        try {
            FileOutputStream fileOut = new FileOutputStream("f:\\PoiDemo\\workbook1.xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

