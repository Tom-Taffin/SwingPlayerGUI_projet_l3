package l3s6.projet.star.gui.board;

import javax.swing.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.*;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.WrongTopologyException;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;

public class BoardPanel extends JPanel {

   protected Board board;
   public Map<String, String> idToColor;
   private Map<String, Player> idToPlayer;
   private TileClickListener listener;
   private final List<String> colors = List.of("blue", "green", "yellow", "red", "black");
   private int nextColor;

   public BoardPanel(TileClickListener listener) throws IOException, ImageNotFoundException {
      this.listener = listener;
      this.idToColor = new HashMap<>();
      this.idToPlayer = new HashMap<>();
      this.nextColor = 0;
      this.board = new Board();
      this.setBackground(Color.BLACK);
      this.createTilePanel();
   }

   public Board getBoard() {
      return board;
   }

   public void addPlayer(String id, int startingAmount){
      this.idToPlayer.put(id, new Player(id, startingAmount));
      this.idToColor.put(id, this.colors.get(this.nextColor));
      this.nextColor++;
   }

   public void addMeepleToPlayer(String id, int amount){
      Player player = this.idToPlayer.get(id);
      for (int i = 0; i < amount; i++){
         player.incrementMeepleCount();
      }
   }

   public void addTile(String tileName, String orientation, Coordinates coord) throws WrongTileSyntaxException, ImageNotFoundException{
      this.board.putTileAt(new TileBuilder().build(orientation+tileName), coord);
      this.createTilePanel();
   }

   public void addTileWithMeeple(String id, String tileName, Coordinates coord, String meepleType, String meeplePosition) throws WrongTileSyntaxException, ImageNotFoundException{
      Character edge = meeplePosition.charAt(0);
      int index = Integer.parseInt(meeplePosition.substring(1));
      this.board.putTileAt(new TileBuilder().build(tileName), coord);
      try {
         this.board.getTileAt(coord).getEdge(Direction.fromChar(edge)).getZoneAt(index).setMeeple(new Meeple(this.idToPlayer.get(id)));
      } catch (WrongTopologyException | WrongTileSyntaxException e) {
         e.printStackTrace();
      };
      this.createTilePanel();
   }

   public void createTilePanel() throws ImageNotFoundException{
      this.removeAll();

      int width = this.board.getMaxX() - this.board.getMinX() + 1;
      int height = this.board.getMaxY() - this.board.getMinY() + 1;

      int gridSize = Math.max(width, height) + 2;
      gridSize = Math.max(gridSize, 5);

      this.setLayout(new GridLayout(gridSize, gridSize));

      int midX = (this.board.getMaxX() + this.board.getMinX()) / 2;
      int midY = (this.board.getMaxY() + this.board.getMinY()) / 2;
      
      int half = gridSize / 2;
                  
      for (int i = midY + half; i > midY - (gridSize - half); i--) {
         for (int j = midX - half; j < midX + (gridSize - half); j++) {
            Coordinates currentCoord = new Coordinates(j, i);
            if (this.board.hasTile(currentCoord)) {
               this.add(new TilePanel(this.listener, this, currentCoord, this.board.getTileAt(currentCoord)));
            } else {
               this.add(new TilePanel(this.listener, this, currentCoord, "Nempty")); 
            }
         }
      }

      revalidate();
      repaint();
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

}