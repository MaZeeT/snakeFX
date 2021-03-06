package CoreLogic;

import DataStructures.*;
import Entities.*;
import GUI.IGUI;
import GUI.SimpleGUI.SimpleGUI;
import Maze.*;
import Movement.IMover;
import Movement.DataStructureCrawlers.Crawler;
import Movement.UserControlled.MoveClockWise;
import UserControl.*;
import Movement.ShortestTree.ShortestTree;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * The purpose of this class is to combine the different elements the program consist of.
 * The combined elements are the {@link GameManager}, the {@link UserInput} interface and the {@link IGUI} interface.
 * Lastly this class provide the Stage with the scene from the program.
 *
 * @author MaZeeT
 */
public class Launcher {
    private int width;
    private int height;
    private int runSpeed;

    private Scene scene;
    private IMaze maze;
    private IMover playerMovement;


    /**
     * The method is used to configure which maze and dataStructure the program shall run with,
     * and the width and height of the maze. The runSpeed is also given here,
     * which is the time between each move, so a lower runSpeed will make the game faster,
     * and a higher runSpeed for a slower game.
     */
    private void configuration() {
        width = 48; // width of maze
        height = 33; // height of maze
        runSpeed = 500; // milliseconds between each update

        // Options for setting maze.
        maze = setMaze(5);
        // 0 for TwoHalls.
        // 1 for SingleRoad.
        // 2 for ManyRoads.
        // 3 for ForkRoad.
        // 4 for ForkRoad2 (good for greedy).
        // 5 for PacMan (for the exam)
        // 6 for AITestMaze (extra maze to test pathfinder algorithms)

        // Player movement options
        playerMovement = setPlayerMovement(0);
        // 0 for User controlled.
        // 1 for DepthFirst.
        // 2 for BreadthFirst.
        // 3 for Greedy.
        // 4 for ShortestTree (target: wayPoint).
    }

    /**
     * Instantiate the different elements of the program and starts the thread that updates the program based on runSpeed.
     */
    Launcher() {
        // Sets all the variables needed for below.
        configuration();

        // Interfaced modules
        IGUI gui = new SimpleGUI(width, height);
        UserInput userInput = new PlayerControl();

        // Game logic
        GameManager gameManager = new GameManager(maze);

        // Setup movement to MovableGameObjects
        InputManager inputManager = new InputManager(playerMovement, userInput);
        maze.getPlayer().setMover(playerMovement);
        setupMoversToGhosts();

        // Setup scene and stage
        this.scene = setupScene(gui.getPane(), inputManager);

        // Update thread
        UpdateProcess updateProcess = new UpdateProcess(gameManager, gui, runSpeed);
        updateProcess.start();
    }

    /**
     * Setup a scene with the pane from the {@link IGUI} and bridges together the {@link InputManager} with the keyEvents from the scene.
     *
     * @param pane The given pane that shall be shown on the scene.
     * @param inputManager The {@link InputManager} that handles the input from the KeyEvent.
     * @return Returns a new Scene based on the given pane and the given {@link InputManager}.
     */
    private Scene setupScene(Pane pane, InputManager inputManager) {
        scene = new Scene(pane, 800, 600);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                inputManager.update(event.getCode());
            }
        });
        return scene;
    }

    /**
     * Returns the scene.
     *
     * @return Returns the scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * This method make it possible to select the wanted maze by using an int as parameter.
     *
     * @param index The index of a given maze.
     *              0 for TwoHalls.
     *              1 for SingleRoad.
     *              2 for ManyRoads.
     *              3 for ForkRoad.
     *              4 for ForkRoad2.
     *              5 for PacMan.
     *              6 for AITestMaze
     * @return Returns the selected maze.
     */
    private IMaze setMaze(int index) {
        if (index == 0) return new TwoHalls(10, 10);
        if (index == 1) return new SingleRoad();
        if (index == 2) return new ManyRoads();
        if (index == 3) return new ForkRoad();
        if (index == 4) return new ForkRoad2();
        if (index == 5) return new PacMan();
        if (index == 6) return new AITestMaze();

        //Default
        return new ManyRoads();
    }

    /**
     * This method make it possible to select the wanted dataStructure by using an int as parameter.
     *
     * @param index The index of a given dataStructure.
     *              0 for User control.
     *              1 for DepthFirst.
     *              2 for BreadthFirst.
     *              3 for Greedy.
     *              4 for ShortestTree
     * @return Returns the selected dataStructure.
     */
    private IMover setPlayerMovement(int index) {
        if (index == 0) {
            return new MoveClockWise(maze, maze.getPlayer());
        }
        if (index == 4) {
            return new ShortestTree(maze, maze.getPlayer(), maze.getWayPoint(), false);
        }

        IDataStructure dataStructure = new DepthFirst(); //Default data
        if (index == 1) dataStructure = new DepthFirst();
        if (index == 2) dataStructure = new BreadthFirst();
        if (index == 3) dataStructure = new Greedy(this.maze.getWayPoint().getPosition());

        return new Crawler(maze, maze.getPlayer(), dataStructure);
    }

    /**
     * This method connects the {@link Entities.Ghost}s in the maze to {@link IMover}s
     * where a {@link IDataStructure} need to be specified to dictate behaviour.
     */
    private void setupMoversToGhosts() {
        List<GameObject> ghosts = maze.getGhosts();
        MovableEntity player = maze.getPlayer();
        if (ghosts != null) {
            int greedy = 0;
            int breadFirst = 1;
            int depthFirst = 2;
            int shortestTree = 3;

            ghosts.get(greedy).setColor(Color.GREEN);
            ghosts.get(breadFirst).setColor(Color.BLUE);
            ghosts.get(depthFirst).setColor(Color.RED);
            ghosts.get(shortestTree).setColor(Color.GOLD);

            ((MovableEntity) ghosts.get(greedy)).setMover(
                    new Crawler(maze, (MovableEntity) ghosts.get(greedy), new Greedy(player.getPosition())));
            ((MovableEntity) ghosts.get(breadFirst)).setMover(
                    new Crawler(maze, (MovableEntity) ghosts.get(breadFirst), new BreadthFirst()));
            ((MovableEntity) ghosts.get(depthFirst)).setMover(
                    new Crawler(maze, (MovableEntity) ghosts.get(depthFirst), new DepthFirst()));
            ((MovableEntity) ghosts.get(shortestTree)).setMover(
                    new ShortestTree(maze, (MovableEntity) ghosts.get(shortestTree), player, true));
        }
    }

}
