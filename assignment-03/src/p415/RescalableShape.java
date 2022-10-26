package p415;

/**
 A shape that can be resized.
 */
public interface RescalableShape extends Shape
{
    /**
     @param size percentage of size, 1 = 100%, 0.5 = 50%, etc.
     */
    void rescale(float size);

    /**
     * Gets the current scale of the object
     * @return a float indicating the percentage
     */
    float getScale();
}