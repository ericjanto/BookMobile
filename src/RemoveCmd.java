import java.util.Iterator;
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
     * Create a remove command.
     * Input is expected to be TITLE | AUTHOR followed by a non-blank string.
     *
     * @param argumentInput command argument input.
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------


    /**
     * TODO
     * @param input command argument input.
     * @return  true if valid input for remove type TITLE, otherwise false
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
     * TODO merge with parseAuthor
     * @param input command argument input.
     * @return  true if valid input for remove type TITLE, otherwise false
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
     * Remove books written by author as specified in class field removeValue.
     *
     * @param data library data containing book entries.
     */
    private void removeByAuthor(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        Iterator<BookEntry> bookIterator = books.iterator();
        int originalSize = books.size();
        int resultSize;

        while (bookIterator.hasNext()) {
            List<String> bookAuthors = asList(bookIterator.next().getAuthors());

            if (bookAuthors.contains(removeValue)) {
                bookIterator.remove();
            }
        }

        resultSize = books.size();

        if (originalSize == resultSize) {
            System.out.printf("0 books removed for author: %s", removeValue);
        } else {
            int bookRmvCount = originalSize - resultSize;
            System.out.printf("%d books removed for author: %s", bookRmvCount, removeValue);
        }
    }

    /**
     * Remove book with title as specified in class field removeValue.
     *
     * @param data library data containing book entries.
     */
    private void removeByTitle(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        Iterator<BookEntry> bookIterator = books.iterator();
        int originalSize = books.size();
        int resultSize;

        while (bookIterator.hasNext()) {
            String title = bookIterator.next().getTitle();
            if (title.equals(removeValue)) {
                bookIterator.remove();
                break;                        // A title appears only once within a library. Can omit rest of iteration.
            }
        }

        resultSize = books.size();

        if (originalSize == resultSize) {
            System.out.printf("%s: not found.", removeValue);
        } else {
            System.out.printf("%s: removed successfully.", removeValue);
        }
    }

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Parse given argument input accordingly.
     *
     * @param argumentInput argument input for this command.
     * @return true if valid argument input, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        return parseAuthor(argumentInput) || parseTitle(argumentInput);
    }

    /**
     * Execute remove command.
     *
     * @param data library data containing book entries.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Provided library data for RemoveCmd must not be null.");

        switch (removeType) {
            case "AUTHOR" :
                removeByAuthor(data);
                break;
            case "TITLE":
                removeByTitle(data);
        }
    }
}
