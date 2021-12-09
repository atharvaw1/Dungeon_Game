package dungeonmodel;

/**
 * A dungeon is a network of tunnels and caves that are interconnected so that player can
 * explore the entire world by traveling from cave to cave through the tunnels that connect
 * them. Each location in the grid represents a location in the dungeon where a player can explore
 * and can be connected to at most four (4) other locations: one to the north, one to the east,
 * one to the south, and one to the west. Notice that in this dungeon locations "wrap" to the one
 * on the other side of the grid. For example, moving to the north from row 0 (at the top) in the
 * grid moves the player to the location in the same column in row 5 (at the bottom).
 * A location can further be classified as tunnel (which has exactly 2 entrances) or a cave
 * (which has 1, 3 or 4 entrances).
 */
public interface Dungeon extends ReadOnlyDungeon {


  /**
   * Move the player in the given direction if an exit exists. Throws an IllegalArgumentException
   * if the given exit does not exist in the room.
   *
   * @param d the direction to move in
   */
  void movePlayer(Direction d);

  /**
   * Takes all the treasure from a room the player is currently in and adds it to the players
   * personal treasure. This will cause the treasure to be removed from the room.
   */
  void pickupTreasure();


  /**
   * Shoots the weapon at the specified direction and distance to try and kill a monster.
   * Returns a boolean value that tells if you hit a monster or not.
   *
   * @param d        direction to shoot at
   * @param distance distance to shoot the weapon
   * @return boolean value to specify if you hit a monster
   */
  boolean shootWeapon(Direction d, int distance);

  /**
   * Pick up all the weapons that are present in the players given location.
   */
  void pickupWeapons();


}
