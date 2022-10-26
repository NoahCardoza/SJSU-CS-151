/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 10/24/2022
 * @assignment Icon Stamper
 */

/**
 * Used by lambdas to create a new composite shape on each call
 */
public interface CompositeShapeFactory {
    /**
     * Creates a new CompositeShape instance
     * @param x the x offset of the new shape
     * @param y the y offset of the new shape
     * @return a new CompositeShape instance
     */
    CompositeShape factory(int x, int y);
}
