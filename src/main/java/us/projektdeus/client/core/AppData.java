package us.projektdeus.client.core;

import java.awt.*;

public class AppData {


    public static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int ScreenH = ScreenSize.height;
    public static int ScreenW = ScreenSize.width;
    public static int ThemeNum = 0;

    public static class MainFrame {//MainFrame
        public static int H = 600;
        public static int W = 800;
        public static int X = (ScreenW - W) / 2;
        public static int Y = (ScreenH - H) / 2;
    }

    public static class FramePanel {//FramePanel
        public static int X = 0;
        public static int Y = 0;
        public static int W = MainFrame.W;
        public static int H = MainFrame.H;
    }

    public static class ContentPanel {//ContentPanel
        public static int Border = 1;
        public static int X = Border;
        public static int Y = Border;
        public static int W = FramePanel.W - Border * 2;
        public static int H = FramePanel.H - Border * 2;
    }

    public static class ContentHeader {//ContentHeader
        public static int X = 0;
        public static int Y = 0;
        public static int W = MainFrame.W - ContentPanel.Border * 2;
        public static int H = 20;
    }

    public static class HeaderLabel {//HeaderLabel
        public static int W = ContentHeader.W / 4 - ContentPanel.Border * 2;
        public static int H = ContentHeader.H;
        public static int X = ContentPanel.W / 2 - HeaderLabel.W / 2;
        public static int Y = 0;
    }

    public static class DesktopPane {//DesktopPane
        public static int X = 0;
        public static int Y = ContentHeader.H;
        public static int W = ContentPanel.W;
        public static int H = ContentPanel.H - ContentHeader.H;
    }

    public static class CloseButton {//CloseButton
        public static int W = ContentHeader.H;
        public static int H = ContentHeader.H;
        public static int X = ContentHeader.W - CloseButton.W;
        public static int Y = 0;
    }

    public static class ResizeButton {//ResizeButton
        public static int W = ContentHeader.H;
        public static int H = ContentHeader.H;
        public static int X = ContentHeader.W - CloseButton.W - ResizeButton.W - 2;
        public static int Y = 0;
    }

    public static class MinimizeButton {//MinimizeButton
        public static int W = ContentHeader.H;
        public static int H = ContentHeader.H;
        public static int X = ContentHeader.W - CloseButton.W - ResizeButton.W - MinimizeButton.W - 4;
        public static int Y = 0;
    }

    public static class FontData {
        public static Font HeaderLabelFont = new Font("Arial", Font.BOLD, HeaderLabel.H);
    }

    public static class DarkTheme {
        public static Color FramePanelBG = new Color(0x414243);     //Border        [0]
        public static Color HeaderPanelBG = new Color(0x1e1f22);    //Header        [1]
        public static Color HeaderLabelBG = new Color(0x313338);    //Title         [2]
        public static Color Spacer = new Color(0x313338);           //Spacer        [3]
        public static Color HeaderLabelFG = new Color(0x1e1f22);    //Header        [4]
        public static Color DesktopPanelBG = new Color(0x000000);   //Desktop       [5]
        public static Color CloseButtonBG = new Color(0x1e1f22);    //closeBG       [6]
        public static Color CloseButtonHoverBG = new Color(0x994457);//closeHov     [7]
        public static Color CloseButtonFG = new Color(0x313338);//closeText         [8]
        public static Color ResizeButtonBG = new Color(0x1e1f22);//resizeBG         [9]
        public static Color ResizeButtonHoverBG = new Color(0x669944);//resizeHov   [10]
        public static Color ResizeButtonFG = new Color(0x313338);//resizeText       [11]
        public static Color MinimizeButtonBG = new Color(0x1e1f22);//minBG          [12]
        public static Color MinimizeButtonHoverBG = new Color(0x6677B3);//minHov    [13]
        public static Color MinimizeButtonFG = new Color(0x313338);//minText        [14]
    }

    public static class LiteTheme {
        public static Color FramePanelBG = new Color(0x414243);     //BorderColor
        public static Color HeaderPanelBG = new Color(0x1e1f22);    //Header Color
        public static Color HeaderLabelBG = new Color(0x313338);    //TitleLabel Color
        public static Color Spacer = new Color(0x313338);
        public static Color HeaderLabelFG = new Color(0x1e1f22);
        public static Color DesktopPanelBG = new Color(0x000000);   //DesktopPanel BG

