package com.example.mchat.javaBean;

/**
 * Created by yinm_pc on 2017/6/5.
 */

public class EmojiBean {
    private String name;
    private int id;

    public EmojiBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
