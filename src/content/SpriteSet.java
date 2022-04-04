package content;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Jern
 * A sprite set contains all animations for an entity, for example walking and standing animations.
 */
public class SpriteSet {

    private Map<String, Image> animationSheets;

    public SpriteSet(){
        this.animationSheets = new HashMap<>();
    }

    public void addSheet(String name, Image animationSheet){
        animationSheets.put(name, animationSheet);
    }

    public Image get(String name){
        return animationSheets.get(name);
    }
}
