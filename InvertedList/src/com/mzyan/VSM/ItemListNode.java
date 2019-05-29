package com.mzyan.VSM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 倒排列表节点
 */
public class ItemListNode {
    private String Word;
    private Integer Freq;

    // 逆文档频率（反映该词项的信息量和权重）
    private Double idf;

    private ArrayList<DocNode> DocList;

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

    public Double getIdf() {
        return idf;
    }

    public ArrayList<DocNode> getDocList() {
        return DocList;
    }

    public void setDocList(ArrayList<DocNode> docList) {
        DocList = docList;
    }

    public void setIdf(Double idf) {
        this.idf = idf;
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
    public ItemListNode(String word, Integer tf, Integer docId) {
        Word = word;
        Freq = 1;
        idf = 0.0;
        DocList = new ArrayList<>();
        DocList.add(new DocNode(docId, tf));
    }

    // 更新node
    public void addfreq(Integer docId, Integer tf){
        Freq++;
        DocList.add(new DocNode(docId, tf));
        DocList.sort(new Comparator<DocNode>() {
            @Override
            public int compare(DocNode o1, DocNode o2) {
                return o1.getDocId().compareTo(o2.getDocId());
            }
        });
    }
}
