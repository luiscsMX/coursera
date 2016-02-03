/**
 * @author jcrypto
 */
public class AbstractClass {
    private static final String hello;

    static {
        System.out.println(AbstractClass.class.getName() + ": static block runtime");
        hello = "hello from " + AbstractClass.class.getName();
    }

    {
        System.out.println(AbstractClass.class.getName() + ": instance block runtime");
    }

    public AbstractClass() {
        System.out.println(AbstractClass.class.getName() + ": constructor runtime");
    }

    public static void hello() {
        System.out.println(hello);
    }
}