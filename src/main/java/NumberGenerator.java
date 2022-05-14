public class NumberGenerator {

    public static double randomWithRange(double min, double max) {
        double range = max - min;
        return Math.random() * range + min;
    }

    public static double[] randomInSegment(double min, double max, int amount) {
        double[] sampling = new double[amount];
        for (int i = 0; i < amount; i++) {
            sampling[i] = randomWithRange(min, max);
        }
        return sampling;
    }
}
