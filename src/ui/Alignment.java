package ui;

public class Alignment {

    public enum Horizontal {
        LEFT, CENTER, RIGHT
    }
    public enum Vertical {
        TOP, CENTER, BOTTOM
    }

    private final Horizontal horizontal;
    private final Vertical vertical;

    public Alignment(Horizontal horizontal, Vertical vertical){
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public Horizontal getHorizontal() {
        return horizontal;
    }

    public Vertical getVertical() {
        return vertical;
    }
}
