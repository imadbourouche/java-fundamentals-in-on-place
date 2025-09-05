public class Test {
    public static void main(String[] args) {
        System.out.println("This is main method from test");
        System.out.println("Showing class loaders types: ");

        // Bootstrap Class Loader (returns null)
        System.out.println("  - Classloader of HashMap (Bootstrap): " + java.util.HashMap.class.getClassLoader()); 

        // This will be the Application (System) Class Loader
        System.out.println("  - Classloader of this Test class: " + Test.class.getClassLoader()); 
        
        // The parent of the Application Class Loader is the Platform Class loader
        System.out.println("  - Parent of Test's classloader: " + Test.class.getClassLoader().getParent()); 
    }
}