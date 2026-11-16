public class Main {
    public static void main(String[] args) {
        TestPlanScanner.buildPlan(Checklist.class);
        TestPlanScanner.buildPlan(EmptyClass.class);
    }
}
