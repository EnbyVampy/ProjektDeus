package us.projektdeus.client.application;

import javax.swing.*;
import java.awt.*;
import static us.projektdeus.client.core.AppData.APPDATA;

public class Clock {
    public static JLabel jLabel_Clock;

    public static void initClock(){
        jLabel_Clock = create_jLabel_Clock((Object[]) APPDATA[10]);
    }

    private static JLabel create_jLabel_Clock(Object[] args) {
        Integer[] DIM = (Integer[]) args[0];
        Color[] TH  = (Color[])   args[1];

        JLabel jLabel_Clock = new JLabel();
        jLabel_Clock.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jLabel_Clock.setForeground(TH[0]);
        jLabel_Clock.setFont(new Font("Arial", Font.BOLD, DIM[3]));

        return jLabel_Clock;
    }





}