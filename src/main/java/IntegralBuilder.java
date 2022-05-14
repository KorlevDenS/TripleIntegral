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
    private final String[] stringLimits = new String[6];
    private int Accuracy;

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
        //System.out.println("Введите подынтегральное выражение символами с клавиатуры.");
        //String exp = ScanValidation.nextNonEmptyLine();
        try {
            return new ExpressionBuilder("1").variables("x", "y", "z").build();
        } catch (IllegalArgumentException ex) {
            System.out.println("Выражение введено некорректно, повторите попытку.");
            return loadIntegrandExp();
        }
    }

    protected int loadAccuracy() {
        System.out.println("Введите количество случайных точек, определяющее точность.");
        this.Accuracy = ScanValidation.nextInt();
        return Accuracy;
    }

    protected void loadLimits() throws UnknownFunctionOrVariableException {
        this.varOrder = loadIntegrateOrder();
        System.out.println("Введите нижнюю границу внешнего предела интегрирования - число ");
        stringLimits[0] = ScanValidation.nextNonEmptyLine();
        this.firstMinLimit = new ExpressionBuilder(stringLimits[0]).variables().build();
        System.out.println("Введите верхнюю границу внешнего предела интегрирования - число ");
        stringLimits[1] = ScanValidation.nextNonEmptyLine();
        this.firstMaxLimit = new ExpressionBuilder(stringLimits[1]).variables().build();
        System.out.println("Введите нижнюю границу второго предела интегрирования - функция " +
                varOrder[1] + "(" + varOrder[0] + ")");
        stringLimits[2] = ScanValidation.nextNonEmptyLine();
        this.secondMinLimit = new ExpressionBuilder(stringLimits[2])
                .variables(String.valueOf(varOrder[0])).build();
        System.out.println("Введите верхнюю границу второго предела интегрирования - функция " +
                varOrder[1] + "(" + varOrder[0] + ")");
        stringLimits[3] = ScanValidation.nextNonEmptyLine();
        this.secondMaxLimit = new ExpressionBuilder(stringLimits[3])
                .variables(String.valueOf(varOrder[0])).build();
        System.out.println("Введите нижнюю границу третьего предела интегрирования - функция " +
                varOrder[2] + "(" + varOrder[0] + "," + varOrder[1] + ")");
        stringLimits[4] = ScanValidation.nextNonEmptyLine();
        this.thirdMinLimit = new ExpressionBuilder(stringLimits[4])
                .variables(String.valueOf(varOrder[0]), String.valueOf(varOrder[1])).build();
        System.out.println("Введите верхнюю границу третьего предела интегрирования - функция " +
                varOrder[2] + "(" + varOrder[0] + "," + varOrder[1] + ")");
        stringLimits[5] = ScanValidation.nextNonEmptyLine();
        this.thirdMaxLimit = new ExpressionBuilder(stringLimits[5])
                .variables(String.valueOf(varOrder[0]), String.valueOf(varOrder[1])).build();
    }

    public TripleIntegral build() {
        return new TripleIntegral(loadIntegrandExp(), loadAccuracy(), varOrder, firstMinLimit, firstMaxLimit,
                secondMinLimit, secondMaxLimit, thirdMinLimit, thirdMaxLimit);
    }

    @Override

    public String toString() {
        return "Первый предел: от " + stringLimits[0] + " до " + stringLimits[1] + "\n"
                + "Второй предел: от " + stringLimits[2] + " до " + stringLimits[3] + "\n"
                + "Третий предел: от " + stringLimits[4] + " до " + stringLimits[5] + "\n"
                + "Количество генерируемых точек: " + Accuracy + "\n";
    }

}
