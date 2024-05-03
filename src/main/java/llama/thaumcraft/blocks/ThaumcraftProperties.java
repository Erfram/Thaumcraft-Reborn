package llama.thaumcraft.blocks;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ThaumcraftProperties {
    public static final IntProperty LEVEL = IntProperty.of("level", 0, 3);
    public static final BooleanProperty BOILED = BooleanProperty.of("boiled");
    public static final BooleanProperty IS_BOILING = BooleanProperty.of("is_boiling");
}
