package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;

public class BoardPanel extends JPanel {

   protected Board board;

   public BoardPanel() throws IOException, ImageNotFoundException {
      this.board = new Board();
      this.setBackground(Color.BLACK);
      this.createTilePanel();
   }

   public void addTile(String tileName, Coordinates coord) throws WrongTileSyntaxException, ImageNotFoundException{
      this.board.putTileAt(new TileBuilder().build(tileName), coord);
      this.createTilePanel();
   }

   public void createTilePanel() throws ImageNotFoundException{
      this.removeAll();

      int width = this.board.getMaxWidth() - this.board.getMinWidth() + 1;
      int height = this.board.getMaxHeight() - this.board.getMinHeight() + 1;

      int gridSize = Math.max(width, height) + 2;
      gridSize = Math.max(gridSize, 5);

      this.setLayout(new GridLayout(gridSize, gridSize));

      int midX = (this.board.getMaxWidth() + this.board.getMinWidth()) / 2;
      int midY = (this.board.getMaxHeight() + this.board.getMinHeight()) / 2;
      
      int half = gridSize / 2;
                  
      for (int i = midY + half; i > midY - (gridSize - half); i--) {
         for (int j = midX - half; j < midX + (gridSize - half); j++) {
            Coordinates currentCoord = new Coordinates(j, i);
            if (this.board.hasTile(currentCoord)) {
               this.add(new TilePanel(this.board.getTileAt(currentCoord).toString()));
            } else {
               this.add(new TilePanel("Nempty")); 
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