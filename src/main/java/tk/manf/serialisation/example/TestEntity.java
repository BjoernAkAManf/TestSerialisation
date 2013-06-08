package tk.manf.serialisation.example;

import tk.manf.serialisation.annotations.Property;
import tk.manf.serialisation.SerialisationType;
import tk.manf.serialisation.annotations.Identification;
import tk.manf.serialisation.annotations.InitiationConstructor;
import tk.manf.serialisation.annotations.Parameter;
import tk.manf.serialisation.annotations.Unit;

/**
 * Example Unit
 * 
 * @author Björn 'manf' Heinrichs
 */
@Unit(isStatic = false, name = "TestEntity", type = SerialisationType.FLATFILE_YAML)
public final class TestEntity {
    @Identification
    @Property
    private final String name;
    @Property
    private int value;
    
    @InitiationConstructor
    public TestEntity(@Parameter(name = "name") String name) {
        this.value = 0;
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}