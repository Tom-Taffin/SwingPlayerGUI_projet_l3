package l3s6.projet.star.gui;

import javax.swing.*;

import l3s6.projet.star.game.board.Coordinates;

import java.awt.*;
import java.io.IOException;

public class PlayerGUI extends JFrame {

    private BoardPanel boardPanel;

    public PlayerGUI() throws IOException, ImageNotFoundException {
        super("Player GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        this.boardPanel = new BoardPanel();
        mainPanel.add(this.boardPanel, BorderLayout.WEST);
        setContentPane(mainPanel);

        setSize(new Dimension(800, 600));;
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                TileManager.getInstance().loadResources("base_game_C3.txt");
                PlayerGUI playerGUI = new PlayerGUI();

                Timer timer = new Timer(2000, null);
                timer.addActionListener(new java.awt.event.ActionListener() {
                    private int step = 0;

                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        try {
                            if (step == 0) {
                                playerGUI.boardPanel.addTile("Wc1-f1-f1-c2", new Coordinates(0, 0));
                                step++;
                            } else if (step == 1) {
                                playerGUI.boardPanel.addTile("Wc1-f1-f1-c2", new Coordinates(0, 1));
                                step++;
                            } else if (step == 2) {
                                playerGUI.boardPanel.addTile("Wc1-f1-f1-c2", new Coordinates(1, 1));
                                step++;
                            } else if (step == 3) {
                                playerGUI.boardPanel.addTile("Wc1-f1-f1-c2", new Coordinates(1, 2));
                                step++;
                            } else if (step == 4) {
                                playerGUI.boardPanel.addTile("Wc1-f1-f1-c2", new Coordinates(1, 3));
                                step++;
                            } else if (step == 5) {
                                playerGUI.boardPanel.addTile("Wc1-f1-f1-c2", new Coordinates(1, 4));
                                timer.stop();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                timer.start();

            } catch (IOException | ImageNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                System.exit(1);
            }
        });
    }
}