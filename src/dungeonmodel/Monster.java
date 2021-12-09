package dungeonmodel;

import dungeonmodel.rng.IRandomNumberGenerator;

/**
 * Represents the monsters in the dungeon. These are harmful to players and need to be killed by
 * the player in order to proceed in the dungeon. Monster has a state that tells if the monster is
 * alive injured or dead.
 */
public interface Monster {

  /**
   * Returns the current state of the monster. Can be ALIVE,INJURED or DEAD.
   *
   * @return state of monster
   */
  MonsterState getState();


  /**
   * Takes in the player and adds the effect of the monster to it.
   * @param player player of type PlayerImpl
   * @param rng the random number generator
   */
  void effectPlayer(PlayerImpl player, IRandomNumberGenerator rng);

}
