package us.projektdeus.server;

import us.projektdeus.server.core.ConsoleHandler;
import us.projektdeus.server.core.FrameHandler;

public class Main {
    public static void main(String[] args) {
        FrameHandler.createConsoleFrame();
        ConsoleHandler.redirectConsole(FrameHandler.getTextArea(), FrameHandler.getTextField());
    }
}