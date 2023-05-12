package bookstore;

import java.util.Objects;

public class App {
    public static void main(String[] args) {
        Store.addBook("1", "book1");
        Store.addBook("2", "book2");
        Store.addBook("3", "book3");
        Store.addBook("4", "book4");
        Store.addBook("5", "book5");
        Store.addBook("6", "book6");

        var booksByTitle = Store.searchAllBooksByTitle("book3");
        if (booksByTitle != null) {
            booksByTitle.forEach(System.out::println);
            Store.deleteBook(booksByTitle.get(0));
        }
    }
}
