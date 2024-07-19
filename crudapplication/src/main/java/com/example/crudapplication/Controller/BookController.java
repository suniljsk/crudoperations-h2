package com.example.crudapplication.Controller;

import com.example.crudapplication.Exception.BookNotFoundException;
import com.example.crudapplication.Repo.BookRepo;
import com.example.crudapplication.model.Book;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepo bookRepo;
    @GetMapping("getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks(){
        try {
            List<Book> booklist=new ArrayList<>();
            bookRepo.findAll().forEach(booklist::add);
            if(booklist.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(booklist, HttpStatus.OK);

        }
        catch (Exception ex){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }


    }
    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){

       Optional <Book> bookData= bookRepo.findById(id);
       if(bookData.isPresent()){

           return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
       }
     throw  new BookNotFoundException("Request Not found");
//        return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
    }
    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
       Book bookobj= bookRepo.save(book);
       return new ResponseEntity<>(bookobj,HttpStatus.OK);

    }
    @PostMapping("/updateBook{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
       try{
        Optional<Book> oldbookdata=bookRepo.findById(id);
       if(oldbookdata.isPresent()){
           Book updatedbookdata=oldbookdata.get();
           updatedbookdata.setTitle(book.getTitle());
           updatedbookdata.setAuthor(book.getAuthor());

           Book bookobj= bookRepo.save(updatedbookdata);
           return new ResponseEntity<>(bookobj,HttpStatus.CREATED);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }catch(Exception ex){
       return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @DeleteMapping("/deleteBookById{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id){
        try{
        bookRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);


    }catch(Exception ex){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
