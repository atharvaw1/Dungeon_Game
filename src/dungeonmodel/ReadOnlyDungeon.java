package dungeonmodel;

import java.util.List;

/**
 * A dungeon is a network of tunnels and caves that are interconnected so that player can
 * explore the entire world by traveling from cave to cave through the tunnels that connect
 * them. Each location in the grid represents a location in the dungeon where a player can explore
 * and can be connected to at most four (4) other locations: one to the north, one to the east,
 * one to the south, and one to the west. Notice that in this dungeon locations "wrap" to the one
 * on the other side of the grid. For example, moving to the north from row 0 (at the top) in the
 * grid moves the player to the location in the same column in row 5 (at the bottom).
 * A location can further be classified as tunnel (which has exactly 2 entrances) or a cave
 * (which has 1, 3 or 4 entrances). Read only version of dungeon for the view.
 */
public interface ReadOnlyDungeon {


  /**
   * Get the starting location of the dungeon. This is the place from where the player starts
   * exploring the dungeon.
   *
   * @return an int array containing x and y co-ordinates of the location
   */
  int[] getStart();

  /**
   * Get the end location of the dungeon. The players' goal is to reach this location in the
   * dungeon.
   *
   * @return an int array containing x and y co-ordinates of the location
   */
  int[] getEnd();


  /**
   * The location where the player is currently present in the dungeon.
   *
   * @return an int array containing x and y co-ordinates of the location
   */
  int[] getPlayerLocation();


  /**
   * Returns the available moves that a player can make from the current location and the treasure
   * present in the  current location.
   *
   * @return List containing (connections,treasures) present in current room
   */
  List<List> describePlayerLocation();

  /**
   * Returns the playerId and the treasure collected by the player till now.
   *
   * @return List of playerId and the List of Treasure in room
   */
  List describePlayer();

  /**
   * Returns the list of weapons at the location that the player is currently at.
   *
   * @return List of Weapon at the players location
   */
  List<Weapon> describeWeaponsAtLocation();

  /**
   * Returns the list of treasure at the location that the player is currently at.
   *
   * @return List of Treasure at the players location
   */
  List<Treasure> describeTreasureAtLocation();

  /**
   * Returns exits that the player has from his current location.
   *
   * @return List of Directions the player can take
   */
  List<Direction> describeConnectionsAtLocation();

  /**
   * Smell at the given location. Can be WEAK if 1 monster at distance of 2 units, or can be
   * STRONG if more than 1 monster at 2 units or 1 or more monsters at 1 unit away. Otherwise,
   * NOSMELL.
   *
   * @return the type of smell in the room
   */
  Smell describeSmellAtLocation();

  /**
   * Returns if the players current location is a cave or tunnel. If it is a cave it returns true.
   *
   * @return boolean if player is in cave
   */
  boolean playerInCave();

  /**
   * Checks if player is still alive or dead.
   *
   * @return boolean value to check if player is still alive
   */
  boolean playerIsAlive();

  /**
   * Get the game settings of the dungeon.
   * @return list of all the settings
   */
  List getSettings();

  /**
   * Get max rows of the dungeon.
   * @return max rows
   */
  int getMaxRows();

  /**
   * Get max rows of the dungeon.
   * @return max rows
   */
  int getMaxCols();

  /**
   * Get the monster in the player location.
   * @return monster object
   */
  Monster getMonsterInLocation();


  /**
   * List if visited nodes in the dungeon.
   * @return list of visited nodes
   */
  List<Boolean> getVisited();


}
