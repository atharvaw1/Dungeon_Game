package dungeonmodel.rng;

import java.util.Random;

/**
 * This class generates true random numbers that are used by the program.
 */
public class RandomNumberGenerator implements IRandomNumberGenerator {
  private final Random rng = new Random();

  @Override
  public int getRandomNumber(int low, int high) {

    return rng.nextInt(high - low + 1) + low;

  }
}
