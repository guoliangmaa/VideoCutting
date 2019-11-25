package view;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @Auther: MGL
 * @Date: 2019/3/22 16:56
 * @Description:监听JLabel组件的拖拽，实现返回最终拖拽图片的位置,构造方法有四个参数为动态生成图片的坐标及大小
 * 注意后面要提供你类中的JFrame对象
 */
@SuppressWarnings("ALL")
public class ListenerForJLabel extends MouseInputAdapter {
    private JLabel jLabel;
    private ImageIcon icon;
    private int relative_X;
    private int relative_Y;
    private int point_X;
    private int point_Y;
    private int width;
    private int height;
    private Point jLableLocation;


    public ListenerForJLabel(int x,int y,int width,int height) {
        this.point_X = x;
        this.point_Y = y;
        this.width = width;
        this.height = height;
        icon = new ImageIcon(DraggingImageUtil.path);
        icon.setImage(icon.getImage().getScaledInstance(this.width,this.height, Image.SCALE_DEFAULT));
        jLabel = new JLabel(icon);
    }



    @Override
    public void mousePressed(MouseEvent e) {

        jLabel.setVisible(true);

        JLabel temp = (JLabel)e.getSource();
        relative_X = e.getX() - point_X;
        relative_Y = e.getY() - point_Y;
        jLabel.setBounds(point_X,point_Y,width,height);


//        需要得到你的JFrame，可以设置为静态后者提供它的get方法，将jLabel放入你的jFrame中

       login.jFrame.getContentPane().add(jLabel);

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        jLabel.setVisible(false);

        jLableLocation = new Point((e.getX() - relative_X),(e.getY() - relative_Y));
        System.out.println("final picture location is :    ( " + (e.getX() - relative_X) + "  ,  " + (e.getY() - relative_Y) + " )");
        System.out.println(jLableLocation);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        jLabel.setBounds(e.getX()-relative_X,e.getY()-relative_Y,width,height);
    }

    /**
     * 生成最终拖拽完成后图片的位置坐标Point，
     * @return
     */
    public Point getjLableLocation(){
        return jLableLocation;
    }
}
