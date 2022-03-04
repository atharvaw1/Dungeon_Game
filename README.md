# PDP Project4 - Graphical and Text Based Adventure Game

## Overview

The world for our game consists of a dungeon, a network of tunnels and caves that are interconnected
so that player can explore the entire world by traveling from cave to cave through the tunnels that
connect them. Consider the following example:

This example dungeon is represented by a 6 x 8 two-dimensional grid. Each location in the grid
represents a location in the dungeon where a player can explore and can be connected to at most
four (4) other locations: one to the north, one to the east, one to the south, and one to the west.
Notice that in this dungeon locations "wrap" to the one on the other side of the grid. For example,
moving to the north from row 0 (at the top) in the grid moves the player to the location in the same
column in row 5 (at the bottom). A location can further be classified as tunnel (which has exactly 2
entrances) or a cave (which has 1, 3 or 4 entrances).

The dungeon also has weapons and treasure that the player can pickup. The player needs to slay the
Otyughs that reside in the dungeon in order to proceed and win. The goal of a player is to enter the
dungeon at the start node and collect treasure from the dungeon and exit the dungeon at the end
node.

## Features

The Dungeon has the following features:

* Both wrapping and non-wrapping dungeons can be created with different degrees of interconnectivity
* Treasure can be added to a specified percentage of caves.
* Provide a description of the player that, at a minimum, includes a description of what treasure
  the player has collected
* Provide a description of the player's location that at the minimum includes a description of
  treasure in the room and the possible moves (north, east, south, west) that the player can make
  from their current location
* A player can move from their current location
* A player can pick up treasure that is located in their same location
* A player starts with 3 arrows
* A player can pickup more arrows in the dungeon
* A player can shoot an arrow by specifing the distance and direction to shoot
* If player enters a cave with an Otyugh he dies
* If player enters cave of an injured Otyugh he has a 50% chance to survive
* If player reaches the end of the dungeon and there is no Otyugh he wins the game.
* Player can quit the game by entering "q"

## How to run

Download the jar file of the project provided in the /res folder.

The jar file can be executed from the command prompt with the following command

```bash
  java -jar c:pathtojarfile.jar 6 6 3 true 20 2 PlayerName
```

The command line arguments in order are :
rows, columns, interconnectivity, wrapping or not, percentage of treasure/arrows, the number of
Otyughs, the player name

## How to use the program

The program is directly run using interactive controller that takes in user inputs and provides the
necessary prompts to the user to play the game. User can quit the game at any time by entering "q".

## Description of examples

