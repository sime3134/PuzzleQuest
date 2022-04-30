package content;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Jern
 * A sprite set contains all animations for an entity, for example walking and standing animations.
 */
public class SpriteSet {
    private final Map<String, Image> animationSheets;

    private final String name;

    public String getName() {
        return name;
    }

    public SpriteSet(String name){
        this.animationSheets = new HashMap<>();
        this.name = name;
    }

    public void addSheet(String name, Image animationSheet){
        animationSheets.put(name, animationSheet);
    }

    public Image get(String name){
        return animationSheets.get(name);
    }
}
