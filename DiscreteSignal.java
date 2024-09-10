import java.util.HashMap;
import java.util.Map;

public class DiscreteSignal {
    private Map<Integer, Double> signalValues = new HashMap<>();

    
    public void setSignalValue(int k, double value) {
        signalValues.put(k, value);
    }

    public double calculateSignal(int n) {
        double result = 0;
        for (Map.Entry<Integer, Double> entry : signalValues.entrySet()) {
            int k = entry.getKey();
            double xk = entry.getValue();
            result += xk * delta(n - k);  
        }
        return result;
    }


    private int delta(int n) {
        return n == 0 ? 1 : 0; 
    }

    
    public void displaySignal() {
        for (int n = -10; n <= 10; n++) {
            System.out.println("x(" + n + ") = " + calculateSignal(n));
        }
    }
}
