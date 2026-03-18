package l3s6.projet.star.gui.board;

import javax.swing.*;
import java.awt.*;

public class MeepleImagePanel extends JPanel {
   private Image meepleImage;
   
   public MeepleImagePanel(String meepleType, String meepleColor) throws ImageNotFoundException {
      this.meepleImage = MeepleImageManager.getInstance().getImage(meepleType + "_" + meepleColor);  
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      int w = getWidth();
      int h = getHeight();
      int size = Math.min(w, h);
      int xOffset = (w - size) / 2;
      int yOffset = (h - size) / 2;

      g.drawImage(this.meepleImage, xOffset, yOffset, size, size, this);
   }

}