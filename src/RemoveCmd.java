import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * Remove command used to remove books in library by author or title.
 */
public class RemoveCmd extends LibraryCommand {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** TODO */
    private static final int AUTHOR_INDEX = "AUTHOR".length();

    /** TODO */
    private static final int TITLE_INDEX = "TITLE".length();

    /** TODO */
    private String removeType;

    /** TODO */
    private String removeValue;

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /**
     * TODO
     * @param argumentInput
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------

    /**
     * TODO
     * @param input
     * @return
     */
    private boolean parseAuthor(String input) {
        String potentialType = "";
        if (input.length() >= AUTHOR_INDEX) {
            potentialType = input.substring(0, AUTHOR_INDEX);
        }

        if (potentialType.equals("AUTHOR")) { // TODO Enum for "AUTHOR"
            removeType = "AUTHOR";

            if (!input.substring(AUTHOR_INDEX).isBlank()) {
                removeValue = input.substring(AUTHOR_INDEX + 1);
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * TODO merge with removeByAuthor & avoid code duplication
     * @param input
     * @return
     */
    private boolean parseTitle(String input) {
        String potentialType = "";
        if (input.length() >= TITLE_INDEX) {
            potentialType = input.substring(0, TITLE_INDEX);
        }

        if (potentialType.equals("TITLE")) { // TODO Enum for "TITLE"
            removeType = "TITLE";

            if (!input.substring(TITLE_INDEX).isBlank()) {
                removeValue = input.substring(TITLE_INDEX + 1);
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * TODO
     * @param data
     */
    private void removeByAuthor(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        int originalSize = books.size();
        int resultSize;

        books.removeIf(bookEntry -> asList(bookEntry.getAuthors()).contains(removeValue));
        resultSize = books.size();

        if (originalSize == resultSize) {
            System.out.println(/*removeValue + " not found. */"0 books removed for author: " + removeValue);
        } else {
            int bookRmvCount = originalSize - resultSize;
            System.out.println(bookRmvCount + " books removed for author: " + removeValue);
        }

        // TODO books removed for author -> extract
        // TODO merge with rmvByTitle
    }

    /**
     * TODO
     * @param data
     */
    private void removeByTitle(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        int originalSize = books.size();
        int resultSize;

        books.removeIf(bookEntry -> bookEntry.getTitle().equals(removeValue));
        resultSize = books.size();

        if (originalSize == resultSize) {
            System.out.println(removeValue + ": not found.");
        } else {
            System.out.println(removeValue + ": removed successfully.");
        }
    }

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * TODO
     * @param argumentInput argument input for this command
     * @return
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        return parseAuthor(argumentInput) || parseTitle(argumentInput);
    }

    /**
     * TODO
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Provided library data for RemoveCmd must not be null.");

        switch (removeType) {
            case "AUTHOR" :
                removeByAuthor(data);
                break;
            case "TITLE" :
                removeByTitle(data);
        }
    }
}