```
C:\Users\athwa\.jdks\corretto-11.0.12\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.2.1\lib\idea_rt.jar=54517:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.2.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Users\athwa\Downloads\College stuff\PDP\Project3-Dungeon\out\production\Project3-Dungeon" Driver 6 6 3 true 20
Run 1:
You are IndianaJones
You have some treasure 0 diamonds , 0 rubies , 0 sapphires 
You have some weapons 3 arrows 
You are in a cave
Doors lead to the NORTH, EAST, WEST
You find 1 arrows in the cave
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
N


You are in a tunnel
Doors lead to the NORTH, SOUTH
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
N


You are in a cave
You smell something bad(somewhat pungent) nearby.
Doors lead to the EAST, NORTH, WEST, SOUTH
You find 0 diamonds , 0 rubies , 1 sapphires 
You find 1 arrows in the cave
Move, Pickup , or Shoot (M-P-S)?
P
What would you like to pickup treasure or weapons (T/W)?
W
Picked up all the weapons.

You are in a cave
You smell something bad(somewhat pungent) nearby.
Doors lead to the EAST, NORTH, WEST, SOUTH
You find 0 diamonds , 0 rubies , 1 sapphires 
Move, Pickup , or Shoot (M-P-S)?
P
What would you like to pickup treasure or weapons (T/W)?
T
Picked up all the treasure.

You are in a cave
You smell something bad(somewhat pungent) nearby.
Doors lead to the EAST, NORTH, WEST, SOUTH
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
W


You are in a cave
You smell something bad(somewhat pungent) nearby.
Doors lead to the EAST, WEST, SOUTH
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
S


You are in a tunnel
You smell something terrible(strongly pungent) nearby.
Doors lead to the NORTH, WEST
You find 1 arrows in the tunnel
Move, Pickup , or Shoot (M-P-S)?
P
What would you like to pickup treasure or weapons (T/W)?
W
Picked up all the weapons.

You are in a tunnel
You smell something terrible(strongly pungent) nearby.
Doors lead to the NORTH, WEST
Move, Pickup , or Shoot (M-P-S)?
S
Distance to shoot(no of caves)?
1
Direction to shoot in?(N/S/E/W)
W
You shoot an arrow west.
You hear a great howl in the distance.

You are in a tunnel
You smell something terrible(strongly pungent) nearby.
Doors lead to the NORTH, WEST
Move, Pickup , or Shoot (M-P-S)?
S
Distance to shoot(no of caves)?
1
Direction to shoot in?(N/S/E/W)
W
You shoot an arrow west.
You hear a great howl in the distance.

You are in a tunnel
Doors lead to the NORTH, WEST
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
W

Congratulations you have reached the end of the dungeon.
You win!!
Process finished with exit code 0

You are IndianaJones
You have some treasure 0 diamonds , 0 rubies , 0 sapphires 
You have some weapons 3 arrows 
You are in a cave
Doors lead to the NORTH, EAST, WEST
You find 1 arrows in the cave
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
N


You are in a tunnel
Doors lead to the NORTH, SOUTH
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
N


You are in a cave
You smell something bad(somewhat pungent) nearby.
Doors lead to the EAST, NORTH, WEST, SOUTH
You find 0 diamonds , 0 rubies , 1 sapphires 
You find 1 arrows in the cave
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
W


You are in a cave
You smell something bad(somewhat pungent) nearby.
Doors lead to the EAST, WEST, SOUTH
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
S


You are in a tunnel
You smell something terrible(strongly pungent) nearby.
Doors lead to the NORTH, WEST
You find 1 arrows in the tunnel
Move, Pickup , or Shoot (M-P-S)?
M
Where do you want to move?(N/S/E/W)
W


Chomp, chomp, chomp, you are eaten by an Otyugh!
Better luck next time
Process finished with exit code 0


```

Run 1:

1. Start by creating a dungeon of size 6*6 with interconnectivity 3 and wrapping with 20% treasure.
2. The player is added to the dungeon at the start node.
3. The player starts traversing the dungeon from start to end.
4. Player collects any treasure and weapons he encouters on the way.
5. Smells an Otyugh on his way.
6. Player shoots an arrow West with ditance 1
7. It hits an Otyugh.
6. Player shoots another arrow West with ditance 1
7. It hits an Otyugh.
8. Player moves to the West.
9. Player has reached the end cave. Player wins.

Run 2:

1. Start by creating a dungeon of size 6*6 with interconnectivity 3 and non-wrapping with 20%
   treasure.
2. The player is added to the dungeon at the start node.
3. The player starts traversing the dungeon from start node to end node.
4. Player smells an Otyugh nearby.
5. Player ignores the smell like an idiot and still moves ahead.
6. Player is eaten by an Otyugh.

## Design Changes

* Added Monster state as enum to check if monster is inured or dead.
* Added a Smell enum to represent the weak,strong and no smell.
* Added a playerIsAlive method to check if player is still alive.

## Assumptions

* Dungeon size can only be greater than or equal to 5*5.
* Player picks up all the treasure in a room when asked to collect treasure.
* Player picks up all the weapons in a room when asked to collect weapons.
* Injured Otyugh does not kill the player if he survives the inital move and stays.

## Limitations

* Player cannot pickup partial amount of treasure from a room.
* Player does not get information if he kills an Otyugh.

## Citations

Kruskals
implementation: https://www.geeksforgeeks.org/kruskals-algorithm-simple-implementation-for-adjacency-matrix/
BFS Search: https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/

## Author

Atharva Wandile (wandile.a@northeastern.edu)

