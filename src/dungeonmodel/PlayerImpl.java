package dungeonmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the player and implements the Player interface. A player has an id and
 * the treasure collected by the player. This class handles the player actions like describing
 * a player, and addingTreasure to the player. Package private to prevent
 * use outside dungeon.
 */
class PlayerImpl implements Player {

  private final String playerId;
  private List<Treasure> treasureCollected;
  private List<Weapon> weapons;
  private boolean alive;


  /**
   * Constructor for player. Takes in playerId and initializes treasure to empty array.
   *
   * @param playerId the playerId as String
   */
  public PlayerImpl(String playerId) {

    if (playerId == null) {
      throw new IllegalArgumentException("Player name cannot be null");
    }

    this.playerId = playerId;
    this.treasureCollected = new ArrayList<>();
    this.weapons = new ArrayList<>();
    this.alive = true;

    //Player starts with 3 crooked arrows
    this.weapons.add(Weapon.CROOKEDARROW);
    this.weapons.add(Weapon.CROOKEDARROW);
    this.weapons.add(Weapon.CROOKEDARROW);
  }


  @Override
  public List describePlayer() {
    List describe = new ArrayList<String>();
    describe.add(playerId);
    describe.add(new ArrayList<Treasure>(treasureCollected));
    describe.add(new ArrayList<Weapon>(weapons));
    return describe;
  }

  @Override
  public List<Weapon> getWeapons() {
    return new ArrayList<Weapon>(weapons);
  }

  @Override
  public boolean isAlive() {
    return this.alive;
  }

  /**
   * Set the player state to alive or dead. Package private to prevent mutation outside
   * dungeon class.
   *
   * @param alive true if alive false if not
   */
  void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Add the given weapons to the player. Package private to prevent use outside dungeon class.
   *
   * @param newWeapons the list of weapons to be added
   */
  void addWeapons(List<Weapon> newWeapons) {
    this.weapons.addAll(new ArrayList<Weapon>(newWeapons));
  }

  /**
   * Add treasure that is collected by the player to his existing List of treasures.
   *
   * @param treasures list of Treasure to be added to player
   */
  void addTreasure(List<Treasure> treasures) {
    this.treasureCollected.addAll(new ArrayList<>(treasures));
  }

  /**
   * Removes the given weapon from the players list of weapons.
   *
   * @param w Weapon to be removed
   */
  void removeWeapon(Weapon w) {
    weapons.remove(w);
  }


  /**
   * Removes the given treasure from the players list of weapons.
   */
  void removeTreasure() {
    treasureCollected = new ArrayList<Treasure>();
  }

}
