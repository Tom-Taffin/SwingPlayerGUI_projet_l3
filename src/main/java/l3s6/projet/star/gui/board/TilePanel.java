package l3s6.projet.star.gui.board;

import javax.swing.*;

import org.javatuples.Pair;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.Edge;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

public class TilePanel extends JPanel {
   private TileClickListener listener;
   private BoardPanel boardPanel;
   private Character rotation;
   private Image unrotatedTileImage;
   private Image meepleImage;
   private String meeplePosition;
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
      Pair<Meeple, String> tileMeeplePair = tile.getMeeple();
      if (tileMeeplePair != null){
         Meeple tileMeeple = tileMeeplePair.getValue0();
         this.meepleImage = MeepleImageManager.getInstance().getImage("regular_" + this.boardPanel.idToColor.get(tileMeeple.getPlayer().getID()));
         this.meeplePosition = tileMeeplePair.getValue1();
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
         g2.drawImage(unrotatedTileImage, 0, 0, w, h, this);
         g2.rotate(-angle, w / 2.0, h / 2.0);
      } 
      else {
         g2.drawImage(unrotatedTileImage, 0, 0, w, h, this);
      }

      if (meepleImage != null) {
         try {
            Character direction = this.meeplePosition.charAt(0);
            int nbOfZones = this.boardPanel.board.getTileAt(coords).getEdge(Direction.fromChar(direction)).getZones().size();
            int indexOnEdge = Integer.parseInt(this.meeplePosition.substring(1));
            if (indexOnEdge < nbOfZones){
               int meepleW = w/4;
               int meepleH = h/4;
               int meepleX = 0;
               int meepleY = 0;
               if (direction.equals('R')){ 
                  meepleX = w*3/4;
                  meepleY = (h / ((indexOnEdge+1) * 8 / (nbOfZones+1))) + (meepleH/2);
               }
               else if (direction.equals('L')){ 
                  meepleY = (h / ((indexOnEdge+1) * 8 / (nbOfZones+1))) + (meepleH/2);
               }
               else if (direction.equals('T')){
                  meepleX = (w / ((indexOnEdge+1) * 8 / (nbOfZones+1))) + (meepleW/2);
               }
               else if (direction.equals('B')){
                  meepleX = (w / ((indexOnEdge+1) * 8 / (nbOfZones+1))) + (meepleW/2);
                  meepleY = h*3/4;
               }
               g2.drawImage(meepleImage, meepleX, meepleY, meepleW, meepleH, this);
            }
            
         } catch (WrongTileSyntaxException e) {
            e.printStackTrace();
         }
      }

      g2.setTransform(oldTransform);
   }

}