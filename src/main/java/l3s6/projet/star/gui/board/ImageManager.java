package l3s6.projet.star.gui.board;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class ImageManager {

    protected final Map<Character, Image> imageCache = new HashMap<>();
    protected final Map<String, Character> nameToLetter = new HashMap<>();

    public abstract void loadResources(String mappingFilePath) throws IOException;

    protected void loadMapping(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    char letter = parts[0].trim().toUpperCase().charAt(0);
                    String tileName = parts[1].trim();
                    nameToLetter.put(tileName, letter);
                }
            }
        }
    }

    public Character getCharacter(String name) throws ImageNotFoundException {
        Character letter = nameToLetter.get(name);
        if (letter != null) {
            return letter;
        } else {
            throw new ImageNotFoundException(name + " not found.");
        }
    }

    public Image getImage(String name) throws ImageNotFoundException {
        Character letter = this.getCharacter(name);
        Image img = imageCache.get(letter);
        if (img != null) {
            return img;
        } else {
            throw new ImageNotFoundException(name + " not found.");
        }
    }
}