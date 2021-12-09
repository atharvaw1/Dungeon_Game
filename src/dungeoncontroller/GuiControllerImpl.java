package dungeoncontroller;

import dungeonmodel.Direction;
import dungeonmodel.Dungeon;
import dungeonmodel.DungeonImpl;
import dungeonmodel.rng.IRandomNumberGenerator;
import dungeonmodel.rng.RandomNumberGenerator;
import dungeonmodel.rng.TestRandomNumberGenerator;
import dungeonview.DungeonView;
import dungeonview.View;

/**
 * The main controller class for the GUI controller that implements the gui controller interface.
 */
public class GuiControllerImpl implements GuiController {

  private Dungeon model;
  private View view;
  private int oldseed;
  private int interconnectivity;
  private boolean wrapping;
  private int x;
  private int y;
  private int treasurePercent;
  private int monsterCount;
  private String playerId = "Indiana Jones";


  /**
   * The constructor takes in model and view.
   * @param model dungeon model
   * @param view dungeon GUI view
   */
  public GuiControllerImpl(Dungeon model, View view) {

    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and view cannot be null.");
    }
    this.model = model;
    this.view = view;
  }

  /**
   * Constructor that assigns default values to parameters to create model.
   */
  public GuiControllerImpl() {
    interconnectivity = 3;
    wrapping = false;
    x = 6;
    y = 6;
    treasurePercent = 30;
    monsterCount = 2;
    playerId = "Indiana Jones";
  }

  @Override
  public void movePlayer(Direction d) {
    try {
      if (model.playerIsAlive() && !(model.getPlayerLocation()[0] == model.getEnd()[0]
              && model.getPlayerLocation()[1] == model.getEnd()[1])) {
        model.movePlayer(d);
        view.refresh();
        if (model.playerIsAlive() && model.getPlayerLocation()[0] == model.getEnd()[0]
                && model.getPlayerLocation()[1] == model.getEnd()[1]) {
          view.displayWin();
        }
        if (!model.playerIsAlive()) {
          view.displayLoss();
        }
      }
    } catch (IllegalArgumentException ignored) {

    }


  }

  @Override
  public void pickupTreasure() {
    if (!(model.getPlayerLocation()[0] == model.getEnd()[0]
            && model.getPlayerLocation()[1] == model.getEnd()[1])) {
      model.pickupTreasure();
      view.refresh();
    }
  }

  @Override
  public void pickupWeapons() {
    if (!(model.getPlayerLocation()[0] == model.getEnd()[0]
            && model.getPlayerLocation()[1] == model.getEnd()[1])) {
      model.pickupWeapons();
      view.refresh();
    }
  }

  @Override
  public boolean shoot(Direction d, Integer distance) {

    if (!(model.getPlayerLocation()[0] == model.getEnd()[0]
            && model.getPlayerLocation()[1] == model.getEnd()[1])) {
      try {
        boolean temp = model.shootWeapon(d, distance);
        view.refresh();
        return temp;
      } catch (IllegalArgumentException | IllegalStateException ignored) {
        return false;
      }
    }
    return false;
  }

  @Override
  public boolean isValidIput(int interconnectivity, boolean wrapping,
                             int x, int y, int treasurePercent, int monsterCount, String playerId) {
    try {
      IRandomNumberGenerator rng = new RandomNumberGenerator(); //Generate random seed
      int temp = rng.getRandomNumber(0, 10000); //Save seed
      rng = new TestRandomNumberGenerator(oldseed); //Create new rng with seed

      Dungeon tempmodel = new DungeonImpl(interconnectivity, wrapping, x, y, rng,
              treasurePercent, monsterCount, playerId);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public void newGame(int interconnectivity, boolean wrapping, int x, int y,
                      int treasurePercent, int monsterCount, String playerId) {


    view.resetView();
    IRandomNumberGenerator rng = new RandomNumberGenerator(); //Generate random seed
    oldseed = rng.getRandomNumber(0, 10000); //Save seed
    rng = new TestRandomNumberGenerator(oldseed); //Create new rng with seed

    model = new DungeonImpl(interconnectivity, wrapping, x, y, rng,
            treasurePercent, monsterCount, playerId);

    this.interconnectivity = interconnectivity;
    this.wrapping = wrapping;
    this.x = x;
    this.y = y;
    this.treasurePercent = treasurePercent;
    this.monsterCount = monsterCount;
    this.playerId = playerId;
    view.updateModel(model);
    view.refresh();


  }

  @Override
  public void newGame() {

    view.resetView();
    IRandomNumberGenerator rng = new RandomNumberGenerator(); //Generate random seed
    oldseed = rng.getRandomNumber(0, 10000); //Save seed
    rng = new TestRandomNumberGenerator(oldseed);
    model = new DungeonImpl(interconnectivity, wrapping, x, y, rng,
            treasurePercent, monsterCount, playerId);
    view.updateModel(model);
    view.refresh();
  }


  @Override
  public void playGame() {

    IRandomNumberGenerator rng = new RandomNumberGenerator(); //Generate random seed
    oldseed = rng.getRandomNumber(0, 10000); //Save seed
    rng = new TestRandomNumberGenerator(oldseed); //Create new rng with seed

    model = new DungeonImpl(interconnectivity, wrapping, x, y, rng,
            treasurePercent, monsterCount, playerId);

    view = new DungeonView(model);
    view.addListener(this);
    view.makeVisible();
    view.refresh();
  }

  @Override
  public void restartGame() {
    view.resetView();
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(oldseed);
    model = new DungeonImpl(interconnectivity, wrapping, x, y, rng,
            treasurePercent, monsterCount, playerId);
    view.updateModel(model);
    view.refresh();


  }


  @Override
  public void handleCellClick(int roomId) {
    int currentRoomId = model.getPlayerLocation()[0] * model.getMaxCols()
            + model.getPlayerLocation()[1];
    try {
      if (model.playerIsAlive() && !(model.getPlayerLocation()[0] == model.getEnd()[0]
              && model.getPlayerLocation()[1] == model.getEnd()[1])) {
        if ((currentRoomId - 1) == roomId) {
          model.movePlayer(Direction.WEST);
        } else if ((currentRoomId + 1) == roomId) {
          model.movePlayer(Direction.EAST);
        } else if ((currentRoomId - model.getMaxCols()) == roomId) {
          model.movePlayer(Direction.NORTH);
        } else if ((currentRoomId + model.getMaxCols()) == roomId) {
          model.movePlayer(Direction.SOUTH);
        }

        if (this.wrapping) {
          if (model.getPlayerLocation()[1] == 0
                  && (roomId == currentRoomId + model.getMaxCols() - 1)) {
            model.movePlayer(Direction.WEST);
          } else if (model.getPlayerLocation()[1] == model.getMaxCols() - 1
                  && (roomId == currentRoomId - (model.getMaxCols() - 1))) {
            model.movePlayer(Direction.EAST);
          } else if (model.getPlayerLocation()[0] == 0
                  && (roomId == currentRoomId + (model.getMaxCols() * (model.getMaxRows() - 1)))) {
            model.movePlayer(Direction.NORTH);
          } else if (model.getPlayerLocation()[0] == model.getMaxRows() - 1
                  && (roomId == currentRoomId - (model.getMaxCols() * (model.getMaxRows() - 1)))) {
            model.movePlayer(Direction.SOUTH);
          }

        }
        view.refresh();
      }
    } catch (IllegalArgumentException ignored) {
    }
  }

}
