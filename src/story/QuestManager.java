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

    public List<Quest> getActiveQuests() {
        return quests.stream()
                .filter(quest -> quest.isActive()).toList();
    }

    public void initializeQuests(Game game) {
        new QuestInitializer().initializeQuests(game, this);
    }

    public void startQuest(int id) {
        for(Quest quest : quests){
            if(quest.getId() == id && !quest.isActive()){
                quest.initialize();
                break;
            }
        }
    }
}
