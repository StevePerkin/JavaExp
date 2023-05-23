package edu.hitsz.DAOP;

import java.util.List;

public interface BookDAOP {
    void findById(String name);
    List<Book> getAllBooks();
    void doAdd(Book book);
    void doDelete(int index);
}
