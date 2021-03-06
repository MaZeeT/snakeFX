package Maze;

import Entities.*;

import java.util.ArrayList;

/**
 * The purpose of this class is to provide a default maze to instantiate the direct super test with.
 * By instantiating this class your are able to run tests against the superclass.
 *
 * @author MaZeeT
 */
class MazeObjTestable extends MazeObjBased {

    /**
     * This constructor takes a width and a height to define the size of the maze.
     * It will than add a {@link Player}, a {@link WayPoint} and a border of {@link Wall}s to the maze.
     *
     * @param width  The width of the maze.
     * @param height The height of the maze.
     */
    MazeObjTestable(int width, int height) {
        this.maze = new GameObject[width][height];
        this.walls = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        addPlayer(2, 2);
        addWayPoint(2, 3);
        addGhost(1, 1);
        borders(width, height);
    }

}
