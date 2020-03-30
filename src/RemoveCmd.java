import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import static java.util.Arrays.asList;

/** Remove command used to remove books in library by author or title. */
public class RemoveCmd extends LibraryCommand {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** Specify what kind of argument is given to remove by. */
    private ExecutionType removeBy;

    /** Value to specify which book(s) must be removed. */
    private String removeValue;

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /**
     * Create a remove command.
     * Input is expected to be TITLE or AUTHOR followed by a non-blank string.
     *
     * @param argumentInput command argument input.
     * @throws IllegalArgumentException if given argumentInput is invalid.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------

    /**
     * Parse remove value from given standard-tokenized argument input.
     *
     * @param inputTokenizer standard-tokenized argument input.
     */
    private void parseRemoveValue(StringTokenizer inputTokenizer) {
        StringBuilder removeValueBuilder = new StringBuilder();

        while (inputTokenizer.hasMoreTokens()) {
            removeValueBuilder.append(inputTokenizer.nextToken());
            removeValueBuilder.append(" ");             // final whitespace to be removed with stripTrailing()
        }
        removeValue = removeValueBuilder.toString().stripTrailing();
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
        printAuthorRemoveResult(originalSize, resultSize);
    }

    /**
     * Print message to console in respective to success of removing books of an author.
     *
     * @param originalSize library size before remove attempt.
     * @param resultSize library size after remove attempt.
     */
    private void printAuthorRemoveResult(int originalSize, int resultSize) {
        if (originalSize == resultSize) {
            System.out.printf("0 books removed for author: %s%n", removeValue);
        } else {
            int bookRmvCount = originalSize - resultSize;
            System.out.printf("%d books removed for author: %s%n", bookRmvCount, removeValue);
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
                break;                 // Break of iteration is sufficient: Title is unique in library.
            }
        }

        resultSize = books.size();
        printTitleRemoveResult(originalSize, resultSize);
    }

    /**
     * Print message to console in respective to success of title removing a book.
     *
     * @param originalSize library size before remove attempt.
     * @param resultSize library size after remove attempt.
     */
    private void printTitleRemoveResult(int originalSize, int resultSize) {
        if (originalSize == resultSize) {
            System.out.printf("%s: not found.%n", removeValue);
        } else {
            System.out.printf("%s: removed successfully.%n", removeValue);
        }
    }

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Check for validity of input, i.e. remove type and remove value.
     * Parse if valid.
     *
     * @param argumentInput argument input for this command.
     * @return true if valid input, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        StringTokenizer inputTokenizer = new StringTokenizer(argumentInput);
        String potentialType = "";

        if (inputTokenizer.hasMoreTokens()) {
            potentialType = inputTokenizer.nextToken();
        }

        for (ExecutionType type : ExecutionType.values()) {
            if (type.name().equals(potentialType) &&        // Condition: Remove type is valid.
                    inputTokenizer.hasMoreTokens()) {       // Condition: There follows a non-blank remove value.
                removeBy = type;
                parseRemoveValue(inputTokenizer);
                return true;
            }
        }

        return false;
    }

    /**
     * Execute remove command.
     *
     * @param data library data containing book entries.
     * @throws NullPointerException if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Provided library data for RemoveCmd must not be null.");

        switch (removeBy) {
            case AUTHOR:
                removeByAuthor(data);
                break;
            case TITLE:
                removeByTitle(data);
        }
    }
}
