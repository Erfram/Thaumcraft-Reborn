package llama.thaumcraft.aspects;

public class IgnisAspect extends Aspect{
    public IgnisAspect() {
        super("ignis");

        this.addIngredient(new IgnisAspect());
    }
}
