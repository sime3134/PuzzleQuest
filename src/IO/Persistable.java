package IO;

/**
 * @author Simon Jern
 * Interface for classes that should be able to save and load data to file.
 */
public interface Persistable {

    String DELIMITER = ":";
    String SECTION_DELIMETER = System.lineSeparator() + "###" + System.lineSeparator();
    String INNER_SECTION_DELIMETER = System.lineSeparator() + "&&&" + System.lineSeparator();
    String LIST_DELIMETER = ",";
    String COLUMN_DELIMETER = System.lineSeparator();

    String serialize();
    void applySerializedData(String serializedData);
}
