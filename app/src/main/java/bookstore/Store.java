package bookstore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

public class Store {
    // books indexed by their titles
    private final ArrayList<Book> books = new ArrayList<>();
    private final String filePath;

    /**
     * Construct a store with the default file path for storing the data
     */
    public Store() {
        this("./store.dat");
    }

    /**
     * Construct a store with the file path specified to store data
     *
     * @param filePath The path to store the store data
     */
    public Store(String filePath) {
        this.filePath = filePath;
        loadStoreData();
    }

    /**
     * Adds a book to the store if not already in
     *
     * @param book The book to add
     * @return true if the book didn't exist before and was added, false otherwise
     */
    public boolean addBook(Book book) {
        var allBooksBySameTitle = searchAllBookIndicesByTitle(book.getTitle());
        if (allBooksBySameTitle != null) {
            for (int idx : allBooksBySameTitle) {
                if (books.get(idx).equals(book)) {
                    return false;
                }
            }
        }

        // insertion sort
        var firstBiggerIdx = 0;

        for (; firstBiggerIdx < books.size(); ++firstBiggerIdx) {
            if (books.get(firstBiggerIdx).getTitle().compareTo(book.getTitle()) > 0) break;
        }

        books.add(firstBiggerIdx, book);
        return true;
    }

    /**
     * Adds a book with the provided information
     *
     * @param isbn  The ISBN of the book
     * @param title The title of the book
     * @return true if the book didn't exist before and was added, false otherwise
     */
    public boolean addBook(String isbn, String title) {
        return addBook(new Book(isbn, title));
    }

    /**
     * Adds a book with the provided information
     *
     * @param isbn     The ISBN of the book
     * @param title    The title of the book
     * @param category The category of the book
     * @return true if the book didn't exist before and was added, false otherwise
     */
    public boolean addBook(String isbn, String title, String category) {
        return addBook(new Book(isbn, title, category));
    }

    /**
     * Adds a book with the provided information
     *
     * @param isbn     The ISBN of the book
     * @param title    The title of the book
     * @param category The category of the book
     * @param price    The price of the book
     * @return true if the book didn't exist before and was added, false otherwise
     */
    public boolean addBook(String isbn, String title, String category, float price) {
        return addBook(new Book(isbn, title, category, price));
    }

    /**
     * Search a book by title
     *
     * @param title The title to search for
     * @return The book with the requested title
     */
    public Book searchBookByTitle(String title) {
        var idx = searchBookIdxByTitle(title);
        if (idx == -1) return null;
        return books.get(idx);
    }

    /**
     * Search all books matching the title
     *
     * @param title The title to search for
     * @return A list of the books matching the requested title
     */
    public ArrayList<Book> searchAllBooksByTitle(String title) {
        var bookArrayList = new ArrayList<Book>();
        var bookIndices = searchAllBookIndicesByTitle(title);
        if (bookIndices == null) return null;
        bookIndices.forEach(idx -> bookArrayList.add(books.get(idx)));
        return bookArrayList;
    }

    /**
     * Returns all books matching the provided category
     *
     * @param category The category to search for
     * @return ArrayList of books matching the provided category or null if non found
     */
    public ArrayList<Book> searchAllBooksByCategory(String category) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    /**
     * Updates a book category or price
     *
     * @param oldBook The old book to update
     * @param newBook The new book to use
     * @return true if the old book was found and updated, false otherwise
     * @apiNote Only price & category can be updated, ISBN & title can't be changed.
     * Although a book can be deleted and re-added with different title or ISBN
     * @implNote The only required fields of the old book are the ISBN & title since equality only checks for them
     */
    public boolean updateBook(Book oldBook, Book newBook) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a book that have the same title & ISBN
     *
     * @param book The book to delete
     * @return true if the book was found and deleted, false otherwise
     */
    public boolean deleteBook(Book book) {
        var allBookIndicesBySameTitle = searchAllBookIndicesByTitle(book.getTitle());
        if (allBookIndicesBySameTitle == null) return false;
        for (int idx : allBookIndicesBySameTitle) {
            if (books.get(idx).equals(book)) {
                books.remove(idx);
                return true;
            }
        }
        return false;
    }

    /**
     * Delete all books that match the title & ISBN
     *
     * @param book The book to delete
     * @return true if any book found and deleted, false otherwise
     */
    public boolean deleteAllBooks(Book book) {
        var res = false;
        var allBookIndicesBySameTitle = searchAllBookIndicesByTitle(book.getTitle());
        if (allBookIndicesBySameTitle == null) return false;
        for (int idx : allBookIndicesBySameTitle) {
            if (books.get(idx).equals(book)) {
                books.remove(idx);
                res = true;
            }
        }
        return res;
    }

