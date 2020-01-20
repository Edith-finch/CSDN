package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Game.*;

public class Windows implements ActionListener {

//  创建公共静态对象方便调用
    public static JFrame frame=new JFrame();
    public static JButton jButton=new JButton();
    public static JLabel label1=new JLabel("待开:"+UNOPEN);
    public static JLabel label2=new JLabel("已开:"+OPENED);
    public static JLabel label3=new JLabel("用时:"+second+"s");

//  定义计时器
    public Timer timer=new Timer(1000,this);
//  创建实例对象
    Function function=new Function();

//  创建窗口
    public void createWindows(){
//      定义窗口大小（600*700）
        frame.setSize(600,700);
//      窗口是否可以改变大小
        frame.setResizable(false);
//      点击可关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      设置窗口布局
        frame.setLayout(new BorderLayout());
//      显示窗口
        frame.setVisible(true);
    }

    //  创建的按钮放窗口顶部
    public void setHeader(){
//      创建布局
        JPanel panel=new JPanel(new GridBagLayout());
        GridBagConstraints c1=new GridBagConstraints(0,0,3,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        panel.add(jButton,c1);
        jButton.addActionListener(this);

//      设置透明度
        label1.setOpaque(true);
//      设置背景色
        label1.setBackground(Color.white);
//      设置边框颜色
        label1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//      label2、label3同理
        label2.setOpaque(true);
        label2.setBackground(Color.white);
        label2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        label3.setOpaque(true);
        label3.setBackground(Color.white);
        label3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//      设置三个状态显示栏
        GridBagConstraints c2=new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        panel.add(label1,c2);
        GridBagConstraints c3=new GridBagConstraints(1,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        panel.add(label2,c3);
        GridBagConstraints c4=new GridBagConstraints(2,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        panel.add(label3,c4);
//      添加至布局的上方
        frame.add(panel,BorderLayout.NORTH);
    }

//  设置按钮
    public void setButtons(){
//      创建容器
        Container container=new Container();
//      创建布局
        container.setLayout(new GridLayout(ROW,COL));
//      添加至整体布局的中央
        frame.add(container,BorderLayout.CENTER);
//      创建按钮
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                JButton btn=new JButton();
                btn.setOpaque(true);
                btn.setBackground(Color.yellow);
                btn.addActionListener(this);
                container.add(btn);
                buttons[i][j]=btn;
            }
        }
    }

//  编写监听器工作
    public void actionPerformed(ActionEvent e) {
//      用于计时
        if(e.getSource() instanceof Timer){
            second++;
            label3.setText("用时"+second+"s");
            timer.start();
            return;
        }
//      按键响应
        JButton btn=(JButton) e.getSource();
//      重开按键响应
        if(btn.equals(jButton))
            function.restart();
//      游戏功能判定
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                if(btn.equals(buttons[i][j])){
                    if(data[i][j]==LEICODE){
                        function.Lose();
                    }else {
                        function.openCell(i, j);
                        function.checkWin();
                    }
                    return;
                }
            }
        }
    }
}
