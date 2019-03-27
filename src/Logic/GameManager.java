package Logic;

import DataStructures.IDataStructure;
import Entities.GameObject;
import Entities.Player;
import Logic.Crawler.Crawler;
import Logic.Crawler.MoveClockWise;
import Maze.IMaze;

import java.util.*;

/**
 * The purpose of this class is, to combine most of the components needed for the logic to work.
 * All the different types of {@link GameObject}s are access here.
 * The {@link Crawler} is combined with the {@link IMaze} and {@link IDataStructure}.
 * It is also possible to disable the {@link Crawler} to control with {@link UserControl.UserControl} instead.
 *
 * @author MaZeeT
 */
public class GameManager {

    private List<GameObject> walls;
    private List<GameObject> wayPoints = new ArrayList<>();
    private List<GameObject> snake = new ArrayList<>();

    private char direction;
    private MoveClockWise movement;
    private Crawler crawler;

    private boolean crawl = true;

    /**
     * Constructor of this class. It takes a {@link IMaze} and a {@link IDataStructure} as input.
     *
     * @param maze          The {@link IMaze} is used to access all the different {@link GameObject}s in the {@link IMaze}.
     * @param dataStructure The {@link IDataStructure} is used to store each possible movement, and returns the next move.
     */
    public GameManager(IMaze maze, IDataStructure dataStructure) {
        walls = maze.getWalls();

        Player player = maze.getPlayer();
        snake.add(player);
        wayPoints.add(maze.getWayPoint());

        movement = new MoveClockWise(maze);
        crawler = new Crawler(maze, dataStructure);
    }

    /**
     * The update method, which is called each update tick.
     */
    public void update() {
        movement.move(direction);
        direction = movement.getDirection();
        if (crawl) crawler.update();
    }

    /**
     * Set the direction.
     *
     * @param direction The direction as a char.
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * Get the direction as char.
     *
     * @return Returns the direction as a char.
     */
    char getDirection() {
        return this.direction;
    }

    /**
     * Combines the different {@link GameObject}s in a list and returns this list.
     *
     * @return Returns a list of all the {@link GameObject}s combined.
     */
    private List<GameObject> combineGameObjects() {
        List<GameObject> gameObjects = new ArrayList<>();
        gameObjects.addAll(wayPoints);
        gameObjects.addAll(walls);
        gameObjects.addAll(snake);

        return gameObjects;
    }

    /**
     * Get a list of {@link GameObject}s.
     *
     * @return Returns a list of {@link GameObject}s.
     */
    public List<GameObject> getGameObjects() {
        return combineGameObjects();
    }

}