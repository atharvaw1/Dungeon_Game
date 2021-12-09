package dungeoncontroller.commands;

import dungeonmodel.Direction;
import dungeonmodel.Dungeon;

import java.io.IOException;
import java.util.Scanner;



/**
 * Implements move command for player. Player can move in any of the 4 given directions, if move
 * is invalid then command will fail and message will be returned to user.
 */
public class Move implements DungeonCommand {

  @Override
  public void execute(Dungeon model, Scanner scan, Appendable out) {
    try {
      out.append("Where do you want to move?(N/S/E/W)\n");
      String input = scan.next();
      switch (input) {
        case "N":
          if (model.describeConnectionsAtLocation().contains(Direction.NORTH)) {
            model.movePlayer(Direction.NORTH);
          } else {
            out.append("No exit in direction " + Direction.NORTH);
          }
          break;
        case "S":
          if (model.describeConnectionsAtLocation().contains(Direction.SOUTH)) {
            model.movePlayer(Direction.SOUTH);
          } else {
            out.append("No exit in direction " + Direction.SOUTH);
          }
          break;
        case "E":
          if (model.describeConnectionsAtLocation().contains(Direction.EAST)) {
            model.movePlayer(Direction.EAST);
          } else {
            out.append("No exit in direction " + Direction.EAST);
          }
          break;
        case "W":
          if (model.describeConnectionsAtLocation().contains(Direction.WEST)) {
            model.movePlayer(Direction.WEST);
          } else {
            out.append("No exit in direction " + Direction.WEST);
          }
          break;
        default:
          out.append("Invalid command.");
          break;
      }
      out.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed.");
    }


  }
}
