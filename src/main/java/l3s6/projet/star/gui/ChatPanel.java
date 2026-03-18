package l3s6.projet.star.gui;

import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;

import java.awt.*;

import javax.swing.*;

public class ChatPanel extends JPanel {

    private PlayerController playerController;
    private PlacePanel placePanel;
    private PlayersPanel playersPanel;
    private JTextArea chatHistory;
    private JTextField chatInput;

    public ChatPanel(PlayerController playerController) {
        this.playerController = playerController;

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        this.chatHistory = new JTextArea();
        this.chatHistory.setEditable(false);
        this.chatHistory.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(this.chatHistory);
        scrollPane.setPreferredSize(new Dimension(0, 0));
        gbc.gridy = 0;
        gbc.weighty = 0.20;
        this.add(scrollPane, gbc);

        this.placePanel = new PlacePanel(playerController);
        this.placePanel.setPreferredSize(new Dimension(0, 0));
        gbc.gridy = 1;
        gbc.weighty = 0.10;
        this.add(placePanel, gbc);

        this.playersPanel = new PlayersPanel(playerController);
        this.playersPanel.setPreferredSize(new Dimension(0, 0));
        gbc.gridy = 2;
        gbc.weighty = 0.65;
        this.add(playersPanel, gbc);
        
        this.chatInput = new JTextField();
        this.chatInput.setPreferredSize(new Dimension(0, 0));
        gbc.gridy = 3;
        gbc.weighty = 0.05;
        this.add(this.chatInput, gbc);

        // ENVOI DU MESSAGE AVEC ENTREE
        this.chatInput.addActionListener(e -> {
            String message = chatInput.getText().trim();

            if(!message.isEmpty()){
                try {
                    playerController.sendMessage(message);
                } catch (InvalidArgumentNumberException ex) {
                    throw new RuntimeException(ex);
                }
                chatInput.setText("");
            }
        });
    }

    public PlacePanel getPlacePanel(){
        return this.placePanel;
    }

    public PlayersPanel getPlayersPanel(){
        return this.playersPanel;
    }

    public void displayMessage(String message){
        this.chatHistory.append(message + "\n");
        this.chatHistory.setCaretPosition(
                this.chatHistory.getDocument().getLength()
        );
    }
}