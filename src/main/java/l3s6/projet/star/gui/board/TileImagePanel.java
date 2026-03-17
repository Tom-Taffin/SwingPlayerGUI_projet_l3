package l3s6.projet.star.gui.board;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

public class TileImagePanel extends JPanel {
   private Character rotation;
   private String tileName;
   private String meepleType;
   private String meepleColor;
   private Character meepleEdge;
   private int meepleIndexOnEdge;
   private int edgeLength;
   private boolean hasMeeple = false;
   private Image unrotatedTileImage;
   private Image meepleImage;

   private static final Map<Character, Double> ROTATIONS = Map.of(
      'N', 0.0,
      'E', Math.PI / 2,
      'S', Math.PI,
      'W', -Math.PI / 2
   );

   
   public TileImagePanel(String tileName) throws ImageNotFoundException {
      this.rotation = tileName.charAt(0);
      this.tileName = tileName.substring(1);
      this.unrotatedTileImage = TileImageManager.getInstance().getImage(this.tileName);  
   }

   public void setTile(String fullTileName) throws ImageNotFoundException {
      this.rotation = fullTileName.charAt(0);
      this.tileName = fullTileName.substring(1);
      this.unrotatedTileImage = TileImageManager.getInstance().getImage(this.tileName);
      this.repaint();
   }

   public void setMeeple(String meepleType, String meepleColor, Character meepleEdge, int meepleIndexOnEdge, int edgeLength) throws ImageNotFoundException{
      this.hasMeeple = true;
      this.meepleType = meepleType;
      this.meepleColor = meepleColor;
      this.meepleEdge = meepleEdge;
      this.meepleIndexOnEdge = meepleIndexOnEdge;
      this.edgeLength = edgeLength;
      this.meepleImage = MeepleImageManager.getInstance().getImage(this.meepleType + "_" + this.meepleColor);
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
              RenderingHints.VALUE_INTERPOLATION_BILINEAR);

      int w = getWidth();
      int h = getHeight();
      int size = Math.min(w, h);
      int xOffset = (w - size) / 2;
      int yOffset = (h - size) / 2;

      AffineTransform oldTransform = g2.getTransform();

      if (rotation != null) {
         double angle = ROTATIONS.getOrDefault(rotation, 0.0);
         g2.rotate(angle, w / 2.0, h / 2.0);
         g2.drawImage(unrotatedTileImage, xOffset, yOffset, size, size, this);
         g2.rotate(-angle, w / 2.0, h / 2.0);
      } 
      else {
         g2.drawImage(unrotatedTileImage, xOffset, yOffset, size, size, this);
      }

      if (this.hasMeeple && this.meepleIndexOnEdge < this.edgeLength){
         this.drawMeeple(g2, xOffset, yOffset, size);
      }

      g2.setTransform(oldTransform);
   }

   private void drawMeeple(Graphics2D g2, int x, int y, int size) {
      int meepleW = size / 4;
      int meepleH = size / 4;
      int meepleX = x;
      int meepleY = y;

      int step = (size / ((this.meepleIndexOnEdge + 1) * 8 / (this.edgeLength + 1)));

      if (this.meepleEdge.equals('R')) { 
         meepleX = x + (size * 3 / 4);
         meepleY = y + step + (meepleH / 2);
      }
      else if (this.meepleEdge.equals('L')) { 
         meepleX = x;
         meepleY = y + step + (meepleH / 2);
      }
      else if (this.meepleEdge.equals('T')) {
         meepleX = x + step + (meepleW / 2);
         meepleY = y;
      }
      else if (this.meepleEdge.equals('B')) {
         meepleX = x + step + (meepleW / 2);
         meepleY = y + (size * 3 / 4);
      }

      g2.drawImage(meepleImage, meepleX, meepleY, meepleW, meepleH, this);
   }

}