package tk.manf.serialisation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import tk.manf.serialisation.SerialisationType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unit {
    /**
     * 
     * @return 
     */
    public String name();
    /**
     * 
     * @return 
     */
    public boolean isStatic() default true;
    /**
     * 
     * @return 
     */
    public SerialisationType handler();
}
