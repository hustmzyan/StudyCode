package com.mzyan.bool_search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * 倒排列表节点
 */
public class ItemListNode {
    private String Word;
    private Integer Freq;
    private ArrayList<Integer> DocList;

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public Integer getFreq() {
        return Freq;
    }

    public void setFreq(Integer freq) {
        Freq = freq;
    }

    public ArrayList<Integer> getDocList() {
        return DocList;
    }

    public void setDocList(ArrayList<Integer> docList) {
        DocList = docList;
    }

    @Override
    public String toString() {
        return "ItemListNode{" +
                "Word='" + Word + '\'' +
                ", Freq=" + Freq +
                ", DocList=" + DocList +
                '}';
    }

    // 新建node
    public ItemListNode(String word, Integer docId) {
        Word = word;
        Freq = 1;
        DocList = new ArrayList<>();
        DocList.add(docId);
    }

    // 更新node
    public void addfreq(Integer docId){
        Freq++;
        DocList.add(docId);
        Collections.sort(DocList);
    }
}
