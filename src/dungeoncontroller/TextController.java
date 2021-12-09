package dungeoncontroller;

import dungeonmodel.Dungeon;

/**
 * Represents the controller to use the dungeon model. Takes care of all the input from user and
 * calls the expected model methods and returns the required to be output as a string.
 */
public interface TextController {

  /**
   * Play the game where player has to traverse through dungeon and pickup treasure and weapons.
   * Players goal is to reach the end node without getting eaten by a monster.
   */
  void playGame(Dungeon model);

}
