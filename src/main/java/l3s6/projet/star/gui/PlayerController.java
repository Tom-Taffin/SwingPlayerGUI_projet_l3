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

    @Override
    public void updateOnAgree(String arg0, List arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnAgree'");
    }

    @Override
    public void updateOnBlame(String arg0, int arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnBlame'");
    }

    @Override
    public void updateOnBlameWithReason(String arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnBlameWithReason'");
    }

    @Override
    public void updateOnClose(String arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnClose'");
    }

    @Override
    public void updateOnElect(String arg0, String arg1, List arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnElect'");
    }

    @Override
    public void updateOnEnd(String arg0, List arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnEnd'");
    }

    @Override
    public void updateOnEnter(String arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnEnter'");
    }

    @Override
    public void updateOnExpel(String arg0, String arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnExpel'");
    }

    @Override
    public void updateOnLeave(String arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnLeave'");
    }

    @Override
    public void updateOnOffer(String arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnOffer'");
    }

    @Override
    public void updateOnPlace(String arg0, String arg1, String arg2, int arg3, int arg4) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnPlace'");
    }

    @Override
    public void updateOnPlaceWithMeeple(String arg0, String arg1, String arg2, int arg3, int arg4, String arg5,
            String arg6) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnPlaceWithMeeple'");
    }

    @Override
    public void updateOnScore(String arg0, String arg1, int arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnScore'");
    }

    @Override
    public void updateOnStart(String arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOnStart'");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new PlayerController("127.0.0.1", 5000, "Rem");
            } catch (URISyntaxException | InterruptedException | IOException | ImageNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    
}
