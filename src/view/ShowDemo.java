package view;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Properties;

@SuppressWarnings("all")
public class ShowDemo {

    private static JFrame frame; //主窗体
    private static JPanel containSplitPanel; //容纳分割后播放器的容器
    private static JProgressBar progressBar;
    private static MainEmbeddedMediaPlayerComponent mainPlayer;
    private static EmbeddedMediaPlayerComponent[][] splitPlayers = new EmbeddedMediaPlayerComponent[4][4];
    private static InputEmbeddedMediaPlayerComponent []inputPlayers = new InputEmbeddedMediaPlayerComponent[16];

    //明确是哪个播放器的视频在主播放器中播放
    private static InputEmbeddedMediaPlayerComponent currentPlayer ;
    private static int currentPlayerId;

    // 可缩放的播放器的默认大小和位置，程序执行过程会改变
    private static Point point = new Point(100,100);
    private static Dimension dimension = new Dimension(304,171);

    private static Dimension initSplitPlayerSize = new Dimension(304,171); //切割播放器起始大小(程序不改变它的值)
    private static Dimension finalSplitPlayerSize = new Dimension(304,171); //显示出来的实际大小(可能有缩放)

    //容纳主播放器和切割播放器的panel的大小，可在此修改起始值。程序运行不会修改它
    private static Dimension panelSize = new Dimension(608,324);

    private String currentMediaAbsolutePath = "2.wmv";
    private Properties initParams = new Properties(); //配置信息

