import java.awt.print.Book;
import java.util.*;

/**
 * Group command used as a varied list command.
 * Displays library content in title or author groups.
 */
public class GroupCmd extends LibraryCommand {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    private static final char NUMBER_TITLE = '1';


    /**
     * TODO
     */
    private String groupType; // TODO enum instead

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /**
     * TODO
     *
     * @param argumentInput
     * @throws
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------


    private void groupByTitle(List<BookEntry> books) {
        HashMap<Character, ArrayList<String>> letterMap = new HashMap<>();

        for (char i = 'A'; i <= 'Z'; i++) {
            ArrayList<String> letterGroup = new ArrayList<>();
            letterMap.put(i, letterGroup);
        }

        ArrayList<String> letterGroup = new ArrayList<>();
        letterMap.put(NUMBER_TITLE, letterGroup);

        // ---

        ArrayList<String> bookTitles = new ArrayList<>();
        for (BookEntry book : books) {
            bookTitles.add(book.getTitle());
        }

        // ---

        Iterator<String> titleIterator = bookTitles.iterator();
        while (titleIterator.hasNext()) {
            String title = titleIterator.next();
            ArrayList<String> specificLetterGroup;
            char firstLetter = title.charAt(0);

            if (!Character.isLetter(firstLetter)) {
                specificLetterGroup = letterMap.get(NUMBER_TITLE);
            } else {
                specificLetterGroup = letterMap.get(Character.toUpperCase(firstLetter));
            }
            specificLetterGroup.add(title);
        }

        // ---

        Iterator<Map.Entry<Character, ArrayList<String>>> entryIterator = letterMap.entrySet().iterator();

        while (entryIterator.hasNext()) {
            Map.Entry<Character, ArrayList<String>> entry = entryIterator.next();

            if (entry.getValue().size() == 0) {
                entryIterator.remove();
            }
        }

        // ---

        Iterator<Map.Entry<Character, ArrayList<String>>> anotherEntryIterator = letterMap.entrySet().iterator();

        StringBuilder bd = new StringBuilder();
        bd.append("Grouped data by TITLE");

        while (anotherEntryIterator.hasNext()) {
            Map.Entry<Character, ArrayList<String>> entry = anotherEntryIterator.next();
            String groupHeader;
            if (entry.getKey() != NUMBER_TITLE) {
                groupHeader = String.format("## %s", entry.getKey().toString());
            } else {
                groupHeader = "## [0-9]";
            }

            bd.append("\n").append(groupHeader);

            Object[] groupAsArray = entry.getValue().toArray();

            for (Object title : groupAsArray) {
                bd.append("\n\t").append(title.toString()); // TODO you may have to replace \t with "   " or just remove
            }
        }

        System.out.println(bd);

    }

    /**
     * TODO
     * @param books
     */
    private void groupByAuthor(List<BookEntry> books) {
        TreeMap<String, ArrayList<String>> authorMap = new TreeMap<>();

        for (BookEntry book : books) {
            String[] bookAuthors = book.getAuthors();

            for (String author : bookAuthors) {
                if (!authorMap.containsKey(author)) {
                    ArrayList<String> bookListAuthor = new ArrayList<>();

                    bookListAuthor.add(book.getTitle());
                    authorMap.put(author, bookListAuthor);
                } else {
                    ArrayList<String> bookListAuthor = authorMap.get(author);
                    bookListAuthor.add(book.getTitle());
                }
            }
        }

        // ---

        StringBuilder bd = new StringBuilder();
        bd.append("Grouped data by AUTHOR");

        for (Map.Entry<String, ArrayList<String>> entry : authorMap.entrySet()) {
            String groupHeader = String.format("## %s", entry.getKey());
            bd.append("\n").append(groupHeader);

            Object[] groupAsArray = entry.getValue().toArray();

            for (Object title : groupAsArray) {
                bd.append("\n\t").append(title.toString()); // TODO may have to remove \n
            }
        }

        System.out.println(bd);
    }


    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * TODO
     *
     * @param argumentInput argument input for this command
     * @return
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput.equals("AUTHOR") || argumentInput.equals("TITLE")) {
            groupType = argumentInput;
            return true;
        } else {
            return false;
        }
    }

    /**
     * TODO
     *
     * @param data book data to be considered for command execution.
     * @throws
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Provided data for GroupCmd execution must not be null.");

        List<BookEntry> books = data.getBookData();

        if (data.getBookData().size() == 0) {
            System.out.println("The library has no book entries.");
        } else {

            switch (groupType) {
                case "TITLE":
                    groupByTitle(books);

                    break;
                case "AUTHOR":
                    groupByAuthor(books);
            }
        }
    }


}
