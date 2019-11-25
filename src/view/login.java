package view;

import mapper.UserMapper;
import pojo.User;
import utils.SqlSessionFactoryUtils;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * @Auther: MGL
 * @Date: 2019/3/17 16:52
 * @Description:
 */
@SuppressWarnings("all")
public class login{

    public static JFrame jFrame;
    private JTextField jTextField;
    private JPasswordField jPasswordField;
    private JLabel username;
    private JLabel password;
    private JButton submit;




    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    login window = new login();
                    window.jFrame.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public login(){
        initialize();
    }

    private void initialize(){
        jFrame = new JFrame();
        jFrame.setBounds(700,300,450,300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(null);

        jTextField = new JTextField();
        jTextField.setBounds(125, 57, 86, 24);
        jTextField.setColumns(10);
        jFrame.getContentPane().add(jTextField);

        username = new JLabel("用户名:");
        username.setBounds(80,57,50,24);
        jFrame.add(username);

        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(125,90,86,24);
        jFrame.add(jPasswordField);

        password = new JLabel("密码:");
        password.setBounds(80,90,50,24);
        jFrame.add(password);

        submit = new JButton("Login");
        submit.setBounds(130,130,90,24);
        jFrame.add(submit);

        DraggingImageUtil draggingImageUtil = new DraggingImageUtil(20,20,100,100);

        jFrame.getContentPane().add(draggingImageUtil.getjLabel());
        ListenerForJLabel listenerForJLabel = draggingImageUtil.getListener();



        ListenForButtom listenForButtom = new ListenForButtom();
        submit.addActionListener(listenForButtom);
        DragComponentListener listener = new DragComponentListener();
        submit.addMouseListener(listener);
        submit.addMouseMotionListener(listener);


    }



    private class ListenForButtom implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand()=="Login") {
                UserMapper userMapper = SqlSessionFactoryUtils.getSqlSessionFactory().openSession().getMapper(UserMapper.class);
                String username = jTextField.getText();
                String password = new String(jPasswordField.getPassword());

                User user = userMapper.getUser(username, password);
                if (user != null) {
                    System.out.println("用户存在！");
                } else {
                    System.out.println("用户不存在！");
                }
            }
        }
    }

    private class DragComponentListener extends MouseInputAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("call method mouseClicked..." + e.getX() + "  " + e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("call method mousePressed..." + e.getX() + "  " + e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            System.out.println("call method mouseDragged..." + e.getX() + "  " + e.getY());
        }
    }
}

