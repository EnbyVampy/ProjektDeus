package us.projektdeus.client.test;

import javax.swing.*;
import java.awt.*;

public class test {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        //testFrame(frame,testFrame);
    }



//
//    Object[] frameArgs = new Object[]{ //JFrame
//
//            "JFrame","window",AppWidth,AppHeight,centerX,centerY,0,0,AppWidth,AppHeight,BGColor,FGColor,
//            new Font("Arial",Font.PLAIN,12),true,null,"window",true,true,null
//
//    };



    public static void testFrame(JFrame frame,Object[] newData){
        frame.setName((String) newData[1]);
        frame.setSize((Integer) newData[2],(Integer) newData[3]);
        frame.setLocation((Integer) newData[4],(Integer) newData[5]);
        frame.setBounds((Integer) newData[6],(Integer) newData[7],(Integer) newData[8],(Integer) newData[9]);
        frame.setBackground((Color) newData[10]);
        frame.setForeground((Color) newData[11]);
        frame.setFont((Font) newData[17]);
        frame.setFocusable((Boolean) newData[12]);
        frame.setLayout((LayoutManager) newData[15]);
        frame.setTitle((String) newData[0]);
        frame.setUndecorated((Boolean) newData[13]);
        frame.setResizable((Boolean) newData[14]);
        frame.setLocationRelativeTo((Component) newData[16]);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);     // Makes sure the
            frame.toFront();            // window is brought
            frame.setAlwaysOnTop(false);// to the front when
            frame.requestFocus();       // it is first opened
    }

}
