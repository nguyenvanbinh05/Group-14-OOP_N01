
public class NNCollection {
    private NameNumber[] nnArray = new NameNumber[100];
    private int free;

    public NNCollection() {
        free = 0;
    }

    public void insert(NameNumber n) {
        int index = free;
        for (int i = free; i > 0 && nnArray[i - 1].getLastName().compareTo(n.getLastName()) > 0; i--) {
            nnArray[i] = nnArray[i - 1];
            index = i - 1;
        }
        nnArray[index] = n;
        free++;
    }

    public String findNumber(String lName) {
        for (int i = 0; i < free; i++) {
            if (nnArray[i].getLastName().equals(lName)) {
                return nnArray[i].getTelNumber();
            }
        }
        return "Name not found";
    }

    public static void main(String[] args) {
        NNCollection collection = new NNCollection();
        NameNumber nn1 = new NameNumber("Smith", "123456789");
        NameNumber nn2 = new NameNumber("Doe", "987654321");

        collection.insert(nn1);
        collection.insert(nn2);

        System.out.println(collection.findNumber("Smith"));
        System.out.println(collection.findNumber("Doe"));
        System.out.println(collection.findNumber("Brown"));
    }
}
