import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {


    /** Character used to separate data values in file. */
    private static final char dataSeparator = ',';
    /** Character used to separate author names. */
    private static final char authorSeparator = '-';
    /** Index of title in file line. */
    private static final int TITLE_INDEX = 0;
    /** Index of authors in file line. */
    private static final int AUTHORS_INDEX = 1;
    /** Index of rating in file line. */
    private static final int RATING_INDEX = 2;
    /** Index of ISBN in file line. */
    private static final int ISBN_INDEX = 3;
    /** Index of page number in file line. */
    private static final int PAGES_INDEX = 4;

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

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
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
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() { // TODO Refactor
        ArrayList<BookEntry> bookEntryList = new ArrayList<>();

        if (fileContent == null) {
            System.err.println("ERROR: No content loaded before parsing.");
        } else {
            Iterator<String> fileContentIterator = fileContent.iterator();

            // Leave out header line in file
            if (fileContentIterator.hasNext()) {
                fileContentIterator.next();
            }

            while (fileContentIterator.hasNext()) {
                ArrayList<String> bookValues = separateLineContent(fileContentIterator.next());
                addBook(bookEntryList, bookValues);
            }
        }

        return bookEntryList;
    }

    private void addBook(ArrayList<BookEntry> destinationLibrary, ArrayList<String> lineContent) {
        String title = getLineTitle(lineContent);
        String[] authors = getLineAuthors(lineContent);
        float rating = getLineRating(lineContent);
        String ISBN = getLineISBN(lineContent);
        int pages = getLinePages(lineContent);

        destinationLibrary.add(new BookEntry(title, authors, rating, ISBN, pages));
    }

    private ArrayList<String> separateLineContent(String line) {
        return new ArrayList<>(Arrays.asList(line.split(dataSeparator + "")));
    }

    private String getLineTitle(ArrayList<String> line) {
        return line.get(TITLE_INDEX);
    }

    private String[] getLineAuthors(ArrayList<String> line) {
        return line.get(AUTHORS_INDEX).split(authorSeparator + "");
    }

    private float getLineRating(ArrayList<String> line) {
        return Float.parseFloat(line.get(RATING_INDEX));
    }

    private String getLineISBN(ArrayList<String> line) {
        return line.get(ISBN_INDEX);
    }

    private int getLinePages(ArrayList<String> line) {
        return Integer.parseInt(line.get(PAGES_INDEX));
    }


}
