package tk.manf.serialisation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import tk.manf.serialisation.SerialisationType;

/**
 * Identifies Units
 * 
 * @author Björn 'manf' Heinrichs
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unit {
    /**
     * Returns Units name
     * @return Units name
     */
    public String name();
    /**
     * Returns if this Unit is static
     * @return true if Unit is Static false if not
     */
    public boolean isStatic() default true;
    
    /**
     * Specify Units SerialisationType 
     * @return SerialisationType of Unit
     * @see SerialisationType
     */
    public SerialisationType type();
}
