import java.util.Objects;

public class Book implements Comparable<Book> {
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

    // Реализация метода equals() для сравнения книг по ISBN
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    // Реализация метода hashCode() для корректной работы HashMap
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    // Реализация метода toString() для вывода информации о книге
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", yearPublished=" + yearPublished +
                '}';
    }

    // Для TreeSet — сортировка книг по названию
    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title);
    }
}
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Library {
    // Хранение книг по ISBN для быстрого поиска
    private Map<String, Book> booksByIsbn = Collections.synchronizedMap(new HashMap<>());
    
    // Хранение книг, отсортированных по названию
    private Set<Book> booksByTitle = Collections.synchronizedSet(new TreeSet<>());

    // Метод для добавления книги
    public synchronized void addBook(Book book) {
        booksByIsbn.put(book.getIsbn(), book);
        booksByTitle.add(book);
    }

    // Метод для удаления книги по ISBN
    public synchronized void removeBook(String isbn) {
        Book book = booksByIsbn.remove(isbn);
        if (book != null) {
            booksByTitle.remove(book);
        }
    }

    // Метод для поиска книги по ISBN
    public Book findBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    // Метод для поиска книг по названию
    public Set<Book> findBooksByTitle(String title) {
        Set<Book> result = new HashSet<>();
        for (Book book : booksByTitle) {
            if (book.getTitle().contains(title)) {
                result.add(book);
            }
        }
        return result;
    }

    // Метод для получения списка всех книг, отсортированных по автору
    public List<Book> getAllBooksSortedByAuthor() {
        List<Book> books = new ArrayList<>(booksByIsbn.values());
        books.sort(Comparator.comparing(Book::getAuthor));
        return books;
    }

    // Метод для получения количества книг, опубликованных после указанного года
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
public class Main {
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
