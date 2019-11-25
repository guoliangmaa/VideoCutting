package view;

import javax.swing.*;

/**
 * @Auther: MGL
 * @Date: 2019/3/21 15:59
 * @Description:测试网上代码，用JLable来显示图片
 */
public class Test extends JFrame {
    private JSplitPane jSplitPane;
    private JLabel jLabel;
    private JList jList;
    public static void main(String[] args){
        Test jsplitPane = new Test();
    }

    public Test(){
        String [] words = {"Java", "Python", "Golang"};
        jLabel = new JLabel(new ImageIcon("D:\\a.png"));
        jList = new JList(words);
// JSplitPane 拆分窗格，垂直拆分方式
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jList, jLabel);
        this.add(jSplitPane);
//设置JFrame属性
        this.setTitle("工程");
        this.setLocation(500, 250);
        this.setSize(350, 200);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
