import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Если изменить RetentionPolicy на CLASS или SOURCE, то метаданные этой аннотации не попадут в рантайм и соответсвенно
// не будет происходить поиск по данной аннотации
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestId {
    String value();
    int order();
}