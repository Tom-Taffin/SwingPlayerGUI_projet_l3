package l3s6.projet.star.gui;

import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;

import java.awt.BorderLayout;
import javax.swing.*;

public class ChatPanel extends JPanel {

    private PlayerController playerController;
    private JTextArea chatHistory;
    private JTextField chatInput;

    public ChatPanel(PlayerController playerController) {
        this.playerController = playerController;
        this.setLayout(new BorderLayout());

        this.chatHistory = new JTextArea();
        this.chatHistory.setEditable(false);
        this.chatHistory.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(this.chatHistory);
        this.add(scrollPane, BorderLayout.CENTER);

        this.chatInput = new JTextField();
        this.add(this.chatInput, BorderLayout.SOUTH);

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

    public void displayMessage(String message){
        this.chatHistory.append(message + "\n");
        this.chatHistory.setCaretPosition(
                this.chatHistory.getDocument().getLength()
        );
    }
}