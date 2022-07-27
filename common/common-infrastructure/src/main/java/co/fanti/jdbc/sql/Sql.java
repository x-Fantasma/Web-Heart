package co.fanti.jdbc.sql;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Documented
public @interface Sql {

    String folder() default "";
    String sql() default "";
}
