package com.king.domain;

import java.util.List;

/**
 * @Program: system_poi
 * @Description:
 * @Author: Mr.King
 * @Create: 2019-09-07 00:49
 **/
public class ExamPro {
    private Integer type;
    private String dry;
    private Integer[] answers;
    private List<String> options;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDry() {
        return dry;
    }

    public void setDry(String dry) {
        this.dry = dry;
    }

    public Integer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Integer[] answers) {
        this.answers = answers;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
