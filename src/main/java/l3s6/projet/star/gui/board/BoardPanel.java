package l3s6.projet.star.gui.board;

import javax.swing.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.Edge;
import l3s6.projet.star.game.meeple.NoMeepleException;
import l3s6.projet.star.game.edge.WrongTopologyException;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.meeple.AlreadyHaveMeepleException;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.NoAbbeyException;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;

public class BoardPanel extends JPanel {

   protected Board board;
   public Map<String, String> idToColor;
   private Map<String, Player> idToPlayer;
   private TileClickListener listener;
   private final List<String> colors = List.of("blue", "green", "yellow", "red", "black");
   private int nextColor;

   private double zoomFactor = 1.0;
   private Point lastMousePt;

   public BoardPanel(TileClickListener listener) throws IOException, ImageNotFoundException {
      this.listener = listener;
      this.idToColor = new HashMap<>();
      this.idToPlayer = new HashMap<>();
      this.nextColor = 0;
      this.board = new Board();
      this.setBackground(Color.BLACK);

      // zoom logic
      this.addMouseWheelListener(e -> {
         if (e.getWheelRotation() < 0) zoomFactor *= 1.1;
         else zoomFactor = Math.max(1.0, zoomFactor / 1.1);
         revalidate();
         repaint();
      });

      // pan logic
      MouseAdapter panHandler = new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            lastMousePt = e.getLocationOnScreen();
         }

         @Override
         public void mouseDragged(MouseEvent e) {
            if (lastMousePt == null || !(getParent() instanceof JViewport)) return;

            JViewport viewport = (JViewport) getParent();
            Point currentPt = e.getLocationOnScreen();
            
            int dx = lastMousePt.x - currentPt.x;
            int dy = lastMousePt.y - currentPt.y;

            Point viewPos = viewport.getViewPosition();
            viewPos.translate(dx, dy);

            int maxX = Math.max(0, getWidth() - viewport.getWidth());
            int maxY = Math.max(0, getHeight() - viewport.getHeight());
            viewPos.x = Math.min(maxX, Math.max(0, viewPos.x));
            viewPos.y = Math.min(maxY, Math.max(0, viewPos.y));

            viewport.setViewPosition(viewPos);
            lastMousePt = currentPt;
         }
      };
      this.addMouseListener(panHandler);
      this.addMouseMotionListener(panHandler);

      this.createTilePanel();
   }

   public Board getBoard() {
      return board;
   }

   public Map<String, Player> getColorsToPlayers(){
      Map<String, Player> res = new HashMap<>();
      idToColor.forEach((id, color) -> {
         Player p = idToPlayer.get(id);
         if (p != null) {
               res.put(color, p);
         }
      });
      return res;
   }

   public Player getPlayerById(String id){
      return this.idToPlayer.get(id);
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

   public void removeMeeple(String id, int x, int y) throws ImageNotFoundException{
      Tile tile = this.board.getTileAt(new Coordinates(x, y));
      for (Direction d : Direction.values()){
         Edge edge = tile.getEdge(d);
         for (Zone zone : edge.getZones()){
            if (zone.hasMeeple()){
               Meeple meeple = zone.getMeeple();
               if(meeple.getPlayer().getID().equals(id)){
                  try {
                     zone.giveBackMeeple();
                  } catch (NoMeepleException e) {
                     throw new RuntimeException(e);
                  }
               }
            }
         }
      }
      this.createTilePanel();
   }

   public void addTile(String id, String tileName, Coordinates coord) throws WrongTileSyntaxException, ImageNotFoundException{
      this.board.putTileAt(new TileBuilder().build(tileName), coord);
      this.createTilePanel();
   }

   public void addTileWithMeeple(String id, String tileName, Coordinates coord, String meepleType, String meeplePosition) throws WrongTileSyntaxException, ImageNotFoundException{
      Character edge = meeplePosition.charAt(0);
      this.board.putTileAt(new TileBuilder().build(tileName), coord);
      try {
         Meeple meeple = new Meeple(this.idToPlayer.get(id), coord);
         if (edge.equals('A')){
            this.board.getTileAt(coord).setAbbeyMeeple(meeple);
         } else {
            int index = Integer.parseInt(meeplePosition.substring(1));
            this.board.getTileAt(coord).getEdge(Direction.fromChar(edge)).getZoneAt(index).setMeeple(meeple);
         }
      } catch (WrongTopologyException | WrongTileSyntaxException | AlreadyHaveMeepleException | NoAbbeyException e) {
         e.printStackTrace();
      };
      this.createTilePanel();
   }

   public void createTilePanel() throws ImageNotFoundException{
      this.removeAll();

      int minX = this.board.getMinX();
      int maxX = this.board.getMaxX();
      int minY = this.board.getMinY();
      int maxY = this.board.getMaxY();

      int width = maxX - minX + 1;
      int height = maxY - minY + 1;

      int gridSize = Math.max(width, height) + 2; 
      gridSize = Math.max(gridSize, 5);

      this.setLayout(new GridLayout(gridSize, gridSize));

      int marginX = (gridSize - width) / 2;
      int marginY = (gridSize - height) / 2;

      int startY = maxY + marginY;
      int startX = minX - marginX;
      
      for (int i = 0; i < gridSize; i++) {
         for (int j = 0; j < gridSize; j++) {
            Coordinates currentCoord = new Coordinates(startX + j, startY - i);
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
      int baseSize = 800; 
      if (parent instanceof JViewport && parent.getHeight() > 0) {
          baseSize = parent.getHeight();
      }
      int scaledSize = (int) (baseSize * zoomFactor);
      return new Dimension(scaledSize, scaledSize);
   }

}