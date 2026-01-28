package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

   public BoardPanel() {
      this.setLayout(new GridBagLayout());
      this.setBackground(Color.LIGHT_GRAY);

      ImageIcon img = new ImageIcon("img/Base_Game_C3_Tile_A.png");
      JLabel label = new JLabel(img);
      this.add(label);
   }

   @Override
   public Dimension getPreferredSize() {
      Container parent = getParent();
      if (parent != null) {
         int h = parent.getHeight();
         return new Dimension(h, h);
      }
      return super.getPreferredSize();
   }
}