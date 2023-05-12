package bookstore;

public class App {
    public static void main(String[] args) {
        var store = new Store();
        store.addBook("1", "book1");
        store.addBook("2", "book2");
        store.addBook("3", "book3");
        store.addBook("4", "book4");
        store.addBook("5", "book5");
        store.addBook("6", "book6");

        var booksByTitle = store.searchAllBooksByTitle("book3");
        if (booksByTitle != null) {
            booksByTitle.forEach(System.out::println);
            store.deleteBook(booksByTitle.get(0));
        }
    }
}
