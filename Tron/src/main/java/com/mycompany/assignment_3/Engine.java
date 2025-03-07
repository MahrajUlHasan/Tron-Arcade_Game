/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author MJ HASAN
 */

public class Engine extends JPanel {

    private final int FPS = 100;
    private final int player_X = 375;
    private final int player_1_Y = 0;
    private final int player_2_Y = 749;
    private final int TRON_WIDTH = 51;
    private final int TRRON_HEIGHT = 51;
//    private final int TRON_MOVEMENT = 2;
    private final int buffer = 26;

    
    private Tron player1;
    private String player1Name;
    private Color player1_color;
    private Image player1_img;
    boolean player1IsAlive = true;
    private int p1Buffer = 0;

    private Tron player2;
    private String player2Name;
    private Color player2_color;
    private Image player2_img;
    boolean player2IsAlive = true;
    private int p2Buffer = 0;

    private boolean paused = false;
    private Image background;

    private Timer newFrameTimer;
    ArrayList<Sprite> sprites;

    private String winner;
    private boolean isDraw = false;
    private boolean isOver = false;
    private int winnerScore;

   
    public boolean IsDraw() {
        return isDraw;
    }

    public boolean IsOver() {
        return isOver;
    }

    public String getWinner() {
        return winner;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public Engine(Color c1, Color c2, String n1, String n2) {
        super();
        this.player1Name = n1;
        this.player2Name = n2;
        player2_color = c2;
        player1_color = c1;
        sprites = new ArrayList<>();
        player1_color = c1;
        player2_color = c2;
        player1_img = getImage(c1);
        player2_img = getImage(c2);
        this.player1 = new Tron(player_X, player_1_Y, TRON_WIDTH, TRRON_HEIGHT, player1_img, 2);
        this.player2 = new Tron(player_X, player_2_Y, TRON_WIDTH, TRRON_HEIGHT, player2_img, 0);
//        sprites.add(player1);
//        sprites.add(player2);

        background = new ImageIcon("data/tron_img/background.jpg").getImage();
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player1.getDirection() != 1 && p1Buffer == 0) {
                    player1.setDirection(3);
                    p1Buffer = buffer;
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player1.getDirection() != 3 && p1Buffer == 0) {
                    player1.setDirection(1);
                    p1Buffer = buffer;
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player1.getDirection() != 0 && p1Buffer == 0) {
                    player1.setDirection(2);
                    p1Buffer = buffer;
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player1.getDirection() != 2 && p1Buffer == 0) {
                    player1.setDirection(0);
                    p1Buffer = buffer;
                }
            }
        });

//        2ndplayer 
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player2.getDirection() != 1 && p2Buffer == 0) {
                    player2.setDirection(3);
                    p2Buffer = buffer;
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player2.getDirection() != 3 && p2Buffer == 0) {
                    player2.setDirection(1);
                    p2Buffer = buffer;
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player2.getDirection() != 0 && p2Buffer == 0) {
                    player2.setDirection(2);
                    p2Buffer = buffer;
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player2.getDirection() != 2 && p2Buffer == 0) {
                    player2.setDirection(0);
                    p2Buffer = buffer;
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });

        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    }
    
    private Image getImage(Color c)
    {
        if (Color.RED.equals(c)) {
            return new ImageIcon("data/tron_img/TronRED.png").getImage();
        } else if (Color.BLUE.equals(c)) {
            return new ImageIcon("data/tron_img/TronBLUE.png").getImage();
        } else if (Color.GREEN.equals(c)) {
            return new ImageIcon("data/tron_img/TronGREEN.png").getImage();
        }else if (Color.YELLOW.equals(c)) {
            return new ImageIcon("data/tron_img/TronYELLOW.png").getImage();
        }else {
            return null;
        }
    }

    public void newTrail(Tron t, Color co) {
        int a = t.getX() + t.getHeight() / 2;
        int b = t.getY() + t.getWidth() / 2;
        int d = a;
        int c = b;
        sprites.add(new Trail(a, b, d, c, co));

    }

    public boolean collides(Sprite target) {
        boolean res = false;
        for (Sprite s : sprites) {

            if (s == target) {
                continue;
            }

            if (target.collision(s)) {
                return true;
            }
        }
//        res = res && player1 == target ? target.collision(player2) : target.collision(player1);
        return res;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 800, 800, null);
        for (Sprite s : sprites) {
            s.draw(grphcs);
        }
        player1.draw(grphcs);
        player2.draw(grphcs);

    }

    String winner() {
        if (!player1IsAlive && !player2IsAlive) {
            isDraw = true;
            return ""; 
        } else if (!player1IsAlive) {
            return player2Name;
        } else if (!player2IsAlive) {
            return player1Name;
        } else if (!player1.validPosition() && !player2.validPosition()) {
            isDraw = true; 
            return "";
        } else if (!player2.validPosition()) {
            return player1Name;
        }else if (!player1.validPosition()) {
            return player2Name;
        }
        else {
            return "";
        }
    }

    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                if (p1Buffer > 0) {
                    p1Buffer--;
                }
                if (p2Buffer > 0) {
                    p2Buffer--;
                }

                player1.autoMove();
                player2.autoMove();
                player1IsAlive= !collides(player1) ;
                player2IsAlive  = !collides(player2) ;
                
                newTrail(player1, player1_color);
                newTrail(player2, player2_color);

                if (!player1IsAlive || !player2IsAlive || !player2.validPosition() || !player1.validPosition()) { 
                    isOver = true;
                    paused = true;
                    winner = winner();
                    newFrameTimer.stop();
                }
                repaint();
            }
        }
    }

}
