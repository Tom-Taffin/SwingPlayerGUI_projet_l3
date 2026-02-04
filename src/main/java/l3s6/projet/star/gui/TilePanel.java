package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

public class TilePanel extends JPanel {
   private Character rotation;
   private String tileName;

   public TilePanel(String tileName) {
      if (tileName.equals("empty")){
         this.rotation = null;
         this.tileName = tileName;
      }
      else if ("NWES".contains(tileName.substring(0, 1))){
         this.rotation = tileName.charAt(0);
         this.tileName = tileName.substring(1);
      }
      else {
         this.rotation = 'N';
         this.tileName = tileName;
      }

         this.setBackground(Color.WHITE);
   }

   private static final Map<Character, Double> ROTATIONS = Map.of(
           'N', 0.0,
           'E', Math.PI / 2,
           'S', Math.PI,
           'W', -Math.PI / 2
   );

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      Image img = TileManager.getInstance().getImage(this.tileName);
      if (img == null) return;

      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
              RenderingHints.VALUE_INTERPOLATION_BILINEAR);

      int w = getWidth();
      int h = getHeight();

      AffineTransform oldTransform = g2.getTransform();

      if (rotation != null) {
         double angle = ROTATIONS.getOrDefault(rotation, 0.0);
         g2.rotate(angle, w / 2.0, h / 2.0);
      }

      g2.drawImage(img, 0, 0, w, h, this);

      g2.setTransform(oldTransform);
   }

}