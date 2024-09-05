
public class Recursion {
    static String message = "I'm ";

    public static void cleanTheHouse(int times) {
        if (times <= 0) {
            return;
        }
        message = message + "so ";
        shout(message + "tired of this!");
        cleanTheHouse(times - 1);
    }

    public static void shout(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        cleanTheHouse(3);
    }
}

