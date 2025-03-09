package us.projektdeus.client.application;

import us.projektdeus.client.core.MainWindow;
import us.projektdeus.client.application.TaskBar;

import javax.swing.*;

public class Desktop {
    public static void main(String[] args) {







    }




    public static void initDesktop(){

    JInternalFrame jInternalFrame_Desktop = new JInternalFrame("Desktop", true, true, true, true);
    jInternalFrame_Desktop.setSize(1000, 700);
    jInternalFrame_Desktop.setLocation(0, 0);
    jInternalFrame_Desktop.setVisible(true);
    MainWindow.jDesktopPane_Desktop.add(jInternalFrame_Desktop);



    }

















}
