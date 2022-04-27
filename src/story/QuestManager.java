package story;

import main.Game;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {

    private final List<Quest> quests;

    public QuestManager() {
        quests = new ArrayList<>();
    }

    public void update(Game game){
        quests.stream().filter(quest -> quest.isActive()).
                forEach(quest -> quest.update(game));
    }

    public void addQuest(Quest quest){
        quests.add(quest);
    }
}
