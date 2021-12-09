package dungeonmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the Room interface. Has additional package private methods that mutate the room. Add
 * a connection to the room for a given direction connecting to a Room. Add and remove treasure
 * from the room. Package private to prevent use outside dungeon.
 */
class RoomImpl implements Room {

  private final Map<Direction, Room> connections;
  private final int nodeNumber;
  private List<Treasure> treasures;
  private Monster monster;
  private List<Weapon> weapons;

  /**
   * Constructor for a room. Takes in node number as input and initializes treasure and connections
   * to empty arrays.
   *
   * @param nodeNumber integer representing the node in a 2d grid
   */
  public RoomImpl(int nodeNumber) {

    if (nodeNumber < 0) {
      throw new IllegalArgumentException("RoomNo cannot be null.");
    }
    this.nodeNumber = nodeNumber;
    this.treasures = new ArrayList<Treasure>();
    this.connections = new HashMap<Direction, Room>();
    this.weapons = new ArrayList<Weapon>();
  }

  @Override
  public List<Treasure> getTreasure() {
    return new ArrayList<Treasure>(treasures);
  }

  @Override
  public Map<Direction, Room> getConnections() {
    return new HashMap<>(connections);
  }

  @Override
  public int getNodeNumber() {
    return this.nodeNumber;
  }

  @Override
  public boolean isCave() {
    return this.getConnections().keySet().size() != 2;
  }

  @Override
  public Monster getMonster() {
    return monster;
  }

  @Override
  public List<Weapon> getWeapons() {
    return new ArrayList<Weapon>(weapons);
  }

  /**
   * Package private method to remove all treasure present in the room.
   */
  void removeTreasure() {
    this.treasures = new ArrayList<Treasure>();
  }

  /**
   * Package private method to add connections to the room. Takes in a Direction and the room
   * that the direction will take you to.
   *
   * @param d direction of connection
   * @param r the room that the direction connects to
   */
  void addConnection(Direction d, Room r) {
    this.connections.put(d, r);
  }

  /**
   * Add treasure to the room. Package private method to prevent mutation access outside package.
   *
   * @param t treasure to be added
   */
  void addTreasure(Treasure t) {
    this.treasures.add(t);
  }

  /**
   * Add monster to the room.
   *
   * @param monster monster to be added
   */
  void addMonster(Monster monster) {
    this.monster = monster;
  }

  /**
   * Add weapon to the room.
   *
   * @param w weapon to be added
   */
  void addWeapon(Weapon w) {
    this.weapons.add(w);
  }

  /**
   * Remove all weapons from the room.
   */
  void removeWeapons() {
    this.weapons = new ArrayList<Weapon>();
  }

}

