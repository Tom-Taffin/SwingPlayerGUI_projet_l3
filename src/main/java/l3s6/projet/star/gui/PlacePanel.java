package l3s6.projet.star.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import l3s6.projet.star.game.board.Coordinates;

public class PlacePanel extends JPanel {

    public PlacePanel(){
        this.setBackground(Color.WHITE);
    }

    @Override
    public Dimension getPreferredSize() {
        Container parent = getParent();
        if (parent != null) {
            int h = parent.getHeight();
            return new Dimension(parent.getWidth(), h/5);
        }
        return super.getPreferredSize();
    }

    public void displayNotTurn() {
        this.removeAll();
        this.add(new JLabel("It's not your turn"));
        this.revalidate();
        this.repaint();
    }

    public void displayWrongPlacement() {
        this.removeAll();
        this.add(new JLabel("Can't place here"));
        this.revalidate();
        this.repaint();
    }

    public void displayGoodPlacement() {
        this.removeAll();
        this.add(new JLabel("Do you really want to place here ?"));
        this.revalidate();
        this.repaint();;
    }

}
