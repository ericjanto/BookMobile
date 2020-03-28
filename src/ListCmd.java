import java.util.List;
import java.util.Objects;

/**
 * List command used to display all books in library.
 */
public class ListCmd extends LibraryCommand {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** Specifies command argument for displaying a short list. */
    private static final String shortListType = "short"; // TODO enum instead bro
    /** Specifies command argument for displaying a long list. */
    private static final String longListType = "long";

    /** Type of list to display. */
    private String listType;

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /**
     * Create a list command.
     *
     * @param argumentInput is expected to be either "short", "long", or blank.
     * @throws IllegalArgumentException if given argument is invalid.
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------

    /**
     * Print a book list header according to book list size.
     *
     * @param data book data in library.
     * @return true if library contains books, otherwise false.
     */
    private boolean listHeader(LibraryData data) {
        int bookCount = data.getBookData().size();

        if (bookCount > 0) {
            System.out.println(bookCount + " books in library:");
            return true;
        } else {
            System.out.println("The library has no book entries.");
            return false;
        }
    }

    /**
     * Print short book list.
     *
     * @param data book data in library.
     */
    private void displayShort(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        StringBuilder shortList = new StringBuilder();

        for (BookEntry book : books) {
            shortList.append(book.getTitle());
            shortList.append("\n");
        }

        System.out.print(shortList);
    }

    /**
     * Print long book list.
     *
     * @param data book data in library.
     */
    private void displayLong(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        StringBuilder longList = new StringBuilder();

        for (BookEntry book : books) {
            longList.append(book.toString());
            longList.append("\n\n");
        }

        System.out.print(longList);
    }

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Check for validity of input and remember it in class field if valid.
     * Is expected to be either "short", "long", or blank.
     *
     * @param argumentInput argument input for this command.
     * @return true if valid input, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (shortListType.equals(argumentInput) || longListType.equals(argumentInput) ||
                argumentInput.isBlank()) {
            listType = argumentInput;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Execute the list command.
     * Prints according to listType either long or short book list.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Provided library data for ListCmd execution must not be null.");

        if (listHeader(data)) {
            switch (listType) { // TODO enum instead
                case "":
                case shortListType:
                    displayShort(data);
                    break;
                case longListType:
                    displayLong(data);
            }
        }
    }
}
