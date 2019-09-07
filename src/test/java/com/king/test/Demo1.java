package com.king.test;

import com.king.domain.Exam;
import com.king.utils.ExcelUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Program: system_poi
 * @Description:
 * @Author: Mr.King
 * @Create: 2019-09-07 14:06
 **/
public class Demo1 {

    public static void main(String[] args) throws Exception {
        String excelFilePath = "C:\\Users\\King\\Desktop\\队友\\来哥\\整单元试题-java高级1单元（导入版).xls";
        LinkedHashMap<String, String> filedMap = new LinkedHashMap<>();
        filedMap.put("知识点", "knowledgePoint");
        filedMap.put("类型", "type");
        filedMap.put("题干", "dry");
        filedMap.put("选项A", "optionA");
        filedMap.put("选项B", "optionB");
        filedMap.put("选项C", "optionC");
        filedMap.put("选项D", "optionD");
        filedMap.put("选项E", "optionE");
        filedMap.put("选项F", "optionF");
        filedMap.put("答案（选择填ABCDEF，中间不能有空格 判断填A或B，填空题多空用逗号分隔）", "answer");


        // 根据文件得到集合
        List<Exam> sheet2 = ExcelUtil.readExcelFile(excelFilePath, "试题模板", true, Exam.class, filedMap);

        System.out.println("sheet2 = " + sheet2);





    }

}
