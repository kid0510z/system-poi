package com.king.utils;

import com.king.pro.ExamPro;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;

import java.io.*;
import java.util.*;

/**
 * @Program: system_poi
 * @Description:
 * @Author: Mr.King
 * @Create: 2019-09-07 02:09
 **/
public class WordUtil {

    private static String[] numOptions = {"A", "B", "C", "D", "E", "F", "G"};
    private static String[] typeOptions = {"0-判断", "1-单选", "2-多选", "3-填空（人工判卷）", "4-问答", "5-填空（自动判卷）"};

    public static void createWord(String path, String fileName) {
        //判断目录是否存在
        File file = new File(path);
        //exists()测试此抽象路径名表示的文件或目录是否存在。
        //mkdir()创建此抽象路径名指定的目录。
        //mkdirs()创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        if (!file.exists()) {
            file.mkdirs();
        }
        //因为HWPFDocument并没有提供公共的构造方法 所以没有办法构造word
        //这里使用word2007及以上的XWPFDocument来进行构造word
        @SuppressWarnings("resource")
        XWPFDocument document = new XWPFDocument();
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(new File(file, fileName));
            document.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeDataDocx(String fileName, TreeMap<Integer, ArrayList<ExamPro>> data) {

        InputStream istream = null;
        OutputStream ostream = null;
        try {
            istream = new FileInputStream(fileName);
            ostream = new FileOutputStream(fileName);
            XWPFDocument document = new XWPFDocument();

            // 标题
            //添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            //设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("Make Mr.King");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(16);
            titleParagraphRun.setBold(true);

            // 准备数据
            Set<Integer> keySet = data.keySet();
            for (Integer type : keySet) {
                // 创建一个段落
                XWPFParagraph paragraph = document.createParagraph();

                // 一个run 类型 （多选）
                XWPFRun run0 = paragraph.createRun();
                run0.setText(typeOptions[type]);
                run0.setColor("0E51BE");
                run0.setBold(true);
                run0.setFontSize(15);
                run0.addCarriageReturn();
                // 题干描述
                List<ExamPro> examPros = data.get(type);
                for (int i = 0; i < examPros.size(); i++) {
                    XWPFRun run00 = paragraph.createRun();
                    ExamPro examPro = examPros.get(i);
                    run00.setText((i + 1) + ". " + examPro.getDry());
                    run00.setBold(true);
                    run00.setFontSize(13);
                    run00.addCarriageReturn();
                    // 4个选项
                    List<String> options = examPro.getOptions();
                    for (int j = 0; j < options.size(); j++) {
                        XWPFRun runT = paragraph.createRun();
                        runT.addTab();
                        XWPFRun run = paragraph.createRun();
                        run.setText(numOptions[j] + ". " + options.get(j));
                        run.addCarriageReturn();
                        Integer[] answers = examPro.getAnswers();
                        if (type != 3 && type != 4) {
                            if (Arrays.asList(answers).contains(j)) {
                                //设置段落背景颜色
                                CTRPr ctrPr = run.getCTR().addNewRPr();
                                CTShd cTShd = ctrPr.addNewShd();
                                cTShd.setFill("FFFC00");
                            }
                        } else {
                            run.setColor("FF0000");
                        }

                    }
                }

            }

            document.write(ostream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ostream != null) {
                try {
                    ostream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
