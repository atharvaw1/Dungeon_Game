package dungeonmodel;


import java.util.List;

/**
 * Class represents the player. The player starts in a dungeon and can move around based on the
 * dungeon connectivity and collect treasure in the dungeon rooms. Package private to prevent
 * use outside dungeon.
 */
interface Player {

  /**
   * Method returns the playerId and the List of Treasure that the player has collected till
   * now. List of the playerId and Treasure List.
   *
   * @return a List of playerId and Treasure List
   */
  List describePlayer();

  /**
   * Returns the list of weapons that the player currently has with them.
   *
   * @return List of Weapon player owns
   */
  List<Weapon> getWeapons();

  /**
   * Returns if player is alive or dead.
   *
   * @return boolean to specify if player is alive
   */
  boolean isAlive();


}
