package com.blake;

public class Main2 {
    public static void main(String[] args) {
        TableColData demo = new TableColData();
        demo.setEnableUnique("1");
        TableColData demo2 = new TableColData();
        copySomething(demo, demo2);
        System.out.println(demo2.getEnableUnique());
    }

    public static void copySomething(TableColData demo, TableColData demo1){
//        demo1.setEnableUnique(demo.getEnableUnique());
    }

}
