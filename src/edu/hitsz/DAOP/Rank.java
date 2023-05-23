package edu.hitsz.DAOP;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Rank {
    private BookDAOPImpl bookDAOPImpl;

    public Rank(){
        bookDAOPImpl =new BookDAOPImpl();
    }
    public BookDAOPImpl getBookDaoIml(){
        return this.bookDAOPImpl;
    }
    public void readBookDaoIml(String file_name) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file_name))) {
            bookDAOPImpl = (BookDAOPImpl) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void writeBookDaoIml(String file_name){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_name));
            oos.writeObject(bookDAOPImpl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeRank(){
        System.out.println("*************************************");
        System.out.println("           "+"得分排行榜"+"             ");
        System.out.println("*************************************");
        int i=1;
        for(Book book: bookDAOPImpl.getAllBooks()){
            System.out.println("第"+String.valueOf(i)+"名，"+book.getName()+","
                    +String.valueOf(book.getScore())+","+book.getDate());
            i+=1;
        }
    }
    public String[][] returnAllData(){
        if(bookDAOPImpl==null) return null;
        List<Book> books=bookDAOPImpl.getAllBooks();
        String[][] data=new String[books.size()][4];
        for (int i = 0; i < books.size(); i++) {
            Book book=books.get(i);
            data[i]=new String[]{String.valueOf(i+1),book.getName(),String.valueOf(book.getScore()),book.getDate()};
        }
        return data;
    }
    public void addBook(Book book){
        bookDAOPImpl.doAdd(book);
    }

}
