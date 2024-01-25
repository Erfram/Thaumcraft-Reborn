package llama.thaumcraft.config;

import llama.thaumcraft.Aspects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ItemAspects {
    private Map<Aspects, Integer> aspects = new LinkedHashMap<>();

    public ItemAspects(Aspects aspect, int value) {
        this.aspects.put(aspect, value);
    }

    public ItemAspects(Aspects aspect) {
        this.aspects.put(aspect, 1);
    }

    public ItemAspects add(Aspects aspect, int value) {
        this.aspects.put(aspect, value);
        return this;
    }

    public ItemAspects add(Aspects aspect) {
        this.aspects.put(aspect, 1);
        return this;
    }

    public Map<Aspects, Integer> getAspects() {
        return this.aspects;
    }
}
