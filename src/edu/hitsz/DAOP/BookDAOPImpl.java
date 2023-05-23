package edu.hitsz.DAOP;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class BookDAOPImpl implements BookDAOP, Serializable {
    private List<Book> books;
    public BookDAOPImpl(){
        books=new LinkedList<>();
    }
    public void setBooks(List<Book> books){
        this.books=books;
    }
    //删除图书
    @Override
    public void doDelete(int index) {
        books.remove(index);
    }
    //获取所有图书
    @Override
    public List<Book> getAllBooks() {
        return books;
    }
    //查找图书
    @Override
    public void findById(String name) {
        for (Book item : books) {
            if (item.getName() == name) {
                System.out.println("Find Book: ID [" + name + "], Book Name [" + item.getName() + "]");
                return;
            }
        }
        System.out.println("Can not find this book!");
    }
    //新增图书
    @Override
    public void doAdd(Book book) {
        int i;
        for (i = 0; i < books.size(); i++) {
            if(book.getScore()>books.get(i).getScore()){
                break;
            }
        }
        books.add(i,book);
    }
}
