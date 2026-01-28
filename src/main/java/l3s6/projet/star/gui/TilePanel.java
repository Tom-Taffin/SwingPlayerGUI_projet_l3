package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.*;

public class TilePanel extends JPanel {
   private String tileName;

   public TilePanel(String tileName) {
      this.tileName = tileName;
      this.setBackground(Color.DARK_GRAY);
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      Image img = TileManager.getInstance().getImage(this.tileName);
      
      if (img != null) {
         g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
      }
   }

}