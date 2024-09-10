public class Main {
    public static void main(String[] args) {
        // Tạo đối tượng tín hiệu rời rạc
        DiscreteSignal discreteSignal = new DiscreteSignal();

        // Nhập giá trị cho các điểm rời rạc của tín hiệu
        discreteSignal.setSignalValue(0, 1.0);
        discreteSignal.setSignalValue(1, 0.5);
        discreteSignal.setSignalValue(2, 0.3);

        // Hiển thị tín hiệu rời rạc
        System.out.println("Discrete Signal:");
        discreteSignal.displaySignal();

        // Tạo đối tượng radar và hiển thị tín hiệu radar
        Radar radar = new Radar();
        System.out.println("\nRadar Signal:");
        radar.displayRadarSignal();

        // Hiển thị riêng tín hiệu mẫu tại n = 4
        radar.displaySampleAtN4();
    }
}