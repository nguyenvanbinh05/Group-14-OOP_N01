

public class App {
    public static void main(String[] args) {
        
        Book book = new Book("1984", "George Orwell", 328);
        System.out.println("Tieu de sach: " + book.title);

        NNCollection collection = new NNCollection();
        NameNumber nn1 = new NameNumber("Nguyen", "123456789");
        NameNumber nn2 = new NameNumber("Tran", "987654321");
        collection.insert(nn1);
        collection.insert(nn2);

        System.out.println("ma so: " + collection.findNumber("Nguyen"));
        System.out.println("ma so: " + collection.findNumber("Tran"));


        Recursion.cleanTheHouse(3);

        Time time = new Time(13, 45, 30);
        System.out.println("Thoi gian hien tai: " + time);
    }
}
