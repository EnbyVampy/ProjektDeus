package us.projektdeus.currentlyunused.games;

import us.projektdeus.client.test.KeyHandler;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import us.projektdeus.currentlyunused.GameTime;

public class SnakeGame {
    // Game Grid Constants
    private static final Color SNAKE_COLOR = Color.WHITE;
    private static final Color FOOD_COLOR = Color.GREEN;
    private static final Color HAZARD_COLOR = Color.RED;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private KeyHandler keyHandler;

    int TILE_SIZE = 20;      // Size of each grid tile in pixels
    int GRID_WIDTH = 40;    // Number of horizontal tiles
    int GRID_HEIGHT = 30;   // Number of vertical tiles

    int minMoveDelay = 10;        // Minimum move delay in ms to avoid excessive speed
    int initialMoveDelay = 200;  // Initial movement delay
    int currentMoveDelay;        // Current movement delay (modifiable)
    int score;
    int highScore;

    // Game State
    boolean running = true;       // Is the game currently running?
    boolean gameOver = false;     // Has the player lost the game?
    boolean paused = false;

    // Player (Snake)
    private List<Point> snake;                  // List of points representing snake's body
    private List<Point> hazards = new ArrayList<>();  // List to hold positions of hazard blocks
    private Point direction;                    // Current movement direction (x, y)

    // Food
    private Point food;                         // Position of the food

    // Snake Movement Speed
    private int snakeMoveDelay = 300; // Milliseconds between snake moves (200 ms = 5 moves per second)

    // Timer System - replacing time calculations with SessionTimer
    private final GameTime timer = GameTime.getInstance(); // Use Singleton instance of SessionTimer
    private long lastMoveTime; // Tracks the last time the snake moved

    private enum GameState {
        MENU,
        PAUSED,
        PLAYING,
        GAME_OVER
    }
    private GameState gameState = GameState.MENU;

    public SnakeGame() {
        this.lastMoveTime = System.currentTimeMillis(); // Set initial move time
        resetGame();
    }

    public void togglePause() {
        paused = !paused;
        if (paused) {
            System.out.println("Game paused: " + timer.getCurrentTime());
        } else {
            System.out.println("Game resumed: " + timer.getCurrentTime());
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void restartGame() {
        resetGame();
    }

    // Reset the game to the initial state
    private void resetGame() {
        this.score = 0;
        this.currentMoveDelay = initialMoveDelay;
        this.snake = new ArrayList<>();
        this.snake.add(new Point(GRID_WIDTH / 2, GRID_HEIGHT / 2)); // Start snake in the middle of the grid
        spawnFood();
        spawnHazards(3); // Spawn 3 hazards, for example
        this.lastMoveTime = System.currentTimeMillis(); // Reset the move time
        this.gameOver = false;
        this.running = true;
        this.direction = new Point(0, 1); // Set initial movement direction
    }

    // Spawn a new food item
    private void spawnFood() {
        // Add implementation for spawning food
    }

    private void spawnHazards(int numHazards) {
        // Add implementation for spawning hazards
    }

    // Update game logic
    public void update() {
        if (!running || paused || gameOver) {
            return;
        }

        // Check if it's time to move the snake
        long now = System.currentTimeMillis();
        if (now - lastMoveTime > snakeMoveDelay) {
            moveSnake();
            lastMoveTime = now; // Reset movement timer to the current time
        }

        checkCollisions();

        if (!gameOver) {
            System.out.println("Session runtime: " + timer.getElapsedSessionTime());
        }
    }

    // Move the snake based on the current direction
    private void moveSnake() {
        // Update snake's position
        Point head = snake.get(0);
        Point newHead = new Point(head.x + direction.x, head.y + direction.y);
        snake.add(0, newHead);

        // If the snake ate food, increase its size, otherwise remove the tail
        if (newHead.equals(food)) {
            score += 10;
            spawnFood(); // Respawn food
        } else {
            snake.remove(snake.size() - 1); // Remove the tail
        }

        // Print current time debug info using SessionTimer
        System.out.println("Snake moved at: " + timer.getCurrentTime());
    }

    // Check for collisions (with walls or the snake itself)
    private void checkCollisions() {
        Point head = snake.get(0);

        // Check collision with walls
        if (head.x < 0 || head.x >= GRID_WIDTH || head.y < 0 || head.y >= GRID_HEIGHT) {
            gameOver = true;
        }

        // Check collision with itself
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
                break;
            }
        }

        if (gameOver) {
            System.out.println("Game Over! Final score: " + score);
            System.out.println("Game ended at: " + timer.getCurrentTime());
        }
    }

    // Change the snake's direction (e.g., from input)
    public void changeDirection(Point newDirection) {
        // Prevent the player from moving the snake in the opposite direction
        if ((direction.x + newDirection.x != 0 || direction.y + newDirection.y != 0) && !paused) {
            direction = newDirection;
        }
    }

    // Render the game
    public void paintComponent(Graphics g) {
        // Implementation of game rendering (e.g., draw snake, food, hazards, etc.)
    }
}