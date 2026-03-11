package l3s6.projet.star.gui;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    private BoardPanel boardPanel;
    private ChatPanel chatPanel;

    public MainWindow(PlayerController playerController) throws IOException, ImageNotFoundException {
        super("Player GUI");
        TileManager.getInstance().loadResources("base_game_C3.txt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        this.boardPanel = new BoardPanel();
        mainPanel.add(this.boardPanel, BorderLayout.WEST);

        this.chatPanel = new ChatPanel(playerController);
        mainPanel.add(this.chatPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        setSize(new Dimension(1200, 800));;
        setVisible(true);
    }

    public void displayMessage(String message){
        this.chatPanel.displayMessage(message);
    }

}