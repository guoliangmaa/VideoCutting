package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Properties;
import javax.swing.JButton;

@SuppressWarnings("all")
public class SelectDemo extends JFrame {

    private JPanel contentPane;

    private static JFrame frame;
    public static Properties properties = new Properties();

    static {
        InputStream inputStream = SelectDemo.class.getClassLoader().getResourceAsStream("init.properties");
        try {
            properties.load(inputStream);

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SelectDemo selectDemo = new SelectDemo();
                selectDemo.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public SelectDemo() {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 783, 576);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        frame.setContentPane(contentPane);

        //处理分辨率的选择框
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(443, 157, 106, 21);
        comboBox.addItem("1920*1080");
        comboBox.addItem("1080*720");
        comboBox.addItem("720*480");
        comboBox.addItem("480*320");
        contentPane.add(comboBox);
        String resulution = properties.getProperty("init.resolution");


        JLabel lblNewLabel = new JLabel("视频处理");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        lblNewLabel.setBounds(283, 39, 167, 33);
        contentPane.add(lblNewLabel);

        JLabel label = new JLabel("视频分辨率");
        label.setFont(new Font("宋体", Font.PLAIN, 30));
        label.setBounds(69, 145, 167, 33);
        contentPane.add(label);

        JLabel label_1 = new JLabel("大屏分割数");
        label_1.setFont(new Font("宋体", Font.PLAIN, 30));
        label_1.setBounds(69, 250, 167, 33);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("视频输入路数");
        label_2.setFont(new Font("宋体", Font.PLAIN, 30));
        label_2.setBounds(69, 350, 186, 33);
        contentPane.add(label_2);


        //处理分割视频数 的第一个乘数
        JComboBox<Integer> comboBox_1 = new JComboBox<>();
        comboBox_1.setBounds(432, 262, 42, 21);
        comboBox_1.addItem(1);
        comboBox_1.addItem(2);
        comboBox_1.addItem(3);
        comboBox_1.addItem(4);
        contentPane.add(comboBox_1);

        //处理分割视频数的第二个乘数
        JComboBox<Integer> comboBox_2 = new JComboBox<>();
        comboBox_2.setBounds(530, 262, 42, 21);
        comboBox_2.addItem(1);
        comboBox_2.addItem(2);
        comboBox_2.addItem(3);
        comboBox_2.addItem(4);
        contentPane.add(comboBox_2);

        //处理输入视频路数
        JComboBox<Integer> comboBox_3 = new JComboBox<>();
        comboBox_3.setBounds(471, 362, 42, 21);
        for (int i = 1; i <= 16; i++) {
            comboBox_3.addItem(i);
        }
        contentPane.add(comboBox_3);

        JLabel lblNewLabel_1 = new JLabel("x");
        lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 35));
        lblNewLabel_1.setBounds(484, 250, 57, 41);
        contentPane.add(lblNewLabel_1);

        JButton btnNewButton = new JButton("确定");
        btnNewButton.setFont(new Font("宋体", Font.BOLD, 20));
        btnNewButton.setBounds(294, 439, 214, 50);
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //File file = new File(String.valueOf(SelectDemo.class.getClassLoader().getResourceAsStream("init.properties")));
                String resolution = comboBox.getItemAt(comboBox.getSelectedIndex());

                int splitCount_X = comboBox_1.getItemAt(comboBox_1.getSelectedIndex());
                int splitCount_Y = comboBox_2.getItemAt(comboBox_2.getSelectedIndex());

                int inputCount = comboBox_3.getItemAt(comboBox_3.getSelectedIndex());
                try {

                    OutputStream outputStream = new FileOutputStream("src/init.properties");
                    properties.setProperty("init.resolution", resolution);
                    properties.setProperty("init.split_x", String.valueOf(splitCount_X));
                    properties.setProperty("init.split_y", String.valueOf(splitCount_Y));
                    properties.setProperty("init.input_count", String.valueOf(inputCount));

                    properties.store(outputStream, "Update value");

                    outputStream.close();

                    frame.dispose();
                    ShowDemo.main(null);

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        contentPane.add(btnNewButton);

        initialization(comboBox, comboBox_1, comboBox_2, comboBox_3);
    }

    /**
     * 根据配置文件把每个选择框的值设置为配置文件的值
     *
     * @param resolutionBox     分辨率
     * @param split_xBox    切割播放器矩阵的x(长是多少个)
     * @param split_yBox    切割播放器矩阵的y(宽是多少个)
     * @param inputBox      输入源的个数
     */
    private void initialization(JComboBox resolutionBox, JComboBox split_xBox, JComboBox split_yBox, JComboBox inputBox) {
        String property = properties.getProperty("init.resolution");
        switch (property) {
            case "1920*1080":
                resolutionBox.setSelectedIndex(0);
                break;
            case "1080*720":
                resolutionBox.setSelectedIndex(1);
                break;
            case "720*480":
                resolutionBox.setSelectedIndex(2);
                break;
            case "480*320":
                resolutionBox.setSelectedIndex(3);
                break;
            default:
                resolutionBox.setSelectedIndex(0);
                break;
        }

        int x = Integer.valueOf(properties.getProperty("init.split_x"));
        split_xBox.setSelectedIndex(x-1);

        int y = Integer.valueOf(properties.getProperty("init.split_y"));
        split_yBox.setSelectedIndex(y-1);

        int input = Integer.valueOf(properties.getProperty("init.input_count"));
        inputBox.setSelectedIndex(input-1);
    }


}
