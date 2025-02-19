package us.enbyvampy.TestSnakeGame.core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
    // Game Grid Constants
    private static final Color SNAKE_COLOR = Color.WHITE;
    private static final Color FOOD_COLOR = Color.GREEN;
    private static final Color HAZARD_COLOR = Color.RED;
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    int TILE_SIZE = 20;      // Size of each grid tile in pixels
    int GRID_WIDTH = 40;    // Number of horizontal tiles
    int GRID_HEIGHT = 30;   // Number of vertical tiles


    int minMoveDelay = 10; // Minimum move delay in ms to avoid excessive speed
    int initialMoveDelay = 200; // Initial movement delay
    int currentMoveDelay;            // Current movement delay (modifiable)
    int score;
    int highScore;

    // Game State
    boolean running = true;       // Is the game currently running?
    boolean gameOver = false;     // Has the player lost the game?
    boolean paused = false;

    // Player (Snake)
    private List<Point> snake;            // List of points representing snake's body
    private List<Point> hazards = new ArrayList<>();  // List to hold positions of hazard blocks
    private Point direction;              // Current movement direction (x, y)

    // Food
    private Point food;                   // Position of the food

    // Snake Movement Speed
    private int snakeMoveDelay = 300; // Milliseconds between snake moves (200 ms = 5 moves per second)
    private long lastMoveTime;            // Tracks the last time the snake moved

    private enum GameState {
        MENU,
        PAUSED,
        PLAYING,
        GAME_OVER
    }
    private GameState gameState = GameState.MENU;

    public SnakeGame() {
        // Initialize the game
        currentMoveDelay = initialMoveDelay;
        highScore = 0;
        resetGame();
    }

    public void togglePause() {
    paused = !paused;
    running = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public void restartGame() {
        resetGame(); // Reset the game to its initial state
    }


    // Reset the game to the initial state
    private void resetGame() {
        running = true;
        gameOver = false;
        paused = false;

        // Initialize the hazards
        hazards.clear();


        // Initialize the snake in the center of the grid
        snake = new ArrayList<>();
        snake.add(new Point(GRID_WIDTH / 2, GRID_HEIGHT / 2));              // Snake head
        snake.add(new Point(GRID_WIDTH / 2 - 1, GRID_HEIGHT / 2));         // Snake body

        // Set the initial direction (moving right)
        direction = new Point(1, 0);

        // Generate the first food position
        spawnFood();

        // Reset the current move delay to the initial value
        currentMoveDelay = initialMoveDelay;
        // Reset the score
        score = 0;
        // Reset the last move time
        lastMoveTime = System.currentTimeMillis();
    }


    // Spawn a new food item
    private void spawnFood() {
        int x = (int) (Math.random() * GRID_WIDTH);
        int y = (int) (Math.random() * GRID_HEIGHT);
        food = new Point(x, y);
    }

    private void spawnHazards(int numHazards) {
        while (hazards.size() < numHazards) {
            Point potentialHazard = new Point(
                    (int) (Math.random() * GRID_WIDTH),
                    (int) (Math.random() * GRID_HEIGHT)
            );

            // Ensure hazards don't overlap with the snake or food
            if (!snake.contains(potentialHazard) && !potentialHazard.equals(food) && !hazards.contains(potentialHazard)) {
                hazards.add(potentialHazard);
            }
        }
    }

    // Update game logic
    public void update() {
        if (!running || gameOver) return; // Pause or stop updates if not running

        // Check if it's time to move the snake based on the delay
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= snakeMoveDelay) {
            moveSnake(); // Move the snake when the delay has passed
            lastMoveTime = currentTime; // Update the last move time
        }
        // Other game logic (if necessary) can go here
    }

    // Move the snake based on the current direction

    private void moveSnake() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x + direction.x, head.y + direction.y);

        snake.add(0, newHead);

        if (newHead.equals(food)) {
            spawnFood();

            // Update score
            score += 10;

            // Check if score is the new high score
            if (score > highScore) {
                highScore = score;
            }

            // Increase difficulty
            if (currentMoveDelay > minMoveDelay) {
                currentMoveDelay -= 10; // Decrease delay by 10 ms per food
            }

            // Spawn more hazards every 50 points
            if (score % 50 == 0) {
                spawnHazards(score / 50); // Increment hazards by 1 every 50 points
            }
        } else {
            snake.remove(snake.size() - 1);
        }

        checkCollisions();
    }

    // Check for collisions (with walls or the snake itself)
    private void checkCollisions() {
        Point head = snake.get(0);

        // Check collision with walls (grid boundaries)
        if (head.x < 0 || head.x >= GRID_WIDTH || head.y < 0 || head.y >= GRID_HEIGHT) {
            gameOver = true;
        }

        // Check collision with the snake's body
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
            }
        }

        // Check collision with hazard blocks
        for (Point hazard : hazards) {
            if (head.equals(hazard)) {
                gameOver = true;
            }
        }
    }


    // Change the snake's direction (e.g., from input)
    public void changeDirection(Point newDirection) {
        // Prevent reversing direction directly
        if (direction.x + newDirection.x == 0 && direction.y + newDirection.y == 0) return;

        direction = newDirection;
    }

    // Render the game
    public void paintComponent(Graphics g) {


        // Draw the background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);

        g.setColor(FOOD_COLOR);
        g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        g.setColor(SNAKE_COLOR);
        for (Point part : snake) {
            g.fillRect(part.x * TILE_SIZE, part.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        g.setColor(HAZARD_COLOR);
        for (Point hazard : hazards) {
            g.fillRect(hazard.x * TILE_SIZE, hazard.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }



        // Display the current score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);  // Current score at the top-left corner

        // Display the current high score
        g.drawString("High Score: " + highScore, 10, 40); // High score below the current score

        // Display "Game Over" message if the game is over
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", GRID_WIDTH * TILE_SIZE / 2 - 100, GRID_HEIGHT * TILE_SIZE / 2 - 20);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", GRID_WIDTH * TILE_SIZE / 2 - 90, GRID_HEIGHT * TILE_SIZE / 2 + 20);

            // Display the final score on the Game Over screen
            g.drawString("Final Score: " + score, GRID_WIDTH * TILE_SIZE / 2 - 80, GRID_HEIGHT * TILE_SIZE / 2 + 50);
        }

        // Display "Paused" if the game is paused
        if (paused) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSED", GRID_WIDTH * TILE_SIZE / 2 - 80, GRID_HEIGHT * TILE_SIZE / 2);
        }


    }
}