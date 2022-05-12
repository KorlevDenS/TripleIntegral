
public class Main {
    public static void main(String[] args) {
        TripleIntegral integral = new IntegralBuilder().build();
        System.out.println(integral.integrate());
    }
}
