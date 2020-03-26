import java.util.List;
import java.util.Objects;

/**
 * List command used to display all books in library.
 */
public class ListCmd extends LibraryCommand {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** Contains all valid input arguments for list command. Blank included. */
    private static final String VALID_INPUT = "shortlong";

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
     * Print a book list header.
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

        for (BookEntry book : books) {
            System.out.println(book.getTitle()); // TODO StringBuilder
        }
    }

    /**
     * Print long book list.
     *
     * @param data book data in library.
     */
    private void displayLong(LibraryData data) {
        List<BookEntry> books = data.getBookData();

        for (BookEntry book : books) {
            System.out.println(book.toString() + "\n");
        }
    }

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Check for validity of input and remember it in class field if valid.
     *
     * @param argumentInput argument input for this command.
     *                      Is expected to be either "short", "long", or blank.
     * @return true if valid input, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (VALID_INPUT.contains(argumentInput)) {
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

        // no additional error handling / default branch for unexpected listTypes needed since
        // possible input errors are caught when constructing ListCmd

        if (listHeader(data)) {
            switch (listType) {
                case "":
                case "short":
                    displayShort(data);
                    break;
                case "long":
                    displayLong(data);
            }
        }
    }
}
