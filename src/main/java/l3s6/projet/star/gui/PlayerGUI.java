package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PlayerGUI extends JFrame {

   public PlayerGUI() throws IOException {
      super("Player GUI");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JPanel mainPanel = new JPanel(new BorderLayout());
      BoardPanel board = new BoardPanel();
      mainPanel.add(board, BorderLayout.WEST);
      setContentPane(mainPanel);

      setSize(1920, 1080);
      setVisible(true);
   }

   public static void main(String [] args){
      SwingUtilities.invokeLater(() -> {
         try {
            new PlayerGUI();
         } catch (IOException e) {
            e.printStackTrace();
         }
      });
   }
}