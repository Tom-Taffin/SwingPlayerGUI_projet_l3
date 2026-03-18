package l3s6.projet.star.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.gui.board.ImageNotFoundException;
import l3s6.projet.star.gui.board.MeepleImagePanel;

public class PlayerPanel extends JPanel {

    private Player player;
    private MeepleImagePanel meepleImage;
    private JPanel playerInfo;

    public PlayerPanel(Player player, String color) {
        this.player = player;
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

        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            new EmptyBorder(10, 10, 10, 10)
        ));
    }

}