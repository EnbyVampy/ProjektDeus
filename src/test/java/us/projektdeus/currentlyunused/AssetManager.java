package us.projektdeus.currentlyunused;

import us.projektdeus.client.test.LogHandler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AssetManager {
    private static final Logger logger = LogHandler.getLogger(FileManager.class);

    static List<BufferedImage> icons = new ArrayList<>();

    public static void main(String[] args) {
    }


    public static void loadIcons(String file) {
        try {
            // Use ImageParser to load and split the image.
            icons = ImageParser.loadAndSplitImage(file, 4, 4); // 4x4 grid
            System.out.println("Icons successfully loaded and split!");
        } catch (IOException e) {
            logger.warning("Failed to load or process the image: " + file);
        } catch (Exception e) {
            logger.warning("An unexpected error occurred.");
        }
    }

    public static List<BufferedImage> getIcons() {
        return icons;
    }
}
