/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_3;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class TronGUI {

    private JFrame frame;
    private Engine game;
    private JPanel menuPanel;
    private JTextField player1NameField;
    private JComboBox<String> player1ColorCombo;
    private JTextField player2NameField;
    private JComboBox<String> player2ColorCombo;
    private String name1;
    private String name2;
    private Timer timer;
    private Color p1Color;
    private Color p2Color;
    private JFrame leaderboardFrame;
    private JTable leaderboardTable;
    private Leaderboard leaderboard;
    private int p1Score;
    private int p2Score;

    public TronGUI() {
        frame = new JFrame("Tron");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu();
        try {
            this.leaderboard = new Leaderboard(10);
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
            return;
        }
    }

    private void mainMenu() {
        if (game != null) {
            frame.remove(game);
        }
        menuPanel = new JPanel(new GridLayout(0, 2));
        menuPanel.setPreferredSize(new Dimension(400, 200));
        player1NameField = new JTextField();
        player1ColorCombo = createColorComboBox();
        player2NameField = new JTextField();
        player2ColorCombo = createColorComboBox();

        menuPanel.add(new JLabel("Player 1 Name:"));
        menuPanel.add(player1NameField);
        menuPanel.add(new JLabel("Player 1 Color:"));
        menuPanel.add(player1ColorCombo);
        menuPanel.add(new JLabel("Player 2 Name:"));
        menuPanel.add(player2NameField);
        menuPanel.add(new JLabel("Player 2 Color:"));
        menuPanel.add(player2ColorCombo);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        JButton leaderBoardButton = new JButton("Leaderboards");
        leaderBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLeaderboard();
            }
        });
        menuPanel.add(startButton);
        menuPanel.add(leaderBoardButton);
        menuPanel.setSize(new Dimension(400, 200));

        frame.add(menuPanel);
//        frame.setLocationRelativeTo(null);
//        menuPanel.requestFocusInWindow();
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JComboBox<String> createColorComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("RED");
        comboBox.addItem("BLUE");
        comboBox.addItem("GREEN");
        comboBox.addItem("YELLOW");
        return comboBox;
    }

    private Color getColor(String c) {
        switch (c) {
            case "RED":
                return Color.RED;
            case "BLUE":
                return Color.BLUE;
            case "GREEN":
                return Color.GREEN;
            case "YELLOW":
                return Color.YELLOW;
            default:
                return null;
        }
    }

    private void startGame() {
        String player1Name = player1NameField.getText();
        String player2Name = player2NameField.getText();
        name1 = player1Name;
        name2 = player2Name;
        this.p1Score = 0;
        this.p2Score = 0;

        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Both players must enter their names.");
            return;
        }

        Color p1Color = getColor((String) player1ColorCombo.getSelectedItem());
        Color p2Color = getColor((String) player2ColorCombo.getSelectedItem());
        this.p1Color = p1Color;
        this.p2Color = p2Color;

        if (p1Color.equals(p2Color)) {
            JOptionPane.showMessageDialog(frame, "Players cannot have the same color, please select another one.");
            return;
        }
        if (p1Color == null) {
            p1Color = Color.RED;
            this.p1Color = p1Color;
        }
        if (p2Color == null) {
            p2Color = Color.BLUE;
            this.p2Color = p2Color;
        }

        frame.remove(menuPanel);
        game = new Engine(p1Color, p2Color, player1Name, player2Name);
        frame.getContentPane().add(game);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setResizable(true);
        frame.pack();
        game.requestFocusInWindow();
        frame.repaint();
        startTimer();

    }

    private void startTimer() {
        timer = new Timer(1000 / 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.IsDraw() || game.IsOver()) {
                    p1Score = game.IsDraw() ? p1Score : (game.getWinner().equals(name1) ? p1Score + 1 : p1Score);
                    p2Score = game.IsDraw() ? p2Score : (game.getWinner().equals(name2) ? p2Score + 1 : p2Score);
                    try {
                        leaderboard.putHighScore(name1, p1Score);
                        leaderboard.putHighScore(name2, p2Score);
                    } catch (SQLException ea) {
                        JOptionPane.showMessageDialog(frame, ea.getMessage());
                        return;
                    }
                    timer.stop();
                    gameFinishedPopUp();
                }
            }
        });
        timer.start();
    }

    private void restart() {
        frame.remove(menuPanel);
        frame.remove(game);
        game = new Engine(p1Color, p2Color, name1, name2);
        frame.getContentPane().add(game);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setResizable(true);
        frame.pack();
        game.requestFocusInWindow();
        frame.repaint();
        startTimer();

    }

    private void showLeaderboard() {
        if (leaderboardFrame == null) leaderboardFrame = new JFrame("Leaderboards");

        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Rank", "Name", "Score"}, 0);
        leaderboardTable = new JTable(tableModel);

        try {
            ArrayList<HighScore> topTen = this.leaderboard.getHighScores();
            for (int i = 0; i < topTen.size(); i++) {
                tableModel.addRow(new Object[]{i + 1, topTen.get(i).getName(), topTen.get(i).getScore()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error accessing database: " + e.getMessage());
            return;
        }
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        leaderboardFrame.add(scrollPane);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            leaderboardFrame.dispose();
            frame.setVisible(true);

        });
        leaderboardFrame.add(backButton, BorderLayout.SOUTH);

        leaderboardFrame.pack();
//            leaderboardFrame.setLocationRelativeTo(null); 

        leaderboardFrame.setVisible(true);

        frame.setVisible(false);
    }

    protected void gameFinishedPopUp() {
        JButton exit = new JButton();
        exit.setText("Back to game");
        JButton menu = new JButton();
        menu.setText("Back to Menu");
        JPanel panel = new JPanel();
        String message = game.IsDraw() ? "IT'S A DRAW !!" : "The Winner is Player " + game.getWinner() + " !!";
        JLabel msg = new JLabel(message);

        JDialog dialog = new JDialog(frame, "popup", true);
        dialog.setLocationRelativeTo(frame);
        dialog.setSize(new Dimension(200, 200));
        panel.setPreferredSize(new Dimension(300, 300));
        exit.addActionListener(e -> {
            dialog.dispose();
            this.restart();
        });
        menu.addActionListener(ea -> {
            dialog.dispose();
            this.mainMenu();
        });
        panel.add(msg);
        panel.add(exit);
        panel.add(menu);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        dialog.pack();
    }

}
