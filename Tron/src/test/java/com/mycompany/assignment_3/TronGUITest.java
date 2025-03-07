package com.mycompany.assignment_3;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TronGUITest {


    
    public TronGUITest() {
    }


    @Test
    void testWinnerDraw() {
        Engine engine = new Engine(Color.RED, Color.BLUE, "Player 1", "Player 2");
        engine.player1IsAlive = false;
        engine.player2IsAlive = false;
        assertEquals("", engine.winner());
        assertTrue(engine.IsDraw());

    }
        @Test
    void testWinnerP2Wins() {
        Engine engine = new Engine(Color.RED, Color.BLUE, "Player 1", "Player 2");
        engine.player1IsAlive = false;
        engine.player2IsAlive = true;
        assertEquals("Player 2", engine.winner());
        assertFalse(engine.IsDraw());

    }
    @Test
    void testWinnerP1Wins() {
        Engine engine = new Engine(Color.RED, Color.BLUE, "Player 1", "Player 2");
        engine.player1IsAlive = true;
        engine.player2IsAlive = false;
        assertEquals("Player 1", engine.winner());
        assertFalse(engine.IsDraw());

    }


    @Test
    void testCollision() { 
        Engine engine = new Engine(Color.RED, Color.BLUE, "Player 1", "Player 2");
        Trail trail = new Trail(10, 10, 20, 20, Color.RED);
        engine.sprites.add(trail);
        Tron tron = new Tron(10, 10, 10, 10, null, 0);
        assertTrue(engine.collides(tron)); 
    }

    @Test
    void testNoCollision() {   
        Engine engine = new Engine(Color.RED, Color.BLUE, "Player 1", "Player 2");
        Trail trail = new Trail(10, 10, 20, 20, Color.RED);
        engine.sprites.add(trail);
        Tron tron = new Tron(50, 50, 10, 10, null, 0); 
        assertFalse(engine.collides(tron));
    }
    


    @Test
    void testPutHighScoreNewScore() throws SQLException, ClassNotFoundException {  
        Leaderboard leaderboard = new Leaderboard(3);
        leaderboard.clearLeaderboard();
        leaderboard.putHighScore("Alice", 100);
        ArrayList<HighScore> highScores = leaderboard.getHighScores();
        assertEquals(1, highScores.size());
        assertEquals("Alice", highScores.get(0).getName());
        assertEquals(100, highScores.get(0).getScore());
    }



    @Test
    void testPutHighScoreUpdateScore() throws SQLException, ClassNotFoundException {
        Leaderboard leaderboard = new Leaderboard(3);
        leaderboard.clearLeaderboard();
        leaderboard.putHighScore("Bob", 50);
        leaderboard.putHighScore("Bob", 150); 
        ArrayList<HighScore> highScores = leaderboard.getHighScores();
        assertEquals(1, highScores.size());
        assertEquals("Bob", highScores.get(0).getName());
        assertEquals(150, highScores.get(0).getScore());

        leaderboard.putHighScore("Bob", 100); 
        highScores = leaderboard.getHighScores();
        assertEquals(150, highScores.get(0).getScore());
    }


    @Test
    void testLeaderboardTrimming() throws SQLException, ClassNotFoundException { 
        Leaderboard leaderboard = new Leaderboard(2);  
        leaderboard.clearLeaderboard();
        leaderboard.putHighScore("Alice", 150);
        leaderboard.putHighScore("Bob", 100);
        leaderboard.putHighScore("Charlie", 200); 

        ArrayList<HighScore> highScores = leaderboard.getHighScores();
        assertEquals(2, highScores.size());
        assertTrue(highScores.stream().anyMatch(hs -> hs.getName().equals("Alice")));
        assertTrue(highScores.stream().anyMatch(hs -> hs.getName().equals("Charlie")));
        assertFalse(highScores.stream().anyMatch(hs -> hs.getName().equals("Bob"))); 
    }


   
    @Test
    void testValidPosition() {
        Tron tron = new Tron(10, 10, 10, 10, null, 0);
        assertTrue(tron.validPosition());

        tron.setX(-1);  
        assertFalse(tron.validPosition());

        tron.setX(10);
        tron.setY(-1);
        assertFalse(tron.validPosition());

        tron.setY(10);
        tron.setX(795); 
        tron.setWidth(10); 
        assertFalse(tron.validPosition());


        tron.setX(795);
        tron.setWidth(50);
        assertFalse(tron.validPosition());


        tron.setWidth(51); 
        tron.setX(0);
        assertTrue(tron.validPosition());
    }
}
