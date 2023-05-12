package bookstore;

import java.util.Objects;

public class Book {
    private final String isbn;
    private final String title;
    private final String category;

    public Book(String isbn, String title) {
        this(isbn, title, "Unclassified");
    }

    public Book(String isbn, String title, String category) {
        this.isbn = isbn;
        this.title = title;
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Book{" + "isbn='" + isbn + '\'' + ", title='" + title + '\'' + ", category='" + category + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return hashCode() == book.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title);
    }
}
