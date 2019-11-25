package view;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.org.apache.bcel.internal.generic.DMUL;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

@SuppressWarnings("all")
public class Demo1 {

    private static String currentMediaAbsolutePath = "2.wmv";

    //五个拉伸位置
    private static int UP_BORDER = 0;
    private static int DOWN_BORDER = 1;
    private static int LEFT_BORDER = 2;
    private static int RIGHT_BORDER = 3;
    private static int ANGLE_UP_LEFT = 4;
    private static int ANGLE_UP_RIGHT = 5;
    private static int ANGLE_DOWN_LEFT = 6;
    private static int ANGLE_DOWN_RIGHT = 7;

    // 可缩放的播放器的大小和位置
    private static Point point = new Point(55,50);
    private static Dimension dimension = new Dimension(473,347);

    public static BufferedImage image1;
    public static BufferedImage image2;
    public static BufferedImage image3;
    private static JFrame frame;

    //these two on the top

    private static MainEmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_1;

    //these split four
    private static MyEmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
    private static MyEmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_split_2;
    private static MyEmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_split_3;
    private static MyEmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_split_4;

    //these three on the buttom
    private JPanel panel_buttom1;
    private static MyEmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_4;
    private JPanel panel_buttom2;
    private static MyEmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_5;
    private JPanel panel_buttom3;
    private static MyEmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_6;

    public static JLabel jLabel;
    public static ImageIcon icon;
    public static JPanel temppanel;
    public static int relative_x;
    public static int relative_y;
    private JButton btnNewButton_1;

    private JProgressBar progressBar;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC\\sdk\\lib");	//�����·����vlc�İ�װ·��
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Demo1 window = new Demo1(args);

//                    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
//                            .getDefaultScreenDevice();
//                    gd.setFullScreenWindow(frame);
                    window.frame.setVisible(true);
//					embeddedMediaPlayerComponent.getMediaPlayer().playMedia("2.wmv");
//					embeddedMediaPlayerComponent_1.getMediaPlayer().playMedia("2.wmv");
                    embeddedMediaPlayerComponent_4.getMediaPlayer().playMedia("2.wmv");
//					embeddedMediaPlayerComponent_5.getMediaPlayer().playMedia("2.wmv");
//					embeddedMediaPlayerComponent_6.getMediaPlayer().playMedia("2.wmv");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();

