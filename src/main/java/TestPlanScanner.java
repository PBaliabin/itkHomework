import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestPlanScanner {

    public static void buildPlan(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        List<TestStep> testSteps = new ArrayList<>();

        for(Method method : methods){
            if (!method.isAnnotationPresent(TestId.class)) continue;

            TestId testId = method.getAnnotation(TestId.class);
            testSteps.add(new TestStep(testId.value(), method.getName(), testId.order()));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("План прогона для ").append(clazz.getSimpleName()).append(":\n");

        if (testSteps.isEmpty()) {
            sb.append("  Кейсов нет");
        } else {
            testSteps.sort(Comparator.comparingInt(TestStep::order));
            testSteps.forEach(testStep -> sb
                    .append("  ").append(testStep.order).append(". ")
                    .append(testStep.id).append(" -> ").append(testStep.methodName).append("\n"));
        }

        System.out.println(sb);
    }

    record TestStep(String id, String methodName, int order){}
}
