package l3s6.projet.star.gui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.SwingUtilities;

import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
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

    public void sendMessage(String message) throws InvalidArgumentNumberException {
        // envoie directement au reflector
        List<String> msg = List.of(message.split(" "));
        this.client.send(msg.get(0), msg.subList(1, msg.size()));
    }

    @Override
    public void updateOnEnter(String id){
        displayMessage(id + " ENTERS.");
    }

    @Override
    public void updateOnPlace(String id, String player, String tile, int x, int y){
        displayMessage(String.format("[%s] Player %s places tile %s on position %d:%d.", id, player, tile, x, y));
    }

    @Override
    public void updateOnPlaceWithMeeple(String id, String player, String tile, int x, int y, String meeple_type, String meeple_position) {
        displayMessage(String.format("[%s] Player %s places tile %s on position %d:%d with meeple %s on position %s.", id, player, tile, x, y, meeple_type, meeple_position));
    }

    @Override
    public void updateOnLeave(String id){
        displayMessage(id + " LEAVES.");
    }

    @Override
    public void updateOnStart(String id){
        displayMessage("GAME STARTS !");
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
