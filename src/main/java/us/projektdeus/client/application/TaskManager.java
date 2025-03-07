package us.projektdeus.client.application;

import us.projektdeus.client.core.MainWindow;

import javax.swing.*;

public class TaskManager {

    public static void initTaskManager() {
        //Create the main Window
        MainWindow.initFrames();
        System.out.println("MainWindow Initialized");
        //Create the TaskBar
        TaskBar.initTaskBar();
        System.out.println("TaskManager Initialized");
        //Add the TaskBar to Desktop
        JDesktopPane Desktop = MainWindow.jDesktopPane_Desktop;
//        Desktop.add(TaskBar.jPanel_TaskBar);
        System.out.println("TaskManager Added to Desktop");


    }

























}
