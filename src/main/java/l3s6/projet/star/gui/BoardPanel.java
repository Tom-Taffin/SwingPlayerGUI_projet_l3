package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoardPanel extends JPanel {

   public BoardPanel() throws IOException {
      this.setLayout(new GridLayout(5, 5));
      this.setBackground(Color.LIGHT_GRAY);

      for (int i = 0; i < 25; i++){
         this.add(new TilePanel());
      }
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