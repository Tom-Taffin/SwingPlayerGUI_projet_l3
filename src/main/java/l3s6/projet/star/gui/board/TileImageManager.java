package l3s6.projet.star.gui.board;

import javax.imageio.ImageIO;
import java.io.*;

public class TileImageManager extends ImageManager {

    private static TileImageManager instance;

    private TileImageManager() {}

    public static TileImageManager getInstance() {
        if (instance == null) {
            instance = new TileImageManager();
        }
        return instance;
    }

    public void loadResources(String mappingFilePath) throws IOException {
        for (char c = 'A'; c <= 'X'; c++) {
            File file = new File("img/Base_Game_C3_Tile_" + c + ".png");
            if (file.exists()) {
                this.imageCache.put(c, ImageIO.read(file));
            }
        }
        File file = new File("img/Base_Game_C3_Empty.png");
        if (file.exists()) {
            this.imageCache.put('_', ImageIO.read(file));
        }
        this.loadMapping(mappingFilePath);
    }

}