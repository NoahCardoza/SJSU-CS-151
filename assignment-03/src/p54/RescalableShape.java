package p54;

/**
 A shape that can be resized.
 */
public interface RescalableShape extends Shape
{
    /**
     @param size percentage of size, 1 = 100%, .5 = 50%, etc.
     */
    void rescale(float size);
}