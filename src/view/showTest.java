package view;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
@SuppressWarnings("all")
public class showTest{

	public static BufferedImage image1;
	public static BufferedImage image2;
	public static BufferedImage image3;
	private static JFrame frame;

	//these four
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_1;
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_2;
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_3;
	private JPanel panel_4;

	//these three
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_4;
	private JPanel panel_5;
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_5;
	private JPanel panel_6;
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_6;

	//show the screen shot
	public static int selected = 1; //默认是第一个播放器
	public static int count = 0;
	public static JLabel jLabel;
	public static ImageIcon icon;
	public static JPanel temppanel;
	public static int relative_x;
	public static int relative_y;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		NativeLibrary.addSearchPath(
				RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC\\sdk\\lib");	//导入的路径是vlc的安装路径
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					showTest window = new showTest(args);
					window.frame.setVisible(true);
					embeddedMediaPlayerComponent.getMediaPlayer().playMedia("2.wmv");
					embeddedMediaPlayerComponent_1.getMediaPlayer().playMedia("2.wmv");
					embeddedMediaPlayerComponent_2.getMediaPlayer().playMedia("2.wmv");
					embeddedMediaPlayerComponent_3.getMediaPlayer().playMedia("2.wmv");
					embeddedMediaPlayerComponent_4.getMediaPlayer().playMedia("2.wmv");
					embeddedMediaPlayerComponent_5.getMediaPlayer().playMedia("2.wmv");
					embeddedMediaPlayerComponent_6.getMediaPlayer().playMedia("2.wmv");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		ListenerForMouse listener = new ListenerForMouse();
		frame.getContentPane().addMouseListener(listener);
		frame.getContentPane().addMouseMotionListener(listener);
		frame.setVisible(true);
		frame.setBounds(100, 100, 1210, 849);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(0, 460, 320, 240);
		frame.getContentPane().add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(330, 460, 320, 240);
		frame.getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setVisible(false);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(660, 460, 320, 240);
		frame.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);

		JPanel panel = new JPanel();
		panel.setBounds(353, 0, 320, 240);
		frame.getContentPane().add(panel,new Integer(100));
		panel.setLayout(null);
		
