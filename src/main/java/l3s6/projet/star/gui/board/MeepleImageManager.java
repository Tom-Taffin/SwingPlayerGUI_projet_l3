package l3s6.projet.star.gui.board;

import javax.imageio.ImageIO;
import java.io.*;

public class MeepleImageManager extends ImageManager{

    private static MeepleImageManager instance;

    private MeepleImageManager() {}

    public static MeepleImageManager getInstance() {
        if (instance == null) {
            instance = new MeepleImageManager();
        }
        return instance;
    }

    public void loadResources(String mappingFilePath) throws IOException {
        for (char c = 'A'; c <= 'E'; c++) {
            File file = new File("img/Base_Game_C3_Meeple_" + c + ".png");
            if (file.exists()) {
                imageCache.put(c, ImageIO.read(file));
            }
        }
        loadMapping(mappingFilePath);
    }

}