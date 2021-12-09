package dungeoncontroller.commands;

import dungeonmodel.Direction;
import dungeonmodel.Dungeon;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;



/**
 * Implements shoot command for player. Player can shoot arrow in given direction and distance
 * from his location.
 */
public class Shoot implements DungeonCommand {


  @Override
  public void execute(Dungeon model, Scanner scan, Appendable out) {

    try {

      if (((List) model.describePlayer().get(2)).size() < 1) {
        out.append("You are out of arrows. Keep searching the dungeon to pick up more.\n");
        return;
      }

      out.append("Distance to shoot(no of caves)?\n");
      int distance = 0;
      try {
        distance = Integer.parseInt(scan.next());
      } catch (NumberFormatException e) {
        out.append("Distance must be an integer.\n");
        return;
      }

      out.append("Direction to shoot in?(N/S/E/W)\n");
      String input = scan.next();
      boolean hit;
      switch (input) {
        case "N":
          hit = model.shootWeapon(Direction.NORTH, distance);
          out.append("You shoot an arrow north.\n");
          break;
        case "S":
          hit = model.shootWeapon(Direction.SOUTH, distance);
          out.append("You shoot an arrow south.\n");
          break;
        case "E":
          hit = model.shootWeapon(Direction.EAST, distance);
          out.append("You shoot an arrow east.\n");
          break;
        case "W":
          hit = model.shootWeapon(Direction.WEST, distance);
          out.append("You shoot an arrow west.\n");
          break;
        default:
          out.append("Invalid command.\n");
          hit = false;
          break;
      }

      if (hit) {
        out.append("You hear a great howl in the distance.\n");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Append failed.");
    } catch (IllegalArgumentException e) {

      try {
        out.append(e.getMessage() + "\n");
      } catch (IOException ex) {
        throw new IllegalStateException("Append failed.");
      }
    }

  }
}
