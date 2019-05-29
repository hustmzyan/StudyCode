package com.mzyan.VSM;

import org.omg.PortableInterceptor.INACTIVE;

import javax.print.Doc;
import java.util.*;
import java.util.function.DoubleBinaryOperator;

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
        ListName = "mzyan's VSM invertedlist";
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

    public void addWord(String word, Integer tf, Integer docId){
        Integer index = searchword(word);
        if(index == -1){
            //当前没有该单词节点
            ItemListNode node = new ItemListNode(word, tf, docId);
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
            nodeList.get(index).addfreq(docId, tf);
        }
    }

    public void computeidf(){
        int i = 0;
        for(ItemListNode node : nodeList){
            System.out.print("计算进度:" + (float)i/nodeList.size() * 100 + "%");
            double idf = Math.log10((double) DocCount/node.getFreq());
            node.setIdf(idf);
            for(DocNode docNode : node.getDocList()){
                docNode.setWeight(idf * (1 + Math.log10(docNode.getTf())));
            }
            i++;

            for (int j = 0; j <= String.valueOf((float)i/nodeList.size() * 100).length() + 9; j++) {
                System.out.print("\b");
            }
        }
    }

    /**
     * 函数功能：支持VSM的查询函数
     * @param words 输入单词set
     * @param k 保留前k个结果
     * @return  结果map <docid, weight>
     */
    public List<Map.Entry<Integer, Double>> searchKdocument(Set<String> words, Integer k){
        Map<Integer, Double> map = new HashMap<>();
        for(String w : words){
            Integer w_idx = searchword(w);
            ArrayList<DocNode> docList = nodeList.get(w_idx).getDocList();
            for(DocNode docNode : docList){
                map.merge(docNode.getDocId(), docNode.getWeight(), Double::sum);
            }
        }
        Set<Map.Entry<Integer, Double>> entries = map.entrySet();
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(entries);
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list.subList(0,k);
    }
}
