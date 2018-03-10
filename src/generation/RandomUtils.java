package generation;

import java.util.Random;

/**
 * This class is a static utilities class that provides Random
 *      data. Only use this class if you need random data,
 *      so that all randomness uses the same seed.
 * @author jeffr
 */
public class RandomUtils 
{
    public static final Random RANDOM = new Random();
    
    /**
     * Consturcor is private so instances of RandomUtils cannot be made.
     */
    private RandomUtils(){}
    
    /**
     * @param min The lowest acceptable random value
     * @param max The largest acceptable random value
     * @return A random int value on [min,max]
     */
    public static int randomInt(int min, int max)
    {
        return RANDOM.nextInt(max + 1) - RANDOM.nextInt(min + 1);
    } 
    
    /**
     * @param min The lowest acceptable random value
     * @param max The largest acceptable random value
     * @return A random float value on [min,max)
     */
    public static float randomFloat(float min, float max)
    {
        return (RANDOM.nextFloat() * (min - max)) + min;
    } 
}