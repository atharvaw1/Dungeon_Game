package dungeonmodel;

import dungeonmodel.rng.IRandomNumberGenerator;

/**
 * Represents a theif in the dungeon that steals all the players treasure when encountered.
 */
public class Thief implements Monster {

  private final MonsterState state;

  /**
   * Constructor for Otyugh makes the default state alive.
   */
  public Thief() {
    state = MonsterState.ALIVE;
  }

  @Override
  public MonsterState getState() {
    return state;
  }

  @Override
  public void effectPlayer(PlayerImpl player, IRandomNumberGenerator rng) {
    player.removeTreasure();

  }

  @Override
  public String toString() {
    return "Thief";
  }


}
