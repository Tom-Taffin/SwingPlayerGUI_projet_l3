package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoardPanel extends JPanel {

   public BoardPanel() throws IOException {
      this.setLayout(new GridLayout(5, 5));
      this.setBackground(Color.LIGHT_GRAY);

      List<String> givenList = Arrays.asList("f1r1f2-f2r2f3-f3r3f4-f4r4f1", "c-c-f-f", "c1-f-f-c2", "c-f1rf2-f2-f2rf1", "C-C-C-C");
      Random rand = new Random();
      for (int i = 0; i < 25; i++){
         String randomTileName = givenList.get(rand.nextInt(givenList.size()));
         this.add(new TilePanel(randomTileName));
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

   public int getNbOfTilesVertical() {
      return 5;
   }
}