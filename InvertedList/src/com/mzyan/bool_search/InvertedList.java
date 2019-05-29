package com.mzyan.bool_search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 倒排列表类
 */
public class InvertedList {
    private String ListName;
    private Integer DocCount;
    private ArrayList<ItemListNode> nodeList;

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        ListName = listName;
    }

    public void addDocCount() {
        DocCount++;
    }

    public ArrayList<ItemListNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<ItemListNode> nodelist) {
        nodeList = nodelist;
    }

    public InvertedList() {
        ListName = "mzyan's invertedlist";
        DocCount = 0;
        nodeList = new ArrayList<>();
    }

    private Integer searchword(String word){
        for(int i = 0; i < nodeList.size(); i++){
            if(nodeList.get(i).getWord().equals(word)){
                return i;
            }
        }
        return -1;
    }

    public void addWord(String word, Integer docId){
        Integer index = searchword(word);
        if(index == -1){
            //当前没有该单词节点
            ItemListNode node = new ItemListNode(word, docId);
            nodeList.add(node);
            // 此处需要对倒排列表进行排序
            nodeList.sort(new Comparator<ItemListNode>() {
                @Override
                public int compare(ItemListNode o1, ItemListNode o2) {
                    return o1.getWord().compareTo(o2.getWord());
                }
            });
        }else{
            //当前已有该单词节点
            nodeList.get(index).addfreq(docId);
        }
    }

    /**
     * 函数功能：and 查询
     * @param a
     * @param b
     * @return
     */
    public ArrayList<Integer> searchAnd(String a, String b){
        ArrayList<Integer> a_doc = new ArrayList<>();
        ArrayList<Integer> b_doc = new ArrayList<>();
        Integer a_idx = searchword(a);
        Integer b_idx = searchword(b);
        if(a_idx != -1 && b_idx != -1) {
            a_doc = nodeList.get(a_idx).getDocList();
            b_doc = nodeList.get(b_idx).getDocList();
            a_doc.retainAll(b_doc);
        }
        return a_doc;
    }

    /**
     * 函数功能：or 查询
     * @param a
     * @param b
     * @return
     */
    public ArrayList<Integer> searchOr(String a, String b){
        ArrayList<Integer> a_doc = new ArrayList<>();
        ArrayList<Integer> b_doc = new ArrayList<>();
        Integer a_idx = searchword(a);
        Integer b_idx = searchword(b);
        if(a_idx != -1 && b_idx != -1){
            a_doc = nodeList.get(a_idx).getDocList();
            b_doc = nodeList.get(b_idx).getDocList();
            a_doc.removeAll(b_doc);
            a_doc.addAll(b_doc);
        }else if(a_idx != -1 && b_idx == -1){
            a_doc = nodeList.get(a_idx).getDocList();
        }else if(a_idx == -1 && b_idx != -1){
            a_doc = nodeList.get(b_idx).getDocList();
        }
        Collections.sort(a_doc);
        return a_doc;
    }
}
