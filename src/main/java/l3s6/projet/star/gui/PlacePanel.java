package l3s6.projet.star.gui;

import java.awt.*;
import javax.swing.*;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.gui.board.ImageNotFoundException;
import l3s6.projet.star.gui.board.TileImagePanel;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;

public class PlacePanel extends JPanel {

    private PlayerController playerController;
    private Coordinates selectedCoordinates;
    private JLabel infoLabel;
    private TileImagePanel tileImagePanel;
    private String currentBaseTileName = "empty";
    private JComboBox<String> orientationCombo;
    private JTextField meeplePositionField;
    private JButton placeButton;
    private JPanel inputContainer;
    private final String DEFAULT_ORIENTATION = "N";

    public PlacePanel(PlayerController playerController) {
        this.playerController = playerController;
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(Color.WHITE);

        // left panel with the current tile
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(new JLabel("Current tile :"), BorderLayout.NORTH);

        
        try {
            this.tileImagePanel = new TileImagePanel("Nempty");
            this.tileImagePanel.setOpaque(false);
            leftPanel.add(tileImagePanel, BorderLayout.CENTER);
            this.add(leftPanel);
        } catch (ImageNotFoundException e) {
            e.printStackTrace();
        }
        

        // right panel with the place action
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        // a container with info on the click on the board
        this.infoLabel = new JLabel("");
        this.infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // a container with orientation and meeple inputs
        this.inputContainer = new JPanel(new GridBagLayout());
        this.inputContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // orientation
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.inputContainer.add(new JLabel("Orientation :"), gbc);
        
        String[] orientations = { "N", "E", "S", "W" };
        this.orientationCombo = new JComboBox<>(orientations);
        gbc.gridx = 1;
        this.inputContainer.add(orientationCombo, gbc);
        this.orientationCombo.addActionListener(e -> this.updatePreview());

        // meeple
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.inputContainer.add(new JLabel("Meeple position :"), gbc);
        
        this.meeplePositionField = new JTextField(5);
        gbc.gridx = 1;
        this.inputContainer.add(meeplePositionField, gbc);
        this.meeplePositionField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
        });

        // place button
        this.placeButton = new JButton("Place");
        this.placeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.placeButton.addActionListener(e -> {
            String orientation = (String) orientationCombo.getSelectedItem();
            String meeplePos = meeplePositionField.getText().trim();
            try {
                if (meeplePos.isEmpty()) {
                    this.playerController.send("PLACES", playerController.getId(), orientation, this.selectedCoordinates.getX(), this.selectedCoordinates.getY());
                } else {
                    this.playerController.send("PLACES", playerController.getId(), orientation, this.selectedCoordinates.getX(), this.selectedCoordinates.getY(), "regular", meeplePos);
                }
                this.setInputVisibility(false);
                this.infoLabel.setVisible(false);
            } catch (InvalidArgumentNumberException ex) {
                ex.printStackTrace();
            }
        });

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(infoLabel);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(inputContainer);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(placeButton);
        rightPanel.add(Box.createVerticalGlue());

        this.add(rightPanel);
        
        this.infoLabel.setVisible(true);
        setInputVisibility(false);
    }

    private void setInputVisibility(boolean visible) {
        this.inputContainer.setVisible(visible);
        this.placeButton.setVisible(visible);
    }

    public void displayTile(String tile) {
        this.currentBaseTileName = tile;

        if (tile.equals("empty") || tile.equals("Nempty")) {
            this.meeplePositionField.setText("");
            this.tileImagePanel.removeMeeple();
        }
        try {
            this.orientationCombo.setSelectedItem(DEFAULT_ORIENTATION);
            String tileNameWithOrientation = ((String) this.orientationCombo.getSelectedItem()) + tile;
            this.tileImagePanel.setTile(tileNameWithOrientation); 
            this.updatePreview();
        } catch (ImageNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void displayNotTurn() {
        this.infoLabel.setVisible(true);
        this.infoLabel.setText("Not your turn");
        setInputVisibility(false);
    }

    public void displayWrongPlacement(Coordinates coords) {
        this.selectedCoordinates = coords;
        this.infoLabel.setVisible(true);
        this.infoLabel.setText(String.format("Can't place on %d:%d", coords.getX(), coords.getY()));
        setInputVisibility(false);
    }

    public void displayGoodPlacement(Coordinates coords) {
        this.selectedCoordinates = coords;
        this.infoLabel.setVisible(true);
        this.infoLabel.setText(String.format("Do you really want to place on %d:%d ?", coords.getX(), coords.getY()));
        setInputVisibility(true);
        this.revalidate();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth(), getParent().getHeight() / 4);
    }

    private void updatePreview() {
        if (this.tileImagePanel == null) return;

        String orientation = (String) orientationCombo.getSelectedItem();
        if (orientation != null && !orientation.isEmpty()) {
            this.tileImagePanel.setRotation(orientation.charAt(0));
        }

        String pos = meeplePositionField.getText().trim();
        if (pos.length() >= 2 && !currentBaseTileName.contains("empty")) {
            try {
                char edge = pos.charAt(0);
                int index = Integer.parseInt(pos.substring(1));
                String color = this.playerController.getGui().getBoardPanel().idToColor.get(this.playerController.getId()); 
                this.tileImagePanel.setMeeple("regular", color, edge, index, new TileBuilder().build(orientation.charAt(0) + this.currentBaseTileName).getEdge(Direction.fromChar(edge)).getSize());
            } catch (ImageNotFoundException | WrongTileSyntaxException e){
                e.printStackTrace();
            }
        } else {
            this.tileImagePanel.removeMeeple();
        }
    }

}