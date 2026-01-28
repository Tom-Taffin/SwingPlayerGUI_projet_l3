package l3s6.projet.star.gui;

import javax.swing.*;
import java.awt.event.*;

public class PlayerGUI extends JFrame {

   public PlayerGUI() {
      super("Player GUI");

      WindowListener l = new WindowAdapter() {
         public void windowClosing(WindowEvent e){
            System.exit(0);
         }
      };

      addWindowListener(l);

      ImageIcon img = new ImageIcon("img/Base_Game_C3_Tile_A.png");
      JButton bouton = new JButton("Mon bouton",img);
      JPanel panel = new JPanel();
      panel.add(bouton);
      setContentPane(panel);


      setSize(1920,1080);
      setVisible(true);
   }

   public static void main(String [] args){
      JFrame frame = new PlayerGUI();
   }
}