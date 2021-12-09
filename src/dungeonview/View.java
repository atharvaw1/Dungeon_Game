package dungeonview;

import dungeoncontroller.GuiController;
import dungeonmodel.ReadOnlyDungeon;

/**
 * Interface to represent the graphical view of the dungeon game.
 */
public interface View {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addListener(GuiController listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Resets the view when a new game is started.
   */
  void resetView();

  /**
   * Updates the model when a new game is started.
   * @param model the readonly model
   */
  void updateModel(ReadOnlyDungeon model);

  /**
   * Display the win message.
   */
  void displayWin();

  /**
   * Display the loss message.
   */
  void displayLoss();

}
