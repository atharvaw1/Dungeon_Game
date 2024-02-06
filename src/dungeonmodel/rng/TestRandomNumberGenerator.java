package dungeonmodel.rng;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class generates mock random numbers using either seed, low or high value.
 */
public class TestRandomNumberGenerator implements IRandomNumberGenerator {
  private final String type;
  private final int seed;
  private final Random rng;
  private List values;


  /**
   * Set the type of rng you want "low" will return only the lower value, "high" will return the
   * higher value and "avg" will return the average of low and high.
   *
   * @param type String
   */
  public TestRandomNumberGenerator(String type) {
    this.type = type;
    rng = new SecureRandom();
    this.seed = 0;
  }

  /**
   * Set the seed of the rng.
   *
   * @param seed int
   */
  public TestRandomNumberGenerator(int seed) {
    this.type = "seed";
    this.seed = seed;
    rng = new Random(seed);
  }

  /**
   * Set the values of the rng.
   *
   * @param values int
   */
  public TestRandomNumberGenerator(List values) {
    this.type = "values";
    this.seed = 0;
    rng = new SecureRandom();
    this.values = new ArrayList();
    this.values.addAll(values);
  }


  @Override
  public int getRandomNumber(int low, int high) {

    if (this.type.equals("values")) {
      int i = (int) values.get(0);
      values.remove(0);
      return i;
    } else {
      return rng.nextInt(high - low + 1) + low;
    }

  }
}
