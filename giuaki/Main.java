public class Main {
    public static void main(String[] args) {

        DiscreteSignal discreteSignal = new DiscreteSignal(10.0, 5.0);


        discreteSignal.setSignalValue(0, 1.0);
        discreteSignal.setSignalValue(1, 0.5);
        discreteSignal.setSignalValue(2, 0.3);


        System.out.println("Discrete Signal:");
        discreteSignal.displaySignal();

 
        Radar radar = new Radar();
        System.out.println("\nRadar Signal:");
        radar.displayRadarSignal();


        radar.displaySampleAtN4();
    }
}
