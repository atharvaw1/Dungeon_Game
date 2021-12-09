package testpackage;


import static org.junit.Assert.assertEquals;

import dungeoncontroller.CmdControllerImpl;
import dungeoncontroller.TextController;
import dungeonmodel.Dungeon;
import dungeonmodel.DungeonImpl;
import dungeonmodel.rng.IRandomNumberGenerator;
import dungeonmodel.rng.TestRandomNumberGenerator;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;





/**
 * Class to test combined behaviour of model and controller.
 */
public class CmdControllerTest {

  /**
   * Test for failing appendable.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    TextController c = new CmdControllerImpl(input, gameLog);
    c.playGame(m);
  }

  /**
   * Test invalid model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);
    Dungeon m = null;
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    TextController c = new CmdControllerImpl(input, gameLog);
    c.playGame(m);
  }

  /**
   * Test if the player can move around in the dungeon.
   */
  @Test
  public void testMovePlayer() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("M N M N M S M W q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n"
            + "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n"
            + "You have some weapons 3 arrows \n"
            + "You are in a cave\n"
            + "Doors lead to the EAST, NORTH, WEST\n"
            + "Move, Pickup , or Shoot (M-P-S)?\n"
            + "Where do you want to move?(N/S/E/W)\n"
            + "\n"
            + "\n"
            + "You are in a tunnel\n"
            + "Doors lead to the NORTH, SOUTH\n"
            + "Move, Pickup , or Shoot (M-P-S)?\n"
            + "Where do you want to move?(N/S/E/W)\n"
            + "\n"
            + "\n"
            + "You are in a cave\n"
            + "Doors lead to the EAST, NORTH, SOUTH, WEST\n"
            + "You find 1 arrows in the cave\n"
            + "Move, Pickup , or Shoot (M-P-S)?\n"
            + "Where do you want to move?(N/S/E/W)\n"
            + "\n"
            + "\n"
            + "You are in a tunnel\n"
            + "Doors lead to the NORTH, SOUTH\n"
            + "Move, Pickup , or Shoot (M-P-S)?\n"
            + "Where do you want to move?(N/S/E/W)\n"
            + "No exit in direction WEST\n"
            + "\n"
            + "You are in a tunnel\n"
            + "Doors lead to the NORTH, SOUTH\n"
            + "Move, Pickup , or Shoot (M-P-S)?\n"
            + "Quitting game.", gameLog.toString());

  }

  /**
   * Test if the player can pickup treasure around in the dungeon.
   */
  @Test
  public void testPickupTreasure() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("M N M N P T q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 50, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "You find 1 arrows in the cave\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, SOUTH\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a cave\n" +
            "You smell something bad(somewhat pungent) nearby.\n" +
            "Doors lead to the EAST, NORTH, SOUTH, WEST\n" +
            "You find 0 diamonds , 0 rubies , 1 sapphires \n" +
            "You find 1 arrows in the cave\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "What would you like to pickup treasure or weapons (T/W)?\n" +
            "Picked up all the treasure.\n" +
            "\n" +
            "You are in a cave\n" +
            "You smell something bad(somewhat pungent) nearby.\n" +
            "Doors lead to the EAST, NORTH, SOUTH, WEST\n" +
            "You find 1 arrows in the cave\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Quitting game.", gameLog.toString());

  }

  /**
   * Test if the player can pickup weapons around in the dungeon.
   */
  @Test
  public void testPickupWeapon() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("M N M N P W q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, SOUTH\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, SOUTH, WEST\n" +
            "You find 1 arrows in the cave\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "What would you like to pickup treasure or weapons (T/W)?\n" +
            "Picked up all the weapons.\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, SOUTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Quitting game.", gameLog.toString());

  }

  /**
   * Test if the player can shoot arrows in the dungeon and kill the Otyugh.
   */
  @Test
  public void testShootWeapon() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("M N M N M W M S S 1 W S 1 W S 1 W M W q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, SOUTH\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, SOUTH, WEST\n" +
            "You find 1 arrows in the cave\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a cave\n" +
            "You smell something bad(somewhat pungent) nearby.\n" +
            "Doors lead to the EAST, SOUTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a tunnel\n" +
            "You smell something terrible(strongly pungent) nearby.\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Distance to shoot(no of caves)?\n" +
            "Direction to shoot in?(N/S/E/W)\n" +
            "You shoot an arrow west.\n" +
            "You hear a great howl in the distance.\n" +
            "\n" +
            "You are in a tunnel\n" +
            "You smell something terrible(strongly pungent) nearby.\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Distance to shoot(no of caves)?\n" +
            "Direction to shoot in?(N/S/E/W)\n" +
            "You shoot an arrow west.\n" +
            "You hear a great howl in the distance.\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Distance to shoot(no of caves)?\n" +
            "Direction to shoot in?(N/S/E/W)\n" +
            "You shoot an arrow west.\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "Congratulations you have reached the end of the dungeon. \n" +
            "You win!!", gameLog.toString());

  }

  /**
   * Test if the player can win the game.
   */
  @Test
  public void testPlayerWin() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("M N M N M W M S S 1 W S 1 W S 1 W M W q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, SOUTH\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, SOUTH, WEST\n" +
            "You find 1 arrows in the cave\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a cave\n" +
            "You smell something bad(somewhat pungent) nearby.\n" +
            "Doors lead to the EAST, SOUTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a tunnel\n" +
            "You smell something terrible(strongly pungent) nearby.\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Distance to shoot(no of caves)?\n" +
            "Direction to shoot in?(N/S/E/W)\n" +
            "You shoot an arrow west.\n" +
            "You hear a great howl in the distance.\n" +
            "\n" +
            "You are in a tunnel\n" +
            "You smell something terrible(strongly pungent) nearby.\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Distance to shoot(no of caves)?\n" +
            "Direction to shoot in?(N/S/E/W)\n" +
            "You shoot an arrow west.\n" +
            "You hear a great howl in the distance.\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Distance to shoot(no of caves)?\n" +
            "Direction to shoot in?(N/S/E/W)\n" +
            "You shoot an arrow west.\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "Congratulations you have reached the end of the dungeon. \n" +
            "You win!!", gameLog.toString());

  }

  /**
   * Test if the player is eaten by Otyugh.
   */
  @Test
  public void testPlayerEaten() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("M N M N M W M S M W q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a tunnel\n" +
            "Doors lead to the NORTH, SOUTH\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, SOUTH, WEST\n" +
            "You find 1 arrows in the cave\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a cave\n" +
            "You smell something bad(somewhat pungent) nearby.\n" +
            "Doors lead to the EAST, SOUTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "You are in a tunnel\n" +
            "You smell something terrible(strongly pungent) nearby.\n" +
            "Doors lead to the NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "\n" +
            "\n" +
            "Chomp, chomp, chomp, you are eaten by an Otyugh!\n" +
            "Better luck next time", gameLog.toString());

  }


  /**
   * Test if the user enters invalid input.
   */
  @Test
  public void testInvalidInput() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("asdw sdad q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Invalid command.\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Invalid command.\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Quitting game.", gameLog.toString());

  }

  /**
   * Test if the player enters invalid move.
   */
  @Test
  public void testInvalidInputMove() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("M sdad q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Where do you want to move?(N/S/E/W)\n" +
            "Invalid command.\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Quitting game.", gameLog.toString());

  }

  /**
   * Test if the player enters invalid shooting.
   */
  @Test
  public void testInvalidInputShoot() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("S sdad q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Distance to shoot(no of caves)?\n" +
            "Distance must be an integer.\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Quitting game.", gameLog.toString());

  }

  /**
   * Test if the player enters invalid shooting.
   */
  @Test
  public void testInvalidInputPickup() {
    IRandomNumberGenerator rng = new TestRandomNumberGenerator(10);

    StringReader input = new StringReader("P sdad q");
    Appendable gameLog = new StringWriter();
    TextController c = new CmdControllerImpl(input, gameLog);
    Dungeon m = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    c.playGame(m);
    assertEquals("You are Indiana Jones\n" +
            "You have some treasure 0 diamonds , 0 rubies , 0 sapphires \n" +
            "You have some weapons 3 arrows \n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "What would you like to pickup treasure or weapons (T/W)?\n" +
            "Invalid command.\n" +
            "\n" +
            "You are in a cave\n" +
            "Doors lead to the EAST, NORTH, WEST\n" +
            "Move, Pickup , or Shoot (M-P-S)?\n" +
            "Quitting game.", gameLog.toString());

  }
}
