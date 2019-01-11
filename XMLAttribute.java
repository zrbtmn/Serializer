import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@interface XMLAttribute{
    String name() default "UNDEFINED";
    String tag() default "UNDEFINED";
}