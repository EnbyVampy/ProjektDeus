package us.projektdeus.currentlyunused;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameTime {

    // Singleton instance of the class
    private static GameTime instance;

    // Formatter for HH:mm:ss
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Variables for tracking session
    private final LocalTime sessionStartTime;
    private LocalTime currentTime;

    private GameTime() {
        // Initialize session start time when the class is instantiated
        sessionStartTime = LocalTime.now();
    }

    // Ensures only one instance of the class exists, and other classes can access it
    public static synchronized GameTime getInstance() {
        if (instance == null) {
            instance = new GameTime();
        }
        return instance;
    }

    // Get the current time in HH:mm:ss format
    public String getCurrentTime() {
        currentTime = LocalTime.now();
        return currentTime.format(TIME_FORMAT);
    }

    // Get elapsed session play time in HH:mm:ss format
    public String getElapsedSessionTime() {
        currentTime = LocalTime.now();
        Duration elapsedDuration = Duration.between(sessionStartTime, currentTime);
        long hours = elapsedDuration.toHours();
        long minutes = elapsedDuration.toMinutes() % 60;
        long seconds = elapsedDuration.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
