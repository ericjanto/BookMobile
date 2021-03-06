import java.util.Objects;
import java.util.Arrays;

/** Immutable class encapsulating data for a single book entry. */
public final class BookEntry {

    // -------------- CONSTANTS AND FIELDS ------------------------------------

    /** Minimum value of rating. */
    private static final float MIN_RATING = (float) 0.0;
    /** Maximum value of rating. */
    private static final float MAX_RATING = (float) 5.0;
    /** Minimum number of pages. */
    private static final int MIN_PAGES = 0;

    /** Title of a BookEntry instance. */
    private final String title;
    /** Author(s) of a BookEntry instance */
    private final String[] authors;
    /** Rating of a BookEntry instance. */
    private final float rating;
    /** International Standard Book Number (ISBN) of a BookEntry instance. */
    private final String ISBN;
    /** Number of pages of a BookEntry instance. */
    private final int pages;

    // -------------- CONSTRUCTOR(S) ------------------------------------------

    /**
     * Create a book entry.
     * Initialises class fields.
     *
     * @param title title of the book.
     * @param authors author(s) of the book.
     * @param rating rating of the book.
     * @param ISBN ISBN of the book.
     * @param pages total number of pages of the book.
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages) {
        checkEntries(title, authors, rating, ISBN, pages);

        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;
    }

    // -------------- ERROR CHECKING ------------------------------------------

    /**
     * Check the validity of its parameters.
     *
     * @param title represents title of a book.
     * @param authors represents author(s) of a book.
     * @param rating represents rating of a book.
     * @param ISBN represents ISBN of a book.
     * @param pages represents number of pages of a book.
     */
    private static void checkEntries(String title, String[] authors, float rating, String ISBN, int pages) {
        Objects.requireNonNull(title, "Given title must not be null.");
        Objects.requireNonNull(authors, "Given author array must not be null.");
        checkRating(rating);
        Objects.requireNonNull(ISBN, "Given ISBN must not be null.");
        checkPages(pages);
    }


    /**
     * Checks that rating is between {@value #MIN_RATING} and {@value #MAX_RATING}.
     *
     * @param rating represents the rating of a book.
     * @throws IllegalArgumentException if rating is out of bound.
     */
    private static void checkRating(float rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Invalid rating: " + rating);
        }
    }

    /**
     * Checks that number of pages is {@value #MIN_PAGES} or greater.
     *
     * @param pages represents the number of pages of a book.
     * @throws IllegalArgumentException if number of pages is less than {@value #MIN_PAGES}.
     */
    private static void checkPages(int pages) {
        if (pages < MIN_PAGES) {
            throw new IllegalArgumentException("Invalid page number: " + pages);
        }
    }

    // -------------- HELPER METHODS FOR CLASS FUNCTIONALITY METHODS ----------

    /**
     * Provide string containing author information.
     *
     * @param authors represents a list of
     * @return string containing author information.
     */
    private static String authorsToString(String[] authors) {
        return String.join(", ", authors);
    }

    /**
     * Provide formatted string for displaying value of parameter rating.
     *
     * @param rating represents rating of a book.
     * @return string containing formatted rating.
     */
    private static String ratingToString(float rating) {
        return String.format("%.2f", rating);
    }

    // -------------- CLASS FUNCTIONALITY METHODS -----------------------------

    /**
     * Getter method for class field title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter method for class field authors.
     * @return authors
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * Getter method for class field rating.
     * @return rating.
     */
    public float getRating() {
        return rating;
    }

    /**
     * Getter method for class field ISBN.
     * @return ISBN.
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Getter method for class field getPages.
     * @return pages.
     */
    public int getPages() {
        return pages;
    }

    /**
     * Provide string which displays information about a BookEntry instance.
     * @return requested string as specified above.
     */
    @Override
    public String toString() {
        // Using a StringBuilder would be equally or less efficient.
        return title +
                "\nby " + authorsToString(authors) +
                "\nRating: " + ratingToString(rating) +
                "\nISBN: " + ISBN +
                "\n" + pages + " pages";
    }

    /**
     * Compare parameter object to this BookEntry instance in terms of equality.
     *
     * @param obj object to be compared to this instance.
     * @return true if object is equal to this instance, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BookEntry otherBook = (BookEntry) obj;
        checkEntries(otherBook.title, otherBook.authors, otherBook.rating, otherBook.ISBN, otherBook.pages);

        return Float.compare(otherBook.rating, rating) == 0 &&
                pages == otherBook.pages &&
                Objects.equals(title, otherBook.title) &&
                Arrays.equals(authors, otherBook.authors) &&
                Objects.equals(ISBN, otherBook.ISBN);
    }

    /**
     * Provide hashCode of this BookEntry instance.
     * @return integer with hashCode value.
     */
    @Override
    public int hashCode() {
        int hashCodeResult = Objects.hash(title, rating, ISBN, pages);
        hashCodeResult = 31 * hashCodeResult + Arrays.hashCode(authors);

        return hashCodeResult;
    }
}
