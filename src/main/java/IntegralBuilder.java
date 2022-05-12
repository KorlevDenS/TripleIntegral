import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import java.io.IOException;
import java.io.StringReader;

public class IntegralBuilder {

    private char[] varOrder;
    private Expression firstMaxLimit;
    private Expression firstMinLimit;
    private Expression secondMaxLimit;
    private Expression secondMinLimit;
    private Expression thirdMaxLimit;
    private Expression thirdMinLimit;

    public IntegralBuilder(){
        loadLimits();
    }

    protected char[] loadIntegrateOrder() {
        char[] variables = new char[3];
        System.out.println("Введите переменные x, y, z в нужном порядке без пробелов.");
        String vars = ScanValidation.nextNonEmptyLine();
        if (!vars.matches("(xyz|xzy|yxz|yzx|zxy|zyx)")) {
            System.out.println("Переменные введены некорректно, введите один из вариантов: " +
                    "xyz, xzy, yxz, yzx, zxy, zyx");
            return loadIntegrateOrder();
        }
        StringReader stringReader = new StringReader(vars);
        try {
            variables[0] = (char) stringReader.read();
            variables[1] = (char) stringReader.read();
            variables[2] = (char) stringReader.read();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return variables;
    }

    protected Expression loadIntegrandExp() throws UnknownFunctionOrVariableException {
        System.out.println("Введите подынтегральное выражение символами с клавиатуры.");
        String exp = ScanValidation.nextNonEmptyLine();
        try {
            return new ExpressionBuilder(exp).variables("x", "y", "z").build();
        } catch (IllegalArgumentException ex) {
            System.out.println("Выражение введено некорректно, повторите попытку.");
            return loadIntegrandExp();
        }
    }

    protected int loadAccuracy() {
        System.out.println("Введите количество случайных точек, определяющее точность.");
        return ScanValidation.nextInt();
    }

    protected void loadLimits() throws UnknownFunctionOrVariableException {
        this.varOrder = loadIntegrateOrder();
        System.out.println("Введите нижнюю границу внешнего предела интегрирования - число ");
        this.firstMinLimit = new ExpressionBuilder(ScanValidation.nextNonEmptyLine()).variables().build();
        System.out.println("Введите верхнюю границу внешнего предела интегрирования - число ");
        this.firstMaxLimit = new ExpressionBuilder(ScanValidation.nextNonEmptyLine()).variables().build();
        System.out.println("Введите нижнюю границу второго предела интегрирования - функция " +
                varOrder[1] + "(" + varOrder[0] + ")");
        this.secondMinLimit = new ExpressionBuilder(ScanValidation.nextNonEmptyLine())
                .variables(String.valueOf(varOrder[0])).build();
        System.out.println("Введите верхнюю границу второго предела интегрирования - функция " +
                varOrder[1] + "(" + varOrder[0] + ")");
        this.secondMaxLimit = new ExpressionBuilder(ScanValidation.nextNonEmptyLine())
                .variables(String.valueOf(varOrder[0])).build();
        System.out.println("Введите нижнюю границу третьего предела интегрирования - функция " +
                varOrder[2] + "(" + varOrder[0] + "," + varOrder[1] + ")");
        this.thirdMinLimit = new ExpressionBuilder(ScanValidation.nextNonEmptyLine())
                .variables(String.valueOf(varOrder[0]), String.valueOf(varOrder[1])).build();
        System.out.println("Введите верхнюю границу третьего предела интегрирования - функция " +
                varOrder[2] + "(" + varOrder[0] + "," + varOrder[1] + ")");
        this.thirdMaxLimit = new ExpressionBuilder(ScanValidation.nextNonEmptyLine())
                .variables(String.valueOf(varOrder[0]), String.valueOf(varOrder[1])).build();
    }

    public TripleIntegral build() {
        return new TripleIntegral(loadIntegrandExp(), loadAccuracy(), varOrder, firstMinLimit, firstMaxLimit,
                secondMinLimit, secondMaxLimit, thirdMinLimit, thirdMaxLimit);
    }

}
