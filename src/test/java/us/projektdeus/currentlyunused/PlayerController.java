package us.projektdeus.currentlyunused;

public class PlayerController {


    // Class-level field to store player position
    private final int[] playerXYZ = {1, 1, 1, 1}; // [W+, A+, S+, D+], initial position

    // Method to handle player input
    public void playerInput(String keyPressed) {
        if (keyPressed == null || keyPressed.isEmpty()) {
            System.out.println("No input detected.");
            return;
        }

        // Process the keyPressed input
        switch (keyPressed.toUpperCase()) { // Ensure case-insensitivity
            case "W":
                System.out.println("Up");
                playerXYZ[0] += 1;
                break;
            case "A":
                System.out.println("Left");
                playerXYZ[1] += 1;
                break;
            case "S":
                System.out.println("Down");
                playerXYZ[2] += 1;
                break;
            case "D":
                System.out.println("Right");
                playerXYZ[3] += 1;
                break;
            default: // Handle invalid input
                System.out.println("Invalid input: " + keyPressed);
                break;
        }
    }

    // Method to get the player's current position
    public int[] getPlayerPosition() {
        return playerXYZ;
    }
}