package llama.thaumcraft.magic;

import java.util.LinkedHashMap;
import java.util.Map;

public class AspectStack {
    private Map<Aspect, Integer> aspects = new LinkedHashMap<>();

    public AspectStack(Aspect aspect, int value) {
        this.aspects.put(aspect, value);
    }

    public AspectStack(Aspect aspect) {
        this.aspects.put(aspect, 1);
    }

    public AspectStack(LinkedHashMap<Aspect, Integer> mapAspects) {
        this.aspects = mapAspects;
    }

    public AspectStack with(Aspect aspect, int value) {
        LinkedHashMap<Aspect, Integer> newAspects = new LinkedHashMap<>(this.aspects);
        newAspects.put(aspect, value);

        return new AspectStack(newAspects);
    }

    public AspectStack with(Aspect aspect) {
        LinkedHashMap<Aspect, Integer> newAspects = new LinkedHashMap<>(this.aspects);
        newAspects.put(aspect, 1);

        return new AspectStack(newAspects);
    }

    public Map<Aspect, Integer> getAspects() {
        return this.aspects;
    }
}