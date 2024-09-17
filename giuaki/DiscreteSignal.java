import java.util.HashMap;
import java.util.Map;

class DiscreteSignal implements Signal {
    private double amplitude;
    private double frequency;
    private Map<Integer, Double> signalValues = new HashMap<>();

    public DiscreteSignal(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    @Override
    public double getAmplitude() {
        return amplitude;
    }

    @Override
    public double getFrequency() {
        return frequency;
    }

    @Override
    public void displayInfo() {
        System.out.println("Discrete Signal - Amplitude: " + amplitude + ", Frequency: " + frequency);
    }

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
