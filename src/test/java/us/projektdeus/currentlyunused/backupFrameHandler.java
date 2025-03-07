//package us.projektdeus.client.core;
//
//import javax.swing.*;
//import java.awt.*;
//
////import java.awt.event.ActionListener;
//
//public class FrameHandler {
//    //Frame,Header,Border sizes
//    public static int AppBorderSize = 2;
//    public static int AppHeaderSize = 20;
//    public static int AppHeight = 600;
//    public static int AppWidth = 800;
//    public static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
//    public static int ScreenHeight = ScreenSize.height;
//    public static int ScreenWidth = ScreenSize.width;
//    public static int centerX = (ScreenWidth - AppWidth) / 2;
//    public static int centerY = (ScreenHeight - AppHeight) / 2;
//
//    public static final Color BGColor = new Color(0x333333);
//    public static final Color FGColor = new Color(0x333333);
//
//    public static final Color BorderColor = new Color(0x333333);//APP Border Color
//    public static final Color HeaderColor = new Color(0x205044);//HeaderBar background color
//    public static final Color TitleFGColor = new Color(0xFFFFFF);//TitleBar font color
//    public static final Color TitleBGColor = new Color(0x000000);//TitleBar background color
//
//
//    public static final Color CloseBGColor = new Color(0x222222);//Close Button background Color
//    public static final Color CloseFGColor = new Color(0x000000);//Close Button font color
//    public static final Color MinimizeBGColor = new Color(0x222222);//Minimize Button background Color
//    public static final Color MinimizeFGColor = new Color(0x000000);//Minimize Button font color
//    public static final Color MaximizeBGColor = new Color(0x222222);//Maximize Button background Color
//    public static final Color MaximizeFGColor = new Color(0x000000);//Maximize Button font color
//
//
//    public static void main(String[] args){
//        createConsoleFrame("Projekt Deus", AppWidth, AppHeight);}
//    //    createConsoleFrame();}
//
//
//
//    //builds the main application window
//    //public static void createConsoleFrame() {
//    public static void createConsoleFrame(String name, Integer width, Integer height) {
//
//
//        JFrame app = new JFrame(name);//create a new JFrame named app
////        JPanel content = new JPanel();
////        JDesktopPane desk = new JDesktopPane();//create a new JDesktopPane named desk
//        JPanel head = new JPanel();//create a new JPanel named head
////        JButton close = new JButton("close");
////        JButton minimize = new JButton("minimize");
////        JButton maximize = new JButton("maximize");
//
//        Object[] titleBarArgs = new Object[]{
//                "title", "title", "< name >", //[0],[1],[2]
//                AppWidth - AppBorderSize / 3 * 2, AppHeaderSize, AppWidth-300, 0, 0, 0, 0, 0,
//                //[3]                                [4]             [5]      [6][7][8][9][10]
//                TitleBGColor, TitleFGColor, new Font("Arial", Font.BOLD, 16), JLabel.CENTER, JLabel.CENTER, "true",null, "true"
//                //[11]            [12]                      [13]                              [14]          [15]         [16]  [17]   [18]
//        };
//
//
//        //builds the Application by adding all these elements where they need to go
//        app.add(head);
//        FrameElements.initTitleBar(head,titleBarArgs); //(Parent, Object[])
//
//        app.add(content);
////        content.add(desk);
////        head.add(minimize);
////        head.add(maximize);
////        head.add(close);
//
//        //adds ComponentListeners to applicationWindow,desktop,and header
////        FrameUtils.addAppComponentListener(app);
////        FrameUtils.addDeskComponentListener(desk);
//        FrameUtils.addHeaderComponentListener(head);
//        FrameUtils.enableHeaderFrameDrag(head, app);
//
//        FrameElements.initFrame( //App Frame
//                app,true,
//                new Integer[]{AppWidth+AppBorderSize*2,AppHeight+AppBorderSize*2+AppHeaderSize,0,0},
//                null,true,true,new Boolean[]{true,false},null);
////        FrameElements.initJDesktop( //Desktop
////                desk,
////                new Integer[]{AppWidth,AppHeight,AppBorderSize, AppBorderSize+AppHeaderSize},Color.BLACK);
////        FrameElements.initJPanel( //Content panel
////                content,BorderColor,
////                new Integer[]{AppWidth+AppBorderSize*2,AppHeight+AppBorderSize*2+AppHeaderSize,0,0},
////                true,null);
//        FrameElements.initJPanel( //header panel
//                head,HeaderColor,
//                new Integer[]{AppWidth,AppHeaderSize,AppBorderSize,AppBorderSize,},
//                true,null);
//
////        FrameElements.initButtons( //close button
////                new Integer[]{AppWidth-AppHeaderSize,0}, CloseFGColor,CloseBGColor,
////                close,"x",
////                new Font("Arial", Font.PLAIN, 18),
////                false,
////                _ -> app.dispose(),
////                new Integer[]{0,0,0,0},
////                false,true);
////        FrameElements.initButtons(new Integer[]{    AppWidth-AppBorderSize-AppHeaderSize*2-2,0},    MaximizeFGColor,MaximizeBGColor,
////        maximize,"+",   new Font("Arial", Font.PLAIN, 18),false,_ -> FrameUtils.resizeWindow(app),  new Integer[]{0,0,0,0},false,true);
////        FrameElements.initButtons(new Integer[]{    AppWidth-AppBorderSize-AppHeaderSize*3-4,0},    MinimizeFGColor,MinimizeBGColor,
////        minimize,"-",   new Font("Arial", Font.PLAIN, 18),false,_ -> app.setState(JFrame.ICONIFIED),new Integer[]{0,0,0,0},false,true);
//
//
////        System.out.println(app.getLayout()+" is the layout of the app");
////        System.out.println(head.getLayout()+" is the layout of the header");
////        System.out.println(content.getLayout()+" is the layout of the content");
////
////        System.out.println(desk.getLayout()+" is the layout of the desktop");
////        System.out.println(close.getLayout()+" is the layout of the close button");
////        System.out.println(maximize.getLayout()+" is the layout of the maximize button");
////        System.out.println(minimize.getLayout()+" is the layout of the minimize button");
//
//
////        System.out.println("App Width: " + AppWidth + " App Height: " + AppHeight);
////        System.out.println("Header Height:" + AppHeaderSize + " Header Width: " + AppHeaderSize);
//
//
//
//        //redraws the buttons
////        close.revalidate();
////        maximize.revalidate();
////        minimize.revalidate();
////        close.repaint();
////        maximize.repaint();
////        minimize.repaint();
//
//    }
//
//
//
//
//}
