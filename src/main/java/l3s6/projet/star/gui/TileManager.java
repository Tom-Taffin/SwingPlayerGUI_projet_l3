package l3s6.projet.star.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TileManager {

    private static TileManager instance;
    private final Map<Character, Image> imageCache = new HashMap<>();
    private final Map<String, Character> nameToLetter = new HashMap<>();

    private TileManager() {}

    public static TileManager getInstance() {
        if (instance == null) {
            instance = new TileManager();
        }
        return instance;
    }

    public void loadResources(String mappingFilePath) throws IOException {
        for (char c = 'A'; c <= 'X'; c++) {
            File file = new File("img/Base_Game_C3_Tile_" + c + ".png");
            if (file.exists()) {
                imageCache.put(c, ImageIO.read(file));
            }
        }
        loadMapping(mappingFilePath);
    }

    private void loadMapping(String filePath) throws IOException {
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

    public Image getImage(String tileName) {
        Character letter = nameToLetter.get(tileName);
        return (letter != null) ? imageCache.get(letter) : null;
    }
}