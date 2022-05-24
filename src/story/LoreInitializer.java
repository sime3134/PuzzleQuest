package story;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import entity.Scenery;
import main.Game;
import story.quests.*;
import ui.RelativeUIText;
import ui.containers.RelativeContainer;

/**
 * @author Simon Jern
 * Initializes all quests in the game.
 */
public class LoreInitializer {

    public void initialize(Game game, QuestManager questManager) {
        initializeQuests(game, questManager);

        addSceneryToOverwrite(game);

        initializeGuardianDialog(game);
    }

    private void initializeGuardianDialog(Game game) {
        NPC jester = (NPC) game.getGameObjectById(100000);
        NPC locky = (NPC) game.getGameObjectById(90147);
        NPC stocky = (NPC) game.getGameObjectById(90149);
        Dialog holeGuardiansDialog = new Dialog();

        holeGuardiansDialog.addLine(new DialogLine("You can't go down here, a lot of people has gotten lost here."));

        jester.addDialog(holeGuardiansDialog);
        locky.addDialog(holeGuardiansDialog);
        stocky.addDialog(holeGuardiansDialog);

        Dialog lockyDialog = new Dialog();
        lockyDialog.addLine(new DialogLine("If you're really going down here, be careful."));
        locky.addDialog(lockyDialog);

        Dialog stockyDialog = new Dialog();
        stockyDialog.addLine(new DialogLine("Jester went on a lunch break.."));
        stocky.addDialog(stockyDialog);

        Dialog emptyDialog = new Dialog();
        emptyDialog.addLine(new DialogLine(""));
        emptyDialog.setActive(false);
        jester.addDialog(emptyDialog);
    }

    private void addSceneryToOverwrite(Game game) {
        game.addSceneryToOverwrite((Scenery) game.getGameObjectById(34000));
        game.addSceneryToOverwrite((Scenery) game.getGameObjectById(10061));
        game.addSceneryToOverwrite((Scenery) game.getGameObjectById(10656));
    }

    private void initializeQuests(Game game, QuestManager questManager) {
        questManager.addQuest(new BornAnew(game,0));
        questManager.addQuest(new Maze(game, 1));
        questManager.addQuest(new ForestMystery(game,2));
        questManager.addQuest(new CatchUpWithBill(game,3));
        questManager.addQuest(new OceanChest(game,4));
        questManager.addQuest(new MedallionHandOver(game,5));
    }

    public void initializeNameTags(Game game) {
        NPC bill = (NPC) game.getGameObjectById(19554);
        NPC isak = (NPC) game.getGameObjectById(89743);
        NPC joffrey = (NPC) game.getGameObjectById(89742);
        NPC akira = (NPC) game.getGameObjectById(89759);
        NPC mensah = (NPC) game.getGameObjectById(90144);
        NPC luna = (NPC) game.getGameObjectById(89736);
        NPC komako = (NPC) game.getGameObjectById(90121);

        RelativeContainer billTag = new RelativeContainer(bill, game.getCamera());
        billTag.addComponent(new RelativeUIText("Bill", bill));
        game.getGameState().addUIContainer(billTag);

        RelativeContainer isakTag = new RelativeContainer(isak, game.getCamera());
        isakTag.addComponent(new RelativeUIText("Isak", isak));
        game.getGameState().addUIContainer(isakTag);

        RelativeContainer joffreyTag = new RelativeContainer(joffrey,
                game.getCamera());
        joffreyTag.addComponent(new RelativeUIText("Lord Joffrey", joffrey));
        game.getGameState().addUIContainer(joffreyTag);

        RelativeContainer akiraTag = new RelativeContainer(akira,
                game.getCamera());
        akiraTag.addComponent(new RelativeUIText("Akira", akira));
        game.getGameState().addUIContainer(akiraTag);

        RelativeContainer mensahTag = new RelativeContainer(mensah,
                game.getCamera());
        mensahTag.addComponent(new RelativeUIText("Mensah", mensah));
        game.getGameState().addUIContainer(mensahTag);

        RelativeContainer lunaTag = new RelativeContainer(luna,
                game.getCamera());
        lunaTag.addComponent(new RelativeUIText("Luna", luna));
        game.getGameState().addUIContainer(lunaTag);

        RelativeContainer komakoTag = new RelativeContainer(komako,
                game.getCamera());
        komakoTag.addComponent(new RelativeUIText("Komako", komako));
        game.getGameState().addUIContainer(komakoTag);
    }
}
