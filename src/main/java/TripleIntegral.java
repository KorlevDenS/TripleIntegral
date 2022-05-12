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

    public static IntegralVariable[] Vars = new IntegralVariable[3];

    public TripleIntegral(Expression exp, int acc, char[] varOrder, Expression firstMinLim, Expression firstMaxLim,
                          Expression secMinLim, Expression secMaxLim, Expression thirdMinLim, Expression thirdMaxLim) {
        this.integrandExpression = exp;
        this.Accuracy = acc;
        this.varOrder = varOrder;
        this.firstMinLimitExpr = firstMinLim;
        this.firstMaxLimitExpr = firstMaxLim;
        this.secondMinLimitExpr = secMinLim;
        this.secondMaxLimitExpr = secMaxLim;
        this.thirdMinLimitExpr = thirdMinLim;
        this.thirdMaxLimitExpr = thirdMaxLim;
    }

    private void calculateNumericalLimits() {
        firstMinLimit = firstMinLimitExpr.evaluate();
        firstMaxLimit = firstMaxLimitExpr.evaluate();
        Vars[0] = new IntegralVariable(String.valueOf(varOrder[0]), firstMinLimit, firstMaxLimit, 0.001);

        FunctionExtremum extremum = new FunctionExtremum(secondMinLimitExpr, secondMaxLimitExpr, Vars[0]);
        secondMinLimit = extremum.generateMinLimitForOneVar();
        secondMaxLimit = extremum.generateMaxLimitForOneVar();
        Vars[1] = new IntegralVariable(String.valueOf(varOrder[1]), secondMinLimit, secondMaxLimit, 0.001);

        FunctionExtremum extremum1 = new FunctionExtremum(thirdMinLimitExpr, thirdMaxLimitExpr, Vars[0], Vars[1]);
        thirdMinLimit = extremum1.generateMinLimitForTwoVars();
        thirdMaxLimit = extremum1.generateMaxLimitForTwoVars();
        Vars[2] = new IntegralVariable(String.valueOf(varOrder[2]), thirdMinLimit, thirdMaxLimit, 0.001);
    }

    public double integrate() {
        calculateNumericalLimits();
        double[] firstSegmentRandom = NumberGenerator.randomInSegment(firstMinLimit, firstMaxLimit, Accuracy);
        double[] secondSegmentRandom = NumberGenerator.randomInSegment(secondMinLimit, secondMaxLimit, Accuracy);
        double[] thirdSegmentRandom = NumberGenerator.randomInSegment(thirdMinLimit, thirdMaxLimit, Accuracy);
        double sum = 0;
        for (int i = 0; i < Accuracy; i++) {
            sum += integrandExpression.setVariable(Vars[0].getTitle(), firstSegmentRandom[i])
                    .setVariable(Vars[1].getTitle(), secondSegmentRandom[i])
                    .setVariable(Vars[2].getTitle(), thirdSegmentRandom[i]).evaluate();
        }
        double estimate = ((firstMaxLimit - firstMinLimit) * (secondMaxLimit - secondMinLimit)
                * (thirdMaxLimit - thirdMinLimit)) / Accuracy;
        return sum * estimate;
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
