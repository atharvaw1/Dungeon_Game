package testpackage;

import static org.junit.Assert.assertEquals;

import dungeoncontroller.GuiController;
import dungeoncontroller.GuiControllerImpl;
import dungeonmodel.Direction;
import dungeonmodel.Dungeon;
import dungeonmodel.rng.IRandomNumberGenerator;
import dungeonmodel.rng.TestRandomNumberGenerator;
import dungeonview.View;
import org.junit.Test;

import java.io.StringWriter;


/**
 * Class the test the GUI controller in isolation from model and view.
 */
public class GuiControllerTest {


  /**
   * Test invalid model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidModel() {
    View view = new MockView(null);
    GuiController controller = new GuiControllerImpl(null, view);
  }

  /**
   * Test invalid view.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidView() {
    Dungeon m = new MockModel(null);
    GuiController controller = new GuiControllerImpl(m, null);
  }


  /**
   * Test moving the player in model.
   */
  @Test
  public void moveTest() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    Appendable gameLog = new StringWriter();
    Appendable viewLog = new StringWriter();
    Dungeon m = new MockModel(gameLog);
    View view = new MockView(viewLog);
    GuiController controller = new GuiControllerImpl(m, view);

    controller.movePlayer(Direction.WEST);
    controller.movePlayer(Direction.EAST);
    controller.movePlayer(Direction.NORTH);
    controller.movePlayer(Direction.SOUTH);

    assertEquals("playerIsAlive called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "Move to WEST called.\n"
            + "playerIsAlive called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "playerIsAlive called.\n"
            + "playerIsAlive called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "Move to EAST called.\n"
            + "playerIsAlive called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "playerIsAlive called.\n"
            + "playerIsAlive called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "Move to NORTH called.\n"
            + "playerIsAlive called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "playerIsAlive called.\n"
            + "playerIsAlive called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "Move to SOUTH called.\n"
            + "playerIsAlive called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "playerIsAlive called.\n", gameLog.toString());

    assertEquals("refresh called.\n"
            + "refresh called.\n"
            + "refresh called.\n"
            + "refresh called.\n", viewLog.toString());

  }

  /**
   * Test picking up treasure and weapons.
   */
  @Test
  public void pickupTest() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    Appendable gameLog = new StringWriter();
    Appendable viewLog = new StringWriter();
    Dungeon m = new MockModel(gameLog);
    View view = new MockView(viewLog);
    GuiController controller = new GuiControllerImpl(m, view);

    controller.pickupTreasure();
    controller.pickupWeapons();

    assertEquals("getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "Pickup treasure called.\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "Pickup weapon called.\n", gameLog.toString());

    assertEquals("refresh called.\n"
            + "refresh called.\n", viewLog.toString());

  }

  /**
   * Test shooting of the player.
   */
  @Test
  public void shootCalled() {

    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    Appendable gameLog = new StringWriter();
    Appendable viewLog = new StringWriter();
    Dungeon m = new MockModel(gameLog);
    View view = new MockView(viewLog);
    GuiController controller = new GuiControllerImpl(m, view);

    controller.shoot(Direction.WEST, 1);
    controller.shoot(Direction.NORTH, 5);

    assertEquals("getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "Shoot weapon WEST. Distance: 1\n"
            + "getPlayerLocation called.\n"
            + "getEnd called.\n"
            + "Shoot weapon NORTH. Distance: 5\n", gameLog.toString());


    assertEquals("refresh called.\n"
            + "refresh called.\n", viewLog.toString());

  }
}
