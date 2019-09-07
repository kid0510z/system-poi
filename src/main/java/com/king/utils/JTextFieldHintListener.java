package com.king.utils;

import com.king.domain.Temp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldHintListener implements FocusListener {
    private String hintText;
    private JTextField textField;
    private Temp temp;

    public JTextFieldHintListener(JTextField jTextField, String hintText, Temp temp) {
        this.textField = jTextField;
        this.hintText = hintText;
        this.temp = temp;
        //默认直接显示
        jTextField.setText(hintText);
        jTextField.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        //获取焦点时，清空提示内容
        temp.setDefault(false);
        String tempp = textField.getText();
        if (tempp.equals(hintText)) {
            textField.setText("");
            textField.setForeground(Color.BLACK);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点时，没有输入内容，显示提示内容
        String tempp = textField.getText();
        if ("".equals(tempp)) {
            textField.setForeground(Color.GRAY);
            textField.setText(hintText);
            temp.setDefault(true);
        }

    }

}