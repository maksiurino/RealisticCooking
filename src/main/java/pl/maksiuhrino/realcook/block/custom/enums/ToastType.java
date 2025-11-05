package pl.maksiuhrino.realcook.block.custom.enums;

import net.minecraft.util.StringIdentifiable;

public enum ToastType implements StringIdentifiable {
    NONE("none"),
    RAW("raw"),
    COOKED("cooked"),
    BURNT("burnt");

    private final String name;

    private ToastType(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
