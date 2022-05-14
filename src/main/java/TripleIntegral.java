import net.objecthunter.exp4j.Expression;

public class TripleIntegral {

    private final Expression integrandExpression;
    private final int Accuracy;
    private final char[] varOrder;
    private final Expression firstMaxLimitExpr;
    private final Expression firstMinLimitExpr;
    private final Expression secondMaxLimitExpr;
    private final Expression secondMinLimitExpr;
    private final Expression thirdMaxLimitExpr;
    private final Expression thirdMinLimitExpr;
    private double firstMaxLimit;
    private double firstMinLimit;
    private double secondMaxLimit;
    private double secondMinLimit;
    private double thirdMaxLimit;
    private double thirdMinLimit;
    public static Point[] pointArray;

    public static IntegralVariable[] Vars = new IntegralVariable[3];

    public TripleIntegral(Expression exp, int acc, char[] varOrder, Expression firstMinLim, Expression firstMaxLim,
                          Expression secMinLim, Expression secMaxLim, Expression thirdMinLim, Expression thirdMaxLim) {
        this.integrandExpression = exp;
        this.Accuracy = acc;
        pointArray = new Point[Accuracy];
        for (int i = 0; i < Accuracy; i++) pointArray[i] = new Point();
        this.varOrder = varOrder;
        this.firstMinLimitExpr = firstMinLim;
        this.firstMaxLimitExpr = firstMaxLim;
        this.secondMinLimitExpr = secMinLim;
        this.secondMaxLimitExpr = secMaxLim;
        this.thirdMinLimitExpr = thirdMinLim;
        this.thirdMaxLimitExpr = thirdMaxLim;
    }

    public static class Point {

        private double X;
        private double Y;
        private double Z;

        public void setVal(char v, double val) {
            if (v == 'x') setX(val);
            if (v == 'y') setY(val);
            if (v == 'z') setZ(val);
        }

        public double getVal(char v){
            if (v == 'x') return getX();
            if (v == 'y') return getY();
            if (v == 'z') return getZ();
            return 0;
        }

        public double getX() {
            return X;
        }

        public double getY() {
            return Y;
        }

        public double getZ() {
            return Z;
        }

        public void setX(double x) {
            X = x;
        }

        public void setY(double y) {
            Y = y;
        }

        public void setZ(double z) {
            Z = z;
        }
    }

    private void calculateNumericalLimits() {
        firstMinLimit = firstMinLimitExpr.evaluate();
        firstMaxLimit = firstMaxLimitExpr.evaluate();
        Vars[0] = new IntegralVariable(varOrder[0], firstMinLimit, firstMaxLimit, 0.001);
        FunctionExtremum extremum = new FunctionExtremum(secondMinLimitExpr, secondMaxLimitExpr, Vars[0]);
        secondMinLimit = extremum.generateMinLimitForOneVar();
        secondMaxLimit = extremum.generateMaxLimitForOneVar();

        double[] arrayOfFirst = NumberGenerator.randomInSegment(firstMinLimit, secondMaxLimit, Accuracy);
        double[] arrayOfSecond = NumberGenerator.randomInSegment(secondMinLimit, secondMaxLimit, Accuracy);


        for (int i = 0; i < Accuracy; i++) {
            pointArray[i].setVal(varOrder[0], arrayOfFirst[i]);
            pointArray[i].setVal(varOrder[1], arrayOfSecond[i]);
        }

        Vars[1] = new IntegralVariable(varOrder[1], secondMinLimit, secondMaxLimit, 0.001);
        FunctionExtremum extremum1 = new FunctionExtremum(thirdMinLimitExpr, thirdMaxLimitExpr, Vars[0], Vars[1]);
        thirdMinLimit = extremum1.generateMinLimitForTwoVars();
        thirdMaxLimit = extremum1.generateMaxLimitForTwoVars();

        double[] arrayOfThird = NumberGenerator.randomInSegment(thirdMinLimit, thirdMaxLimit, Accuracy);
        for (int i = 0; i < Accuracy; i++) pointArray[i].setVal(varOrder[2], arrayOfThird[i]);

        Vars[2] = new IntegralVariable(varOrder[2], thirdMinLimit, thirdMaxLimit, 0.001);
    }

    public double integrate() {
        String var1 = String.valueOf(varOrder[0]);
        String var2 = String.valueOf(varOrder[1]);
        calculateNumericalLimits();
        int hitPoints = 0;
        for (int i = 0; i < Accuracy; i++) {
            double first = pointArray[i].getVal(varOrder[0]);
            double second = pointArray[i].getVal(varOrder[1]);
            double third = pointArray[i].getVal(varOrder[2]);

            if ((second >= secondMinLimitExpr.setVariable(var1, first).evaluate())
                    && (second <= secondMaxLimitExpr.setVariable(var1, first).evaluate())
                    && (third >= thirdMinLimitExpr.setVariable(var1, first).setVariable(var2, second).evaluate())
                    && (third <= thirdMaxLimitExpr.setVariable(var1, first).setVariable(var2, second).evaluate()))
                hitPoints ++;
        }
        return (firstMaxLimit-firstMinLimit)*(secondMaxLimit-secondMinLimit)*(thirdMaxLimit-thirdMinLimit)
                *(((double) hitPoints)/Accuracy);
    }

    public Expression getIntegrandExpression() {
        return integrandExpression;
    }

    public long getAccuracy() {
        return Accuracy;
    }

    public Expression getFirstMaxLimitExpr() {
        return firstMaxLimitExpr;
    }

    public Expression getFirstMinLimitExpr() {
        return firstMinLimitExpr;
    }

    public Expression getSecondMaxLimitExpr() {
        return secondMaxLimitExpr;
    }

    public Expression getSecondMinLimitExpr() {
        return secondMinLimitExpr;
    }

    public Expression getThirdMaxLimitExpr() {
        return thirdMaxLimitExpr;
    }

    public Expression getThirdMinLimitExpr() {
        return thirdMinLimitExpr;
    }

}
