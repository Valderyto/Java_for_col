import java.util.*;
import java.util.Objects;

public class Main {

    public static class Book implements Comparable<Book> {
        private String title;
        private String author;
        private String isbn;
        private int yearPublished;

        public Book(String title, String author, String isbn, int yearPublished) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.yearPublished = yearPublished;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getIsbn() {
            return isbn;
        }

        public int getYearPublished() {
            return yearPublished;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return isbn.equals(book.isbn);
        }

        @Override
        public int hashCode() {
            return Objects.hash(isbn);
        }

        @Override
        public String toString() {
            return "Book{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", isbn='" + isbn + '\'' +
                    ", yearPublished=" + yearPublished +
                    '}';
        }

        @Override
        public int compareTo(Book other) {
            return this.title.compareTo(other.title);
        }
    }

    // Класс Library
    public static class Library {
        private Map<String, Book> booksByIsbn = Collections.synchronizedMap(new HashMap<>());
        private Set<Book> booksByTitle = Collections.synchronizedSet(new TreeSet<>());

        public synchronized void addBook(Book book) {
            booksByIsbn.put(book.getIsbn(), book);
            booksByTitle.add(book);
        }

        public synchronized void removeBook(String isbn) {
            Book book = booksByIsbn.remove(isbn);
            if (book != null) {
                booksByTitle.remove(book);
            }
        }

        public Book findBookByIsbn(String isbn) {
            return booksByIsbn.get(isbn);
        }

        public Set<Book> findBooksByTitle(String title) {
            Set<Book> result = new HashSet<>();
            for (Book book : booksByTitle) {
                if (book.getTitle().contains(title)) {
                    result.add(book);
                }
            }
            return result;
        }

        public List<Book> getAllBooksSortedByAuthor() {
            List<Book> books = new ArrayList<>(booksByIsbn.values());
            books.sort(Comparator.comparing(Book::getAuthor));
            return books;
        }

        public int countBooksPublishedAfter(int year) {
            int count = 0;
            for (Book book : booksByIsbn.values()) {
                if (book.getYearPublished() > year) {
                    count++;
                }
            }
            return count;
        }
    }

    // Точка входа
    public static void main(String[] args) {
        Library library = new Library();

        // Добавление книг в библиотеку
        library.addBook(new Book("Effective Java", "Joshua Bloch", "12345", 2008));
        library.addBook(new Book("Java Concurrency in Practice", "Brian Goetz", "67890", 2006));
        library.addBook(new Book("Clean Code", "Robert C. Martin", "54321", 2008));

        // Поиск книги по ISBN
        System.out.println("Найденная книга по ISBN 12345: " + library.findBookByIsbn("12345"));

        // Поиск книг по части названия
        System.out.println("Книги, содержащие 'Java' в названии: " + library.findBooksByTitle("Java"));

        // Получение списка книг, отсортированных по автору
        System.out.println("Все книги, отсортированные по автору: " + library.getAllBooksSortedByAuthor());

        // Подсчет книг, опубликованных после 2007 года
        System.out.println("Книги, опубликованные после 2007 года: " + library.countBooksPublishedAfter(2007));
    }
}
