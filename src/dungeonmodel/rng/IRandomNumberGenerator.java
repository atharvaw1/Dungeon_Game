package dungeonmodel.rng;

/**
 * This is an interface to do all the random number generation of different kinds.
 * https://softwareengineering.stackexchange.com/questions/356456/testing-a-function-that-uses-random-number-generator
 */
public interface IRandomNumberGenerator {

  /**
   * Returns a random number in the given range (inclusive).
   *
   * @param low  the lower limit
   * @param high the higher limit
   * @return the random number between low and high
   */
  int getRandomNumber(int low, int high);
}
