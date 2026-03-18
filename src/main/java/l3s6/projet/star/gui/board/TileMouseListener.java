package l3s6.projet.star.gui.board;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TileMouseListener implements MouseListener {

    protected TilePanel tilePanel;

    public TileMouseListener(TilePanel tilePanel){
        this.tilePanel = tilePanel;
    }

    public void mousePressed(MouseEvent e) {
        tilePanel.clicked();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        tilePanel.setHovered(true);
    }

    public void mouseExited(MouseEvent e) {
        tilePanel.setHovered(false);
    }

    public void mouseClicked(MouseEvent e) {
    }

}