    public static void main(String[] args) {
        NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC\\sdk\\lib");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        EventQueue.invokeLater(()->{
            ShowDemo showDemo = new ShowDemo();
            showDemo.frame.setVisible(true);
        });
    }


    public ShowDemo(){

        initializeProperties();
        initializeMain();
        initializeShowArea2();
        initializeInputArea();

    }

    /**
     * 初始化配置文件，通过配置文件的值来实初始化程序参数
     *
     * 本来是想通过文件加载的方式得到配置文件
     * 但是这样得到的一直都是上一次更改之前的配置文件值，暂时这样用。
     *
     * 这种方式是传入了{@link SelectDemo#properties} 这个对象，因为是静态的，
     * 传进来的一定是初始化好的配置文件
     */
    private void initializeProperties(){
        initParams = SelectDemo.properties;

    }

    /**
     * 初始化  输入源界面
     * 主要实现的功能：
     *      从properties文件中获取输入源的个数
     *      将输入的视频源按照坐标显示在下方位置
     */

    private void initializeInputArea() {
        int inputCount = Integer.parseInt(initParams.getProperty("init.input_count"));
        for (int i = 0; i < inputCount; i++) {
            inputPlayers[i] = new InputEmbeddedMediaPlayerComponent();
            int row = i / 8;

            inputPlayers[i].setBounds(20 + ( i % 8)*190 , 587 + row *  180,185,119);
            inputPlayers[i].setCursorEnabled(true);
            inputPlayers[i].setPlayerId(i);

            frame.getContentPane().add(inputPlayers[i]);

            JButton chooseButton = new JButton("select");
            chooseButton.setBounds( 66 + ( i % 8)*190 , 716 + row * 180 , 93 , 23);
            chooseButton.addMouseListener(new Button_Match_PlayerMouseListener(inputPlayers[i]) {
            });
            frame.getContentPane().add(chooseButton);

        }
    }

    /**
     * 初始化 切割组合后显示界面
     *  主要实现的功能：
     *      通过properties读取的方式获取相应屏幕的m*n矩阵
     *      通过计算得到最终每个播放器的大小（由于播放器数量的不确定和固定的容纳播放器的panel决定，
     *      播放器的大小会随着数量的改变而改变）
     *      播放器大小的起始值：{@link ShowDemo#initSplitPlayerSize}(程序不会改变它）
     *      播放器大小的最终值：{@link ShowDemo#finalSplitPlayerSize} (默认是和起始值一致，程序执行过程中会改变）
     *      最终播放器的坐标会通过一定的计算显示在{@link ShowDemo#containSplitPanel} 上
     *
     *@Deprecated: 过期的方法，不用了。。
     * */
    @Deprecated
    private void initializeShowArea() {

        // 边框
        containSplitPanel = new JPanel();
        containSplitPanel.setBounds(772, 30, (int)panelSize.getWidth(), (int)panelSize.getHeight());
        containSplitPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        containSplitPanel.setLayout(null);
        frame.getContentPane().add(containSplitPanel);

        int split_x = Integer.parseInt(initParams.getProperty("init.split_x"));
        int split_y = Integer.parseInt(initParams.getProperty("init.split_y"));


        double width = initSplitPlayerSize.getWidth();
        double height = initSplitPlayerSize.getHeight();

        int rel_x,rel_y;

        if(split_y * width <= containSplitPanel.getWidth() && split_x * height <= containSplitPanel.getHeight()){

            rel_x = (int) ((containSplitPanel.getWidth() - split_y * width)/2);

            rel_y = (int) ((containSplitPanel.getHeight() - split_x * height)/2);

        }else if(split_y * width > containSplitPanel.getWidth() && split_x * height <= containSplitPanel.getHeight()){

            double temp = split_y * width;
            while(temp >= containSplitPanel.getWidth()){

                temp -= 5;
            }

            rel_x = (int) ((containSplitPanel.getWidth() - temp)/2);
            rel_y = (int) ((containSplitPanel.getHeight() - (initSplitPlayerSize.getHeight()/initSplitPlayerSize.getWidth())*temp/split_y*split_x)/2);

            finalSplitPlayerSize.setSize(temp/split_y,(initSplitPlayerSize.getHeight()/initSplitPlayerSize.getWidth())*temp/split_y);
        }else{
            double tempHeight = split_x * height;
            double tempWidth = split_y * width;

            while(tempHeight >= containSplitPanel.getHeight() || tempWidth >= containSplitPanel.getWidth()){
                tempHeight -= 5;
                tempWidth = tempHeight * initSplitPlayerSize.getWidth() * split_y / (initSplitPlayerSize.getHeight() * split_x);
            }

            rel_x = (int) ((containSplitPanel.getWidth() - tempWidth)/2);
            rel_y = (int) ((containSplitPanel.getHeight() - tempHeight)/2);

            finalSplitPlayerSize.setSize(tempWidth/split_y,
                    tempHeight/split_x);
        }

        for (int i = 0; i<split_x;i++){
            for (int j = 0; j < split_y; j++) {
                splitPlayers[i][j] = new EmbeddedMediaPlayerComponent();

                splitPlayers[i][j].setBounds((int)(rel_x + j * finalSplitPlayerSize.getWidth()),(int)(rel_y + i * finalSplitPlayerSize.getHeight()),
                        (int)finalSplitPlayerSize.getWidth(),(int)finalSplitPlayerSize.getHeight());

                splitPlayers[i][j].setCursorEnabled(true);
                containSplitPanel.add(splitPlayers[i][j]);


            }
        }

        /**
         * 播放类似于下述
         */
        for (int i = 0; i<split_x;i++) {
            for (int j = 0; j < split_y; j++) {
                splitPlayers[i][j].getMediaPlayer().playMedia("2.wmv");
            }
        }

    }

    /**
     * 初始化切割组合后显示的界面
     *      新版本，手动笑哭，哈哈
     *      和上一版的区别在于她是适应屏幕大小，填充屏幕
     *      上一版是按照播放器原来的宽高比缩放，这版会改变播放器的宽高比
     *
     *      切割播放器大小的最终值：{@link ShowDemo#finalSplitPlayerSize} (程序执行过程中会改变）
     *
     *      定义了新变量{@link ShowDemo#panelSize} 容纳主播放器和切割播放器的panel大小
     *
     */
    private void initializeShowArea2(){


        int split_x = Integer.parseInt(initParams.getProperty("init.split_x"));
        int split_y = Integer.parseInt(initParams.getProperty("init.split_y"));

        int playerWidth = (int) (panelSize.getWidth() / split_y);
        int playerHeight = (int) (panelSize.getHeight() / split_x);

        //更新最终切割播放器的长和宽
        finalSplitPlayerSize.setSize(playerWidth,playerHeight);

        for (int i = 0; i<split_x;i++){
            for (int j = 0; j < split_y; j++) {
                splitPlayers[i][j] = new EmbeddedMediaPlayerComponent();

                splitPlayers[i][j].setBounds(playerWidth * j,playerHeight * i,playerWidth,playerHeight);

                splitPlayers[i][j].setCursorEnabled(true);
                containSplitPanel.add(splitPlayers[i][j]);


            }
        }

        /**
         * 播放类似于下述
         */
//        for (int i = 0; i<split_x;i++) {
//            for (int j = 0; j < split_y; j++) {
//                splitPlayers[i][j].getMediaPlayer().playMedia("2.wmv");
//            }
//        }


    }

    /**
     * 初始化 起始界面
     * 包括以下部分：
     *      主窗口的大小设定；
     *      主窗口的播放器的初始化（并未播放视频）
     *      添加了主窗口播放器的播放与暂停
     *      播放器可以实现基本拉伸，放大和缩小
     */
    private void initializeMain() {


        frame = new JFrame();

        frame.setVisible(true);
        frame.setBounds(50, 50, 1700, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 边框
        containSplitPanel = new JPanel();
        containSplitPanel.setBounds(772, 50, (int)panelSize.getWidth(), (int)panelSize.getHeight());
        containSplitPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        containSplitPanel.setLayout(null);
        frame.getContentPane().add(containSplitPanel);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBounds(63, 469, 475, 16);
        frame.getContentPane().add(progressBar);
        progressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        JPanel panel_main = new JPanel();
        panel_main.setBounds(0, 49, (int) panelSize.getWidth(), (int) panelSize.getHeight());
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
                    position = LocationParameters.ANGLE_UP_LEFT;
                    frame.setCursor(Cursor.NW_RESIZE_CURSOR);
                }else if(distant2 >=0 && distant2 <=64){
                    position = LocationParameters.ANGLE_UP_RIGHT;
                }else if(distant3 >= 0 && distant3 <=64){
                    position = LocationParameters.ANGLE_DOWN_LEFT;
                }else if (distant4 >=0 && distant4 <=64){
                    position = LocationParameters.ANGLE_DOWN_RIGHT;
                }else if(x >= point.getX() && x <= point.getX() + dimension.getWidth() && y - point.getY() >=-10 && y - point.getY() <=10){
                    position = LocationParameters.UP_BORDER;
                }else if(x >= point.getX() && x <= point.getX() + dimension.getWidth() && y - point.getY() - dimension.getHeight() >=-10 && y - point.getY() - dimension.getHeight() <= 10){
                    position = LocationParameters.DOWN_BORDER;
                }else if(y >= point.getY() && y <= point.getY() + dimension.getHeight() && x - point.getX() >= -10 && x - point.getX() <= 10){
                    position = LocationParameters.LEFT_BORDER;
                }else if(y >= point.getY() && y <= point.getY() + dimension.getHeight() && x - point.getX() - dimension.getWidth() >= -10 && x - point.getX() - dimension.getWidth() <= 10){
                    position = LocationParameters.RIGHT_BORDER;
                }

                onPressedLoction.setLocation(e.getX(),e.getY());

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                int relative_x=0,relative_y=0,width=0,height=0;

                if(position == LocationParameters.ANGLE_UP_LEFT){
                    relative_x = (int)(e.getX() - point.getX());
                    relative_y = (int)(e.getY() - point.getY());

                    width = (int) (dimension.getWidth() - relative_x);
                    height = (int) (dimension.getHeight() - relative_y);

                    mainPlayer.setSize(width, height);
                    mainPlayer.setLocation(e.getX(),e.getY());
                    point.setLocation(e.getX(),e.getY());
                    dimension.setSize(width,height);

                }else if(position == LocationParameters.ANGLE_UP_RIGHT){
                    relative_x = (int)(e.getX() - point.getX() - dimension.getWidth());
                    relative_y = (int)(e.getY() - point.getY());

                    width = (int) (dimension.getWidth() + relative_x);
                    height = (int) (dimension.getHeight() - relative_y);

                    mainPlayer.setSize(width, height);
                    mainPlayer.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                    point.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                    dimension.setSize(width,height);

                }else if(position == LocationParameters.ANGLE_DOWN_LEFT) {
                    relative_x = (int) (e.getX() - point.getX());
                    relative_y = (int) (e.getY() - point.getY() - dimension.getHeight());

                    width = (int) (dimension.getWidth() - relative_x);
                    height = (int) (dimension.getHeight() + relative_y);

                    mainPlayer.setSize(width, height);
                    mainPlayer.setLocation((int)point.getX() + relative_x, (int) point.getY());
                    point.setLocation((int)point.getX() + relative_x, (int) point.getY());
                    dimension.setSize(width,height);
                }else if(position == LocationParameters.ANGLE_DOWN_RIGHT){
                    relative_x = (int) (e.getX() - point.getX() - dimension.getWidth());
                    relative_y = (int) (e.getY() - point.getY() - dimension.getHeight());

                    width = (int) (dimension.getWidth() + relative_x);
                    height = (int) (dimension.getHeight() + relative_y);

                    mainPlayer.setSize(width, height);
                    mainPlayer.setLocation((int)point.getX(), (int) point.getY());
                    point.setLocation((int)point.getX(), (int) point.getY());
                    dimension.setSize(width,height);
                }else if(position == LocationParameters.UP_BORDER){
                    relative_y = (int) (e.getY() - point.getY());

                    height = (int) (dimension.getHeight() - relative_y);

                    mainPlayer.setSize((int) dimension.getWidth(), height);
                    mainPlayer.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                    point.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                    dimension.setSize((int) dimension.getWidth(),height);
                }else if(position == LocationParameters.DOWN_BORDER){
                    relative_y = (int) (e.getY() - point.getY() - dimension.getHeight());

                    height = (int) (dimension.getHeight() + relative_y);

                    mainPlayer.setSize((int) dimension.getWidth(), height);
                    mainPlayer.setLocation((int)point.getX(),(int)point.getY());
                    point.setLocation((int)point.getX(),(int)point.getY());
                    dimension.setSize((int) dimension.getWidth(),height);
                }else if(position == LocationParameters.LEFT_BORDER){

                    relative_x = (int) (e.getX() - point.getX());

                    width = (int) (dimension.getWidth() - relative_x);

                    mainPlayer.setSize(width, (int) dimension.getHeight());
                    mainPlayer.setLocation((int)point.getX() + relative_x,(int)point.getY());
                    point.setLocation((int)point.getX() + relative_x,(int)point.getY());
                    dimension.setSize(width, (int) dimension.getHeight());
                }else if(position == LocationParameters.RIGHT_BORDER){
                    relative_x = (int) (e.getX() - point.getX() - dimension.getWidth());

                    width = (int) (dimension.getWidth() + relative_x);

                    mainPlayer.setSize(width, (int) dimension.getHeight());
                    mainPlayer.setLocation((int)point.getX(),(int)point.getY());
                    point.setLocation((int)point.getX() ,(int)point.getY());
                    dimension.setSize(width, (int) dimension.getHeight());
                }

                position = -1;
                currentPlayer.getMainPlayerSize().setSize(dimension);
                currentPlayer.getMianPlayerLocation().setLocation(point);


            }
        });

        mainPlayer = new MainEmbeddedMediaPlayerComponent();

        mainPlayer.setBounds((int)point.getX(),(int)point.getY(),(int)dimension.getWidth(),(int)dimension.getHeight());

        panel_main.add(mainPlayer);

        JButton pauseButton = new JButton("pause");
        pauseButton.setBounds(125, 495, 93, 23);
        frame.getContentPane().add(pauseButton);
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentPlayer.getMainPlayerSize().setSize(dimension);
                mainPlayer.getMediaPlayer().pause();
                currentPlayer.getMediaPlayer().pause();


            }
        });

        JButton playButton = new JButton("play");
        playButton.setBounds(361, 495, 93, 23);
        frame.getContentPane().add(playButton);
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                mainPlayer.getMediaPlayer().playMedia(currentPlayer.getMediaAbsolutePath());
                mainPlayer.getMediaPlayer().skipPosition(currentPlayer.getMediaPlayer().getPosition());
                currentPlayer.getMediaPlayer().play();
            }
        });

        JButton confirmButton = new JButton("confirm");
        confirmButton.setBounds(237, 401, 93, 23);
        frame.getContentPane().add(confirmButton);
        confirmButton.addMouseListener(new MouseAdapter() {
            /**
             * 可以得到四个参数
             *      x:主播放器的位置位于二维数组播放器的x(第几行的播放器){@link ShowDemo#splitPlayers}
             *      y:主播放器的位置位于二维数组播放器的y(第几列的播放器){@link ShowDemo#splitPlayers}
             *
             *      由x和y这两个参数可以确定需要的二维数组播放器的索引
             *
             *      rel_x:播放器的位置相对于所在切割播放器位置的横坐标
             *              原本主播放器的坐标是相对于容纳它的panel决定的
             *              ({@link ShowDemo#initializeMain()})方法中的panel_main这个panel
             *      re_y;播放器的位置相对于所在播放器的纵坐标
             *              同上
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {

                int width = (int) finalSplitPlayerSize.getWidth();
                int height = (int) finalSplitPlayerSize.getHeight();

                int y = (int) (point.getX() / width);
                int x = (int) (point.getY() / height);
                int rel_x = (int) (point.getX() - y * width);
                int rel_y = (int) (point.getY() - x * height);

                /**
                 * 测试时候用的，选择删除
                 *      finalSplitPlayerSize: 右侧的播放器大小
                 *      point: 主播放器相对于panel_main的位置
                 */
                System.out.println("切割播放器的大小为 ： width =  " + finalSplitPlayerSize.getWidth() + "    height=   " + finalSplitPlayerSize.getHeight());
                System.out.println("对应二维数组的索引：  x =  " + x + "  y=  " + y);
                System.out.println("相对坐标： x=   " + rel_x + "   y=   " + rel_y);
            }
        });
    }

    /**
     * 主播放器，在左上角的播放器，继承自
     * @see EmbeddedMediaPlayerComponent
     * 目的是重写播放器的按下和松开事件
     * @see EmbeddedMediaPlayerComponent#mousePressed(MouseEvent)
     * @see EmbeddedMediaPlayerComponent#mouseReleased(MouseEvent)
     * 利用这两个事件可以通过鼠标的坐标改变播放器的大小，
     * 用户通过拖拽来改变
     */
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
            if(distant1 >=0 && distant1 <=200){
                position = LocationParameters.ANGLE_UP_LEFT;
            }else if(distant2 >=0 && distant2 <=200){
                position = LocationParameters.ANGLE_UP_RIGHT;
            }else if(distant3 >= 0 && distant3 <=200){
                position = LocationParameters.ANGLE_DOWN_LEFT;
            }else if (distant4 >=0 && distant4 <=200){
                position = LocationParameters.ANGLE_DOWN_RIGHT;
            }else if(x >=0 && x <= dimension.getWidth() && y >=-10 && y <=10){
                position = LocationParameters.UP_BORDER;
            }else if(x >=0 && x <= dimension.getWidth() && y - dimension.getHeight()>=-10 && y - dimension.getHeight() <=10){
                position = LocationParameters.DOWN_BORDER;
            }else if(x >= -10 && x <= 10 && y >=0 && y <= dimension.getHeight()){
                position = LocationParameters.LEFT_BORDER;
            }else if(x - dimension.getWidth() >= -10 && x - dimension.getWidth()<= 10 &&  y>=0 && y <= dimension.getHeight()){
                position = LocationParameters.RIGHT_BORDER;
            }

            onPressedLoction.setLocation(e.getX(),e.getY());

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int relative_x=0,relative_y=0,width=0,height=0;

            if(position == LocationParameters.ANGLE_UP_LEFT){
                relative_x = (int)(e.getX());
                relative_y = (int)(e.getY());

                width = (int) (dimension.getWidth() - relative_x);
                height = (int) (dimension.getHeight() - relative_y);

                mainPlayer.setSize(width, height);
                mainPlayer.setLocation((int) (point.getX() + e.getX()), (int) (point.getY() + e.getY()));
                point.setLocation((int) (point.getX() + e.getX()), (int) (point.getY() + e.getY()));
                dimension.setSize(width,height);

            }else if(position == LocationParameters.ANGLE_UP_RIGHT){
                relative_x = (int)(e.getX()  - dimension.getWidth());
                relative_y = (int)(e.getY());

                width = (int) (dimension.getWidth() + relative_x);
                height = (int) (dimension.getHeight() - relative_y);

                mainPlayer.setSize(width, height);
                mainPlayer.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                point.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                dimension.setSize(width,height);

            }else if(position == LocationParameters.ANGLE_DOWN_LEFT) {
                relative_x = (int) (e.getX());
                relative_y = (int) (e.getY() - dimension.getHeight());

                width = (int) (dimension.getWidth() - relative_x);
                height = (int) (dimension.getHeight() + relative_y);

                mainPlayer.setSize(width, height);
                mainPlayer.setLocation((int)point.getX() + relative_x, (int) point.getY());
                point.setLocation((int)point.getX() + relative_x, (int) point.getY());
                dimension.setSize(width,height);
            }else if(position == LocationParameters.ANGLE_DOWN_RIGHT){
                relative_x = (int) (e.getX() - dimension.getWidth());
                relative_y = (int) (e.getY() - dimension.getHeight());

                width = (int) (dimension.getWidth() + relative_x);
                height = (int) (dimension.getHeight() + relative_y);

                mainPlayer.setSize(width, height);
                mainPlayer.setLocation((int)point.getX(), (int) point.getY());
                point.setLocation((int)point.getX(), (int) point.getY());
                dimension.setSize(width,height);
            }else if(position == LocationParameters.UP_BORDER){
                relative_y = (int) (e.getY());

                height = (int) (dimension.getHeight() - relative_y);

                mainPlayer.setSize((int) dimension.getWidth(), height);
                mainPlayer.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                point.setLocation((int)point.getX(),(int)point.getY() + relative_y);
                dimension.setSize((int) dimension.getWidth(),height);
            }else if(position == LocationParameters.DOWN_BORDER){
                relative_y = (int) (e.getY()  - dimension.getHeight());

                height = (int) (dimension.getHeight() + relative_y);

                mainPlayer.setSize((int) dimension.getWidth(), height);
                mainPlayer.setLocation((int)point.getX(),(int)point.getY());
                point.setLocation((int)point.getX(),(int)point.getY());
                dimension.setSize((int) dimension.getWidth(),height);
            }else if(position == LocationParameters.LEFT_BORDER){

                relative_x = (int) (e.getX());

                width = (int) (dimension.getWidth() - relative_x);

                mainPlayer.setSize(width, (int) dimension.getHeight());
                mainPlayer.setLocation((int)point.getX() + relative_x,(int)point.getY());
                point.setLocation((int)point.getX() + relative_x,(int)point.getY());
                dimension.setSize(width, (int) dimension.getHeight());
            }else if(position == LocationParameters.RIGHT_BORDER){
                relative_x = (int) (e.getX() - dimension.getWidth());

                width = (int) (dimension.getWidth() + relative_x);

                mainPlayer.setSize(width, (int) dimension.getHeight());
                mainPlayer.setLocation((int)point.getX(),(int)point.getY());
                point.setLocation((int)point.getX() ,(int)point.getY());
                dimension.setSize(width, (int) dimension.getHeight());
            }


            position = -1;
            currentPlayer.getMainPlayerSize().setSize(dimension);
            currentPlayer.getMianPlayerLocation().setLocation(point);

        }
    }

    /**
     * 自定义播放器，在输入源中的播放器，继承自
     * @see EmbeddedMediaPlayerComponent
     * 目的为了重写播放器的点击时事件{@link EmbeddedMediaPlayerComponent#mouseClicked(MouseEvent)}，
     * 当点击这个播放器的时候，主播放器(MainEmbeddedMediaPlayerComponent)会播放这个视频播放的视频
     *
     * 这个类也存储了播放器播放的视频在主播放器中的播放参数，有播放器被拖拽后的最终大小和位置
     * {@link InputEmbeddedMediaPlayerComponent#mainPlayerSize} 大小
     * {@link InputEmbeddedMediaPlayerComponent#mianPlayerLocation} 坐标
     * 很多参数都可以从这个类中获取，不用特别设定参数类了
     *
     *
     */
    private class InputEmbeddedMediaPlayerComponent extends  EmbeddedMediaPlayerComponent{

        private String mediaAbsolutePath;
        private Dimension mainPlayerSize = new Dimension(dimension);
        private Point mianPlayerLocation = new Point(point);
        private int playerId; //输入播放器的id，为一位数组的索引

        public Point getMianPlayerLocation() {
            return mianPlayerLocation;
        }

        public void setMianPlayerLocation(Point mianPlayerLocation) {
            this.mianPlayerLocation = mianPlayerLocation;
        }

        public int getPlayerId() {
            return playerId;
        }

        public void setPlayerId(int playerId) {
            this.playerId = playerId;
        }

        public Dimension getMainPlayerSize() {
            return mainPlayerSize;
        }

        public void setMainPlayerSize(Dimension mainPlayerSize) {
            this.mainPlayerSize = mainPlayerSize;
        }

        public String getMediaAbsolutePath() {
            return mediaAbsolutePath;
        }

        public void setMediaAbsolutePath(String mediaAbsolutePath) {
            this.mediaAbsolutePath = mediaAbsolutePath;
        }


        @Override
        public void mouseClicked(MouseEvent e) {


            if(this.mediaAbsolutePath!=null && this.mediaAbsolutePath!= ""){
                currentMediaAbsolutePath = this.mediaAbsolutePath;
            }
            currentPlayer = this;
            currentPlayerId = this.getPlayerId();

            mainPlayer.setSize(this.getMainPlayerSize());
            mainPlayer.setLocation(this.getMianPlayerLocation());

            mainPlayer.getMediaPlayer().playMedia(currentMediaAbsolutePath);

            float position = this.getMediaPlayer().getPosition();
            mainPlayer.getMediaPlayer().skipPosition(position);
            for(InputEmbeddedMediaPlayerComponent player : inputPlayers){
                if(player != null){
                    if(player.getPlayerId() != this.getPlayerId()){
                        player.getMediaPlayer().setPause(true);
                    }
                }
            }
            this.getMediaPlayer().play();

            EventQueue.invokeLater(()-> {
                    try {
                        new SwingWorker<String , Integer>() {

                            @Override
                            protected String doInBackground() throws Exception {
                                while(true){
                                    long total = mainPlayer.getMediaPlayer().getLength();
                                    long curr = mainPlayer.getMediaPlayer().getTime();
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
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            );
        }

    }

    /**
     * 内部类，目的是为了匹配选择播放源按钮与数组播放器的一个监听器，继承了MouseAdapter
     * @see MouseAdapter
     * 需要传入一个特定的参数：自定义的播放器(InputEmbeddedMediaPlayerComponent)
     * 这个 播放器继承自
     * @see EmbeddedMediaPlayerComponent
     */
    private class Button_Match_PlayerMouseListener extends MouseAdapter {

        private InputEmbeddedMediaPlayerComponent playerComponent;
        private String mediaAbsolutePath;

        public String getMediaAbsolutePath() {
            return mediaAbsolutePath;
        }

        public void setMediaAbsolutePath(String mediaAbsolutePath) {
            this.mediaAbsolutePath = mediaAbsolutePath;
        }

        public Button_Match_PlayerMouseListener(InputEmbeddedMediaPlayerComponent playerComponent) {
            this.playerComponent = playerComponent;
        }

        public InputEmbeddedMediaPlayerComponent getPlayerComponent() {
            return playerComponent;
        }

        public void setPlayerComponent(InputEmbeddedMediaPlayerComponent playerComponent) {
            this.playerComponent = playerComponent;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JFileChooser chooser = new JFileChooser();
            int v = chooser.showOpenDialog(null);
            if (v == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                this.playerComponent.getMediaPlayer().playMedia(file.getAbsolutePath());
                this.mediaAbsolutePath = file.getAbsolutePath();
                this.playerComponent.setMediaAbsolutePath(file.getAbsolutePath());

            }
        }
    }
}

/**
 * 写着玩的，用于判断拖拽播放器鼠标的位置定义的一个参数类
 */
class LocationParameters{
    public static int UP_BORDER = 0;
    public static int DOWN_BORDER = 1;
    public static int LEFT_BORDER = 2;
    public static int RIGHT_BORDER = 3;
    public static int ANGLE_UP_LEFT = 4;
    public static int ANGLE_UP_RIGHT = 5;
    public static int ANGLE_DOWN_LEFT = 6;
    public static int ANGLE_DOWN_RIGHT = 7;
}


