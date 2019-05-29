package com.mzyan.VSM;

/**
 * 最小节点类，存放每个词项对应文档的数据（频率）
 */
public class DocNode {
    private Integer docId;
    private Integer tf;
    private Double weight;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getTf() {
        return tf;
    }

    public void setTf(Integer tf) {
        this.tf = tf;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public DocNode(Integer docId, Integer tf) {
        this.docId = docId;
        this.tf = tf;
        this.weight = 0.0;
    }
}
