package com.king.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Program: system_poi
 * @Description:
 * @Author: Mr.King
 * @Create: 2019-09-06 19:59
 **/
public class ExcelUtil {

    /**
     * 读取指定目录的excel文件返回对应的list
     *
     * @param filePath    文件目录
     * @param sheetName   sheet名称
     * @param isByName    sheetName是否根据名称 false则根据位置取
     * @param entityClass 返回值的实体类类型
     * @param fieldMap    表头与实体类的对应关系 key：中文 value实体类应为
     * @param <T>         泛型，为了不使用poi的T的类型
     * @return 包含实体类的list集合
     */
    public static <T> List<T> readExcelFile(
            String filePath, String sheetName, boolean isByName, Class<T> entityClass, LinkedHashMap<String, String> fieldMap) {
        //定义要返回的list
        List<T> resultList = new ArrayList<T>();
        try {
            // 获得excel文件对象workbook
            Workbook wb = getExcelBook(filePath);

            Sheet sheet;

            if (isByName) {
                sheet = wb.getSheet(sheetName);
            } else {
                sheet = wb.getSheetAt(Integer.valueOf(sheetName));
            }

            //获取工作表的有效行数
            int realRows = sheet.getPhysicalNumberOfRows();

            //如果Excel中没有数据则提示错误
            if (realRows <= 1) {
                throw new ExcelException("Excel文件中没有任何数据");
            }
            Row firstRow = sheet.getRow(0);
            // 第一行的列数
            int columnNum = firstRow.getPhysicalNumberOfCells();
            String[] excelFieldNames = new String[columnNum];

            //获取Excel中的列名
            for (int i = 0; i < columnNum; i++) {
                excelFieldNames[i] = getCellFormatValue(firstRow.getCell(i)).trim();
            }

            //判断需要的字段在Excel中是否都存在
            boolean isExist = true;
            List<String> excelFieldList = Arrays.asList(excelFieldNames);
            for (String cnName : fieldMap.keySet()) {
                if (!excelFieldList.contains(cnName)) {
                    isExist = false;
                    break;
                }
            }

            //如果有列名不存在，则抛出异常，提示错误
            if (!isExist) {
                throw new ExcelException("Excel中缺少必要的字段，或字段名称有误");
            }

            //将列名和列号放入Map中,这样通过列名就可以拿到列号
            LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < excelFieldNames.length; i++) {
                colMap.put(excelFieldNames[i], firstRow.getCell(i).getColumnIndex());
            }
            // 将sheet转换成list
            sheetConversionList(entityClass, fieldMap, resultList, sheet, realRows, colMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    // 将sheet转换list
    private static <T> void sheetConversionList(Class<T> entityClass, LinkedHashMap<String, String> fieldMap, List<T> resultList, Sheet sheet, int realRows, LinkedHashMap<String, Integer> colMap) throws Exception {
        //将sheet转换为list
        for (int i = 1; i < realRows; i++) {
            //新建要转换的对象
            T entity = entityClass.newInstance();

            //给对象中的字段赋值
            for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
                //获取中文字段名
                String cnNormalName = entry.getKey();
                //获取英文字段名
                String enNormalName = entry.getValue();
                //根据中文字段名获取列号
                int col = colMap.get(cnNormalName);

                //获取当前单元格中的内容
                String content = getCellFormatValue(sheet.getRow(i).getCell(col)).trim();
                if (!"".equals(content)) {
                    //给对象赋值
                    setFieldValueByName(enNormalName, content, entity);
                }

            }

            resultList.add(entity);
        }
    }

    /**
     * xls/xlsx都使用的Workbook
     */
    private static Workbook getExcelBook(String fileName) {
        if (fileName == null) {
            return null;
        }
        String extString = fileName.substring(fileName.lastIndexOf("."));
        InputStream is;
        try {
            is = new FileInputStream(fileName);
            if (".xls".equals(extString)) {
                return new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return new XSSFWorkbook(is);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * format表格内容
     */
    private static String getCellFormatValue(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }
        return cellValue;
    }

    /**
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param o          对象
     * @MethodName : setFieldValueByName
     * @Description : 根据字段名给对象的字段赋值
     */
    private static void setFieldValueByName(String fieldName, Object fieldValue, Object o) throws Exception {

        Field field = getFieldByName(fieldName, o.getClass());
        if (field != null) {
            field.setAccessible(true);
            //获取字段类型
            Class<?> fieldType = field.getType();

            // 如果传过来值为空则赋值null
            if ("".equals(fieldValue)) {
                field.set(o,null);
                return;
            }
                //根据字段类型给字段赋值
            if (String.class == fieldType) {
                field.set(o, String.valueOf(fieldValue));
            } else if ((Integer.TYPE == fieldType)
                    || (Integer.class == fieldType)) {
                field.set(o, Integer.parseInt(fieldValue.toString()));
            } else if ((Long.TYPE == fieldType)
                    || (Long.class == fieldType)) {
                field.set(o, Long.valueOf(fieldValue.toString()));
            } else if ((Float.TYPE == fieldType)
                    || (Float.class == fieldType)) {
                field.set(o, Float.valueOf(fieldValue.toString()));
            } else if ((Short.TYPE == fieldType)
                    || (Short.class == fieldType)) {
                field.set(o, Short.valueOf(fieldValue.toString()));
            } else if ((Double.TYPE == fieldType)
                    || (Double.class == fieldType)) {
                field.set(o, Double.valueOf(fieldValue.toString()));
            } else if (Character.TYPE == fieldType) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    field.set(o, fieldValue.toString().charAt(0));
                }
            } else if (Date.class == fieldType) {
                field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
            } else {
                field.set(o, fieldValue);
            }
        } else {
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }
    }

    /**
     * @param fieldName 字段名
     * @param clazz     包含该字段的类
     * @return 字段
     * @MethodName : getFieldByName
     * @Description : 根据字段名获取字段
     */
    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        //拿到本类的所有字段
        Field[] selfFields = clazz.getDeclaredFields();

        //如果本类中存在该字段，则返回
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        //否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }

        //如果本类和父类都没有，则返回空
        return null;
    }

}
