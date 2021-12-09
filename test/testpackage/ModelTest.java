package testpackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeonmodel.Direction;
import dungeonmodel.Dungeon;
import dungeonmodel.DungeonImpl;
import dungeonmodel.rng.IRandomNumberGenerator;
import dungeonmodel.rng.TestRandomNumberGenerator;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Class to test the functionality of dungeon.
 */
public class ModelTest {
  private IRandomNumberGenerator rng;
  private Dungeon nonwrappingDungeon;


  /**
   * Test if interconnectivity above number of edges throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInterconnectivity() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(100, false, 6, 6, rng, 20, 2, "Indiana Jones");

  }

  /**
   * Test rows and columns below 5 throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRowsColumnsLimit() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 1, 1, rng, 20, 2, "Indiana Jones");

  }


  /**
   * Test to check if start is returned correctly.
   */
  @Test
  public void getStart() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    assertEquals(5, nonwrappingDungeon.getStart()[0]);
    assertEquals(2, nonwrappingDungeon.getStart()[1]);

  }

  /**
   * Test to check if end is returned correctly.
   */
  @Test
  public void getEnd() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    assertEquals(4, nonwrappingDungeon.getEnd()[0]);
    assertEquals(0, nonwrappingDungeon.getEnd()[1]);

  }

  /**
   * Test to check if end is returned correctly.
   */
  @Test
  public void getPlayerLocation() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    assertEquals(5, nonwrappingDungeon.getPlayerLocation()[0]);
    assertEquals(2, nonwrappingDungeon.getPlayerLocation()[1]);
  }

  /**
   * Move player and check if he was moved in the grid.
   */
  @Test
  public void movePlayer() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    assertEquals(5, nonwrappingDungeon.getPlayerLocation()[0]);
    assertEquals(2, nonwrappingDungeon.getPlayerLocation()[1]);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    assertEquals(5, nonwrappingDungeon.getPlayerLocation()[0]);
    assertEquals(1, nonwrappingDungeon.getPlayerLocation()[1]);

    //Test if dungeon "wraps" by moveing player beyond the edge
    Dungeon wrappingDungeon;
    rng = new TestRandomNumberGenerator(150);
    wrappingDungeon = new DungeonImpl(3, true, 6, 6, rng, 20, 2, "Indiana Jones");
    assertEquals(1, wrappingDungeon.getPlayerLocation()[0]);
    assertEquals(0, wrappingDungeon.getPlayerLocation()[1]);
    wrappingDungeon.movePlayer(Direction.SOUTH);
    wrappingDungeon.movePlayer(Direction.SOUTH);
    wrappingDungeon.movePlayer(Direction.SOUTH);
    wrappingDungeon.movePlayer(Direction.WEST);
    assertEquals(4, wrappingDungeon.getPlayerLocation()[0]);
    assertEquals(5, wrappingDungeon.getPlayerLocation()[1]);

  }

  /**
   * Move player in illegal location and check if IAE was thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void movePlayerFail() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
  }

  /**
   * Check if pickupTreasure adds the treasure to the player and removes it from the room.
   */
  @Test
  public void pickupTreasure() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    assertEquals(5, nonwrappingDungeon.getPlayerLocation()[0]);
    assertEquals(2, nonwrappingDungeon.getPlayerLocation()[1]);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.pickupTreasure();
    assertEquals("[DIAMOND]", nonwrappingDungeon.describePlayer().get(1).toString());
    assertEquals("\n"
                    + "0,0[] [] null 0,1[] [CROOKEDARROW] null 0,2[] [] null 0,3[] "
                    + "[CROOKEDARROW] null 0,4[] [] null 0,5[] [] null \n"
                    + "1,0[] [] null 1,1[] [CROOKEDARROW, CROOKEDARROW] null "
                    + "1,2[] [] null 1,3[] [] null 1,4[] [] null 1,5[DIAMOND] [] null \n"
                    + "2,0[] [] null 2,1[] [] null 2,2[] [] null 2,3[] [] null "
                    + "2,4[DIAMOND, RUBY, DIAMOND] [] null 2,5[] [CROOKEDARROW] null \n"
                    + "3,0[] [] Thief 3,1[] [] null 3,2[] [CROOKEDARROW] null 3,3[] [CROOKEDARROW]"
                    + " null 3,4[DIAMOND] [] null 3,5[] [CROOKEDARROW] Otyugh \n"
                    + "4,0[RUBY] [] Otyugh 4,1[] [] null 4,2[] [] null 4,3[] [] null "
                    + "4,4[] [] null 4,5[] [CROOKEDARROW] null \n"
                    + "5,0[] [] null 5,1[] [] null 5,2[] [] null 5,3[] [] "
                    + "null 5,4[] [] null 5,5[] [] null \n",
            nonwrappingDungeon.toString());

    //Test if it works in tunnel and no treasure is added
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.pickupTreasure();
    assertEquals("[DIAMOND]", nonwrappingDungeon.describePlayer().get(1).toString());
    assertEquals("\n"
                    + "0,0[] [] null 0,1[] [CROOKEDARROW] null 0,2[] [] null 0,3[] [CROOKEDARROW] "
                    + "null 0,4[] [] null 0,5[] [] null \n"
                    + "1,0[] [] null 1,1[] [CROOKEDARROW, CROOKEDARROW] null 1,2[] [] null 1,3[] []"
                    + " null 1,4[] [] null 1,5[DIAMOND] [] null \n"
                    + "2,0[] [] null 2,1[] [] null 2,2[] [] null 2,3[] [] null 2,4[DIAMOND, RUBY,"
                    + " DIAMOND] [] null 2,5[] [CROOKEDARROW] null \n"
                    + "3,0[] [] Thief 3,1[] [] null 3,2[] [CROOKEDARROW] null 3,3[] [CROOKEDARROW]"
                    + " null 3,4[DIAMOND] [] null 3,5[] [CROOKEDARROW] Otyugh \n"
                    + "4,0[RUBY] [] Otyugh 4,1[] [] null 4,2[] [] null 4,3[] [] null 4,4[] [] null"
                    + " 4,5[] [CROOKEDARROW] null \n"
                    + "5,0[] [] null 5,1[] [] null 5,2[] [] null 5,3[] [] null 5,4[] [] null 5,5[]"
                    + " [] null \n",
            nonwrappingDungeon.toString());

  }

  /**
   * Check if player location is returned in the correct format and datastructures.
   */
  @Test
  public void describePlayerLocation() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng,
            20, 2, "Indiana Jones");


    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    assertEquals("[[EAST], [DIAMOND]]", nonwrappingDungeon.describePlayerLocation()
            .toString());
  }

  /**
   * Check if player details are returned in the correct format and datastructures.
   */
  @Test
  public void describePlayer() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    assertEquals("[Indiana Jones, [], [CROOKEDARROW, CROOKEDARROW, CROOKEDARROW]]",
            nonwrappingDungeon.describePlayer().toString());
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.pickupTreasure();
    assertEquals("[Indiana Jones, [DIAMOND], [CROOKEDARROW, CROOKEDARROW, CROOKEDARROW]]",
            nonwrappingDungeon.describePlayer().toString());
  }

  /**
   * Test toString method of dungeon to dump dungeon on the screen.
   */
  @Test
  public void testToString() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    assertEquals("\n"
                    + "0,0[] [] null 0,1[] [CROOKEDARROW] null 0,2[] [] null 0,3[] [CROOKEDARROW] "
                    + "null 0,4[] [] null 0,5[] [] null \n"
                    + "1,0[] [] null 1,1[] [CROOKEDARROW, CROOKEDARROW] null 1,2[] "
                    + "[] null 1,3[] [] "
                    + "null 1,4[] [] null 1,5[DIAMOND] [] null \n"
                    + "2,0[] [] null 2,1[] [] null 2,2[] [] null 2,3[] [] null 2,4[DIAMOND, RUBY, "
                    + "DIAMOND] [] null 2,5[] [CROOKEDARROW] null \n"
                    + "3,0[] [] Thief 3,1[] [] null 3,2[] [CROOKEDARROW] null 3,3[] [CROOKEDARROW] "
                    + "null 3,4[DIAMOND] [] null 3,5[] [CROOKEDARROW] Otyugh \n"
                    + "4,0[RUBY] [] Otyugh 4,1[] [] null 4,2[] [] null 4,3[] [] null 4,4[] [] null "
                    + "4,5[] [CROOKEDARROW] null \n"
                    + "5,0[DIAMOND] [] null 5,1[] [] null 5,2[] [] null 5,3[] [] null 5,4[] [] null"
                    + " 5,5[] [] null \n",
            nonwrappingDungeon.toString());

  }

  /**
   * Test the minimum length of path between start and end node is 5.
   */
  @Test
  public void testPathLength() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    int pathLength = 0;
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    pathLength++;
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    pathLength++;
    nonwrappingDungeon.movePlayer(Direction.WEST);
    pathLength++;
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    pathLength++;
    nonwrappingDungeon.movePlayer(Direction.WEST);
    pathLength++;
    assertTrue(pathLength >= 5);
    assertEquals(4, nonwrappingDungeon.getPlayerLocation()[0]);
    assertEquals(0, nonwrappingDungeon.getPlayerLocation()[1]);


  }

  /**
   * Test to check if all the nodes of dungeon can be reached.
   */
  @Test
  public void testConnectivity() {
    rng = new TestRandomNumberGenerator(10);
    Dungeon nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng,
            20, 2, "Indiana Jones");
    assertEquals(5, nonwrappingDungeon.getPlayerLocation()[0]);
    assertEquals(2, nonwrappingDungeon.getPlayerLocation()[1]);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.EAST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    assertEquals(4, nonwrappingDungeon.getPlayerLocation()[0]);
    assertEquals(3, nonwrappingDungeon.getPlayerLocation()[1]);

  }

  /**
   * Test if the weapons descriprion works properly.
   */
  @Test
  public void describeWeaponsAtLocation() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    assertEquals("[CROOKEDARROW]", nonwrappingDungeon.describeWeaponsAtLocation().toString());
  }

  /**
   * Test if treasure described is accurate to the actual treasure at the location.
   */
  @Test
  public void describeTreasureAtLocation() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    assertEquals("[DIAMOND]", nonwrappingDungeon.describeTreasureAtLocation().toString());

  }

  /**
   * Test that the connections returned are equal to the actual connections of the cave.
   */
  @Test
  public void describeConnectionsAtLocation() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    List<Direction> sorted = nonwrappingDungeon.describeConnectionsAtLocation();
    sorted.sort(Comparator.comparing(Enum::toString));
    assertEquals("[EAST, NORTH, WEST]", sorted.toString());

  }

  /**
   * Test if smell returned is equal to the expected smell when Otyugh is 1 and 2 units away.
   */
  @Test
  public void describeSmellAtLocation() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    assertEquals("NOSMELL", nonwrappingDungeon.describeSmellAtLocation().toString());
    nonwrappingDungeon.movePlayer(Direction.WEST);
    assertEquals("WEAK", nonwrappingDungeon.describeSmellAtLocation().toString());
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    assertEquals("STRONG", nonwrappingDungeon.describeSmellAtLocation().toString());

  }

  /**
   * Test if player is in cave or tunnel.
   */
  @Test
  public void playerInCave() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    assertTrue(nonwrappingDungeon.playerInCave());
    nonwrappingDungeon.movePlayer(Direction.WEST);
    assertFalse(nonwrappingDungeon.playerInCave());
  }

  /**
   * Test if player is alive before and after the Otyugh killing the player.
   */
  @Test
  public void playerIsAlive() {

    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    assertTrue(nonwrappingDungeon.playerIsAlive());
    nonwrappingDungeon.movePlayer(Direction.WEST);
    assertFalse(nonwrappingDungeon.playerIsAlive());

  }

  /**
   * Test shooting the weapon and hitting Otyugh.
   */
  @Test
  public void shootWeapon() {
    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.WEST);
    nonwrappingDungeon.movePlayer(Direction.SOUTH);
    assertTrue(nonwrappingDungeon.shootWeapon(Direction.WEST, 1));
    assertFalse(nonwrappingDungeon.shootWeapon(Direction.NORTH, 10));

  }

  /**
   * Test if weapons are picked up properly.
   */
  @Test
  public void pickupWeapons() {

    rng = new TestRandomNumberGenerator(10);
    nonwrappingDungeon = new DungeonImpl(3, false, 6, 6, rng, 20, 2, "Indiana Jones");
    nonwrappingDungeon.pickupWeapons();
    assertEquals("[Indiana Jones, [], [CROOKEDARROW, CROOKEDARROW, CROOKEDARROW]]",
            nonwrappingDungeon.describePlayer().toString());
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.movePlayer(Direction.NORTH);
    nonwrappingDungeon.pickupWeapons();
    assertEquals("[Indiana Jones, [], [CROOKEDARROW, CROOKEDARROW, CROOKEDARROW, CROOKEDARROW]]",
            nonwrappingDungeon.describePlayer().toString());
  }


}