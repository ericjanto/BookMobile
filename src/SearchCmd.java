import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Search command used to search for book titles in library.
 */
public class SearchCmd extends LibraryCommand {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** Provided argument input search value for list command. */
    private String searchValue;

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /**
     * Create a search command.
     *
     * @param argumentInput argument input for this command.
     * @throws IllegalArgumentException if given argument is invalid.
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------

    /**
     * Check if given argument is valid search value.
     *
     * @param value string to be checked.
     * @return true if valid search value, otherwise false.
     */
    private boolean isValidSearchValue(String value) {
        return !value.isBlank() && !value.contains(" ");
    }

    /**
     * Checks if book title of parameter contains search value.
     *
     * @param book bookEntry to be checked.
     * @return true if valid book title, otherwise false.
     */
    private boolean isValidTitle(BookEntry book) {
        String bookTitle = book.getTitle().toLowerCase();

        return bookTitle.contains(searchValue.toLowerCase());
    }

    /**
     * Prints all titles provided in parameter list. If empty list,
     * prints according message.
     *
     * @param titles list containing book titles.
     */
    private void printTitles(ArrayList<String> titles) {
        if (titles.size() == 0) {
            System.out.println("No hits found for search term: " + searchValue);
        } else {
            StringBuilder bd = new StringBuilder();

            for (String title : titles) {
                bd.append(title).append("\n");
            }

            bd.deleteCharAt(bd.length() - 1); // Remove last line break.
            System.out.println(bd);
        }
    }

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Check for validity of input and remember it in class field if valid.
     * Is expected to be a single word search value.
     * Case insensitive.
     *
     * @param argumentInput argument input for this command.
     * @return true if valid input, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (isValidSearchValue(argumentInput)) {
            searchValue = argumentInput;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Execute the search command.
     * Search in book library for book titles using the search value.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Provided library data for SearchCmd must not be null.");

        List<BookEntry> books = data.getBookData();
        ArrayList<String> foundTitles = new ArrayList<>();

        for (BookEntry book : books) {   // TODO find more effective algorithm? & extract to helper method
            if (isValidTitle(book)) {
                foundTitles.add(book.getTitle());
            }
        }

        printTitles(foundTitles);
    }
}
