package tk.manf.serialisation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identify any Propertie, which is to be saved
 * 
 * @author Björn 'manf' Heinrichs
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
    /**
     * Name of Propertie, will use fields name if not specified
     * @return name
     */
    public String name() default "";
}