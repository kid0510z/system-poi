package com.king;

import com.king.utils.GenWordByExcelUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GenWordUI {
    private static JFrame frmIpa;
    private static JTextArea textArea;
    private static String excelFilePath = null;
    private static String wordPath = null;
    private static String wordFileName = null;

    private GenWordUI() {
        // 窗口框架
        frmIpa = new JFrame();
        frmIpa.setTitle("生成Word文档");
        frmIpa.setBounds(600, 300, 600, 400);
        frmIpa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 面板1
        JPanel panel = new JPanel();
        frmIpa.getContentPane().add(panel, BorderLayout.NORTH);
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
            GenWordByExcelUtil.genWordByExcel(excelFilePath, wordPath, wordFileName, "Sheet1", true);
            textArea.append("生成文件成功! \t" + wordPath + wordFileName + "\n");

        });
        panel.add(button1);
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