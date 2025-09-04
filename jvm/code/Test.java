public class Test {
    public static void main(String[] args) {
        System.out.println("This is main method from test");
        System.out.println("Showing class loaders types");

        System.out.println("Bootstrap class loader: " + java.util.HashMap.class.getClassLoader()); 
        // null → Bootstrap

        System.out.println(Test.class.getClassLoader()); 
        // AppClassLoader
    }
}
