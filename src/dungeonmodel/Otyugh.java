package dungeonmodel;

import dungeonmodel.rng.IRandomNumberGenerator;

/**
 * Represents an Otyugh. Otyughs are extremely smelly creatures that lead solitary lives in
 * the deep, dark places of the world like a dungeon.
 */
public class Otyugh implements Monster {

  private MonsterState state;

  /**
   * Constructor for Otyugh makes the default state alive.
   */
  public Otyugh() {
    state = MonsterState.ALIVE;
  }

  @Override
  public MonsterState getState() {
    return state;
  }

  @Override
  public void effectPlayer(PlayerImpl player, IRandomNumberGenerator rng) {
    if (state == MonsterState.ALIVE) {
      player.setAlive(false);
    } else if (state == MonsterState.INJURED) {
      if (rng.getRandomNumber(0, 100) < 50) {
        player.setAlive(false);
      }
    }
  }

  void strikeOtyugh() {
    if (state == MonsterState.ALIVE) {
      state = MonsterState.INJURED;
    } else if (state == MonsterState.INJURED) {
      state = MonsterState.DEAD;
    }
  }

  @Override
  public String toString() {
    return "Otyugh";
  }


}