        public static Color CloseButtonBG = new Color(0x1e1f22);
        public static Color CloseButtonHoverBG = new Color(0x994457);
        public static Color CloseButtonFG = new Color(0x313338);
        public static Color ResizeButtonBG = new Color(0x1e1f22);
        public static Color ResizeButtonHoverBG = new Color(0x669944);
        public static Color ResizeButtonFG = new Color(0x313338);
        public static Color MinimizeButtonBG = new Color(0x1e1f22);
        public static Color MinimizeButtonHoverBG = new Color(0x6677B3);
        public static Color MinimizeButtonFG = new Color(0x313338);
    }

    public static final Object[] ThemeData = new Object[]{
            new Color[]{
                    DarkTheme.FramePanelBG,         //[0]
                    DarkTheme.HeaderPanelBG,        //[1]
                    DarkTheme.HeaderLabelBG,        //[2]
                    DarkTheme.Spacer,               //[3]
                    DarkTheme.HeaderLabelFG,        //[4]
                    DarkTheme.DesktopPanelBG,       //[5]
                    DarkTheme.CloseButtonBG,        //[6]
                    DarkTheme.CloseButtonHoverBG,   //[7]
                    DarkTheme.CloseButtonFG,        //[8]
                    DarkTheme.ResizeButtonBG,       //[9]
                    DarkTheme.ResizeButtonHoverBG,  //[10]
                    DarkTheme.ResizeButtonFG,       //[11]
                    DarkTheme.MinimizeButtonBG,     //[12]
                    DarkTheme.MinimizeButtonHoverBG,//[13]
                    DarkTheme.MinimizeButtonFG,     //[14]

            },
            new Color[]{
                LiteTheme.FramePanelBG,        //[0]
                LiteTheme.HeaderPanelBG,       //[1]
                LiteTheme.HeaderLabelBG,       //[2]
                LiteTheme.Spacer,              //[3]
                LiteTheme.HeaderLabelFG,       //[4]
                LiteTheme.DesktopPanelBG,      //[5]
                LiteTheme.CloseButtonBG,       //[6]
                LiteTheme.CloseButtonHoverBG,  //[7]
                LiteTheme.CloseButtonFG,       //[8]
                LiteTheme.ResizeButtonBG,      //[9]
                LiteTheme.ResizeButtonHoverBG, //[10]
                LiteTheme.ResizeButtonFG,      //[11]
                LiteTheme.MinimizeButtonBG,    //[12]
                LiteTheme.MinimizeButtonHoverBG,//[13]
                LiteTheme.MinimizeButtonFG,    //[14]
            }
    };

    public static final Object[] Fonts = new Object[]{
            new Font[]{FontData.HeaderLabelFont,}
    };

    public static final Object[] DIM = new Object[]{
        new Integer[]{MainFrame.X, MainFrame.Y, MainFrame.W, MainFrame.H},
        new Integer[]{FramePanel.X, FramePanel.Y, FramePanel.W, FramePanel.H},
        new Integer[]{ContentPanel.X, ContentPanel.Y, ContentPanel.W, ContentPanel.H},
        new Integer[]{ContentHeader.X, ContentHeader.Y, ContentHeader.W, ContentHeader.H},
        new Integer[]{HeaderLabel.X, HeaderLabel.Y, HeaderLabel.W, HeaderLabel.H},
        new Integer[]{DesktopPane.X, DesktopPane.Y, DesktopPane.W, DesktopPane.H},
        new Integer[]{CloseButton.X, CloseButton.Y, CloseButton.W, CloseButton.H},
        new Integer[]{ResizeButton.X, ResizeButton.Y, ResizeButton.W, ResizeButton.H},
        new Integer[]{MinimizeButton.X, MinimizeButton.Y, MinimizeButton.W, MinimizeButton.H},
    };

    public static final Object[] APPDATA = new Object[]{
        new Object[]{DIM[0], ThemeData[0], Fonts[0]},
        new Object[]{DIM[1], ThemeData[0], Fonts[0]},
        new Object[]{DIM[2], ThemeData[0], Fonts[0]},
        new Object[]{DIM[3], ThemeData[0], Fonts[0]},
        new Object[]{DIM[4], ThemeData[0], Fonts[0]},
        new Object[]{DIM[5], ThemeData[0], Fonts[0]},
        new Object[]{DIM[6], ThemeData[0], Fonts[0]},
        new Object[]{DIM[7], ThemeData[0], Fonts[0]},
        new Object[]{DIM[8], ThemeData[0], Fonts[0]},
    };
}