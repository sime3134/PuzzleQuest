package main.state;

import main.Game;
import settings.Settings;
import story.quests.Quest;
import ui.Spacing;
import ui.UIComponent;
import ui.UIHorizontalDivider;
import ui.UIVerticalDivider;
import ui.clickable.QuestText;
import ui.clickable.UIText;
import ui.containers.HorizontalContainer;
import ui.containers.UIContainer;
import ui.containers.VerticalContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuestViewState extends State {

    private UIContainer questList;

    private UIText questStepText;

    private UIText questDescText;

    private UIText questName;

    private int currentIndex;

    public QuestViewState() {
        super();
        currentIndex = 0;
    }

    public void setClickedQuest(Game game, int questId) {
        List<Quest> activeQuests = new ArrayList<>();
        for(Quest quest : game.getGameState().getQuestManager().getQuests()){
            if(quest.isActive()){
                activeQuests.add(quest);
            }
        }
        int index = getIndexFromQuestId(activeQuests, questId);
        if(index != -1) {
            currentIndex = index;
            questName.setText(activeQuests.get(index).getName());
            questStepText.setText("- " + activeQuests.get(index).getCurrentStep().getName());
            questDescText.setText(activeQuests.get(index).getDescription());

            List<UIComponent> questTexts = questList.getChildren();
            for (UIComponent component : questTexts) {
                QuestText questText = (QuestText) component;
                questText.setUnderlined(false);

                if (questText.getQuestId() == questId) {
                    questText.setUnderlined(true);
                }
            }
        }
    }

    private int getIndexFromQuestId(List<Quest> activeQuests, int questId) {
        for(int i = 0; i < activeQuests.size(); i++) {
            if(activeQuests.get(i).getId() == questId){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void setupUI() {
        super.setupUI();

        UIContainer mainContainer = new VerticalContainer();
        mainContainer.setFixedWidth(Settings.getScreenWidth());
        mainContainer.setFixedHeight(Settings.getScreenHeight());
        mainContainer.setCenterChildren(true);
        mainContainer.setMargin(new Spacing(0));
        mainContainer.setPadding(new Spacing(0));

        UIContainer activeQuests = new VerticalContainer(new UIText("Active quests"));
        activeQuests.setBackgroundColor(Color.DARK_GRAY);
        activeQuests.setBorderColor(Color.WHITE);

        UIContainer questMenu = new HorizontalContainer();
        questMenu.setFixedWidth(Settings.getScreenWidth() - 60);
        questMenu.setFixedHeight(Settings.getScreenHeight() - 80);
        questMenu.setCenterChildren(true);
        questMenu.setBackgroundColor(Color.DARK_GRAY);
        questMenu.setBorderColor(Color.WHITE);
        questMenu.setMargin(0);
        questMenu.setPadding(0);

        questList = new VerticalContainer();
        questList.setFixedWidth((int) (questMenu.getFixedWidth() * 0.4));
        questList.setFixedHeight(questMenu.getFixedHeight());
        questList.setCenterChildren(true);
        questList.setMargin(0);
        questList.setPadding(new Spacing(15, 0, 0, 0));

        UIHorizontalDivider divider = new UIHorizontalDivider(questMenu.getFixedHeight() - 15);

        UIContainer questDescOuterContainer = new VerticalContainer();
        questDescOuterContainer.setFixedWidth((int) (questMenu.getFixedWidth() * 0.6));
        questDescOuterContainer.setFixedHeight(questMenu.getFixedHeight());
        questDescOuterContainer.setCenterChildren(true);
        questDescOuterContainer.setPadding(new Spacing(15, 0, 0, 0));
        questDescOuterContainer.setMargin(0);

        questStepText = new UIText("");
        questStepText.setFontSize(12);
        questDescText = new UIText("");
        questDescText.setFontSize(10);
        questName = new UIText("");
        questName.setFontSize(20);

        UIContainer questDescInnerContainer = new VerticalContainer();
        questDescInnerContainer.setFixedWidth(questDescOuterContainer.getFixedWidth());
        questDescInnerContainer.setFixedHeight(questMenu.getFixedHeight());
        questDescInnerContainer.setCenterChildren(true);
        questDescInnerContainer.setPadding(new Spacing(15, 0, 0, 0));
        questDescInnerContainer.setMargin(0);

        UIText currentStep = new UIText("Current step", 20);
        currentStep.setPadding(new Spacing(0, 0, 15, 0));

        questDescInnerContainer.addComponent(questDescText);
        questDescInnerContainer.addComponent(new UIVerticalDivider(questDescInnerContainer.getFixedWidth() - 25));
        questDescInnerContainer.addComponent(currentStep);
        questDescInnerContainer.addComponent(questStepText);

        questDescOuterContainer.addComponent(questName);
        questDescOuterContainer.addComponent(questDescInnerContainer);

        questMenu.addComponent(questList);
        questMenu.addComponent(divider);
        questMenu.addComponent(questDescOuterContainer);

        mainContainer.addComponent(activeQuests);
        mainContainer.addComponent(questMenu);

        uiContainers.add(mainContainer);
    }

    public void updateQuestMenu(GameState gameState){
        questList.clear();

        List<Quest> activeQuests = new ArrayList<>();
        for(Quest quest : gameState.getQuestManager().getQuests()){
            if(quest.isActive()){
                activeQuests.add(quest);
            }
        }

        for(int i = 0; i < activeQuests.size(); i++){
            QuestText questText = new QuestText(activeQuests.get(i).getName(), this, activeQuests.get(i).getId());
            questList.addComponent(questText);

            if(i == currentIndex){
                questText.setUnderlined(true);
            }
        }

        if(!activeQuests.isEmpty()) {
            questStepText.setText("- " + activeQuests.get(currentIndex).getCurrentStep().getName());
            questName.setText(activeQuests.get(currentIndex).getName());
            questDescText.setText(activeQuests.get(currentIndex).getDescription());
        }

    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.resumeGame();
    }

    public void QButtonPressed(Game game){
        game.resumeGame();
    }
}
