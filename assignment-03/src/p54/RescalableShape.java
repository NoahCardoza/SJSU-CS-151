package p54;

/**
 A shape that can be resized.
 */
public interface RescalableShape extends Shape
{
    /**
     @param size percentage of size, 1 = 100%, etc.
     */
    void rescale(float size);

    float getScale();
}