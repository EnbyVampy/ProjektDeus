package us.projektdeus.client.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class FrameUtils {
    public static MouseListener enableHeaderFrameDrag(JPanel head, JFrame app) {
                    final Point clickPoint = new Point();
                    head.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            clickPoint.setLocation(e.getX(), e.getY());
                            head.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));}
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            head.setCursor(Cursor.getDefaultCursor());
                        }});
                    head.addMouseMotionListener(new MouseAdapter() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            int x = app.getX() + e.getX() - clickPoint.x;
                            int y = app.getY() + e.getY() - clickPoint.y;
                            app.setLocation(x, y);
                        }
                    });return null;
    }

    public static Icon createCloseButtonIcon(int size, Color color, int strokeWidth,int offset) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(strokeWidth));
                int end = size - offset;
                g2d.drawLine(offset, offset, end, end); // Top-left to bottom-right
                g2d.drawLine(offset, end, end, offset); // Bottom-left to top-right
            }
            @Override
            public int getIconWidth() {return size;}
            @Override
            public int getIconHeight() {return size;}
        };
    }
    public static Icon createMinimizeButtonIcon(int size, Color color, int strokeWidth, int offset) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(strokeWidth));
                int startY = size - strokeWidth+2 - offset; // Near the bottom of the icon
                // Horizontal start with padding
                int endX = size - offset; // Horizontal end with padding
                g2d.drawLine(offset, startY, endX, startY);
            }
            @Override
            public int getIconWidth() {return size;}
            @Override
            public int getIconHeight() {return size;}
        };
    }
    public static Icon createResizeButtonIcon(int size, Color color, int strokeWidth, int offset) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(strokeWidth));
                int squareSize = size - (strokeWidth) - (offset);
                int squareX = (size - squareSize) / 2; // Horizontally centered
                int squareY = (size - squareSize) / 2; // Vertically centered
                g2d.drawRect(squareX, squareY, squareSize, squareSize);
            }
            @Override
            public int getIconWidth() {return size;}
            @Override
            public int getIconHeight() {return size;}
        };
    }

    public static class ResizeListener {
        public static void addResizeListener(JComponent component, Runnable onResize) {
            if (component == null || onResize == null) {
                throw new IllegalArgumentException("Component and onResize action cannot be null.");
            }

            component.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    onResize.run();
                }
            });
        }
        public static void addResizeListenerToAll(Container container, Runnable onResize) {
            if (container == null || onResize == null) {
                throw new IllegalArgumentException("Container and onResize action cannot be null.");
            }

            // Add listener to the container itself (if it's a JComponent)
            if (container instanceof JComponent) {
                addResizeListener((JComponent) container, onResize);
            }

            // Recursively add listeners to all child components
            for (Component component : container.getComponents()) {
                if (component instanceof JComponent) {
                    addResizeListener((JComponent) component, onResize);
                }
                if (component instanceof Container) {
                    addResizeListenerToAll((Container) component, onResize);
                }
            }
        }
        @FunctionalInterface
        public interface ResizeCallback {
            void onResize(JComponent resizedComponent);
        }
        public static void addResizeable(JFrame jframe){
            FrameUtils.ResizeListener.addResizeListenerToAll(jframe, () -> {
                AppData.MainFrame.W = jframe.getWidth();
                AppData.MainFrame.H = jframe.getHeight();
                AppData.MainFrame.X = jframe.getX();
                AppData.MainFrame.Y = jframe.getY();
                    AppData.FramePanel.W = AppData.MainFrame.W;
                    AppData.FramePanel.H = AppData.MainFrame.H;
                AppData.ContentPanel.W = AppData.FramePanel.W - AppData.ContentPanel.Border * 2;
                AppData.ContentPanel.H = AppData.FramePanel.H - AppData.ContentPanel.Border * 2;
                AppData.ContentPanel.X = AppData.ContentPanel.Border;
                AppData.ContentPanel.Y = AppData.ContentPanel.Border;
                    AppData.ContentHeader.W = AppData.ContentPanel.W - AppData.ContentPanel.Border * 2;
                AppData.HeaderLabel.W = AppData.ContentHeader.W/4 - AppData.ContentPanel.Border * 2;
                AppData.HeaderLabel.H = AppData.ContentHeader.H;
                AppData.HeaderLabel.X = AppData.ContentPanel.W/2 - AppData.HeaderLabel.W/2;
                    AppData.DesktopPane.Y = AppData.ContentHeader.H;
                    AppData.DesktopPane.W = AppData.ContentPanel.W;
                    AppData.DesktopPane.H = AppData.ContentPanel.H - AppData.ContentHeader.H;



                // Logic to adjust components dynamically
                System.out.println("Outer Panel Resized: Width = " + AppData.MainFrame.W + ", Height = " + AppData.MainFrame.H);

                // Dynamically adjust height of the inner panels
//            innerPanel1.setPreferredSize(new Dimension(width, height / 5));
//            innerPanel3.setPreferredSize(new Dimension(width, height / 5));

                // Revalidate and repaint to apply size changes
                MainWindow.jFrame_MainFrame.revalidate();
                MainWindow.jFrame_MainFrame.repaint();
                MainWindow.jPanel_Frame.revalidate();
                MainWindow.jPanel_Frame.repaint();
                MainWindow.jPanel_Content.revalidate();
                MainWindow.jPanel_Content.repaint();
                MainWindow.jPanel_Header.revalidate();
                MainWindow.jPanel_Header.repaint();
                MainWindow.jLabel_Header.revalidate();
                MainWindow.jLabel_Header.repaint();
                MainWindow.jDesktopPane_Desktop.revalidate();
                MainWindow.jDesktopPane_Desktop.repaint();

            });
        }


    }



    public static void addButtonAction(JButton button,int main,int sub,Color defaultColor,Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor); // Change to hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultColor); // Revert to default color
            }
        });
        switch (main) {
            case 0: //MainFrame
                switch (sub) {
                    case 0: //Close
                        button.addActionListener(_ -> System.exit(0)); // Exit the application
                    break;
                    case 1: //Resize
                        button.addActionListener(_ -> {
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
                            if (frame != null) {
                                if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                                    frame.setExtendedState(JFrame.NORMAL); // Restore to normal size
                                    AppData.MainFrame.W = frame.getWidth();
                                    AppData.MainFrame.H = frame.getHeight();
                                } else {
                                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Toggle to fullscreen
                                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                                    AppData.MainFrame.W = (int) screenSize.getWidth();
                                    AppData.MainFrame.H = (int) screenSize.getHeight();
                                }
                            }
                        });
                    break;
                    case 2: //Minimize
                        button.addActionListener(_ -> {
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
                            if (frame != null) {
                                frame.setState(JFrame.ICONIFIED); // Minimize the JFrame
                            }
                        });
                    break;
                    default:
                        System.out.println("Button action 0 not defined");
                }
            break;
            case 1: //InternalFrames
                switch (sub) {
                    case 0: //Close
                        button.addActionListener(_ -> {
                            JInternalFrame parent = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, button);
                            if (parent != null) {
                                parent.dispose();
                            }
                        });
                    break;
                    case 1: //Resize
                        button.addActionListener(_ -> {
                            JInternalFrame parent = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, button);
                            if (parent != null) {
                                JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, parent);
                                if (desktopPane != null) {
                                    if (parent.getBounds().equals(desktopPane.getBounds())) {
                                        parent.setBounds(parent.getClientProperty("originalBounds") != null
                                                ? (java.awt.Rectangle) parent.getClientProperty("originalBounds")
                                                : parent.getBounds());
                                    } else {
                                        parent.putClientProperty("originalBounds", parent.getBounds());
                                        parent.setBounds(desktopPane.getBounds());
                                    }
                                }
                            }
                        });
                    break;
                    case 2: //Minimize
                        button.addActionListener(_ -> {
                            JInternalFrame parent = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, button);
                            if (parent != null) {
                                try {
                                    parent.setIcon(true); // Minimize the JInternalFrame
                                } catch (java.beans.PropertyVetoException ex) {
                                    System.out.println("Failed to set icon for JInternalFrame: " + ex.getMessage());
                                }
                            }
                        });
                    break;
                    default:
                        System.out.println("Button action 1 not defined");
                }
            break;
            default:
                System.out.println("Button Main not defined");
        }
    }
}