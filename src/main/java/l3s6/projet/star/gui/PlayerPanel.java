package l3s6.projet.star.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;

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

        this.playerInfo = new JPanel();
        this.playerInfo.add(new JLabel(player.getID()));
        this.playerInfo.add(new JLabel(Integer.toString(player.getScore())));
        this.add(this.playerInfo);
    }

}