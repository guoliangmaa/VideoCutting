package view;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

@SuppressWarnings("all")
public class showTest2{

	public static BufferedImage image1;
	public static BufferedImage image2;
	public static BufferedImage image3;
	private static JFrame frame;

	//these four
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_1;
	private JPanel panel_4;

	//these three
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_4;
	private JPanel panel_5;
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_5;
	private JPanel panel_6;
	private static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent_6;

	public static JLabel jLabel;
	public static ImageIcon icon;
	public static JPanel temppanel;
	public static int relative_x;
	public static int relative_y;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JButton btnNewButton_1;

	private JProgressBar progressBar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		NativeLibrary.addSearchPath(
				RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC\\sdk\\lib");	//�����·����vlc�İ�װ·��
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					showTest2 window = new showTest2(args);
					window.frame.setVisible(true);
//					embeddedMediaPlayerComponent.getMediaPlayer().playMedia("2.wmv");
//					embeddedMediaPlayerComponent_1.getMediaPlayer().playMedia("2.wmv");
//					embeddedMediaPlayerComponent_4.getMediaPlayer().playMedia("2.wmv");
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
		frame.setBounds(100, 100, 1210, 849);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(55, 407, 475, 16);
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
		panel.setBounds(55, 50, 475, 347);
		frame.getContentPane().add(panel,new Integer(100));
		panel.setLayout(null);
		
		embeddedMediaPlayerComponent_1 = new EmbeddedMediaPlayerComponent();

		embeddedMediaPlayerComponent_1.setBounds(0, 0, 473, 347);
		panel.add(embeddedMediaPlayerComponent_1);


		JPanel panel_1 = new JPanel();
		panel_1.setBounds(580, 50, 494, 347);
		frame.getContentPane().add(panel_1,new Integer(100));
		panel_1.setLayout(null);
		embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent.setBounds(0, 0, 494, 347);
		panel_1.add(embeddedMediaPlayerComponent);
		embeddedMediaPlayerComponent.setCursorEnabled(true);
		
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
		
		JButton btnNewButton = new JButton("choose");
		btnNewButton.setBounds(10, 777, 93, 23);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				int v = chooser.showOpenDialog(null);
				if (v== JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					embeddedMediaPlayerComponent_1.getMediaPlayer().playMedia(file.getAbsolutePath());
//					embeddedMediaPlayerComponent.getMediaPlayer().playMedia(file.getAbsolutePath());
					System.out.println(file.getAbsolutePath());

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
		});
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("play");
		btnNewButton_1.setBounds(169, 777, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("pause");
		btnNewButton_2.setBounds(118, 443, 93, 23);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				embeddedMediaPlayerComponent_1.getMediaPlayer().pause();
			}
		});

		JButton btnNewButton_3 = new JButton("play");
		btnNewButton_3.setBounds(355, 443, 93, 23);
		frame.getContentPane().add(btnNewButton_3);
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				embeddedMediaPlayerComponent_1.getMediaPlayer().play();
			}
		});
	}



	public showTest2(String[] args) {
		initialize();
	}

}
