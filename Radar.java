public class Radar {
    
    public double calculateX(int n) {
        if (n >= 0 && n <= 15) {
            return 1 - (double) n / 15;
        } else {
            return 0;
        }
    }

    
    public void displayRadarSignal() {
        for (int n = 0; n <= 15; n++) {
            System.out.println("X(" + n + ") = " + calculateX(n));
        }
    }

    
    public void displaySampleAtN4() {
        int n = 4;
        System.out.println("\nMẫu tín hiệu tại n = " + n + ": X(" + n + ") = " + calculateX(n));
    }
}
