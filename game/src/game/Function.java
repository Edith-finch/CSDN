package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static game.Game.*;

public class Function implements ActionListener{
//  定义计时器
    public Timer timer=new Timer(1000,this);
//  添加地雷
    public void add(){
        Random random=new Random();
        for(int i=0;i<LEICOUNT;){
//          使用随机数生成地雷
            int r=random.nextInt(ROW);
            int c=random.nextInt(COL);
//          避免生成位置相同
            if(data[r][c]!=LEICODE){
                data[r][c]=LEICODE;
                i++;
            }
        }
//      计算周围雷的数量
        for (int i=0;i< ROW;i++){
            for(int j=0;j<COL;j++){
                if(data[i][j]==LEICODE) continue;
                int tempcount=0;
                if(i>0 && j>0 && data[i-1][j-1]==LEICODE)
                    tempcount++;
                if(i>0 && data[i-1][j]==LEICODE)
                    tempcount++;
                if(i>0 && j<COL-1 && data[i-1][j+1]==LEICODE)
                    tempcount++;
                if(j>0 && data[i][j-1]==LEICODE)
                    tempcount++;
                if(j<COL-1 && data[i][j+1]==LEICODE)
                    tempcount++;
                if(i<ROW-1 && j>0 && data[i+1][j-1]==LEICODE)
                    tempcount++;
                if(i<ROW-1 && data[i+1][j]==LEICODE)
                    tempcount++;
                if(i<ROW-1 && j<COL-1 && data[i+1][j+1]==LEICODE)
                    tempcount++;
//              格子中记录周围炸弹数
                data[i][j]=tempcount;
            }
        }
    }

//  检验是否胜利
    public void checkWin(){
        int count=0;
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                if(buttons[i][j].isEnabled())count++;
            }
        }
        if(count==LEICOUNT){
//          停止计时器，并输出提示信息
            timer.stop();
            JOptionPane.showMessageDialog(Windows.frame,"你成功了！");
        }
    }

//  打开格子
    public void openCell(int i,int j){
        JButton btn=buttons[i][j];
//      去除已经打开的格子
        if(!btn.isEnabled())
            return;
//      设置打开格子的效果
        btn.setEnabled(false);
        btn.setOpaque(true);
        btn.setBackground(Color.green);
        btn.setText(data[i][j]+"");
//      调用方法，更新label中的值
        updateCount();
//      使用递归的思想，实现级联打开
        if(data[i][j]==0){
            if(i>0 && j>0 && data[i-1][j-1]==0)
                openCell(i-1,j-1);
            if(i>0 && data[i-1][j]==0)
                openCell(i-1,j);
            if(i>0 && j<COL-1 && data[i-1][j+1]==0)
                openCell(i-1,j+1);
            if(j>0 && data[i][j-1]==0)
                openCell(i,j-1);
            if(j<COL-1 && data[i][j+1]==0)
                openCell(i,j+1);
            if(i<ROW-1 && j>0 && data[i+1][j-1]==0)
                openCell(i+1,j-1);
            if(i<ROW-1 && data[i+1][j]==0)
                openCell(i+1,j);
            if(i<ROW-1 && j<COL-1 &&data[i+1][j+1]==0)
                openCell(i+1,j+1);
        }
    }

//  更新label中的值
    public void updateCount() {
        OPENED++;
        UNOPEN--;
        Windows.label1.setText("待开:"+UNOPEN);
        Windows.label2.setText("已开:"+OPENED);
    }

//  检验是否踩雷
    public void Lose(){
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                JButton btn= buttons[i][j];
                if(data[i][j]==LEICODE){
                    btn.setBackground(Color.red);
                    btn.setEnabled(false);
                }else{
                    btn.setEnabled(false);
                    btn.setOpaque(true);
                    btn.setText(data[i][j]+"");
                }
            }
        }
//      停止计时器，并输出提示信息
        timer.stop();
        JOptionPane.showMessageDialog(Windows.frame,"你踩雷了！");
    }

//  重新开始
    public void restart(){
//      重置格子
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                data[i][j]=0;
                buttons[i][j].setBackground(Color.yellow);
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
            }
        }
//      重置参数
        UNOPEN=ROW*COL;
        OPENED=0;
        second=0;
//      重置状态栏
        Windows.label1.setText("待开:"+UNOPEN);
        Windows.label2.setText("未开:"+OPENED);
        Windows.label3.setText("用时:"+second+"s");
//      重新添加地雷
        add();
//      重新启动计时器
        timer.start();
    }

//  编写监听器，用于计时
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            second++;
            Windows.label3.setText("用时" + second + "s");
            timer.start();
            return;
        }
    }
}