        frame.setVisible(true);
        frame.setBounds(100, 100, 1340, 977);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBounds(56, 545, 475, 16);
        frame.getContentPane().add(progressBar);
        progressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                System.out.println("x=  "  + x);
                System.out.println("e.getX() =   " + e.getX());
                long length = embeddedMediaPlayerComponent_1.getMediaPlayer().getLength();
                float percent = (float)x/progressBar.getWidth();
                embeddedMediaPlayerComponent_1.getMediaPlayer().setTime((long) (percent * length));
            }
        });

        JPanel panel_main = new JPanel();
        panel_main.setBounds(0, 49, 608, 342);
        frame.getContentPane().add(panel_main,new Integer(100));
        panel_main.setLayout(null);
        panel_main.setBorder(BorderFactory.createLoweredBevelBorder());

        panel_main.addMouseListener(new MouseAdapter() {

            private Point onPressedLoction = new Point();

            private int position;



            @Override
            public void mousePressed(MouseEvent e) {
               //四个角的位置
                int x = e.getX();
                int y = e.getY();
                int distant1 = (int)((x-point.getX())*(x-point.getX()) + (y-point.getY())*(y-point.getY()));
                int distant2 =(int) ((x-dimension.getWidth() - point.getX())*(x-dimension.getWidth() - point.getX()) + (y-point.getY())*(y-point.getY()));
                int distant3 = (int)((x-point.getX())*(x-point.getX()) + (y-dimension.getHeight() - point.getY())*(y-dimension.getHeight() - point.getY()));
                int distant4 = (int)((x-dimension.getWidth() -point.getX())*(x-dimension.getWidth() -point.getX()) + (y-dimension.getHeight() - point.getY())*(y-dimension.getHeight() - point.getY()));
                if(distant1 >=0 && distant1 <=64){
                    position = Demo1.ANGLE_UP_LEFT;
                    frame.setCursor(Cursor.NW_RESIZE_CURSOR);
                }else if(distant2 >=0 && distant2 <=64){
                    position = Demo1.ANGLE_UP_RIGHT;
                }else if(distant3 >= 0 && distant3 <=64){
                    position = Demo1.ANGLE_DOWN_LEFT;
                }else if (distant4 >=0 && distant4 <=64){
                    position = Demo1.ANGLE_DOWN_RIGHT;
                }else if(x >= point.getX() && x <= point.getX() + dimension.getWidth() && y - point.getY() >=-10 && y - point.getY() <=10){
                    position = Demo1.UP_BORDER;
                }else if(x >= point.getX() && x <= point.getX() + dimension.getWidth() && y - point.getY() - dimension.getHeight() >=-10 && y - point.getY() - dimension.getHeight() <= 10){
                    position = Demo1.DOWN_BORDER;
                }else if(y >= point.getY() && y <= point.getY() + dimension.getHeight() && x - point.getX() >= -10 && x - point.getX() <= 10){
                    position = Demo1.LEFT_BORDER;
                }else if(y >= point.getY() && y <= point.getY() + dimension.getHeight() && x - point.getX() - dimension.getWidth() >= -10 && x - point.getX() - dimension.getWidth() <= 10){
                    position = Demo1.RIGHT_BORDER;
                }

                onPressedLoction.setLocation(e.getX(),e.getY());

            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                int y = e.getY();
//                int disp = Math.abs(y - (int)onPressedLoction.getY());
//
//                embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY()-disp);
//                embeddedMediaPlayerComponent_1.setSize((int)dimension.getWidth(),(int)dimension.getHeight()+disp);
//
//                System.out.println("fangxiale");

                int relative_x=0,relative_y=0,width=0,height=0;

                if(position == Demo1.ANGLE_UP_LEFT){
                    relative_x = (int)(e.getX() - point.getX());
                    relative_y = (int)(e.getY() - point.getY());

                    width = (int) (dimension.getWidth() - relative_x);
                    height = (int) (dimension.getHeight() - relative_y);

                    embeddedMediaPlayerComponent_1.setSize(width, height);
                    embeddedMediaPlayerComponent_1.setLocation(e.getX(),e.getY());
                    point.setLocation(e.getX(),e.getY());
                    dimension.setSize(width,height);

                }else if(position == Demo1.ANGLE_UP_RIGHT){
                    relative_x = (int)(e.getX() - point.getX() - dimension.getWidth());
                    relative_y = (int)(e.getY() - point.getY());

                    width = (int) (dimension.getWidth() + relative_x);
                    height = (int) (dimension.getHeight() - relative_y);

                    embeddedMediaPlayerComponent_1.setSize(width, height);
                    embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                    point.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                    dimension.setSize(width,height);

                }else if(position == Demo1.ANGLE_DOWN_LEFT) {
                    relative_x = (int) (e.getX() - point.getX());
                    relative_y = (int) (e.getY() - point.getY() - dimension.getHeight());

                    width = (int) (dimension.getWidth() - relative_x);
                    height = (int) (dimension.getHeight() + relative_y);

                    embeddedMediaPlayerComponent_1.setSize(width, height);
                    embeddedMediaPlayerComponent_1.setLocation((int)point.getX() + relative_x, (int) point.getY());
                    point.setLocation((int)point.getX() + relative_x, (int) point.getY());
                    dimension.setSize(width,height);
                }else if(position == Demo1.ANGLE_DOWN_RIGHT){
                    relative_x = (int) (e.getX() - point.getX() - dimension.getWidth());
                    relative_y = (int) (e.getY() - point.getY() - dimension.getHeight());

                    width = (int) (dimension.getWidth() + relative_x);
                    height = (int) (dimension.getHeight() + relative_y);

                    embeddedMediaPlayerComponent_1.setSize(width, height);
                    embeddedMediaPlayerComponent_1.setLocation((int)point.getX(), (int) point.getY());
                    point.setLocation((int)point.getX(), (int) point.getY());
                    dimension.setSize(width,height);
                }else if(position == Demo1.UP_BORDER){
                    relative_y = (int) (e.getY() - point.getY());

                    height = (int) (dimension.getHeight() - relative_y);

                    embeddedMediaPlayerComponent_1.setSize((int) dimension.getWidth(), height);
                    embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                    point.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                    dimension.setSize((int) dimension.getWidth(),height);
                }else if(position == Demo1.DOWN_BORDER){
                    relative_y = (int) (e.getY() - point.getY() - dimension.getHeight());

                    height = (int) (dimension.getHeight() + relative_y);

                    embeddedMediaPlayerComponent_1.setSize((int) dimension.getWidth(), height);
                    embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY());
                    point.setLocation((int)point.getX(),(int)point.getY());
                    dimension.setSize((int) dimension.getWidth(),height);
                }else if(position == Demo1.LEFT_BORDER){

                    relative_x = (int) (e.getX() - point.getX());

                    width = (int) (dimension.getWidth() - relative_x);

                    embeddedMediaPlayerComponent_1.setSize(width, (int) dimension.getHeight());
                    embeddedMediaPlayerComponent_1.setLocation((int)point.getX() + relative_x,(int)point.getY());
                    point.setLocation((int)point.getX() + relative_x,(int)point.getY());
                    dimension.setSize(width, (int) dimension.getHeight());
                }else if(position == Demo1.RIGHT_BORDER){
                    relative_x = (int) (e.getX() - point.getX() - dimension.getWidth());

                    width = (int) (dimension.getWidth() + relative_x);

                    embeddedMediaPlayerComponent_1.setSize(width, (int) dimension.getHeight());
                    embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY());
                    point.setLocation((int)point.getX() ,(int)point.getY());
                    dimension.setSize(width, (int) dimension.getHeight());
                }

                System.out.println("播放器的位置： x :" + point.getX() + "   y:  " + point.getY());

//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//                System.out.println(point.getX()+"  "+point.getY());
//                embeddedMediaPlayerComponent_1.getMediaPlayer().playMedia("2.wmv");
                position = -1;
            }
        });

        embeddedMediaPlayerComponent_1 = new MainEmbeddedMediaPlayerComponent();

        embeddedMediaPlayerComponent_1.setBounds(100, 100, 304, 171);

        //存储当前播放器的位置和大小
        point.setLocation(100,100);
        dimension.setSize(304,171);

        panel_main.add(embeddedMediaPlayerComponent_1);


        JPanel panel_split_1 = new JPanel();
        panel_split_1.setBounds(689, 49, 304, 171);
        frame.getContentPane().add(panel_split_1,new Integer(100));
        panel_split_1.setLayout(null);
        embeddedMediaPlayerComponent = new MyEmbeddedMediaPlayerComponent();
        embeddedMediaPlayerComponent.setBounds(0, 0, 304, 171);
        panel_split_1.add(embeddedMediaPlayerComponent);
        embeddedMediaPlayerComponent.setCursorEnabled(true);

        JPanel panel_split_2 = new JPanel();
        panel_split_2.setLayout(null);
        panel_split_2.setBounds(996, 49, 304, 171);
        frame.getContentPane().add(panel_split_2);
        embeddedMediaPlayerComponent_split_2 = new MyEmbeddedMediaPlayerComponent();
        embeddedMediaPlayerComponent_split_2.setBounds(0, 0, 304, 171);
        panel_split_2.add(embeddedMediaPlayerComponent_split_2);
        embeddedMediaPlayerComponent_split_2.setCursorEnabled(true);

        JPanel panel_split_3 = new JPanel();
        panel_split_3.setLayout(null);
        panel_split_3.setBounds(689, 223, 304, 171);
        frame.getContentPane().add(panel_split_3);
        embeddedMediaPlayerComponent_split_3 = new MyEmbeddedMediaPlayerComponent();
        embeddedMediaPlayerComponent_split_3.setBounds(0, 0, 304, 171);
        panel_split_3.add(embeddedMediaPlayerComponent_split_3);
        embeddedMediaPlayerComponent_split_3.setCursorEnabled(true);

        JPanel panel_split_4 = new JPanel();
        panel_split_4.setLayout(null);
        panel_split_4.setBounds(996, 223, 304, 171);
        frame.getContentPane().add(panel_split_4);
        embeddedMediaPlayerComponent_split_4 = new MyEmbeddedMediaPlayerComponent();
        embeddedMediaPlayerComponent_split_4.setBounds(0, 0, 304, 171);
        panel_split_4.add(embeddedMediaPlayerComponent_split_4);
        embeddedMediaPlayerComponent_split_4.setCursorEnabled(true);

        panel_buttom1 = new JPanel();
        panel_buttom1.setLayout(null);
        panel_buttom1.setBounds(10, 639, 320, 240);
        frame.getContentPane().add(panel_buttom1,new Integer(100));

        embeddedMediaPlayerComponent_4 = new MyEmbeddedMediaPlayerComponent();
        embeddedMediaPlayerComponent_4.setBounds(0, 0, 320, 240);
        panel_buttom1.add(embeddedMediaPlayerComponent_4);

        panel_buttom2 = new JPanel();
        panel_buttom2.setLayout(null);
        panel_buttom2.setBounds(340, 639, 320, 240);
        frame.getContentPane().add(panel_buttom2,new Integer(100));

        embeddedMediaPlayerComponent_6 = new MyEmbeddedMediaPlayerComponent();
        embeddedMediaPlayerComponent_6.setBounds(0, 0, 320, 240);
        panel_buttom2.add(embeddedMediaPlayerComponent_6);

        panel_buttom3 = new JPanel();
        panel_buttom3.setLayout(null);
        panel_buttom3.setBounds(670, 639, 320, 240);
        frame.getContentPane().add(panel_buttom3,new Integer(100));

        embeddedMediaPlayerComponent_5 = new MyEmbeddedMediaPlayerComponent();
        embeddedMediaPlayerComponent_5.setBounds(0, 0, 320, 240);
        panel_buttom3.add(embeddedMediaPlayerComponent_5);

        JButton btnNewButton = new JButton("choose");
        btnNewButton.setBounds(20, 896, 93, 23);
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                int v = chooser.showOpenDialog(null);
                if (v== JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    //embeddedMediaPlayerComponent_1.getMediaPlayer().playMedia(file.getAbsolutePath());

                    //小窗口播放视频
                    embeddedMediaPlayerComponent_4.getMediaPlayer().playMedia(file.getAbsolutePath());
                    //静音

                    //给自定义播放器中成员变量赋值(播放文件的绝对路径)
                    embeddedMediaPlayerComponent_4.setMediaAbsolutePath(file.getAbsolutePath());
//					embeddedMediaPlayerComponent.getMediaPlayer().playMedia(file.getAbsolutePath());
                    System.out.println(file.getAbsolutePath());


                }
            }
        });
        frame.getContentPane().add(btnNewButton);

        btnNewButton_1 = new JButton("play");
        btnNewButton_1.setBounds(179, 896, 93, 23);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_4 = new JButton("confirm");
        btnNewButton_4.setBounds(237, 401, 93, 23);
        frame.getContentPane().add(btnNewButton_4);
        btnNewButton_4.addMouseListener(new MouseAdapter(){

            //确定按钮
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("confirm button clicked");
                float position = embeddedMediaPlayerComponent_1.getMediaPlayer().getPosition();
                System.out.println(position);
            }
        });

        JButton btnNewButton_2 = new JButton("pause");
        btnNewButton_2.setBounds(118, 560, 93, 23);
        frame.getContentPane().add(btnNewButton_2);
        btnNewButton_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                embeddedMediaPlayerComponent_1.getMediaPlayer().pause();
            }
        });

        JButton btnNewButton_3 = new JButton("play");
        btnNewButton_3.setBounds(354, 560, 93, 23);
        frame.getContentPane().add(btnNewButton_3);
        btnNewButton_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                embeddedMediaPlayerComponent_1.getMediaPlayer().play();
            }
        });
    }



    public Demo1(String[] args) {
        initialize();
    }


    private class MyEmbeddedMediaPlayerComponent extends EmbeddedMediaPlayerComponent{

        private String mediaAbsolutePath;



//        private

        public String getMediaAbsolutePath() {
            return mediaAbsolutePath;
        }

        public void setMediaAbsolutePath(String mediaAbsolutePath) {
            this.mediaAbsolutePath = mediaAbsolutePath;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("this component has clicked!");
            if(this.mediaAbsolutePath!=null && this.mediaAbsolutePath!= ""){
                currentMediaAbsolutePath = this.mediaAbsolutePath;
            }

            embeddedMediaPlayerComponent_1.getMediaPlayer().playMedia(currentMediaAbsolutePath);

            float position = embeddedMediaPlayerComponent_4.getMediaPlayer().getPosition();
            embeddedMediaPlayerComponent_1.getMediaPlayer().skipPosition(position);

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new SwingWorker<String , Integer>() {

                            @Override
                            protected String doInBackground() throws Exception {
                                while(true){
                                    long total = embeddedMediaPlayerComponent_1.getMediaPlayer().getLength();
                                    long curr = embeddedMediaPlayerComponent_1.getMediaPlayer().getTime();
                                    float percent = (float)curr/total;
                                    publish((int)(percent*100));
                                    Thread.sleep(100);
                                }
                            }

                            protected void process(java.util.List<Integer> chunks) {

                                for (int v:chunks) {
                                    progressBar.setValue(v);
                                }
                            };

                        }.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private class MainEmbeddedMediaPlayerComponent extends EmbeddedMediaPlayerComponent{

        private Point onPressedLoction = new Point();

        private int position;

        @Override
        public void mousePressed(MouseEvent e) {
            //四个角的位置
            int x = e.getX();
            int y = e.getY();
            int distant1 = (int)(x*x + y*y);
            int distant2 =(int) ((x - dimension.getWidth()) * (x - dimension.getWidth()) + y*y);
            int distant3 = (int)(x*x + (y-dimension.getHeight())*(y-dimension.getHeight()));
            int distant4 = (int)((x-dimension.getWidth()) * (x-dimension.getWidth()) + (y-dimension.getHeight())*(y-dimension.getHeight()));
            System.out.println("x:  " + x + "   y:   "+y);
            if(distant1 >=0 && distant1 <=200){
                position = Demo1.ANGLE_UP_LEFT;
            }else if(distant2 >=0 && distant2 <=200){
                position = Demo1.ANGLE_UP_RIGHT;
                System.out.println("up right");
            }else if(distant3 >= 0 && distant3 <=200){
                position = Demo1.ANGLE_DOWN_LEFT;
            }else if (distant4 >=0 && distant4 <=200){
                position = Demo1.ANGLE_DOWN_RIGHT;
            }else if(x >=0 && x <= dimension.getWidth() && y >=-10 && y <=10){
                position = Demo1.UP_BORDER;
            }else if(x >=0 && x <= dimension.getWidth() && y - dimension.getHeight()>=-10 && y - dimension.getHeight() <=10){
                position = Demo1.DOWN_BORDER;
            }else if(x >= -10 && x <= 10 && y >=0 && y <= dimension.getHeight()){
                position = Demo1.LEFT_BORDER;
            }else if(x - dimension.getWidth() >= -10 && x - dimension.getWidth()<= 10 &&  y>=0 && y <= dimension.getHeight()){
                position = Demo1.RIGHT_BORDER;
            }

            onPressedLoction.setLocation(e.getX(),e.getY());

        }

        @Override
        public void mouseReleased(MouseEvent e) {
//                int y = e.getY();
//                int disp = Math.abs(y - (int)onPressedLoction.getY());
//
//                embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY()-disp);
//                embeddedMediaPlayerComponent_1.setSize((int)dimension.getWidth(),(int)dimension.getHeight()+disp);
//
//                System.out.println("fangxiale");

            int relative_x=0,relative_y=0,width=0,height=0;

            if(position == Demo1.ANGLE_UP_LEFT){
                relative_x = (int)(e.getX());
                relative_y = (int)(e.getY());

                width = (int) (dimension.getWidth() - relative_x);
                height = (int) (dimension.getHeight() - relative_y);

                embeddedMediaPlayerComponent_1.setSize(width, height);
                embeddedMediaPlayerComponent_1.setLocation((int) (point.getX() + e.getX()), (int) (point.getY() + e.getY()));
                point.setLocation((int) (point.getX() + e.getX()), (int) (point.getY() + e.getY()));
                dimension.setSize(width,height);

            }else if(position == Demo1.ANGLE_UP_RIGHT){
                System.out.println("lalalla");
                relative_x = (int)(e.getX()  - dimension.getWidth());
                relative_y = (int)(e.getY());

                width = (int) (dimension.getWidth() + relative_x);
                height = (int) (dimension.getHeight() - relative_y);

                embeddedMediaPlayerComponent_1.setSize(width, height);
                embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                point.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                dimension.setSize(width,height);

            }else if(position == Demo1.ANGLE_DOWN_LEFT) {
                relative_x = (int) (e.getX());
                relative_y = (int) (e.getY() - dimension.getHeight());

                width = (int) (dimension.getWidth() - relative_x);
                height = (int) (dimension.getHeight() + relative_y);

                embeddedMediaPlayerComponent_1.setSize(width, height);
                embeddedMediaPlayerComponent_1.setLocation((int)point.getX() + relative_x, (int) point.getY());
                point.setLocation((int)point.getX() + relative_x, (int) point.getY());
                dimension.setSize(width,height);
            }else if(position == Demo1.ANGLE_DOWN_RIGHT){
                relative_x = (int) (e.getX() - dimension.getWidth());
                relative_y = (int) (e.getY() - dimension.getHeight());

                width = (int) (dimension.getWidth() + relative_x);
                height = (int) (dimension.getHeight() + relative_y);

                embeddedMediaPlayerComponent_1.setSize(width, height);
                embeddedMediaPlayerComponent_1.setLocation((int)point.getX(), (int) point.getY());
                point.setLocation((int)point.getX(), (int) point.getY());
                dimension.setSize(width,height);
            }else if(position == Demo1.UP_BORDER){
                relative_y = (int) (e.getY());

                height = (int) (dimension.getHeight() - relative_y);

                embeddedMediaPlayerComponent_1.setSize((int) dimension.getWidth(), height);
                embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                point.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                dimension.setSize((int) dimension.getWidth(),height);
            }else if(position == Demo1.DOWN_BORDER){
                relative_y = (int) (e.getY()  - dimension.getHeight());

                height = (int) (dimension.getHeight() + relative_y);

                embeddedMediaPlayerComponent_1.setSize((int) dimension.getWidth(), height);
                embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY());
                point.setLocation((int)point.getX(),(int)point.getY());
                dimension.setSize((int) dimension.getWidth(),height);
            }else if(position == Demo1.LEFT_BORDER){

                relative_x = (int) (e.getX());

                width = (int) (dimension.getWidth() - relative_x);

                embeddedMediaPlayerComponent_1.setSize(width, (int) dimension.getHeight());
                embeddedMediaPlayerComponent_1.setLocation((int)point.getX() + relative_x,(int)point.getY());
                point.setLocation((int)point.getX() + relative_x,(int)point.getY());
                dimension.setSize(width, (int) dimension.getHeight());
            }else if(position == Demo1.RIGHT_BORDER){
                relative_x = (int) (e.getX() - dimension.getWidth());

                width = (int) (dimension.getWidth() + relative_x);

                embeddedMediaPlayerComponent_1.setSize(width, (int) dimension.getHeight());
                embeddedMediaPlayerComponent_1.setLocation((int)point.getX(),(int)point.getY());
                point.setLocation((int)point.getX() ,(int)point.getY());
                dimension.setSize(width, (int) dimension.getHeight());
            }

            System.out.println("播放器的位置： x :" + point.getX() + "   y:  " + point.getY());
            position = -1;

        }
    }
}