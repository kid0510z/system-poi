package com.king.utils;

/**
 * @program: SpringZtreeDemo
 * @description:
 * @author: Mr.King
 * @create: 2019-05-15 21:15
 **/
public class ExcelException extends Exception {

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }


}