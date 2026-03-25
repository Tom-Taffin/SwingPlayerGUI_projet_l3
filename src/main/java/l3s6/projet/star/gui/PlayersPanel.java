package l3s6.projet.star.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import l3s6.projet.star.game.player.Player;

public class PlayersPanel extends JPanel {

    private PlayerController playerController;
    private Map<String, Player> colorsToPlayers;
    private List<PlayerPanel> playerPanels;
    private String currentPlayerId;
    private List<String> expelledPlayers;
    private List<String> winners;

    public PlayersPanel(PlayerController playerController) {
        this.playerController = playerController;
        this.expelledPlayers = new ArrayList<>();
        this.winners = new ArrayList<>();
        this.setBackground(Color.WHITE);
    }

    public void updatePanel(Map<String, Player> colorsToPlayers){
        this.colorsToPlayers = colorsToPlayers;
        this.updatePanel();
    }

    public void updatePanel(){
        this.removeAll();
        this.setLayout(new GridLayout(this.colorsToPlayers.size(), 1));
        this.playerPanels = new ArrayList<>();
        this.colorsToPlayers.forEach((color, player) -> {
            PlayerPanel panel = new PlayerPanel(player, color);
            if (player.getID().equals(this.currentPlayerId)) {
                panel.setCurrent(true);
            }
            if (this.expelledPlayers.contains(player.getID())) {
                panel.setExpelled(true);
            }
            if (this.winners.contains(player.getID())) {
                panel.setWinner(true);
            }
            this.playerPanels.add(panel);
            this.add(panel);
        });
        this.revalidate();
        this.repaint();
    }

    public void setCurrent(String id){
        this.currentPlayerId = id;
        this.updatePanel();
    }

    public void expelPlayer(String id){
        this.expelledPlayers.add(id);
        this.updatePanel();
    }

    public void setWinners(List<String> ids){
        this.winners = ids;
        this.updatePanel();
    }

}