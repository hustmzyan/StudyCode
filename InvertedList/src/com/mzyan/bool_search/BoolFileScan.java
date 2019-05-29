package com.mzyan.bool_search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 文本扫描类：生成倒排列表
 */
public class BoolFileScan {
    private String filepath;
    private InvertedList invertedList;

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public InvertedList getInvertedList() {
        return invertedList;
    }

    public BoolFileScan() {
        invertedList = new InvertedList();
    }

    public BoolFileScan(String filepath) {
        this.filepath = filepath;
        invertedList = new InvertedList();
    }

    /**
     * 函数功能：扫描文本文件，生成倒排列表
     */
    public void Scan(){
        File file = new File(filepath);
        if(file.isDirectory()){
            System.out.println("开始扫描文本...");
            String[] filelist = file.list();
            for(int i = 0; i < filelist.length; i++){
                System.out.print("扫描进度:" +(float)(i+1)/filelist.length * 100 + "%");
                Integer docid = Integer.valueOf(filelist[i].split("\\.")[0]);
                buildInvertedList(text2Word(readFile(filepath + "/" + filelist[i])), docid);

                for (int j = 0; j <= String.valueOf((float)(i+1)/filelist.length * 100).length()+7; j++) {
                    System.out.print("\b");
                }
            }
            System.out.println("扫描完毕，倒排列表生成成功!!!");
        }
    }

    /**
     * 函数功能：读取单个文本文件内容
     * @param FileName：文本路径+文本名+后缀
     * @return 文本String
     */
    public String readFile(String FileName){
        File file = new File(FileName);
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                sb.append(line + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 函数功能：将多行文本，分割为单词
     * @param text 输入文本
     * @return 返回单词Set
     */
    public Set<String> text2Word(String text){
        Set<String> word = new HashSet<>();
        StringTokenizer st = new StringTokenizer(text," ,?.!:\"\"''%&$_-[]<>()/1234567890“”*;\n#");
        while (st.hasMoreElements()) {
            word.add(st.nextToken().toLowerCase());
        }
        return word;
    }

    /**
     * 函数功能：将单词集添加到建立的倒排列表
     * @param words 输入的单词集
     */
    public void buildInvertedList(Set<String> words, Integer docId){
        //文档计数+1
        invertedList.addDocCount();
        Iterator<String> iter = words.iterator();
        while(iter.hasNext()){
            invertedList.addWord(iter.next(), docId);
        }
    }

    // Test
    public static void main(String[] args) throws InterruptedException {
        BoolFileScan boolFileScan = new BoolFileScan("/Users/macbook/Desktop/essay");
//        String text = boolFileScan.readFile("/Users/macbook/Desktop/test.txt");
//        System.out.println(boolFileScan.text2Word(text));
        boolFileScan.Scan();
        ArrayList<Integer> res = boolFileScan.invertedList.searchAnd("with", "love");
        System.out.println(res);
    }
}