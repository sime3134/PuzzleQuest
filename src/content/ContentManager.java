package content;

import utilities.ImgUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Jern
 * Used to load different content to the game. For example spritesheets, tiles and music.
 */
public class ContentManager {

    private final Map<String, SpriteSet> spriteSets;
    private final Map<String, Image> images;
    private Font font;

    public ContentManager(){
        spriteSets = new HashMap<>();
        images = new HashMap<>();
    }

    public void loadContent() {
        loadSpriteSets("/sprites/gameObjects");
        loadImages("/sprites/tiles");
        loadImages("/sprites/scenery");
        loadImages("/sprites/icons");
        loadFont("/font/joystix.ttf");
    }

    private void loadFont(String filePath) {
        URL resource = ContentManager.class.getResource(filePath);
        File fontFile = new File(resource.getFile());
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
    }

    private void loadImages(String filePath) {
        String[] imagesInFolder = getImagesInFolder(filePath);

        for(String fileName : imagesInFolder){
            images.put(fileName.substring(0, fileName.length() - 4),
                    ImgUtils.loadImage(filePath + "/" + fileName));
        }
    }

    private void loadSpriteSets(String filePath) {
        String[] folderNames = getFolderNames(filePath);

        for(String folderName : folderNames){
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = filePath + "/" + folderName;
            String[] sheetsInFolder = getImagesInFolder(pathToFolder);

            for(String sheetName : sheetsInFolder){
                spriteSet.addSheet(sheetName.substring(0, sheetName.length() - 4),
                        ImgUtils.loadImage(pathToFolder + "/" + sheetName));
            }

            spriteSets.put(folderName, spriteSet);
        }}

    private String[] getImagesInFolder(String basePath) {
        URL resource = ContentManager.class.getResource(basePath);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isFile());
    }

    private String[] getFolderNames(String basePath) {
        URL resource = ContentManager.class.getResource(basePath);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

    public SpriteSet getSpriteSet(String name) {
        return spriteSets.get(name);
    }

    public Image getImage(String name) {
        return images.get(name);
    }
}
