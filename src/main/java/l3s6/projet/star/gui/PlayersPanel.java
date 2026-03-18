package l3s6.projet.star.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.*;

import l3s6.projet.star.game.player.Player;

public class PlayersPanel extends JPanel {

    private PlayerController playerController;

    public PlayersPanel(PlayerController playerController) {
        this.playerController = playerController;
        this.setBackground(Color.WHITE);
    }

    public void updatePanel(Map<String, Player> colorsToPlayers){
        this.removeAll();
        this.setLayout(new GridLayout(colorsToPlayers.size(), 1));
        colorsToPlayers.forEach((color, player) -> {
            this.add(new PlayerPanel(player, color));
        });
        this.revalidate();
        this.repaint();
    }


}