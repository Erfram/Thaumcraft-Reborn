package llama.thaumcraft.aspects;

import java.util.ArrayList;
import java.util.List;

public class Aspect {
    public String name;
    public List<Aspect> ingredients = new ArrayList<Aspect>();
    public Aspect(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addIngredient(Aspect aspect) {
        this.ingredients.add(aspect);
    };

    public List<Aspect> getIngredients() {
        return ingredients;
    };
}