public class IntegralVariable {

    private final char title;
    protected double[] valuesOnSegment;

    public IntegralVariable(char title, double minValue, double maxValue, double step) {
        this.title = title;
        this.valuesOnSegment = splitNumericSegment(minValue, maxValue, step);
    }

    private double[] splitNumericSegment(double begin, double end, double step) {
        int amount = (int) ((end - begin) / step);
        double[] partsArray;
        partsArray = new double[amount + 1];
        partsArray[0] = begin;
        for (int i = 1; i < amount; i++) {
            partsArray[i] = begin + step;
            begin += step;
        }
        partsArray[amount] = end;
        return partsArray;
    }

    public char getTitle() {
        return this.title;
    }

    public double[] getValuesOnSegment() {
        return this.valuesOnSegment;
    }
}
