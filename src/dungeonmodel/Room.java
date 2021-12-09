package dungeonmodel;

import java.util.List;
import java.util.Map;

/**
 * Represents a room in the dungeon that can be either a cave or a tunnel. Each room has
 * connections to the adjacent rooms and links to them with a specific direction. Each room has a
 * nodeNumber that is a unique integer assigned to it to identify it in the grid. A room that has
 * only 2 connections is a tunnel and all others are caves. A cave can contain a single or multiple
 * treasures. Package private to prevent use outside dungeon.
 */
interface Room {

  /**
   * The treasure contained in a room(cave). Will return empty List for all tunnels.
   *
   * @return a List of Treasure that the room has
   */
  List<Treasure> getTreasure();

  /**
   * The directions that can be reached from this room and the room that it connects to.
   *
   * @return Map of Direction and the Room that the direction will connect to
   */
  Map<Direction, Room> getConnections();

  /**
   * The nodeNumber of the room in the dungeon grid.
   *
   * @return the node number
   */
  int getNodeNumber();

  /**
   * Returns if the given room is a cave or tunnel.
   *
   * @return true if it is a cave and false if it is a tunnel
   */
  boolean isCave();

  /**
   * Returns the Monster is present in room. Returns null if there is no monster.
   *
   * @return Monster object
   */
  Monster getMonster();

  /**
   * Returns the list weapons present in the room.
   *
   * @return List of Weapon in room
   */
  List<Weapon> getWeapons();

}
