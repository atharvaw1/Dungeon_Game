package testpackage;

import dungeonmodel.Direction;
import dungeonmodel.Dungeon;
import dungeonmodel.Monster;
import dungeonmodel.Smell;
import dungeonmodel.Treasure;
import dungeonmodel.Weapon;

import java.io.IOException;
import java.util.List;

/**
 * The mock model to test controller that logs the input.
 */
public class MockModel implements Dungeon {

  private final Appendable log;

  /**
   * Constructor that initializes the log.
   * @param log the log to append to
   */
  public MockModel(Appendable log) {
    this.log = log;
  }

  @Override
  public void movePlayer(Direction d) {
    try {
      log.append("Move to " + d.toString() + " called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }

  }

  @Override
  public void pickupTreasure() {
    try {
      log.append("Pickup treasure called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
  }

  @Override
  public boolean shootWeapon(Direction d, int distance) {
    try {
      log.append("Shoot weapon ").append(d.toString()).append(". Distance: ")
              .append(String.valueOf(distance)).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return false;
  }

  @Override
  public void pickupWeapons() {

    try {
      log.append("Pickup weapon called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }


  }

  @Override
  public int[] getStart() {
    try {
      log.append("getStart called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return new int[]{0, 0};
  }

  @Override
  public int[] getEnd() {
    try {
      log.append("getEnd called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return new int[]{5, 5};
  }

  @Override
  public int[] getPlayerLocation() {
    try {
      log.append("getPlayerLocation called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return new int[]{0, 0};
  }

  @Override
  public List<List> describePlayerLocation() {
    try {
      log.append("describePlayerLocation called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return null;
  }

  @Override
  public List describePlayer() {
    try {
      log.append("describePlayer called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return null;
  }

  @Override
  public List<Weapon> describeWeaponsAtLocation() {
    try {
      log.append("describeWeaponsAtLocation  called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return null;
  }

  @Override
  public List<Treasure> describeTreasureAtLocation() {
    try {
      log.append("describeTreasureAtLocation called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return null;
  }

  @Override
  public List<Direction> describeConnectionsAtLocation() {
    try {
      log.append("describeConnectionsAtLocation called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return null;
  }

  @Override
  public Smell describeSmellAtLocation() {
    try {
      log.append("describeSmellAtLocation called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return null;
  }

  @Override
  public boolean playerInCave() {
    try {
      log.append("isPlayerInCave called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return true;
  }

  @Override
  public boolean playerIsAlive() {
    try {
      log.append("playerIsAlive called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return true;
  }

  @Override
  public int getMaxRows() {
    try {
      log.append("getMaxRows called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return 0;
  }

  @Override
  public int getMaxCols() {
    try {
      log.append("getMaxCols called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return 0;
  }

  @Override
  public Monster getMonsterInLocation() {
    return null;
  }


  @Override
  public List getSettings() {
    try {
      log.append("getSettings called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
    return null;
  }

  @Override
  public List<Boolean> getVisited() {
    return null;
  }
}
