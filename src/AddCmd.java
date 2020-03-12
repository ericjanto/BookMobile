/**
 * Add command allowing to add books to library database.
 */
public class AddCmd extends LibraryCommand {

    /** Saves the parsed input enabling later use */
    private
    /**
     * Create an add command.
     *
     * @param argumentInput argument input is expected to be a valid path
     *                      to either a single file or a subfolder.
     * @throws IllegalArgumentException if given arguments are invalid, i.e. the path.
     * @throws NullPointerException if given argumentInput is null.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    @Override
    protected boolean parseArguments(String argumentInput) {

    }

    @Override
    public void execute(LibraryData data) {

    }
}
