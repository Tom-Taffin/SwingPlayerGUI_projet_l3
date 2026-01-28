package l3s6.projet.star.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TilePanel extends JPanel {
   private Image img;

   public TilePanel() throws IOException {
      this.setBackground(Color.DARK_GRAY);
      this.img = ImageIO.read(new File("img/Base_Game_C3_Tile_A.png"));
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (img != null) {
         g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
      }
   }
}