package dungeoncontroller;

import dungeonmodel.Direction;

/**
 * Represents the controller to use the dungeon model. Takes care of all the input from user and
 * calls the expected model methods and returns the required to be output as a string.
 */
public interface GuiController {

  /**
   * Play the game where player has to traverse through dungeon and pickup treasure and weapons.
   * Players goal is to reach the end node without getting eaten by a monster.
   */
  void playGame();

  /**
   * Start a new game with default settings in the model.
   */
  void newGame();

  /**
   * Start a new game with specified settings.
   * @param interconnectivity no of extra connections in the dungeon
   * @param wrapping if dungeon can be wrapped or not
   * @param x height of dungeon
   * @param y width of dungeon
   * @param treasurePercent the treasure percentage in dungeon
   * @param monsterCount the count of monsters in the dungeon
   * @param playerId the players Name
   */
  void newGame(int interconnectivity, boolean wrapping, int x, int y,
               int treasurePercent, int monsterCount, String playerId);


  /**
   * Restart game with the seed that was being used for current game.
   */
  void restartGame();

  /**
   * Handle the situation where a cell in the dungeon is clicked to move player.
   * @param roomId the room clicked
   */
  void handleCellClick(int roomId);

  /**
   * Move the player in specified direction.
   * @param d direction to move
   */
  void movePlayer(Direction d);

  /**
   * Pickup the treasure at players current location.
   */
  void pickupTreasure();

  /**
   * Pickup the weapons at players current location.
   */
  void pickupWeapons();

  /**
   * Shoot an arrow in given direction and distance.
   * @param d the direction to shoot
   * @param distance the distance to shoot
   * @return if shot was a successful hit or not
   */
  boolean shoot(Direction d, Integer distance);

  /**
   * Check if given input is valid to make a new model.
   * @param interconnectivity no of extra connections in the dungeon
   * @param wrapping if dungeon can be wrapped or not
   * @param x height of dungeon
   * @param y width of dungeon
   * @param treasurePercent the treasure percentage in dungeon
   * @param monsterCount the count of monsters in the dungeon
   * @param playerId the players Name
   * @return if a valid model can be made
   */
  boolean isValidIput(int interconnectivity, boolean wrapping, int x, int y,
                      int treasurePercent, int monsterCount, String playerId);

}
