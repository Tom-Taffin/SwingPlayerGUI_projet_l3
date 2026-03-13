package l3s6.projet.star.gui;

import javax.swing.*;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.gui.board.*;

import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    private BoardPanel boardPanel;
    private ChatPanel chatPanel;

    public MainWindow(PlayerController playerController) throws IOException, ImageNotFoundException {
        super("Player GUI");
        TileImageManager.getInstance().loadResources("base_game_C3_tile.txt");
        MeepleImageManager.getInstance().loadResources("base_game_C3_meeple.txt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        this.boardPanel = new BoardPanel(playerController);
        mainPanel.add(this.boardPanel, BorderLayout.WEST);

        this.chatPanel = new ChatPanel(playerController);
        mainPanel.add(this.chatPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        setSize(new Dimension(1200, 800));;
        setVisible(true);
    }

    public BoardPanel getBoardPanel() {
      return boardPanel;
    }

    public ChatPanel getChatPanel() {
      return chatPanel;
    }

    public void displayMessage(String message){
        this.chatPanel.displayMessage(message);
    }

    public void addTile(String tileName, Coordinates coord) throws WrongTileSyntaxException, ImageNotFoundException{
      this.boardPanel.addTile(tileName, coord);
    }

    public void addTileWithMeeple(String tileName, Coordinates coord, String meepleType, String meeplePosition) throws WrongTileSyntaxException, ImageNotFoundException{
      this.boardPanel.addTileWithMeeple(tileName, coord, meepleType, meeplePosition);
    }

}