package com.king;

import com.king.domain.Exam;
import com.king.pro.ExamPro;
import com.king.utils.ExcelUtil;
import com.king.utils.WordUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * @Program: system_poi
 * @Description:
 * @Author: Mr.King
 * @Create: 2019-09-07 09:15
 **/
public class GenWordByExcel {

    public static void main(String[] args) {
        String excelFilePath = "F:\\poiDemo\\example.xlsx";
        String wordPath = "F:\\poiDemo\\";
        String wordFileName = "poi.docx";
        genWordByExcel(excelFilePath, wordPath, wordFileName, "Sheet1", true);

    }

    public static void genWordByExcel(String excelFilePath, String wordPath, String wordFileName, String sheetName, boolean isByName) {

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
        List<Exam> sheet2 = ExcelUtil.readExcelFile(excelFilePath, sheetName, isByName, Exam.class, filedMap);

        TreeMap<Integer, ArrayList<ExamPro>> all = separete(sheet2);

        //创建word
        WordUtil.createWord(wordPath, wordFileName);
        //写入数据
        WordUtil.writeDataDocx(wordPath + wordFileName, all);
    }

    // 根据分类转换map
    private static TreeMap<Integer, ArrayList<ExamPro>> separete(List<Exam> list) {
        TreeMap<Integer, ArrayList<ExamPro>> tm = new TreeMap<>();

        for (Exam exam : list) {
            ExamPro examPro = new ExamPro();
            genExamPro(exam, examPro);
            if (tm.containsKey(examPro.getType())) {
                ArrayList<ExamPro> l11 = tm.get(examPro.getType());
                l11.add(examPro);
            } else {
                ArrayList<ExamPro> tem = new ArrayList<>();
                tem.add(examPro);
                tm.put(examPro.getType(), tem);
            }

        }
        return tm;
    }

    // 将exam处理成exampro
    private static void genExamPro(Exam exam, ExamPro examPro) {
        // 1.设置题干
        examPro.setDry(exam.getDry());
        // 2.设置类型
        int type = Integer.valueOf(exam.getType().substring(0, 1));
        examPro.setType(type);
        // 3.处理答案
        // 3.1 问答题以及填空题
        if (type == 3 || type == 4) {
            // 将正确答案放到A选项，将答案选项填写A
            exam.setOptionA(exam.getAnswer());
            exam.setAnswer("A");
        }

        // 3.2 单选、多选、以及判断
        String[] strings = exam.getAnswer().split("");
        Integer[] aws = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            switch (strings[i]) {
                case "A":
                    aws[i] = 0;
                    break;
                case "B":
                    aws[i] = 1;
                    break;
                case "C":
                    aws[i] = 2;
                    break;
                case "D":
                    aws[i] = 3;
                    break;
                default:
                    aws[i] = -1;

            }
        }
        examPro.setAnswers(aws);
        // 设置选项 需要特殊处理的是判断题
        List<String> options = new ArrayList<>();
        String optionA = exam.getOptionA();
        if (StringUtils.isNotBlank(optionA)) {
            options.add(optionA);
        } else if (type == 0) {
            options.add("正确");
        }
        String optionB = exam.getOptionB();
        if (StringUtils.isNotBlank(optionB)) {
            options.add(optionB);
        } else if (type == 0) {
            options.add("错误");
        }
        String optionC = exam.getOptionC();
        if (StringUtils.isNotBlank(optionC)) {
            options.add(optionC);
        }
        String optionD = exam.getOptionD();
        if (StringUtils.isNotBlank(optionD)) {
            options.add(optionD);
        }
        String optionE = exam.getOptionE();
        if (StringUtils.isNotBlank(optionE)) {
            options.add(optionE);
        }
        String optionF = exam.getOptionF();
        if (StringUtils.isNotBlank(optionF)) {
            options.add(optionF);
        }
        examPro.setOptions(options);
    }
}
