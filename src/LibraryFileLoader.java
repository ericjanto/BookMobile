import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** Character used to separate data values in file. */
    private static final char DATA_SEPARATOR = ',';
    /** Character used to separate author names. */
    private static final char AUTHOR_SEPARATOR = '-';

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------

    /**
     * Has file content been loaded already?
     *
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Separate data values within a file line.
     *
     * @param line file line to be parsed.
     * @return list containing separated data values.
     */
    private ArrayList<String> separateLineContent(String line) {
        return new ArrayList<>(Arrays.asList(line.split(DATA_SEPARATOR + "")));
    }

    /**
     * Get title from a file line.
     *
     * @param line file line.
     * @return title.
     */
    private String getLineTitle(ArrayList<String> line) {
        return line.get(DataOrder.TITLE.ordinal());
    }

    /**
     * Get author(s) from a file line.
     *
     * @param line file line.
     * @return author(s).
     */
    private String[] getLineAuthors(ArrayList<String> line) {
        int authorIndex = DataOrder.AUTHORS.ordinal();
        return line.get(authorIndex).split(AUTHOR_SEPARATOR + "");
    }

    /**
     * Get rating from a file line.
     *
     * @param line file line.
     * @return rating.
     */
    private float getLineRating(ArrayList<String> line) {
        int ratingIndex = DataOrder.RATING.ordinal();
        return Float.parseFloat(line.get(ratingIndex));
    }

    /**
     * Get ISBN from a file line.
     *
     * @param line file line.
     * @return ISBN.
     */
    private String getLineISBN(ArrayList<String> line) {
        return line.get(DataOrder.ISBN.ordinal());
    }

    /**
     * Get page number from a file line.
     *
     * @param line file line.
     * @return page number.
     */
    private int getLinePages(ArrayList<String> line) {
        int pagesIndex = DataOrder.PAGES.ordinal();
        return Integer.parseInt(line.get(pagesIndex));
    }

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data.
     * @return true if book data could be loaded successfully, false otherwise.
     * @throws NullPointerException if the given file name is null.
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);

            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }
        return success;
    }


    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() {
        ArrayList<BookEntry> newLibrary = new ArrayList<>();

        if (!contentLoaded()) {
            System.err.println("ERROR: No content loaded before parsing.");
        } else {
            Iterator<String> lineIterator = fileContent.iterator();
            lineIterator.next(); // Leave out header line in file

            while (lineIterator.hasNext()) {
                ArrayList<String> bookValues = separateLineContent(lineIterator.next());
                addBook(newLibrary, bookValues);
            }
        }

        return newLibrary;
    }

    /**
     * Add a book to the provided library.
     * Parses book data from a list.
     *
     * @param library library where the book will be added.
     * @param bookValues list containing required book data values for a BookEntry.
     */
    private void addBook(ArrayList<BookEntry> library, ArrayList<String> bookValues) {
        String title = getLineTitle(bookValues);
        String[] authors = getLineAuthors(bookValues);
        float rating = getLineRating(bookValues);
        String ISBN = getLineISBN(bookValues);
        int pages = getLinePages(bookValues);

        try {               // prevents program from crashing when invalid book data is given
            library.add(new BookEntry(title, authors, rating, ISBN, pages));
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument for book: " + title + ": " + e);
        }
    }
}
