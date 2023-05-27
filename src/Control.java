import java.awt.Point;

public abstract class Control {
    public static Component getComponentByCoordinates(Point point) { // Ez a függvény lehetne máshol is
        // TODO: kattintott komponens meghatározása - MAYBE SOLVED
        for (var component : Game.Instance.pipelineSystem.components){
            if(component.intersect(point)){
                return component;
            }
        }
        return null;
    }
}
