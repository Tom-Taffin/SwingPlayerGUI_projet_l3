package l3s6.projet.star.gui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.SwingUtilities;

import org.javatuples.Pair;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.interaction.network.PlayerClient;
import l3s6.projet.star.interaction.role.Role;
import l3s6.projet.star.interaction.view.PlayerView;

public class PlayerController extends PlayerView<PlayerClient> {

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
        List<String> msg = List.of(message.split(" "));
        this.send(msg.get(0), msg.subList(1, msg.size()));
    }

    @Override
    public void updateOnEnter(String id){
        this.roleManager.addRole(id, Role.SPECTATOR);
        displayMessage(String.format("[%s] %s enters.", id, id));
    }

    @Override
    public void updateOnLeave(String id){
        displayMessage(String.format("[%s] %s leaves.", id, id));
    }

    @Override
    public void updateOnStart(String id){
        displayMessage(String.format("[%s] The game starts.", id));
    }

    @Override
    public void updateOnPlace(String id, String player, String tile, int x, int y){
        if (this.roleManager.isRole(id, Role.PLAYER)){
            displayMessage(String.format("[%s] Player %s wants to place tile %s on position %d:%d.", id, player, tile, x, y));
        }       
        else if (this.roleManager.isRole(id, Role.REFEREE)){
            try {
                this.gui.addTile(tile, new Coordinates(new Pair<Integer,Integer>(x, y)));
                displayMessage(String.format("[%s] Player %s places tile %s on position %d:%d.", id, player, tile, x, y));
            } catch (WrongTileSyntaxException | ImageNotFoundException e) {
                e.printStackTrace();
            }
        }       
    }

    @Override
    public void updateOnPlaceWithMeeple(String id, String player, String tile, int x, int y, String meeple_type, String meeple_position) {
        displayMessage(String.format("[%s] Player %s places tile %s on position %d:%d with meeple %s on position %s.", id, player, tile, x, y, meeple_type, meeple_position));
    }

    @Override
    public void updateOnBlame(String id, int amount) {
        displayMessage(String.format("[%s] %d blames are authorized for this game.", id, amount));
    }

    @Override
    public void updateOnBlameWithReason(String id, String player, String reason) {
        displayMessage(String.format("[%s] Player %s was blamed for the reason %s.", id, player, reason));
    }

    @Override
    public void updateOnCollect(String id, String player, String meeple_type) {
        displayMessage(String.format("[%s] Player %s collects a meeple %s.", id, player, meeple_type));
    }

    @Override
    public void updateOnCollectWithAmount(String id, String player, String meeple_type, int amount) {
        displayMessage(String.format("[%s] Player %s collects %d meeples %s.", id, player, amount, meeple_type));
    }

    @Override
    public void updateOnOffer(String id, String player, String tile) {
        displayMessage(String.format("[%s] Player %s gets the tile %s.", id, player, tile));
    }

    @Override
    public void updateOnClose(String id)  {
        displayMessage(String.format("[%s] %s closes.", id, id));
    }

    @Override
    public void updateOnExpel(String id, String expelledPlayer) {
        displayMessage(String.format("[%s] Player %s was expelled.", id, expelledPlayer));
    }

    @Override
    public void updateOnElect(String id, String role, List<String> ids) {
        for (String i : ids) {
            this.roleManager.addRole(i, Role.getRoleFromString(role));
        }
        displayMessage(String.format("[%s] Players %s gained the role %s.", id, ids, role));
    }

    @Override
    public void updateOnAgree(String id, List<String> expOrVar) {
        displayMessage(String.format("[%s] The expansions and variations %s are chosen for this game.", id, expOrVar.toString()));
    }

    @Override
    public void updateOnScore(String id, String otherId, int points) {
        displayMessage(String.format("[%s] Player %s gains %d points.", id, otherId, points));
    }

    @Override
    public void updateOnEnd(String id, List<String> ids) {
        displayMessage(String.format("[%s] The game ends. Winners : %s.", id, ids.toString()));
    }

    public void displayMessage(String msg) {
        if (this.gui != null) {
            this.gui.displayMessage(msg);
        } else {
            messageQueue.add(msg);
        }
    }

    public static void main(String[] args) throws InvalidArgumentNumberException {
        if (args.length != 3){
            throw new InvalidArgumentNumberException("Usage : <IPAddress> <Port> <PlayerName>");
        }
        String IPAddress = args[0];
        int port = Integer.parseInt(args[1]);
        String playerName = args[2];
        SwingUtilities.invokeLater(() -> {
            try {
                new PlayerController(IPAddress, port, playerName);
            } catch (URISyntaxException | InterruptedException | IOException | ImageNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    
}
