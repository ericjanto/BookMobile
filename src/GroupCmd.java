import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Group command used as a varied list command.
 * Displays library content in title or author groups.
 */
public class GroupCmd extends LibraryCommand {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** Indicates that a title starts with a number. */
    private static final char NUMBER_TITLE_KEY = '1';

    /** Specify what kind of argument is given to group by. */
    private ExecutionType groupBy;

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /**
     * Create a group command.
     * Input is expected to be TITLE or AUTHOR.
     *
     * @param argumentInput command argument input.
     * @throws IllegalArgumentException if given argumentInput is invalid.
     * @throws NullPointerException if given argumentInput is null.
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------

    /**
     * Group library by title.
     *
     * @param books book entries.
     */
    private void groupByTitle(List<BookEntry> books) {
        HashMap<Character, ArrayList<String>> letterMap = initialiseLetterMap();

        ArrayList<String> bookTitles = new ArrayList<>();
        for (BookEntry book : books) {
            bookTitles.add(book.getTitle());
        }

        assignTitlesToGroup(letterMap, bookTitles);
        removeEmptyGroups(letterMap);
        printTitleGroups(letterMap);
    }

    /**
     * Initialise letter map.
     * Create for each letter an entry with respective letter as key
     * and an initialised list within titles can be grouped.
     *
     * An additional entry for titles starting with a number.
     *
     * @return initialised letter map.
     */
    private HashMap<Character, ArrayList<String>> initialiseLetterMap() {
        HashMap<Character, ArrayList<String>> letterMap = new HashMap<>();

        for (char i = 'A'; i <= 'Z'; i++) {
            ArrayList<String> letterGroup = new ArrayList<>();
            letterMap.put(i, letterGroup);
        }

        ArrayList<String> letterGroup = new ArrayList<>();
        letterMap.put(NUMBER_TITLE_KEY, letterGroup);

        return letterMap;
    }

    /**
     * Assign each title in given parameter bookTitles to
     * its respective group in letterMap.
     *
     * @param letterMap HashMap with letters as key and title groups as value.
     * @param bookTitles list of all book titles in library.
     */
    private void assignTitlesToGroup(HashMap<Character, ArrayList<String>> letterMap, ArrayList<String> bookTitles) {
        for (String title : bookTitles) {
            ArrayList<String> letterGroup;
            char firstLetter = title.charAt(0);

            if (!Character.isLetter(firstLetter)) {
                letterGroup = letterMap.get(NUMBER_TITLE_KEY);
            } else {
                letterGroup = letterMap.get(Character.toUpperCase(firstLetter));
            }
            letterGroup.add(title);
        }
    }

    /**
     * Remove entries of parameter letterMap which contain an empty letter group.
     *
     * @param letterMap HashMap with letters as key and title groups as value.
     */
    private void removeEmptyGroups(HashMap<Character, ArrayList<String>> letterMap) {
        Iterator<Map.Entry<Character, ArrayList<String>>> entryIterator = letterMap.entrySet().iterator();

        //noinspection Java8CollectionRemoveIf: would be more compact but makes use of lambda expression.
        while (entryIterator.hasNext()) {
            Map.Entry<Character, ArrayList<String>> entry = entryIterator.next();

            if (entry.getValue().isEmpty()) {
                entryIterator.remove();
            }
        }
    }

    /**
     * Print title groups.
     *
     * @param letterMap HashMap with letters as key and title groups as value.
     */
    private void printTitleGroups(HashMap<Character, ArrayList<String>> letterMap) {
        Iterator<Map.Entry<Character, ArrayList<String>>> letterMapIterator = letterMap.entrySet().iterator();
        StringBuilder titleGroups = new StringBuilder();

        while (letterMapIterator.hasNext()) {
            Map.Entry<Character, ArrayList<String>> entry = letterMapIterator.next();

            String groupHeader = getGroupHeader(entry);
            Object[] titles = entry.getValue().toArray();

            titleGroups.append("\n").append(groupHeader);
            for (Object title : titles) {
                titleGroups.append("\n\t").append(title);
            }
        }

        System.out.println(titleGroups);
    }

    /**
     * Get respective title group header.
     *
     * @param entry HashMap entry with letter as key and title group value.
     * @return group header.
     */
    private String getGroupHeader(Map.Entry<Character, ArrayList<String>> entry) {
        String groupHeader;

        if (entry.getKey() != NUMBER_TITLE_KEY) {
            groupHeader = String.format("## %s", entry.getKey());
        } else {
            groupHeader = "## [0-9]";
        }
        return groupHeader;
    }



    /**
     * Group library by author.
     *
     * @param books book entries.
     */
    private void groupByAuthor(List<BookEntry> books) {
        TreeMap<String, ArrayList<String>> authorMap = new TreeMap<>();

        for (BookEntry book : books) {
            String[] bookAuthors = book.getAuthors();

            for (String author : bookAuthors) {
                ArrayList<String> authorBookGroup = new ArrayList<>();

                if (!authorMap.containsKey(author)) { // Condition: There does not exist a book group for this author yet.
                    authorBookGroup.add(book.getTitle());
                    authorMap.put(author, authorBookGroup);
                } else {
                    authorBookGroup = authorMap.get(author);
                    authorBookGroup.add(book.getTitle());
                }
            }
        }

        printAuthorGroups(authorMap);
    }

    /**
     * Print author groups.
     *
     * @param authorMap TreeMap with author names as key and their books as value.
     */
    private void printAuthorGroups(TreeMap<String, ArrayList<String>> authorMap) {
        StringBuilder authorGroups = new StringBuilder();

        for (Map.Entry<String, ArrayList<String>> entry : authorMap.entrySet()) {
            String groupHeader = String.format("## %s", entry.getKey());
            authorGroups.append("\n").append(groupHeader);

            Object[] titles = entry.getValue().toArray();

            for (Object title : titles) {
                authorGroups.append("\n\t").append(title);
            }
        }

        System.out.println(authorGroups);
    }


    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Check for validity of input, i.e. group type.
     * Parse if valid.
     *
     * @param argumentInput argument input for this command.
     * @return true if valid input, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        for (ExecutionType type : ExecutionType.values()) {
            if (type.name().equals(argumentInput)) {
                groupBy = type;
                return true;
            }
        }

        return false;
    }

    /**
     * Execute group command.
     *
     * @param data library data containing book entries.
     * @throws NullPointerException if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Provided data for GroupCmd execution must not be null.");

        List<BookEntry> library = data.getBookData();

        if (library.isEmpty()) {
            System.out.println("The library has no book entries.");
        } else {
            System.out.printf("Grouped data by %s", groupBy);
            switch (groupBy) {
                case TITLE:
                    groupByTitle(library);
                    break;
                case AUTHOR:
                    groupByAuthor(library);
            }
        }
    }
}
