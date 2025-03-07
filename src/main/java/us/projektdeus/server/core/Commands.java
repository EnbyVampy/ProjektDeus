package us.projektdeus.server.core;

public class Commands {

    // Process the input as a command
    public static void executeCommand(String command) {
        // Remove the leading "/" and split the command into parts
        String[] parts = command.substring(1).split(" ");
        String commandName = parts[0]; // The command itself (e.g., "help")
        String[] args = new String[parts.length - 1]; // The arguments (if any)

        System.arraycopy(parts, 1, args, 0, args.length); // Copy the arguments

        // Handle different commands
        switch (commandName.toLowerCase()) {
            case "help":
                System.out.println("All commands must be prefixed with a forward slash (/)." +
                        "\nType /commands for a list of available commands.\nType /exit to exit the application");
                break;
            case "commands":
                System.out.println("Available commands:\n/help - Show this help menu\n/exit - Exit the application");

            case "exit":
                System.out.println("Exiting application...");
                System.exit(0);
                break;
            case "server-start":
                System.out.println("Starting server...");
                ClientListener.startListener(); // Call to start the listener thread
                break;
            default:
                System.out.println("Unknown command: " + commandName);
                break;
        }
    }
}