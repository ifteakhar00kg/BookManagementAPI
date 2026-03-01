package com.bookmanager.BookManagementAPI.controllers;

import com.bookmanager.BookManagementAPI.Book;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final List<Book> books;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        books.add(book);
        return ResponseEntity
                .ok(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> showBook(){
        return ResponseEntity
                .ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id){
        Book book = books.stream().filter(bk ->{
                return bk.getId() == id ;
        }).findFirst().orElse(new Book());
        return ResponseEntity
                .ok(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable long id){
        Optional<Book> optionalBook = books.stream()
                .filter(bk ->{
                    return bk.getId() == id;
                })
                .findFirst();

        Book book;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        } else {
            book = new Book();
        }
        books.remove(book);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book updatedBook){

        books.forEach(book -> {
            if(book.getId() == updatedBook.getId()){
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
            }
        });

        return ResponseEntity.ok(updatedBook);
    }
}
