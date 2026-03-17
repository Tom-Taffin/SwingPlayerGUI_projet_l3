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

      AffineTransform oldTransform = g2.getTransform();

      if (rotation != null) {
         double angle = ROTATIONS.getOrDefault(rotation, 0.0);
         g2.rotate(angle, w / 2.0, h / 2.0);
         g2.drawImage(unrotatedTileImage, 0, 0, w, h, this);
         g2.rotate(-angle, w / 2.0, h / 2.0);
      } 
      else {
         g2.drawImage(unrotatedTileImage, 0, 0, w, h, this);
      }

      if (this.hasMeeple) {
         if (this.meepleIndexOnEdge < this.edgeLength){
            int meepleW = w/4;
            int meepleH = h/4;
            int meepleX = 0;
            int meepleY = 0;
            if (this.meepleEdge.equals('R')){ 
               meepleX = w*3/4;
               meepleY = (h / ((this.meepleIndexOnEdge+1) * 8 / (this.edgeLength+1))) + (meepleH/2);
            }
            else if (this.meepleEdge.equals('L')){ 
               meepleY = (h / ((this.meepleIndexOnEdge+1) * 8 / (this.edgeLength+1))) + (meepleH/2);
            }
            else if (this.meepleEdge.equals('T')){
               meepleX = (w / ((this.meepleIndexOnEdge+1) * 8 / (this.edgeLength+1))) + (meepleW/2);
            }
            else if (this.meepleEdge.equals('B')){
               meepleX = (w / ((this.meepleIndexOnEdge+1) * 8 / (this.edgeLength+1))) + (meepleW/2);
               meepleY = h*3/4;
            }
            g2.drawImage(meepleImage, meepleX, meepleY, meepleW, meepleH, this);
         }
      }

      g2.setTransform(oldTransform);
   }

}