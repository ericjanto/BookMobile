import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Add command used to add books to library database.
 */
public class AddCmd extends LibraryCommand {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** Sets valid file type for argument input. */
    private static final String VALID_FILE_SUFFIX = ".csv";

    /** Saves parsed command argument as a string, enabling later use. */
    private Path libraryFilePath;

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /**
     * Create an add command.
     *
     * @param argumentInput argument input is expected to represent a valid path.
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Check for validity of input, i.e. file type,
     * and parse if valid.
     *
     * @param argumentInput argument input for this command.
     * @return true if valid input, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        try {
            Path input = Paths.get(argumentInput);
            if (input.toString().endsWith(VALID_FILE_SUFFIX)) {
                libraryFilePath = input;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Invalid path to file: " + e);
            return false;
        }
    }

    /**
     * Execute the add command.
     * Calls method to load data from file.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data);
        data.loadData(libraryFilePath);
    }
}
