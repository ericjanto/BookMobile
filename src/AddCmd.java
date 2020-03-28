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

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Check for validity of input, i.e. file type.
     * Parse if valid.
     *
     * @param argumentInput argument input for this command.
     * @return true if valid input, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput.endsWith(VALID_FILE_SUFFIX)) {
            libraryFilePath = Paths.get(argumentInput);
            return true;
        } else {
            System.err.printf("ERROR: Invalid argument for ADD command: %s", argumentInput);
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
        Objects.requireNonNull(data, "Provided library data for AddCmd execution must not be null.");
        data.loadData(libraryFilePath);
    }
}