		embeddedMediaPlayerComponent_1 = new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent_1.addMouseListener(new MouseAdapter() {
			/* （非 Javadoc）
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();  //得到鼠标x坐标
                int y = e.getY();  //得到鼠标y坐标
                String banner = "鼠标当前点击位置的坐标是" + x + "," + y;
                canUse();
//                getSnap();
                System.out.println(banner);
			}
		});
		embeddedMediaPlayerComponent_1.setBounds(0, 0, 320, 240);
		panel.add(embeddedMediaPlayerComponent_1);
		
//		JPanel panel_7 = new JPanel();
//		panel_7.setBounds(0, 0, 320, 240);
//		panel.add(panel_7);
//		panel_7.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(675, 0, 320, 240);
		frame.getContentPane().add(panel_1,new Integer(100));
		panel_1.setLayout(null);
		embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent.setBounds(0, 0, 320, 240);
		panel_1.add(embeddedMediaPlayerComponent);
		embeddedMediaPlayerComponent.setCursorEnabled(true);
		
//		JPanel panel_8 = new JPanel();
//		panel_8.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				System.out.println("heiheihei");
//			}
//		});
//		panel_8.setLayout(null);
//		panel_8.setBounds(0, 0, 320, 240);
//		panel_1.add(panel_8);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(353, 241, 320, 240);
		frame.getContentPane().add(panel_2,new Integer(100));
		panel_2.setLayout(null);
		
		embeddedMediaPlayerComponent_2 = new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent_2.setBounds(0, 0, 320, 240);
		panel_2.add(embeddedMediaPlayerComponent_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(675, 242, 320, 239);
		frame.getContentPane().add(panel_3,new Integer(100));
		panel_3.setLayout(null);
		
		embeddedMediaPlayerComponent_3 = new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent_3.setBounds(0, 0, 320, 239);
		panel_3.add(embeddedMediaPlayerComponent_3);
		
		panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBounds(0, 520, 320, 240);
		frame.getContentPane().add(panel_4,new Integer(100));
		
		embeddedMediaPlayerComponent_4 = new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent_4.setBounds(0, 0, 320, 240);
		panel_4.add(embeddedMediaPlayerComponent_4);
		
		panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBounds(330, 520, 320, 240);
		frame.getContentPane().add(panel_5,new Integer(100));
		
		embeddedMediaPlayerComponent_6 = new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent_6.setBounds(0, 0, 320, 240);
		panel_5.add(embeddedMediaPlayerComponent_6);
		
		panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBounds(660, 520, 320, 240);
		frame.getContentPane().add(panel_6,new Integer(100));
		
		embeddedMediaPlayerComponent_5 = new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent_5.setBounds(0, 0, 320, 240);
		panel_6.add(embeddedMediaPlayerComponent_5);
		cantUse();
	}
	public void getSnap(int relative_x,int relative_y,int pos_x, int pos_y)//获取截图
	{
//		System.out.println("调用");
//		embeddedMediaPlayerComponent.getMediaPlayer().setPosition(0.1f);
//		File file1=new File("D:/1.jpg");
//		try {
//			file1.createNewFile();
//		} catch (IOException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		}
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		}

		//良哥的函数
		//返回鼠标释放时的位置


//		try {
//			ImageIO.write(image, "jpg", file1);
//		} catch (IOException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		}
//		show("111",image,1);


		
		if(pos_x >=0 && pos_x <= 320 && pos_y >=520 && pos_y <=760){
			
			image1 = embeddedMediaPlayerComponent_4.getMediaPlayer().getSnapshot();
		}else if(pos_x >=330 && pos_x <= 650 && pos_y >=520 && pos_y <=760){
			
			image2 = embeddedMediaPlayerComponent_5.getMediaPlayer().getSnapshot();
		}else if(pos_x >=660 && pos_x <= 980 && pos_y >=520 && pos_y <=760){
			
			image3 = embeddedMediaPlayerComponent_6.getMediaPlayer().getSnapshot();
		}

		
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		Image shotImage = null;
//		try {
//			ImageIO.write(image,"JPEG",outputStream);
//			byte[] byteImage = outputStream.toByteArray();
//			shotImage = ImageIO.read(new ByteArrayInputStream(byteImage));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	
//		Image  shotImage = (Image)image;
		

		switch(selected) {
		case 1:
			icon = new ImageIcon(image1);
			icon.setImage(icon.getImage().getScaledInstance(320,240,Image.SCALE_DEFAULT));
			lblNewLabel_1.setIcon(icon);

			break;
		case 2:
			icon = new ImageIcon(image2);
			icon.setImage(icon.getImage().getScaledInstance(320,240,Image.SCALE_DEFAULT));
			lblNewLabel_2.setIcon(icon);
			

			
			break;
		case 3:
			icon = new ImageIcon(image3);
			icon.setImage(icon.getImage().getScaledInstance(320,240,Image.SCALE_DEFAULT));
			lblNewLabel_3.setIcon(icon);		
			break;
		}
		
//		jLabel.setBounds(pos_x,pos_y,320,240);
//		lblNewLabel.setBounds(0,320,320,240);
//		jLabel.setVisible(true);
//		temppanel.add(jLabel);
//		temppanel.setVisible(true);
//		frame.getContentPane().add(temppanel,new Integer(200));
		



//		show("111",image,1);
	}
	public void cantUse() {//控键不可用，设置鼠标点击事件
		embeddedMediaPlayerComponent.disable();
		embeddedMediaPlayerComponent_1.disable();
		embeddedMediaPlayerComponent_2.disable();
		embeddedMediaPlayerComponent_3.disable();
		embeddedMediaPlayerComponent_4.disable();
		embeddedMediaPlayerComponent_5.disable();
		embeddedMediaPlayerComponent_6.disable();
		//lblNewLabel_1.disable();
	}
	public void canUse() {
		embeddedMediaPlayerComponent.enable();
		embeddedMediaPlayerComponent_1.enable();
		embeddedMediaPlayerComponent_2.enable();
		embeddedMediaPlayerComponent_3.enable();
		embeddedMediaPlayerComponent_4.enable();
		embeddedMediaPlayerComponent_5.enable();
		embeddedMediaPlayerComponent_6.enable();
	}
    @SuppressWarnings("serial")
    private static void show(String title, final BufferedImage img, int i) {
        JFrame f = new JFrame(title);		
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new JPanel() {
            @Override
            protected void paintChildren(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.drawImage(img, null, 0, 0);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(img.getWidth(), img.getHeight());
            }
        });
        f.pack();
        f.setLocation(50 + (i * 50), 50 + (i * 50));
        f.setVisible(true);
    	}
	public showTest(String[] args) {
		initialize();
	}
	
	private class ListenerForMouse extends MouseInputAdapter{
		
		private int rel_x;
		private int rel_y;
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getX() >=0 && e.getX() <= 320 && e.getY() >=520 && e.getY() <=760){
				DoShow.relative_x = e.getX() - 0;
				DoShow.relative_y = e.getY() - 520 - 60;
				rel_x = e.getX() - 0;
				rel_y = e.getY() - 460;
				getSnap(e.getX() - 0,e.getY() - 520,e.getX(),e.getY());
				selected = 1;
				count ++;
				lblNewLabel_1.setVisible(true);

			}else if(e.getX() >=330 && e.getX() <= 650 && e.getY() >=520 && e.getY() <=760){
				DoShow.relative_x = e.getX() - 330;
				DoShow.relative_y = e.getY() - 520 - 60;
				rel_x = e.getX() - 330;
				rel_y = e.getY() - 460;
				getSnap(e.getX() - 330,e.getY() - 520,e.getX(),e.getY());
				selected = 2;
				count ++;
				lblNewLabel_2.setVisible(true);

			}else if(e.getX() >=660 && e.getX() <= 980 && e.getY() >=520 && e.getY() <=760){
				DoShow.relative_x = e.getX() - 660;
				DoShow.relative_y = e.getY() - 520 - 60;
				rel_x = e.getX() - 660;
				rel_y = e.getY() - 460;
				getSnap(e.getX() - 660,e.getY() - 520,e.getX(),e.getY());
				selected = 3;
				count ++;
				lblNewLabel_3.setVisible(true);

			}
			
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			lblNewLabel_1.setVisible(false);
			lblNewLabel_2.setVisible(false);
			lblNewLabel_3.setVisible(false);
			
			switch(selected) {
			case 1:
				lblNewLabel_1.setBounds(0,460,320,240);
				break;
			case 2:
				lblNewLabel_2.setBounds(330,460,320,240);
				break;
			case 3:
				lblNewLabel_3.setBounds(660,460,320,240);
				break;
			}
			
			
			//返回  changeVideo(e.getX() - rel_x,e.getY() - rel_y,"path",count);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			
			switch(selected) {
			case 1:
				lblNewLabel_1.setBounds(e.getX() - rel_x,e.getY() - rel_y,320,240);
				break;
			case 2:
				lblNewLabel_2.setBounds(e.getX() - rel_x,e.getY() - rel_y,320,240);
				break;
			case 3:
				lblNewLabel_3.setBounds(e.getX() - rel_x,e.getY() - rel_y,320,240);
				break;
			}
			
			//lblNewLabel_1.setBounds(e.getX(),e.getY(),320,240);
			//System.out.println(jLabel.getText());
			System.out.println(e.getX() + "   " + e.getY());
		}
		
	}
}
