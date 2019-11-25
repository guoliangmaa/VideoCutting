package view;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Auther: MGL
 * @Date: 2019/3/20 22:17
 * @Description:生成显示图片的JLabel，构造器需提供位置坐标以及组件的大小，可以获得监听器
 * 通过监听器可以得到最后拖拽的图片坐标，测试可能会出现坐标为空的情况，我有一个想法就是
 */
@SuppressWarnings("all")
public class DraggingImageUtil {

    private  ImageIcon icon;
    private  JLabel jLabel;
    private  BufferedImage image;
    private ListenerForJLabel listener;
    private int point_X;
    private int point_Y;
    private int width;
    private int height;

    public static String path = "D://b.gif";

    /**
     *  构造器
     *
     * @param x 图片放置的x坐标
     * @param y 图片防止的y坐标
     * @param width 图片的宽度
     * @param height 图片的高度
     */
    public DraggingImageUtil(int x,int y,int width,int height){
        this.point_X = x;
        this.point_Y = y;
        this.width = width;
        this.height = height;
        initialize();
    }

    private void initialize(){
        try {
            image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //        控制缩放，自适应
        icon = new ImageIcon(path);
        icon.setImage(icon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));

        jLabel = new JLabel(icon,JLabel.CENTER);
        jLabel.setBounds(point_X,point_Y,width,height);

        listener = new ListenerForJLabel(point_X,point_Y,width,height);
        jLabel.addMouseListener(listener);
        jLabel.addMouseMotionListener(listener);
    }

    /**
     * 得到JLabel
     * @return
     */
    public JLabel getjLabel(){
        return this.jLabel;
    }

    /**
     * 得到JLable的监听器
     * @return
     */
    public ListenerForJLabel getListener(){
        return this.listener;
    }

}
