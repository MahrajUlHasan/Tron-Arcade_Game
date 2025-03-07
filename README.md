# Tron-Arcade_Game

## Overview
Tron-Arcade_Game is a Java-based implementation of the classic Tron light cycle game. The game features two players who control light cycles that leave a trail behind them. The objective is to avoid colliding with the trails and the walls while trying to force the opponent to crash.

## Features
- Two-player gameplay with customizable player names and colors.
- Real-time collision detection and game state management.
- Leaderboard to track high scores.
- Graphical user interface using Java Swing.
- Database integration for storing high scores.

## Requirements
- Java 21 or higher
- MySQL database

## Setup
1. Clone the repository:
    ```sh
    git clone <repository-url>
    ```
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA, NetBeans).
3. Set up the MySQL database:
    - Create a database named `highscores`.
    - Create a table named `HIGHSCORES` with columns `TIMESTAMP`, `NAME`, and `SCORE`.
4. Update the database connection properties in `Leaderboard.java`:
    ```java
    Properties connectionProps = new Properties();
    connectionProps.put("user", "root");
    connectionProps.put("password", "");
    connectionProps.put("serverTimezone", "UTC");
    ```
5. Build and run the project.

## Usage
- Launch the game by running the `Assignment_3` class.
- Enter player names and select colors in the main menu.
- Click "Start Game" to begin playing.
- Use arrow keys for Player 1 and WASD keys for Player 2 to control the light cycles.
- View the leaderboard by clicking the "Leaderboards" button in the main menu.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.