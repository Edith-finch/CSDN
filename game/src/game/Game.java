package game;
/*任务一：创建窗口
  任务二：窗口布局
  任务三：定义数据结构
  任务四：实现游戏的基础功能（布雷，查雷，排雷,判定输赢）
  任务五：辅助功能(待开数，已开数，游戏重开，计时器)

  要点：swing组件的使用，编程抽象思想,数组遍历判定（核心算法）,计时器的使用,重启游戏的变量归零
*/

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
//  定义计时器
    public Timer timer=new Timer(1000,this);
//  实例化对象
    Function function = new Function();
    Windows windows = new Windows();

//  定义地图大小及数据结构
    public static int ROW = 10;
    public static int COL = 10;
    public static int[][] data = new int[ROW][COL];
    public static JButton[][] buttons = new JButton[ROW][COL];
//  定义地雷的个数
    public static int LEICOUNT = 10;
//  定义表示地雷的特征数
    public static int LEICODE = -1;
//  定义状态栏参数
    public static int UNOPEN = ROW * COL;
    public static int OPENED = 0;
    public static int second = 0;

//  创建游戏过程
    public Game() {
//      调用创建窗口
        windows.createWindows();
//      创建顶部状态框
        windows.setHeader();
//      添加地雷
        function.add();
//      设置按钮
        windows.setButtons();
//      启动计时器
        timer.start();
    }

//  主方法函数，启动游戏
    public static void main(String[] args) {
        new Game();
    }

//  编写监听器
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            second++;
            Windows.label3.setText("用时" + second + "s");
            timer.start();
            return;
        }
    }
}