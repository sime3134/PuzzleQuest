package main.state;

import IO.Persistable;
import controller.PlayerController;
import core.Direction;
import core.Vector2D;
import dialog.DialogManager;
import entity.Player;
import main.Game;
import settings.Settings;
import story.LoreInitializer;
import story.QuestManager;
import ui.Alignment;
import ui.clickable.UIText;
import ui.containers.HorizontalContainer;
import ui.containers.UIContainer;
import ui.containers.VerticalContainer;

import java.awt.*;

/**
 * @author Simon Jern, Johan Salomonsson
 * The class containing all the front logic for the game. This class should not contain any complicated or overly long
 * methods but instead pair together several components of the game such as the player, NPCs, content etc.
 */
public class GameState extends State implements Persistable {

    private boolean nonNPCDialogActive;
    private Player player;

    private final QuestManager questManager;

    private boolean repeatMap;

    private UIText medallionText;
    private int medallionsCollected = 0;

    private boolean mazeMedallionFound;

    private UIContainer dialogContainer;
    private UIText currentDialogLine;

    private final DialogManager dialogManager;

    private final String[][] worldMap = {

            {"map1", "map2", "map3", "map4", "map5"},
            {"map6", "map7", "map8", "map9", "map10"},
            {"map11", "map12", "map13", "map14", "map15"},
            {"map16", "map17", "map18", "map19", "map20"},
            {"map21", "map22", "map23", "map24", "map25"}
    };

    //region Getters & Setters (click to view)

    public boolean isMazeMedallionFound() {
        return mazeMedallionFound;
    }

    public void setMazeMedallionFound(boolean mazeMedallionFound) {
        this.mazeMedallionFound = mazeMedallionFound;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    public Player getPlayer() {
        return player;
    }

    public String[][] getWorldMap() {
        return worldMap;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public void setNonNPCDialogActive(boolean nonNPCDialogActive) {
        this.nonNPCDialogActive = nonNPCDialogActive;
    }

    public boolean getNonNPCDialogActive() {
        return nonNPCDialogActive;
    }

    //endregion

    public GameState(Game game){
        super();
        questManager = new QuestManager();
        dialogManager = new DialogManager();
        initializePlayer(game);
    }

    private void initializePlayer(Game game) {
        player = new Player(PlayerController.getInstance(),
                game.getContent().getSpriteSet("player"), "PlayerName");
    }

    @Override
    public void update(Game game) {
        super.update(game);

        handleWorldMapLocation(game);
        questManager.update(game);
    }

    private void handleWorldMapLocation(Game game) {
        Direction direction = player.findDirectionToMapBorder(game.getCurrentMap());

        if (direction != Direction.NULL) {

            Vector2D directionValue = Direction.toVelocity(direction);

            if (!repeatMap) {

                if (player.getWorldMapPosition().intX() + directionValue.intX() < worldMap.length
                        && player.getWorldMapPosition().intX() + directionValue.intX() >= 0
                        && player.getWorldMapPosition().intY() + directionValue.intY() < worldMap[0].length
                        && player.getWorldMapPosition().intY() + directionValue.intY() >= 0) {

                    player.getWorldMapPosition().add(directionValue);

                    setPlayerPositionFromDirectionToMapBorder(game, direction);

                        game.loadMap(worldMap[player.getWorldMapPosition().intX()]
                                [player.getWorldMapPosition().intY()]);
                }
            }else{
                setPlayerPositionFromDirectionToMapBorder(game, direction);
                game.loadMap(worldMap[0][0]);
            }
        }
    }

    private void setPlayerPositionFromDirectionToMapBorder(Game game, Direction direction) {
        switch (direction) {
            case RIGHT -> player.setPosition(new Vector2D(0, player.getPosition().getY()));
            case LEFT -> player.setPosition(new Vector2D(game.getCurrentMap().getWidth() - (double)player.getWidth(),
                    player.getPosition().getY()));
            case UP -> player.setPosition(new Vector2D(player.getPosition().getX(),
                    game.getCurrentMap().getHeight() - (double)player.getHeight()));
            case DOWN -> player.setPosition(new Vector2D(player.getPosition().getX(), 0));
        }
    }

    @Override
    public void setupUI() {
        super.setupUI();

        UIContainer medallions = new VerticalContainer();
        medallions.setAlignment(new Alignment(Alignment.Horizontal.RIGHT, Alignment.Vertical.TOP));
        medallionText = new UIText("Medallions collected: " + medallionsCollected + "/3");
        medallions.addComponent(medallionText);
        uiContainers.add(medallions);

        currentDialogLine = new UIText("");
        dialogContainer = new HorizontalContainer(currentDialogLine);
        dialogContainer.setFixedPosition(true);
        dialogContainer.setFixedWidth(Settings.getScreenWidth() - dialogContainer.getMargin().getHorizontal());
        dialogContainer.setFixedHeight(Settings.getScreenHeight() / 4);
        dialogContainer.setAbsolutePosition(new Vector2D(0f + dialogContainer.getMargin().getHorizontal() / 2f,
                Settings.getScreenHeight() - (double)dialogContainer.getFixedHeight() - dialogContainer.getMargin().getVertical() / 2f));
        dialogContainer.setBackgroundColor(Color.LIGHT_GRAY);
        dialogContainer.setBorderColor(Color.DARK_GRAY);
        dialogContainer.setVisible(false);
        dialogContainer.setCenterChildren(true);
        uiContainers.add(dialogContainer);
    }

    public void increaseMedallionCount(Game game) {
        if(medallionsCollected < 3) {
            medallionsCollected++;
            game.displayNotification("Found a piece of the medallion!");
        }
        medallionText.setText("Medallions collected: " + medallionsCollected + "/3");
        if(medallionsCollected == 3) {
            medallionText.setFontColor(Color.yellow);
            game.displayNotification("You have found all the pieces of the medallion!");
            game.getGameState().getQuestManager().startQuest(game, 5);
        }
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.pauseGame();
    }

    public void QButtonPressed(Game game){
        game.goToQuestViewState();
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName())
                .append(SECTION_DELIMETER)
                .append(player.serialize())
                .append(INNER_SECTION_DELIMETER)
                .append(questManager.serialize())
                .append(INNER_SECTION_DELIMETER)
                .append(medallionsCollected)
                .append(INNER_SECTION_DELIMETER)
                .append(mazeMedallionFound);
        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] sections = serializedData.split(INNER_SECTION_DELIMETER);
        player.applySerializedData(sections[0]);
        if(sections.length > 1) {
            questManager.applySerializedData(sections[1]);
        }
        medallionsCollected = Integer.parseInt(sections[2]);
        mazeMedallionFound = Boolean.parseBoolean(sections[3]);
    }

    public void resetPlayerPosition() {
        player.setPosition(new Vector2D(2145,750));
        player.setWorldMapPosition(new Vector2D(0,4));
        player.setDirection("RIGHT");
    }

    public UIText getDialogText() {
        return currentDialogLine;
    }

    public void hideDialog() {
        dialogContainer.setVisible(false);
    }

    public void showDialog() {
        dialogContainer.setVisible(true);
    }

    public void handleNonNpcDialog(Game game) {
        if(dialogManager.hasDialog()) {
            dialogManager.handleDialog(game);
        }
    }

    public void initializeNewGame(Game game, String playerName) {
        nonNPCDialogActive = true;
        player.setName(playerName);
        resetPlayerPosition();
        questManager.initializeQuests(game);
        medallionsCollected = 0;
        mazeMedallionFound = false;
    }

    public void initializeNameTags(Game game) {
        new LoreInitializer().initializeNameTags(game);
    }
}
