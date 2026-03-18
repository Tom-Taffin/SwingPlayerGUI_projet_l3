package l3s6.projet.star.gui.board;

import javax.swing.*;

import org.javatuples.Pair;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;

import java.awt.*;

public class TilePanel extends JPanel {
   private TileClickListener listener;
   private BoardPanel boardPanel;
   private Coordinates coords;
   private TileImagePanel tileImagePanel;
   private boolean hovered = false;
   
   public TilePanel(TileClickListener listener, BoardPanel boardPanel, Coordinates coords, String tileName) throws ImageNotFoundException {
      this.setLayout(new BorderLayout());
      this.addMouseListener(new TileMouseListener(this));
      this.listener = listener;
      this.boardPanel = boardPanel;
      this.coords = coords;
      this.tileImagePanel = new TileImagePanel(tileName);
      this.tileImagePanel.addMouseListener(new TileMouseListener(this));
      this.add(this.tileImagePanel, BorderLayout.CENTER);
   }

   public TilePanel(TileClickListener listener, BoardPanel boardPanel, Coordinates coords, Tile tile) throws ImageNotFoundException {
      this(listener, boardPanel, coords, tile.toString());
      Pair<Meeple, String> tileMeeplePair = tile.getMeeple();
      if (tileMeeplePair != null){
         try{
            Meeple meeple = tileMeeplePair.getValue0();
            String meepleColor = this.boardPanel.idToColor.get(meeple.getPlayer().getID());
            String meeplePosition = tileMeeplePair.getValue1();
            Character meepleEdge = meeplePosition.charAt(0);
            int meepleIndexOnEdge = Integer.parseInt(meeplePosition.substring(1));
            int edgeLength = tile.getEdge(Direction.fromChar(meepleEdge)).getSize();
            this.tileImagePanel.setMeeple("regular", meepleColor, meepleEdge, meepleIndexOnEdge, edgeLength);
         } catch (WrongTileSyntaxException e){
            e.printStackTrace();
         }
      }
   }

   public void clicked(){
      if (listener != null) {
         this.listener.clicked(this.coords);
      }
   }

   public void setHovered(boolean hovered) {
      this.hovered = hovered;
      this.tileImagePanel.setHovered(hovered);
      repaint();
   }

}