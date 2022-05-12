import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;
import java.util.Collections;

public class FunctionExtremum {

    private final Expression maxExpression;
    private final Expression minExpression;
    private final IntegralVariable var1;
    private IntegralVariable var2;

    public FunctionExtremum(Expression minExpression, Expression maxExpression, IntegralVariable var1) {
        this.minExpression = minExpression;
        this.maxExpression = maxExpression;
        this.var1 = var1;
    }

    public FunctionExtremum(Expression minExpression, Expression maxExpression, IntegralVariable var1, IntegralVariable var2) {
        this.minExpression = minExpression;
        this.maxExpression = maxExpression;
        this.var1 = var1;
        this.var2 = var2;
    }

    public double generateMinLimitForOneVar() {
        ArrayList<Double> current = new ArrayList<>();
        for (double value : var1.getValuesOnSegment())
            current.add(minExpression.setVariable(var1.getTitle(), value).evaluate());
        return Collections.min(current);
    }

    public double generateMaxLimitForOneVar() {
        ArrayList<Double> current = new ArrayList<>();
        for (double value : var1.getValuesOnSegment())
            current.add(maxExpression.setVariable(var1.getTitle(), value).evaluate());
        return Collections.max(current);
    }

    public double generateMinLimitForTwoVars() {
        ArrayList<Double> current = new ArrayList<>();
        for (double value : var1.getValuesOnSegment()) {
            for (double v : var2.getValuesOnSegment())
                current.add(minExpression.setVariable(var1.getTitle(), value).setVariable(var2.getTitle(), v).evaluate());
        }
        return Collections.min(current);
    }

    public double generateMaxLimitForTwoVars() {
        ArrayList<Double> current = new ArrayList<>();
        for (double value : var1.getValuesOnSegment()) {
            for (double v : var2.getValuesOnSegment())
                current.add(maxExpression.setVariable(var1.getTitle(), value).setVariable(var2.getTitle(), v).evaluate());
        }
        return Collections.max(current);
    }

}
