package ui;

import core.Vector2D;
import display.Camera;
import entity.GameObject;

public class RelativeContainer extends HorizontalContainer{

    GameObject objectToFollow;
    Camera camera;

    public RelativeContainer(GameObject objectToFollow, Camera camera){
        super();
        this.objectToFollow = objectToFollow;
        this.camera = camera;
    }

    @Override
    public void calculatePosition(){
        if(objectToFollow != null) {
            this.position = objectToFollow.getRenderPosition(camera);
            position.subtract(new Vector2D(5, 20));
            calculateContentPosition();
        }
    }
}
