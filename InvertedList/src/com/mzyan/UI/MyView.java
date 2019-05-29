package com.mzyan.UI;

import com.mzyan.VSM.VsmFileScan;
import com.mzyan.bool_search.BoolFileScan;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MyView {


    private BoolFileScan boolFileScan;
    private VsmFileScan vsmFileScan;

    private static final int viewWidth = 600;
    private static final int viewHeight = 400;

    private int normlsize = 20;
    private Font normalFont = new Font("微软雅黑", Font.PLAIN, 15);
    private int lineHeight = 25;

    private int PADDING = 5;
    private int MARGIN = 15;

    private JFrame jFramemain;
    private Container container;

    private JTextArea statusArea;

    public MyView() {
        boolFileScan = new BoolFileScan();
        vsmFileScan = new VsmFileScan();
    }

    public void show(){
        jFramemain = new JFrame();
        jFramemain.setTitle("信息检索系统");
        jFramemain.setSize(viewWidth, viewHeight);
        jFramemain.setLocationRelativeTo(null);
        jFramemain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFramemain.setResizable(false);
        container = jFramemain.getContentPane();

        // 主面板
        JPanel panel = new JPanel();
        int panelwidth = viewWidth - 2 * PADDING;
        int panelHeight = viewHeight - 3 * PADDING;
        panel.setSize(panelwidth, panelHeight);
        panel.setLocation(0, 0);
        panel.setLayout(null);
        container.add(panel);

        // 文本标签
        JLabel chooseLabel = new JLabel("选择扫描目录：", JLabel.LEFT);
        chooseLabel.setFont(normalFont);
        chooseLabel.setBounds(PADDING, PADDING, 5 * lineHeight, lineHeight);
        panel.add(chooseLabel);

        int top = PADDING;

        // 文本框
        JTextField choosetextfield = new JTextField();
        choosetextfield.setFont(normalFont);
        choosetextfield.setForeground(Color.GRAY);
        choosetextfield.setBounds(chooseLabel.getX()+chooseLabel.getWidth() - 30, PADDING, 15*lineHeight + 25, lineHeight);
        panel.add(choosetextfield);

        // 按钮
        JButton openfileButton = new JButton("打 开");
        openfileButton.setFont(normalFont);
        openfileButton.setBounds(choosetextfield.getX()+choosetextfield.getWidth(), PADDING, 4*lineHeight, lineHeight);
        openfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示打开的文件对话框
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showOpenDialog(null);
                try {
                    // 使用文件类获取选择器选择的文件
                    File file = jfc.getSelectedFile();
                    choosetextfield.setText(file.getAbsolutePath());
                    boolFileScan.setFilepath(file.getAbsolutePath());
                    vsmFileScan.setFilepath(file.getAbsolutePath());
                } catch (Exception e2) {
                    JPanel panel3 = new JPanel();
                    JOptionPane.showMessageDialog(panel3, "没有选中任何文件", "提示", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        panel.add(openfileButton);

        top += lineHeight + PADDING;

        // bool检索面板
        JPanel boolsearchPanel = new JPanel();
        boolsearchPanel.setBounds(PADDING, top, viewWidth - 2 * PADDING, 100);
        boolsearchPanel.setLayout(null);
        panel.add(boolsearchPanel);

        TitledBorder boolTitle = new TitledBorder("布尔查询");
        boolTitle.setTitleColor(Color.pink);
        boolsearchPanel.setBorder(boolTitle);

        JLabel bool_step1 = new JLabel("Step-1:扫描文本集，生成倒排列表", JLabel.LEFT);
        bool_step1.setFont(normalFont);
        bool_step1.setBounds(PADDING, PADDING+10, 18*lineHeight, lineHeight);
        boolsearchPanel.add(bool_step1);

        JLabel bool_step2 = new JLabel("Step-2:输入单词对，进行布尔查询", JLabel.LEFT);
        bool_step2.setFont(normalFont);
        bool_step2.setBounds(PADDING, PADDING+10+lineHeight, 18*lineHeight, lineHeight);
        boolsearchPanel.add(bool_step2);

        JLabel search_label1 = new JLabel("搜索词1：");
        search_label1.setBounds(PADDING+10, PADDING+10+lineHeight*2, 3*lineHeight, lineHeight);
        boolsearchPanel.add(search_label1);
        JTextField boolword1 = new JTextField();
        boolword1.setBounds(PADDING+10+search_label1.getWidth()-10, PADDING+10+lineHeight*2, 3*lineHeight, lineHeight);
        boolword1.setForeground(Color.RED);
        boolsearchPanel.add(boolword1);

        JLabel search_label2 = new JLabel("搜索词2：");
        search_label2.setBounds(boolword1.getX()+boolword1.getWidth()+10, PADDING+10+lineHeight*2, 3*lineHeight, lineHeight);
        boolsearchPanel.add(search_label2);
        JTextField boolword2 = new JTextField();
        boolword2.setBounds(search_label2.getX()+search_label2.getWidth()-10, PADDING+10+lineHeight*2, 3*lineHeight, lineHeight);
        boolword2.setForeground(Color.RED);
        boolsearchPanel.add(boolword2);

        JButton boolScan = new JButton("扫 描");
        boolScan.setBounds(PADDING + bool_step1.getWidth(), PADDING+10, 4*lineHeight, lineHeight);
        boolScan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // bool检索  扫描函数
                statusArea.setText("开始扫描文本...");
                boolFileScan.Scan();
                statusArea.setText("扫描完毕，倒排列表生成成功!!!");
            }
        });
        boolsearchPanel.add(boolScan);

        JButton AndButton = new JButton("AND 搜索");
        AndButton.setBounds(boolword2.getX()+boolword2.getWidth()+40, PADDING+10+2*lineHeight, 4*lineHeight, lineHeight);
        AndButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // bool And search
                ArrayList<Integer> res = boolFileScan.getInvertedList().searchAnd(boolword1.getText(), boolword2.getText());
                statusArea.setText("匹配文档集id："+ res.toString());
            }
        });
        boolsearchPanel.add(AndButton);

        JButton OrButton = new JButton("OR 搜索");
        OrButton.setBounds(AndButton.getX()+AndButton.getWidth()+10, PADDING+10+2*lineHeight, 4*lineHeight, lineHeight);
        OrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // bool Or search
                ArrayList<Integer> res = boolFileScan.getInvertedList().searchOr(boolword1.getText(), boolword2.getText());
                statusArea.setText("匹配文档集id："+ res.toString());
            }
        });
        boolsearchPanel.add(OrButton);

        top += 100;

        // vsm 面板
        JPanel vsmPanel = new JPanel();
        vsmPanel.setBounds(PADDING, top, viewWidth - 2 * PADDING, 100);
        vsmPanel.setLayout(null);
        panel.add(vsmPanel);

        TitledBorder vsmTitle = new TitledBorder("基于VSM的查询");
        vsmTitle.setTitleColor(Color.ORANGE);
        vsmPanel.setBorder(vsmTitle);

        JLabel vsm_step1 = new JLabel("Step-1:扫描文本集，生成VSM", JLabel.LEFT);
        vsm_step1.setFont(normalFont);
        vsm_step1.setBounds(PADDING, PADDING+10, 18*lineHeight, lineHeight);
        vsmPanel.add(vsm_step1);

        JLabel vsm_step2 = new JLabel("Step-2:输入文本，进行查询", JLabel.LEFT);
        vsm_step2.setFont(normalFont);
        vsm_step2.setBounds(PADDING, PADDING+10+lineHeight, 18*lineHeight, lineHeight);
        vsmPanel.add(vsm_step2);

        JButton VsmScan = new JButton("扫 描");
        VsmScan.setBounds(PADDING + vsm_step1.getWidth(), PADDING+10, 4*lineHeight, lineHeight);
        VsmScan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vsm检索  扫描函数
                statusArea.setText("开始扫描文本...");
                vsmFileScan.Scan();
                statusArea.setText("扫描完毕，倒排列表生成成功!!!");
            }
        });
        vsmPanel.add(VsmScan);

        JLabel search_label3 = new JLabel("搜索文本：");
        search_label3.setBounds(PADDING+10, PADDING+10+lineHeight*2, 3*lineHeight, lineHeight);
        vsmPanel.add(search_label3);
        JTextField vsmwords = new JTextField();
        vsmwords.setBounds(PADDING+10+search_label1.getWidth()-10, PADDING+10+lineHeight*2, 10*lineHeight-10, lineHeight);
        vsmwords.setForeground(Color.RED);
        vsmPanel.add(vsmwords);

        JLabel search_label4 = new JLabel("结果数：");
        search_label4.setBounds(vsmwords.getX()+vsmwords.getWidth()+10, PADDING+10+lineHeight*2, 70, lineHeight);
        vsmPanel.add(search_label4);

        JComboBox<Integer> resNum = new JComboBox<>();
        resNum.setBounds(search_label4.getX()+search_label4.getWidth()-25, PADDING+10+lineHeight*2, 70, lineHeight);
        resNum.addItem(3);
        resNum.addItem(5);
        resNum.addItem(10);
        resNum.addItem(15);
        resNum.addItem(20);
        resNum.addItem(50);
        vsmPanel.add(resNum);

        JButton vsmSearchButton = new JButton("搜 索");
        vsmSearchButton.setBounds(resNum.getX()+resNum.getWidth()+10, PADDING+10+2*lineHeight, 4*lineHeight, lineHeight);
        vsmSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // vsm search
                ArrayList<Integer> res = vsmFileScan.VSMsearch(vsmwords.getText(), (int)resNum.getSelectedItem());
                statusArea.setText("匹配文档集id："+ res.toString());
            }
        });
        vsmPanel.add(vsmSearchButton);



        top += 100;

        // 监控面板
        JPanel statusJPanel = new JPanel();
        statusJPanel.setBounds(PADDING, top, viewWidth - 2 * PADDING, 140);
        statusJPanel.setLayout(null);
        panel.add(statusJPanel);

        TitledBorder statusTitle = new TitledBorder("监控");
        statusTitle.setTitleColor(Color.cyan);
        statusJPanel.setBorder(statusTitle);

        statusArea = new JTextArea();
        statusArea.setBounds(PADDING, PADDING+10, + viewWidth-4*PADDING, 120);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);
        statusArea.setText("请先选择文档集目录!");
        statusJPanel.add(statusArea);


        // 放在最后，避免视图问题
        jFramemain.setVisible(true);

    }
}
