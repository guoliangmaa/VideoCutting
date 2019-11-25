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
 * @Description:从本地目录读取图片，实现拖拽功能
 */
@SuppressWarnings("all")
public class ImageDragged {

    private static JFrame jFrame;
    private ImageIcon icon;
    private JLabel jLabel;
    private BufferedImage image;
    public static String path = "D://b.gif";

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ImageDragged window = new ImageDragged();
                window.jFrame.setVisible(true);
            }
        });
    }

    public ImageDragged(){initialize();}

    private void initialize(){

        try {
            image = new BufferedImage(300,200,BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        jFrame = new JFrame();
        jFrame.setBounds(700,300,720,480);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(null);

//        控制缩放，自适应
        icon = new ImageIcon(path);
        icon.setImage(icon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));

        jLabel = new JLabel(icon,JLabel.CENTER);
        jLabel.setBounds(20,20,100,100);

        ListenerForJLable listener = new ListenerForJLable();
        jLabel.addMouseListener(listener);
        jLabel.addMouseMotionListener(listener);
        jFrame.getContentPane().add(jLabel);
    }

    public JLabel getjLabel(){
        return jLabel;
    }
    private class ListenerForJLable extends MouseInputAdapter {

        private JLabel jLabel;
        private ImageIcon icon;
        private int relative_X;
        private int relative_Y;
        public ListenerForJLable() {
            super();

//            icon = new ImageIcon(ImageDragged.path);
            icon = new ImageIcon(image);
            icon.setImage(icon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
            jLabel = new JLabel(icon);
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel temp = (JLabel)e.getSource();
            System.out.println("call method mouseClicked...." + "location is :    (" +e.getX() + "  ,  " + e.getY() + " )");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            jLabel.setVisible(true);
            JLabel temp = (JLabel)e.getSource();
            relative_X = e.getX() - 20;
            relative_Y = e.getY() - 20;
            jLabel.setBounds(20,20,100,100);
            ImageDragged.jFrame.getContentPane().add(jLabel);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("================================= ");
            System.out.println("call method mouseReleased....");
            System.out.println("final mouse location is :    ( " + e.getX() + "  ,  " + e.getY() + " )");
            System.out.println("final picture location is :    ( " + (e.getX() - relative_X) + "  ,  " + (e.getY() - relative_Y) + " )");
            jLabel.removeAll();
            jLabel.setVisible(false);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            jLabel.setBounds(e.getX()-relative_X,e.getY()-relative_Y,100,100);
        }
    }
}
