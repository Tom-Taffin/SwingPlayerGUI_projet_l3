package l3s6.projet.star.gui.board;

import javax.swing.*;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.tile.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

public class TilePanel extends JPanel {
   private TileClickListener listener;
   private BoardPanel boardPanel;
   private Character rotation;
   private Image unrotatedTileImage;
   private Image meepleImage;
   private Coordinates coords;

   private static final Map<Character, Double> ROTATIONS = Map.of(
      'N', 0.0,
      'E', Math.PI / 2,
      'S', Math.PI,
      'W', -Math.PI / 2
   );

   
   public TilePanel(TileClickListener listener, BoardPanel boardPanel, Coordinates coords, String tileName) throws ImageNotFoundException {
      this.addMouseListener(new TileMouseListener(this));
      this.listener = listener;
      this.boardPanel = boardPanel;
      this.coords = coords;
      this.rotation = tileName.charAt(0);
      this.unrotatedTileImage = TileImageManager.getInstance().getImage(tileName.substring(1));
      
   }

   public TilePanel(TileClickListener listener, BoardPanel boardPanel, Coordinates coords, Tile tile) throws ImageNotFoundException {
      this(listener, boardPanel, coords, tile.toString());
      Meeple tileMeeple = tile.getMeeple();
      if (tileMeeple != null){
         this.meepleImage = MeepleImageManager.getInstance().getImage("regular_" + this.boardPanel.idToColor.get(tileMeeple.getPlayer().getID()));
      }
   }

   public void clicked(){
      if (listener != null) {
         this.listener.clicked(this.coords);
      }
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