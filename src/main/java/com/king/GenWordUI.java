package com.king;

import com.king.domain.Temp;
import com.king.utils.GenWordByExcelUtil;
import com.king.utils.JTextFieldHintListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GenWordUI {
    // 组件相关
    private static JFrame frmIpa;
    // sheet字段
    private static JTextField sheetField;
    private static JTextArea textArea;
    private static JRadioButton jrb1;
    // 路径文件
    private static String excelFilePath = null;
    private static String wordPath = null;
    private static String wordFileName = null;

    private GenWordUI() {
        // 窗口框架
        frmIpa = new JFrame();
        frmIpa.setTitle("生成Word文档");
        frmIpa.setBounds(600, 300, 800, 400);
        frmIpa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 面板1
        JPanel panel = new JPanel();
        frmIpa.getContentPane().add(panel, BorderLayout.NORTH);

        JLabel jLabel = new JLabel("byName?");
        //创建按钮组
        ButtonGroup bg = new ButtonGroup();
        //创建单选框
        jrb1 = new JRadioButton("YES");
        JRadioButton jrb2 = new JRadioButton("NO");
        jrb2.setSelected(true);
        //必须要把单选框放入按钮组作用域中才能实现单选！！！！
        bg.add(jrb1);
        bg.add(jrb2);

        //SheetName TODO
        Temp temp = new Temp(true);
        sheetField = new JTextField(10);
        sheetField.addFocusListener(new JTextFieldHintListener(sheetField, "Sheet名称(编号)", temp));

        JButton button1 = new JButton("选择Excel文件");
        JButton button2 = new JButton("选择生成文件的文件夹位置");
        JButton button3 = new JButton("生成 Go!");
        // 监听button的选择路径
        button1.addActionListener(e -> {
            // 显示打开的文件对话框
            JFileChooser jfc = new JFileChooser();
            jfc.showSaveDialog(frmIpa);

            // 使用文件类获取选择器选择的文件
            File file = jfc.getSelectedFile();
            excelFilePath = file + "";
            String fileName = file.getName();
            // 生成word名称
            if (fileName.endsWith(".xlsx")) {
                wordFileName = fileName.replace(".xlsx", ".docx");
            } else if (fileName.endsWith(".xls")) {
                wordFileName = fileName.replace(".xls", ".docx");
            }

            textArea.setText("Excel目录：  " + excelFilePath + " \n");

        });
        button2.addActionListener(e -> {
            // 显示打开的文件对话框
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("选择一个目录");
            jfc.setDialogType(JFileChooser.OPEN_DIALOG);
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showSaveDialog(frmIpa);
            // 使用文件类获取选择器选择的文件
            File file = jfc.getSelectedFile();
            wordPath = file + "\\";
            textArea.append("Word目录：  " + wordPath + "\n");

        });
        button3.addActionListener(e -> {
            try {
                // 如果未填写 采用默认第一个sheet
                String sheetName = sheetField.getText();
                boolean isByName = jrb1.isSelected();
                if (temp.getDefault()) {
                    sheetName = "0";
                    isByName = false;
                }

                GenWordByExcelUtil.genWordByExcel(excelFilePath, wordPath, wordFileName, sheetName, isByName);
                textArea.append("生成文件成功! \t" + wordPath + wordFileName + "\n");
            } catch (Exception e1) {

                String message = e1.getMessage();
                System.out.println("message = " + message);
                textArea.append("生成文件失败! \t" + wordPath + wordFileName + "\n" + e1.toString() + "\n");
            }

        });

        panel.add(button1);

        panel.add(sheetField);
        panel.add(jLabel);
        panel.add(jrb1);
        panel.add(jrb2);

        panel.add(button2);
        panel.add(button3);

        // 可滚动面板
        JScrollPane scrollPane = new JScrollPane();
        frmIpa.getContentPane().add(scrollPane, BorderLayout.CENTER);
        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        // 选中生成目录

        //这个最好放在最后，否则会出现视图问题。
        frmIpa.setVisible(true);
    }

    public static void main(String[] args) {
        new GenWordUI();
    }
}