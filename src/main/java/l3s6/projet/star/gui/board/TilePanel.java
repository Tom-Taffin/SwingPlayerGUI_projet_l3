package l3s6.projet.star.gui.board;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

public class TilePanel extends JPanel {
   private Character rotation;
   private Image unrotatedTileImage;
   private Image meepleImage;

   private static final Map<Character, Double> ROTATIONS = Map.of(
      'N', 0.0,
      'E', Math.PI / 2,
      'S', Math.PI,
      'W', -Math.PI / 2
   );

   public TilePanel(String tileName) throws ImageNotFoundException {
      rotation = tileName.charAt(0);
      unrotatedTileImage = TileImageManager.getInstance().getImage(tileName.substring(1));
   }

   public TilePanel(String tileName, String meepleName) throws ImageNotFoundException {
      this(tileName);
      meepleImage = MeepleImageManager.getInstance().getImage(meepleName);
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

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

      g2.drawImage(unrotatedTileImage, 0, 0, w, h, this);

      if (meepleImage != null) {
         g2.drawImage(meepleImage, 0, 0, w, h, this);
      }

      g2.setTransform(oldTransform);
   }

}