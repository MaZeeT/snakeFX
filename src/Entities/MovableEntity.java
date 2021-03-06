package Entities;

import Components.Position;
import Movement.IMover;

import java.util.Arrays;

/**
 * The purpose of this class is to enable {@link GameObject}s to enable move around.
 * This is done by having a set of methods that check if the next intended move is possible,
 * and a set of methods that actually perform the movement.
 * To be able to move, a {@link IMover} need to handle where to move next.
 * So the class also contains a few methods to interact with the {@link IMover}.
 *
 * @author MaZeeT
 */
public abstract class MovableEntity extends GameObject {

    IMover mover;

    /**
     * Move the {@link MovableEntity} around by calling update().
     *
     * @return Return true if an IMover is present and the update() was successful, else return false.
     */
    public abstract boolean update();

    /**
     * Change the {@link Position} of the {@link MovableEntity} to one above.
     *
     * @return Returns the new {@link Position}.
     */
    public Position moveUp() {
        position.setY(position.getY() - 1);
        return getPosition();
    }

    /**
     * Change the {@link Position} of the {@link MovableEntity} to one below.
     *
     * @return Returns the new {@link Position}.
     */
    public Position moveDown() {
        position.setY(position.getY() + 1);
        return getPosition();
    }

    /**
     * Change the {@link Position} of the {@link MovableEntity} to the left.
     *
     * @return Returns the new {@link Position}.
     */
    public Position moveLeft() {
        position.setX(position.getX() - 1);
        return getPosition();
    }

    /**
     * Change the {@link Position} of the {@link MovableEntity} to the right.
     *
     * @return Returns the new {@link Position}.
     */
    public Position moveRight() {
        position.setX(position.getX() + 1);
        return getPosition();
    }

    /**
     * Make a new {@link Position} above the {@link MovableEntity}.
     *
     * @return Returns a new {@link Position}.
     */
    public Position checkUp() {
        int x = position.getX();
        int y = position.getY() - 1;
        return new Position(x, y);
    }

    /**
     * Make a new {@link Position} below the {@link MovableEntity}.
     *
     * @return Returns a new {@link Position}.
     */
    public Position checkDown() {
        int x = position.getX();
        int y = position.getY() + 1;
        return new Position(x, y);
    }

    /**
     * Make a new {@link Position} to the left of the {@link MovableEntity}.
     *
     * @return Returns a new {@link Position}.
     */
    public Position checkLeft() {
        int x = position.getX() - 1;
        int y = position.getY();
        return new Position(x, y);
    }

    /**
     * Make a new {@link Position} to the right of the {@link MovableEntity}.
     *
     * @return Returns a new {@link Position}.
     */
    public Position checkRight() {
        int x = position.getX() + 1;
        int y = position.getY();
        return new Position(x, y);
    }

    /**
     * Set the {@link IMover} which update the {@link MovableEntity} around.
     *
     * @param mover The mover the {@link MovableEntity} is moved by.
     */
    public void setMover(IMover mover) {
        this.mover = mover;
    }

    /**
     * Get the {@link IMover} which update the {@link MovableEntity} around.
     *
     * @return Returns the mover, the {@link MovableEntity} is moved by.
     */
    public IMover getMover() {
        return this.mover;
    }

    /**
     * Override the equals function, where it will check if {@link IMover} is equal included the super.equal with the other object.
     *
     * @param obj The object the {@link MovableEntity} is trying to compare to.
     * @return Returns a boolean to show if the obj is equal to the {@link MovableEntity} or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MovableEntity) {
            MovableEntity other = (MovableEntity) obj;
            if (this.mover == null && other.mover == null) {
                return position.equals(other.position) && color == other.color;
            }
            if (this.mover == null || other.mover == null) {
                return false;
            }
            return (super.equals(other) && this.mover.getClass().equals(other.mover.getClass()));
        } else {
            return false;
        }
    }

    /**
     * Calculate the hashcode of the {@link MovableEntity} based on the position, color and mover variables.
     *
     * @return Return a hashcode based on the position, color and mover variables.
     */
    @Override
    public int hashCode() {
        final Object[] objects = {position, color, mover};
        return Arrays.hashCode(objects);
    }

}
