package us.projektdeus.currentlyunused;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static us.projektdeus.currentlyunused.AssetManager.icons;

public class ImageParser {

    public static void main(String[] args) {




    }



    public static List<BufferedImage> loadAndSplitImage(String imagePath, int rows, int cols) throws IOException {

        var resourceStream = ImageParser.class.getResourceAsStream(imagePath);
        if (resourceStream == null) {
            throw new IOException("Resource not found: " + imagePath);
        }

        // Load the full image from the resource stream.
        BufferedImage fullImage = ImageIO.read(resourceStream);

        // Calculate subimage dimensions
        int iconWidth = fullImage.getWidth() / cols;
        int iconHeight = fullImage.getHeight() / rows;

        List<BufferedImage> subImages = new ArrayList<>();

        // Split the image into subimages
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                BufferedImage subImage = fullImage.getSubimage(
                        x * iconWidth,
                        y * iconHeight,
                        iconWidth,
                        iconHeight
                );
                subImages.add(subImage);
            }
        }

        return subImages; // Return all split subimages
    }

    public static BufferedImage getIconByIndex(int index) {
        if (index < 0 || index >= icons.size()) {
            throw new IndexOutOfBoundsException("Invalid icon index: " + index);
        }
        return icons.get(index);
    }







}
