package ui.clickable;

import main.Game;
import main.state.QuestViewState;

public class QuestText extends UIText{

    private final QuestViewState questState;
    private final int questId;

    public int getQuestId() {
        return questId;
    }

    public QuestText(String text, QuestViewState questState, int questId) {
        super(text);
        this.questState = questState;
        this.questId = questId;
    }

    @Override
    public void onClick(Game game) {
        super.onClick(game);
        questState.setClickedQuest(game, questId);
    }
}
