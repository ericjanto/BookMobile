import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

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
        if (fileContent != null) {

            List<String> lineContent;

            Iterator<String> contentIterator = fileContent.iterator();


            while (contentIterator.hasNext()) {

                lineContent = parseLineContent(contentIterator.next());

                String title = lineContent.get(0);
                String[] authors = new String[lineContent.size() - 5];

                for(int i = 1; i < lineContent.size() - 4; i++) {
                    authors[i-1] = lineContent.get(i);
                }

                float rating = Float.parseFloat(lineContent.get(lineContent.size() - 3));

                String ISBN = lineContent.get(lineContent.size() - 2);

                int pages = Integer.parseInt(lineContent.get(lineContent.size() - 1));

                BookEntry parseFileEntry = new BookEntry(title, authors, rating, ISBN, pages);

                bookEntryList.add(parseFileEntry);

                return bookEntryList;

            }


        } else {
            System.err.println("ERROR: No content loaded before parsing.");
        }

    return bookEntryList;

    }

    private static List<String> parseLineContent(String fileLine) { //TODO Refactor
        List<String> parsedContent;
        String[] temp;
        String[] authors;

        temp = fileLine.split(",");

        authors = temp[1].split("-");

        System.arraycopy(authors, 0, temp, 1, authors.length);

        parsedContent = Arrays.asList(temp);
        return parsedContent;
    }
}
