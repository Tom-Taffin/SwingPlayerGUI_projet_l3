package l3s6.projet.star.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.*;

import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.gui.board.ImageNotFoundException;
import l3s6.projet.star.gui.board.MeepleImagePanel;

public class PlayerPanel extends JPanel {

    private MeepleImagePanel meepleImage;
    private JPanel playerInfo;
    private boolean currentPlayer = false;
    private boolean isExpelled = false;

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
    public void paint(Graphics g){
        super.paint(g);

        if (this.isExpelled){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawLine(0, 0, w, h);
            g2d.drawLine(w, 0, 0, h);
        }
        
    }

    public void setCurrent(boolean b){
        this.currentPlayer = b;
        Color c = b ? Color.LIGHT_GRAY : Color.WHITE;
        this.setBackground(c);
        this.playerInfo.setBackground(c);
        this.revalidate();
        this.repaint();
    }

    public void setExpelled(boolean b){
        this.isExpelled = b;
        this.revalidate();
        this.repaint();
    }

}