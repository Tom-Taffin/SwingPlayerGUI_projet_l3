package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoardPanel extends JPanel {

   public BoardPanel() throws IOException, ImageNotFoundException {
      this.setLayout(new GridLayout(5, 5));
      this.setBackground(Color.LIGHT_GRAY);

      List<String> givenList = Arrays.asList("Wc1-f2r3f4-f4r5f6-f6r7f2", "Ef1-C2-f3-C2", "Nf1-f1-f1-f1:A", "Sf1r2f3-f3r4f5-f5r6f7-f7r8f1", "SC1-C1-f2r3f4-C1", "Nempty");
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