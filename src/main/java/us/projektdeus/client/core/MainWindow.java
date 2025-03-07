package us.projektdeus.client.core;

import javax.swing.*;
import java.awt.*;

import static us.projektdeus.client.core.AppData.APPDATA;
//import static us.projektdeus.client.core.AppData.MainWindowData.APPDATA;

public class MainWindow {

    public static JFrame        jFrame_MainFrame;
    public static JPanel        jPanel_Frame;
    public static JPanel        jPanel_Content;
    public static JPanel        jPanel_Header;
    public static JLabel        jLabel_Header;
    public static JDesktopPane  jDesktopPane_Desktop;
    public static JButton       jButton_Close;
    public static JButton       jButton_Maximize;
    public static JButton       jButton_Minimize;


    public static void initFrames()  {
        jFrame_MainFrame     = create_jFrame_MainFrame     ((Object[]) APPDATA[0]);                                                  //MFArgs = A when passed to initMF
        jPanel_Frame         = create_jPanel_Frame         ((Object[]) APPDATA[1]);    jFrame_MainFrame.add( jPanel_Frame );         //FPArgs = B when passed to initFP
        jPanel_Content       = create_jPanel_Content       ((Object[]) APPDATA[2]);    jPanel_Frame    .add( jPanel_Content );       //CPArgs = C when passed to initCP
        jPanel_Header        = create_jPanel_Header        ((Object[]) APPDATA[3]);    jPanel_Content  .add( jPanel_Header );        //HPArgs = D when passed to initHP
        jLabel_Header        = create_jLabel_Header        ((Object[]) APPDATA[4]);    jPanel_Header   .add( jLabel_Header );        //TLArgs = E when passed to initTL
        jDesktopPane_Desktop = create_jDesktopPane_Desktop ((Object[]) APPDATA[5]);    jPanel_Content  .add( jDesktopPane_Desktop ); //DPArgs = F when passed to initDP
        jButton_Close        = create_jButton_Close        ((Object[]) APPDATA[6]);    jPanel_Header   .add( jButton_Close );        //CBArgs = G when passed to initCB
        jButton_Maximize     = create_jButton_Maximize     ((Object[]) APPDATA[7]);    jPanel_Header   .add( jButton_Maximize );     //RBArgs = H when passed to initCB
        jButton_Minimize     = create_jButton_Minimize     ((Object[]) APPDATA[8]);    jPanel_Header   .add( jButton_Minimize );     //MBArgs = I when passed to initCB

        jPanel_Header.addMouseListener(FrameUtils.enableHeaderFrameDrag(jPanel_Header,jFrame_MainFrame));
        FrameUtils.ResizeListener.addResizeable(jFrame_MainFrame);





    }
    private static JButton create_jButton_Minimize(Object[]args){
        Integer[] DIM = (Integer[]) args[0];
          Color[] TH  = (Color[])   args[1];

        JButton jButton_Minimize = new JButton();
        jButton_Minimize.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jButton_Minimize.setBorderPainted(false);
        jButton_Minimize.setContentAreaFilled(false);
        jButton_Minimize.setFocusPainted(false);
        jButton_Minimize.setOpaque(true);
        jButton_Minimize.setVisible(true);
        jButton_Minimize.setBackground(TH[12]);
        FrameUtils.addButtonAction(jButton_Minimize,0,2,TH[12],TH[13]);
        Icon minimizeIcon = FrameUtils.createMinimizeButtonIcon(jButton_Minimize.getHeight(),TH[14], 2,6);//6
        jButton_Minimize.setIcon(minimizeIcon);
        return jButton_Minimize;
    }
    private static JButton create_jButton_Maximize(Object[]args){
        Integer[] DIM = (Integer[]) args[0];
        Color[] TH  = (Color[])   args[1];

        JButton jButton_Maximize = new JButton();
        jButton_Maximize.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jButton_Maximize.setBorderPainted(false);
        jButton_Maximize.setContentAreaFilled(false);
        jButton_Maximize.setFocusPainted(false);
        jButton_Maximize.setOpaque(true);
        jButton_Maximize.setVisible(true);
        jButton_Maximize.setBackground(TH[9]);
        FrameUtils.addButtonAction(jButton_Maximize,0,1,TH[9],TH[10]);
        Icon resizeIcon = FrameUtils.createResizeButtonIcon(jButton_Maximize.getHeight(),TH[11], 2,8);//4
        jButton_Maximize.setIcon(resizeIcon);
        return jButton_Maximize;
    }
    private static JButton create_jButton_Close(Object[]args){
        Integer[] DIM = (Integer[]) args[0];
        Color[] TH  = (Color[])   args[1];

        JButton jButton_Close = new JButton();
        jButton_Close.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jButton_Close.setBorderPainted(false);
        jButton_Close.setContentAreaFilled(false);
        jButton_Close.setFocusPainted(false);
        jButton_Close.setOpaque(true);
        jButton_Close.setVisible(true);
        jButton_Close.setBackground(TH[6]);
        FrameUtils.addButtonAction(jButton_Close,0,0,TH[6],TH[7]);
        Icon closeIcon = FrameUtils.createCloseButtonIcon(jButton_Close.getHeight(),TH[8], 2,6);//6
        jButton_Close.setIcon(closeIcon);
        return jButton_Close;
    }
    public static JDesktopPane create_jDesktopPane_Desktop(Object[] args){
        Integer[] DIM = (Integer[]) args[0];
        Color[] TH  = (Color[])   args[1];

        JDesktopPane jDesktopPane_Desktop = new JDesktopPane();
        jDesktopPane_Desktop.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jDesktopPane_Desktop.setVisible(true);
        jDesktopPane_Desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        jDesktopPane_Desktop.setBackground(TH[5]);
        return jDesktopPane_Desktop;
    }
    private static JLabel create_jLabel_Header(Object[]args){
        Integer[] DIM = (Integer[]) args[0];
        Color[] TH  = (Color[])   args[1];
        Font[] FT  = (Font[])    args[2];

        JLabel jLabel_Header = new JLabel();
        jLabel_Header.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jLabel_Header.setOpaque(true);
        jLabel_Header.setVisible(true);
        jLabel_Header.setText("Projekt Deus");
        jLabel_Header.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_Header.setVerticalAlignment(SwingConstants.CENTER);
        jLabel_Header.setBackground(TH[2]);
        jLabel_Header.setFont(FT[0]);
        jLabel_Header.setForeground(TH[4]);
        return jLabel_Header;
    }
    private static JPanel create_jPanel_Header(Object[]args){
        Integer[] DIM = (Integer[]) args[0];
        Color[] TH  = (Color[])   args[1];

        JPanel jPanel_Header = new JPanel();
        jPanel_Header.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jPanel_Header.setBackground(TH[1]);
        jPanel_Header.setLayout(null);
        return jPanel_Header;
    }
    private static JPanel create_jPanel_Content(Object[]args){
        Integer[] DIM = (Integer[]) args[0];

        JPanel jPanel_Content = new JPanel();
        jPanel_Content.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jPanel_Content.setLayout(null);
        return jPanel_Content;
    }
    private static JPanel create_jPanel_Frame(Object[]args){
        Integer[] DIM = (Integer[]) args[0];
        Color[] TH  = (Color[])   args[1];

        JPanel jPanel_Frame = new JPanel();
        jPanel_Frame.setBounds(DIM[0], DIM[1], DIM[2], DIM[3]);
        jPanel_Frame.setBackground(TH[0]);
        jPanel_Frame.setLayout(null);
        return jPanel_Frame;
    }
    private static JFrame create_jFrame_MainFrame(Object[]args){
        Integer[] DIM = (Integer[]) args[0];

        JFrame jFrame_MainFrame = new JFrame();
        jFrame_MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame_MainFrame.setBounds(DIM[0],DIM[1],DIM[2],DIM[3]);
        jFrame_MainFrame.setUndecorated(true);
        jFrame_MainFrame.setResizable(false);
        jFrame_MainFrame.setLocationRelativeTo(null);
        jFrame_MainFrame.setLayout(null);
        jFrame_MainFrame.setVisible(true);
        return jFrame_MainFrame;
    }




}