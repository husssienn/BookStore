package bookstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

// TODO: add the following functions
//  Book searchBookByCategory(String)
//  ArrayList<Book> searchAllBooksByCategory(String)


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

        if (firstBiggerIdx == 0) {
            books.add(0, book);
        } else if (firstBiggerIdx == books.size()) {
            books.add(firstBiggerIdx, book);
        } else {
            books.add(firstBiggerIdx - 1, book);
        }

        return true;
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
     * @param isbn  The ISBN of the book
     * @param title The title of the book
     * @return true if the book didn't exist before and was added, false otherwise
     */
    public boolean addBook(String isbn, String title) {
        return addBook(new Book(isbn, title));
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

//    /**
//     * Delete a book that only matches the title
//     *
//     * @param title The title to search for
//     * @return true if the book was found and deleted, false otherwise
//     */
//    public boolean deleteBookByTitle(String title) {
//        var bookIdx = searchBookIdxByTitle(title);
//        if (bookIdx != -1) {
//            books.remove(bookIdx);
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Delete all books having the provided title
//     * @param title The title of the books to delete
//     * @return true if any book matched and was deleted, false otherwise
//     */
//    public boolean deleteAllBooksByTitle(String title) {
//        var res = false;
//        var bookIndices = searchAllBookIndicesByTitle(title);
//        if (bookIndices == null) return false;
//        // delete from the end of the list
//        Collections.reverse(bookIndices);
//        for (int idx : bookIndices) {
//            res = true;
//            books.remove(idx);
//        }
//        return res;
//    }

    public ArrayList<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

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
        if (books.size() == 0) return -1;
        // binary search
        for (int start = 0, end = books.size() - 1; start < end; ) {
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
        if (books.size() == 0) return null;
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
            var file = new File(filePath);

            try (var fileInputStream = new FileInputStream(file)) {
                var tokenizer = new StringTokenizer(new String(fileInputStream.readAllBytes()), "{}[]', ");

                if (!tokenizer.hasMoreTokens()) {
                    return;
                } else if (!Objects.equals(tokenizer.nextToken(), "Store")) {
                    System.err.println("Malformed store data, not reading");
                    return;
                } else {
                    tokenizer.nextToken();  // skip the name of the array field to start reading books in the next loop
                }

                while (tokenizer.hasMoreTokens()) {
                    if (!Objects.equals(tokenizer.nextToken(), "Book")) {
                        System.err.println("Malformed store data, not reading further");
                        return;
                    }
                    if (!Objects.equals(tokenizer.nextToken(), "isbn=")) {
                        System.err.println("Malformed store data, not reading further");
                        return;
                    }
                    var isbn = tokenizer.nextToken();
                    if (!Objects.equals(tokenizer.nextToken(), "title=")) {
                        System.err.println("Malformed store data, not reading further");
                        return;
                    }
                    var title = tokenizer.nextToken();
                    if (!Objects.equals(tokenizer.nextToken(), "category=")) {
                        System.err.println("Malformed store data, not reading further");
                        return;
                    }
                    var category = tokenizer.nextToken();
                    books.add(new Book(isbn, title, category));
                }
            } catch (IOException e) {
                System.err.println("Failed to open store file to read data");
            }
        }
    }
}
