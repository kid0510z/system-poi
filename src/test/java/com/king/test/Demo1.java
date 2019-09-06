package com.king.test;

import com.king.domain.Exam;
import com.king.utils.ExcelUtil;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: Bsea
 * @CreateDate: 2019/8/26$ 20:55$
 */
public class Demo1 {

    @Test
    public void getExcelBook() {
        String filePath = "F:\\poiDemo\\example.xlsx";
        LinkedHashMap<String, String> filedMap = new LinkedHashMap<>();
        filedMap.put("题干","dry");
        filedMap.put("选项A","optionA");
        filedMap.put("选项B","optionB");
        filedMap.put("选项C","optionC");
        filedMap.put("选项D","optionD");
        filedMap.put("选项E","optionE");
        filedMap.put("选项F","optionF");
        filedMap.put("答案（选择填ABCDEF，中间不能有空格 判断填A或B，填空题多空用逗号分隔）","answer");


        List<Exam> sheet2 = ExcelUtil.readExcelFile(filePath, "Sheet1", true,Exam.class, filedMap);

        for (Exam exam : sheet2) {
            System.out.println("exam = " + exam);
        }



    }

    public static void main(String[] args) {

    }

}

