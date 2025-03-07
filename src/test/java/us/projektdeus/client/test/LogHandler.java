package us.projektdeus.client.test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public class LogHandler {
    // Track logging levels per class
    private static final Map<Class<?>, Integer> classLoggingLevels = new HashMap<>();

    // Static initialization block to configure default Formatter
    static {
        // Create a root ConsoleHandler with the custom formatter
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                // Extract the simple class name from the full class name
                String className = record.getSourceClassName();
                String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

                // Extract the method name
                String methodName = record.getSourceMethodName();

                // Format log message
                return String.format("[%s - %s] %s: %s%n",
                        simpleClassName, // Class name
                        methodName != null ? methodName : "UNKNOWN", // Method name
                        record.getLevel(), // Log level
                        record.getMessage() // Log message
                );
            }
        });

        // Configure the root logger
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setUseParentHandlers(false); // Disable default handlers
        rootLogger.addHandler(consoleHandler);  // Add our custom console handler
    }

    /**
     * Get or create a logger for a specific class.
     */
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());

        // Prevent propagation to parent handlers
        logger.setUseParentHandlers(false);

        // Add a ConsoleHandler with the same custom formatter
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                // Extract the simple class name and method name
                String className = record.getSourceClassName();
                String simpleClassName = className.substring(className.lastIndexOf(".") + 1);
                String methodName = record.getSourceMethodName();

                // Format the log message
                return String.format("[%s - %s] %s: %s%n",
                        simpleClassName, // Class name
                        methodName != null ? methodName : "UNKNOWN", // Method name
                        record.getLevel(), // Log level
                        record.getMessage() // Log message
                );
            }
        });

        // Attach the ConsoleHandler to the logger if no handlers exist
        if (logger.getHandlers().length == 0) {
            logger.addHandler(consoleHandler);
        }

        // Adjust log level for this logger
        adjustLogLevel(logger, clazz);
        return logger;
    }


    /**
     * Set the logging level for a specific class.
     */
    public static void setLoggingLevel(Class<?> clazz, int level) {
        classLoggingLevels.put(clazz, level);

        // Adjust log level for the logger immediately
        Logger logger = Logger.getLogger(clazz.getName());
        adjustLogLevel(logger, clazz);
    }

    /**
     * Adjust the logging level for a class based on its configured level.
     */
    private static void adjustLogLevel(Logger logger, Class<?> clazz) {
        int level = classLoggingLevels.getOrDefault(clazz, 3); // Default level: INFO
        switch (level) {
            case 0: // Disable logging
                logger.setLevel(Level.OFF);
                break;
            case 1: // SEVERE only
                logger.setLevel(Level.SEVERE);
                break;
            case 2: // WARNING and above
                logger.setLevel(Level.WARNING);
                break;
            case 3: // INFO and above
                logger.setLevel(Level.INFO);
                break;
            case 4: // Log ALL (including fine details)
                logger.setLevel(Level.ALL);
                break;
            default:
                logger.setLevel(Level.INFO); // Default is INFO level
        }
    }
}