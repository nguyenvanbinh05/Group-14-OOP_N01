public class javawhileloop1 {
    public static void main(String[] args) {
        int count = 0;

        // The loop will continue as long as count is less than 5
        while (count < 5) {
            System.out.println("The count is: " + count);
            count++;  // Increment count to avoid infinite loop
        }
    }
}

