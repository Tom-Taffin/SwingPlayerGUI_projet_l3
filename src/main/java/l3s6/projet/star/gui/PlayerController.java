package l3s6.projet.star.gui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.SwingUtilities;

import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.interaction.view.PlayerView;

public class PlayerController extends PlayerView {

    private MainWindow gui;
    private final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();

    public PlayerController(String ipAdress, int port, String id) throws URISyntaxException, InterruptedException, IOException, ImageNotFoundException{
        super(ipAdress, port, id);
        this.gui = new MainWindow(this);

        String msg;
        while ((msg = messageQueue.poll()) != null) {
            this.gui.displayMessage(msg);
        }
    }

    public void displayMessage(String msg) {
        if (this.gui != null) {
            this.gui.displayMessage(msg);
        } else {
            messageQueue.add(msg);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new PlayerController("localhost", 3000, "Rem");
            } catch (URISyntaxException | InterruptedException | IOException | ImageNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    
}
