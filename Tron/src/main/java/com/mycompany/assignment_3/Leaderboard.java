package com.mycompany.assignment_3;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class Leaderboard {

    int maxScores;
    PreparedStatement insertStatement;
    PreparedStatement selectStatement;
    PreparedStatement updateStatement;
    Connection connection;

    public Leaderboard(int maxScores) throws SQLException, ClassNotFoundException {
        this.maxScores = maxScores;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "");
        connectionProps.put("serverTimezone", "UTC");

        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/highscores";
        connection = DriverManager.getConnection(dbURL, connectionProps);
        String insertQuery = "INSERT INTO HIGHSCORES (TIMESTAMP, NAME, SCORE) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);

        String selectQuery = "SELECT SCORE FROM HIGHSCORES WHERE NAME = ?";
        selectStatement = connection.prepareStatement(selectQuery);

        String updateQuery = "UPDATE HIGHSCORES SET SCORE = ?, TIMESTAMP = ? WHERE NAME = ?";
        updateStatement = connection.prepareStatement(updateQuery);
    }

    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES ORDER BY SCORE DESC";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }

    public void putHighScore(String name, int score) throws SQLException {
        selectStatement.setString(1, name);
        ResultSet rs = selectStatement.executeQuery();

        if (rs.next()) { 
            int currentScore = rs.getInt("SCORE");
            if (score > currentScore) {
                updateScore(name, score);
            }
        } else {
            insertScore(name, score);

            // Trim leaderboard if it exceeds maxScores
            ArrayList<HighScore> highScores = getHighScores();
            if (highScores.size() > maxScores) {
                HighScore lowestScore = highScores.get(highScores.size() - 1);
                deleteScores(lowestScore.getName());  // Corrected this to delete by name
            }
        }

    }


    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, (t, t1) -> t1.getScore() - t.getScore());
    }

    private void updateScore(String name, int score) throws SQLException {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        updateStatement.setInt(1, score);
        updateStatement.setTimestamp(2, ts);
        updateStatement.setString(3, name);
        updateStatement.executeUpdate();
    }

    private void insertScore(String name, int score) throws SQLException {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        insertStatement.setTimestamp(1, ts);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, score);
        insertStatement.executeUpdate();
    }

    private void deleteScores(String name) throws SQLException {   
        String deleteQuery = "DELETE FROM HIGHSCORES WHERE NAME = ?"; 
        try(PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
             deleteStatement.setString(1, name);
             deleteStatement.executeUpdate();
        }

    }
    
     public void clearLeaderboard() throws SQLException {
        String clearQuery = "DELETE FROM HIGHSCORES";  
        try(PreparedStatement clearStatement = connection.prepareStatement(clearQuery)){ 
            clearStatement.executeUpdate(clearQuery);
        }
    }
}
