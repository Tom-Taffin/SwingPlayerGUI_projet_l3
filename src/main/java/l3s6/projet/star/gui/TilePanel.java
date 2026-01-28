package l3s6.projet.star.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TilePanel extends JPanel {

   public TilePanel() throws IOException {
      this.setBackground(Color.DARK_GRAY);
      Image img = ImageIO.read(new File("img/Base_Game_C3_Tile_A.png"));
      ImageIcon icon = new ImageIcon(img);
      JLabel label = new JLabel(icon);
      this.add(label);
   }
}