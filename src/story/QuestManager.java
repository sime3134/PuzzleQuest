package story;

import IO.Persistable;
import main.Game;
import story.quests.Quest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Manages all quests in the game.
 */
public class QuestManager implements Persistable {

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

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < quests.size(); i++){
            sb.append(quests.get(i).serialize());
            if(i < quests.size() - 1) {
                sb.append(COLUMN_DELIMETER);
            }
        }

        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] questStrings = serializedData.split(COLUMN_DELIMETER);

        for(String questString : questStrings){
            String[] tokens = questString.split(DELIMITER);
            for(Quest quest : quests){
                if(quest.getId() == Integer.parseInt(tokens[1])){
                    quest.applySerializedData(questString);
                }
            }
        }
    }
}
