package tk.manf.testserialisation;

import tk.manf.serialisation.annotations.Property;
import tk.manf.serialisation.SerialisationType;
import tk.manf.serialisation.annotations.Identification;
import tk.manf.serialisation.annotations.Unit;

@Unit(isStatic = true, name = "TestEntity", handler = SerialisationType.FLATFILE_YAML)
public final class TestEntity {
    @Identification
    @Property
    private final String name;
    @Property
    private int value;

    public TestEntity(String name) {
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