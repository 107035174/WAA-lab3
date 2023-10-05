package lab3.lab3.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lab3.lab3.model.Book;

@RestController
@RequestMapping("/books")
public class BookController {
    List<Book> books = new ArrayList<>();

    @GetMapping("/")
    public String getBooks(Model model) {
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/{id}")
    public String getBookbyId(@PathVariable int id, Model model) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "bookDetail";
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    @PostMapping("/")
    public String addBook(@RequestParam int id, @RequestParam String title, @RequestParam String isbn,
            @RequestParam double price, Model model) {
        Book newBook = new Book(id, title, isbn, price);
        books.add(newBook);
        model.addAttribute("id", id);
        model.addAttribute("title", title);
        model.addAttribute("isbn", isbn);
        model.addAttribute("price", price);
        return "redirect: /books/";
    }

    @PutMapping("/{id}")
    public String updateBookbyId(@PathVariable int id, @RequestParam String title, @RequestParam String isbn,
            @RequestParam double price, Model model) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            Book booktoUpdate = book.get();
            booktoUpdate.setTitle(title);
            booktoUpdate.setIsbn(isbn);
            booktoUpdate.setPrice(price);
            return "bookUpdated";
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBookbyId(@PathVariable int id, Model model) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            books.remove(book.get());
            model.addAttribute("message", "Book with ID " + id + " has been deleted");
            return "bookDeleted";
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    @GetMapping(value = "/books", produces = "application/cs.miu.edu-v2+json")
    public String getBooksV2(Model model) {
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping(value = "/books/{id}", headers = "X-API-VERSION=2")
    public String getBookByIdV2(@PathVariable int id, Model model) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "bookDetail";
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    @PostMapping("/v1")
    public String addBookV1(@RequestParam int id, @RequestParam String title, @RequestParam String isbn,
            @RequestParam double price, Model model) {
        Book newBook = new Book(id, title, isbn, price);
        books.add(newBook);
        model.addAttribute("id", id);
        model.addAttribute("title", title);
        model.addAttribute("isbn", isbn);
        model.addAttribute("price", price);
        return "redirect: /books/";
    }

    @PutMapping(value = "/books/{id}", params = "version=1")
    public String updateBookbyIdV1(@PathVariable int id, @RequestParam String title, @RequestParam String isbn,
            @RequestParam double price, Model model) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            Book booktoUpdate = book.get();
            booktoUpdate.setTitle(title);
            booktoUpdate.setIsbn(isbn);
            booktoUpdate.setPrice(price);
            return "bookUpdated";
        } else {
            throw new RuntimeException("Book not found");
        }
    }

}
