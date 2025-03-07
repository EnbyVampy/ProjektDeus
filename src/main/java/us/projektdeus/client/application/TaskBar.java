package us.projektdeus.client.application;

import javax.swing.*;
import java.awt.*;

//import static us.projektdeus.client.core.AppData.MainWindowData.APPDATA;

public class TaskBar {

    public static JPanel jPanel_TaskBar;

    public static void initTaskBar(){
//        jPanel_TaskBar = create_jPanel_TaskBar((Object[]) APPDATA[9]);
    }

    public static JPanel create_jPanel_TaskBar(Object[]args){
        Integer[] DIM = (Integer[]) args[0];
        Color[] TH  = (Color[])   args[1];

        JPanel jPanel_TaskBar = new JPanel();
        jPanel_TaskBar.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jPanel_TaskBar.setLayout(null);
        jPanel_TaskBar.setBackground(Color.BLUE);
        jPanel_TaskBar.setOpaque(true);
        jPanel_TaskBar.setVisible(true);
        System.out.println("TaskBar created");
        return jPanel_TaskBar;
    }










}
