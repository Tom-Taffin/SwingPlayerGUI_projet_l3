package l3s6.projet.star.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.*;

import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.gui.board.ImageNotFoundException;
import l3s6.projet.star.gui.board.MeepleImagePanel;

public class PlayerPanel extends JPanel {

    private MeepleImagePanel meepleImage;
    private JPanel playerInfo;
    private boolean currentPlayer = false;

    public PlayerPanel(Player player, String color) {
        this.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(1, 2));

        try {
            this.meepleImage = new MeepleImagePanel("regular", color);
            this.add(this.meepleImage);
        } catch (ImageNotFoundException e) {
            e.printStackTrace();
        }

        this.playerInfo = new JPanel(new GridLayout(4, 1, 0, 2));
        
        JLabel idLabel = new JLabel(player.getID());
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.playerInfo.add(idLabel);

        JLabel scoreLabel = new JLabel("Score : " + player.getScore());
        this.playerInfo.add(scoreLabel);

        JLabel meepleCountLabel = new JLabel("Meeples remaining : " + player.getNbMeeples());
        this.playerInfo.add(meepleCountLabel);

        JLabel blameLabel = new JLabel("Blames : " + player.getNumberOfBlames());
        this.playerInfo.add(blameLabel);

        this.add(this.playerInfo);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        if (this.currentPlayer) {
            this.setBackground(Color.LIGHT_GRAY);
            this.playerInfo.setBackground(Color.LIGHT_GRAY); 
        } else {
            this.setBackground(Color.WHITE);
            this.playerInfo.setBackground(Color.WHITE);
        }
    }

    public void setCurrent(boolean b){
        this.currentPlayer = b;
        this.revalidate();
        this.repaint();
    }

}