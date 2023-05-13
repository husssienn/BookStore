package bookstore;

import java.util.Objects;

public class Book {
    private final String isbn;
    private final String title;
    private String category;
    private float price;

    /**
     * Creates a book with unclassified category
     *
     * @param isbn  The ISBN of the book
     * @param title The title of the book
     */
    public Book(String isbn, String title) {
        this(isbn, title, null, 0.0f);
    }

    /**
     * Creates a book with no price
     *
     * @param isbn     The ISBN of the book
     * @param title    The title of the book
     * @param category The category of the book
     */
    public Book(String isbn, String title, String category) {
        this(isbn, title, category, 0.0f);
    }

    /**
     * Creates a book
     *
     * @param isbn     The ISBN of the book
     * @param title    The title of the book
     * @param category The category of the book
     */
    public Book(String isbn, String title, String category, float price) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN can't be null or empty");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title can't be null or empty");
        }
        if (price < 0.0f) {
            throw new IllegalArgumentException("Price can't be less than 0");
        }
        this.isbn = isbn;
        this.title = title;
        this.category = Objects.requireNonNullElse(category, "Unclassified");
        this.price = price;
    }

    /**
     * Get the book ISBN
     *
     * @return String representing the book ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Get book title
     *
     * @return The title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the category of the book
     *
     * @return The category of the book
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the book
     *
     * @param category The new category of the book
     */
    public void setCategory(String category) {
        this.category = Objects.requireNonNullElse(category, "Unclassified");
    }

    /**
     * Get the book price
     *
     * @return The price of the book
     */
    public float getPrice() {
        return price;
    }

    /**
     * Set the book price
     *
     * @param price The price of the book
     */
    public void setPrice(float price) {
        if (price < 0) throw new IllegalArgumentException("Price can't be less than 0");
        this.price = price;
    }

    /**
     * String representation of the object
     *
     * @return String representing the object
     */
    @Override
    public String toString() {
        return "Book{" + "isbn='" + isbn + '\'' + ", title='" + title + '\'' + ", category='" + category + '\'' + ", price=" + price + '}';
    }

    /**
     * Checks if two books have the same ISBN & title
     *
     * @param o Object to check for equality
     * @return ture if they are equal, false otherwise
     * @implNote Only checks for ISBN & title
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return hashCode() == book.hashCode();
    }

    /**
     * Hash code of the book ISBN & title
     *
     * @return hash of the book
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn, title);
    }
}