    /**
     * Returns a copy of the internal books array
     *
     * @return ArrayList copy of the books
     */
    public ArrayList<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Returns a string representation of the store object
     *
     * @return String representing the store object
     */
    @Override
    public String toString() {
        return "Store{" + "books=" + books + '}';
    }

    /**
     * Search a book by title
     *
     * @param title The title to search for
     * @return The index of that book in the list
     */
    private int searchBookIdxByTitle(String title) {
        // binary search
        for (int start = 0, end = books.size() - 1; start <= end; ) {
            var middle = (start + end) / 2;
            var res = books.get(middle).getTitle().compareTo(title);
            if (res == 0) {
                return middle;
            } else if (res > 0) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
        return -1;
    }

    /**
     * Search all books matching the title
     *
     * @param title The title to search for
     * @return A list of all the indices having the same title
     * @implNote The list returned is sorted in ascending order
     */
    private ArrayList<Integer> searchAllBookIndicesByTitle(String title) {
        var indices = new ArrayList<Integer>();
        // binary search
        for (int start = 0, end = books.size() - 1; start <= end; ) {
            var middle = (start + end) / 2;
            var res = books.get(middle).getTitle().compareTo(title);
            if (res == 0) {
                var idx = middle;

                while (--idx >= 0) {
                    if (books.get(idx).getTitle().compareTo(title) != 0) break;
                }

                while (++idx < books.size() && Objects.equals(books.get(idx).getTitle(), title)) indices.add(idx);

                return indices;
            } else if (res > 0) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
        return null;
    }

    /**
     * Reads the store object data from a file using simple serialization
     */
    private void loadStoreData() {
        var path = Paths.get(filePath);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try (var file = new FileOutputStream(filePath)) {
                file.write(this.toString().getBytes());
            } catch (IOException e) {
                System.err.println("Failed to save store content to file");
            }
        }));

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.err.println("Failed to create file to save store content");
            }
        } else {
            try (var fileInputStream = new FileInputStream(filePath)) {
                var tokenizer = new StringTokenizer(new String(fileInputStream.readAllBytes()), "{}[]', =");

                if (!tokenizer.hasMoreTokens()) {
                    return;
                } else if (!Objects.equals(tokenizer.nextToken(), "Store")) {
                    System.err.println("Malformed store data: Object is not store, not reading");
                    return;
                } else {
                    tokenizer.nextToken();  // skip the name of the array field to start reading books in the next loop
                }

                var bookDeclaredFields = Book.class.getDeclaredFields();
                String isbn = null, title = null, category = null;
                var price = 0.0f;

                while (tokenizer.hasMoreTokens()) {
                    if (!Objects.equals(tokenizer.nextToken(), "Book")) {
                        System.err.println("Malformed store data: Expected book object not found, not reading further");
                        return;
                    }

                    for (var declaredField : bookDeclaredFields) {
                        var fieldName = declaredField.toString();
                        var lastDotIdx = fieldName.lastIndexOf('.');
                        fieldName = fieldName.substring(lastDotIdx + 1);

                        switch (fieldName) {
                            case "isbn" -> {
                                if (!Objects.equals(tokenizer.nextToken(), "isbn")) {
                                    System.err.println("Malformed store data: Book doesn't have ISBN, not reading further");
                                    return;
                                }
                                isbn = tokenizer.nextToken();
                            }
                            case "title" -> {
                                if (!Objects.equals(tokenizer.nextToken(), "title")) {
                                    System.err.println("Malformed store data: Book doesn't have title, not reading further");
                                    return;
                                }
                                title = tokenizer.nextToken();
                            }
                            case "category" -> {
                                if (!Objects.equals(tokenizer.nextToken(), "category")) {
                                    System.err.println("Malformed store data: Book doesn't have category, not reading further");
                                    return;
                                }
                                category = tokenizer.nextToken();
                            }
                            case "price" -> {
                                if (!Objects.equals(tokenizer.nextToken(), "price")) {
                                    System.err.println("Malformed store data: Book doesn't have price, not reading further");
                                    return;
                                }
                                price = Float.parseFloat(tokenizer.nextToken());
                            }
                            default -> {
                                System.err.println("Malformed store data: Non-parsable book field (" + fieldName + "), not reading further");
                                return;
                            }
                        }
                    }
                    addBook(isbn, title, category, price);
                }
            } catch (IOException e) {
                System.err.println("Failed to open store file to read data");
            }
        }
    }
}
