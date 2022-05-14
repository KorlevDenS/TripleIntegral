import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        solveTheIntegral();
    }

    public static void solveTheIntegral() {
        IntegralBuilder builder = new IntegralBuilder();
        TripleIntegral integral = builder.build();
        if (!checkIntegralInfo(builder)) solveTheIntegral();
        else {
            System.out.println("Выполняется интегрирование...");
            double answer = integral.integrate();
            System.out.println("Результат: " + answer);
        }
    }

    public static boolean checkIntegralInfo(IntegralBuilder in){
        Scanner sc = new Scanner(System.in);
        System.out.println("Введенные данные:");
        System.out.println(in.toString());
        System.out.println("Если всё верно нажмите Enter, иначе введите команду update.");
        return !sc.nextLine().equals("update");
    }
}
