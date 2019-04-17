package Entities;

import Components.Position;

public abstract class MovableEntity extends GameObject{
    /**
     * Change the {@link Position} of the {@link MovableEntity} to one above.
     * @return Returns the new {@link Position}.
     */
    public Position moveUp() {
        position.setY(position.getY() - 1);
        return getPosition();
    }

    /**
     * Change the {@link Position} of the {@link MovableEntity} to one below.
     * @return Returns the new {@link Position}.
     */
    public Position moveDown() {
        position.setY(position.getY() + 1);
        return getPosition();
    }

    /**
     * Change the {@link Position} of the {@link MovableEntity} to the left.
     * @return Returns the new {@link Position}.
     */
    public Position moveLeft() {
        position.setX(position.getX() - 1);
        return getPosition();
    }

    /**
     * Change the {@link Position} of the {@link MovableEntity} to the right.
     * @return Returns the new {@link Position}.
     */
    public Position moveRight() {
        position.setX(position.getX() + 1);
        return getPosition();
    }

    /**
     * Make a new {@link Position} above the {@link MovableEntity}.
     * @return Returns a new {@link Position}.
     */
    public Position checkUp() {
        int x = position.getX();
        int y = position.getY() - 1;
        return new Position(x, y);
    }

    /**
     * Make a new {@link Position} below the {@link MovableEntity}.
     * @return Returns a new {@link Position}.
     */
    public Position checkDown() {
        int x = position.getX();
        int y = position.getY() + 1;
        return new Position(x, y);
    }

    /**
     * Make a new {@link Position} to the left of the {@link MovableEntity}.
     * @return Returns a new {@link Position}.
     */
    public Position checkLeft() {
        int x = position.getX() - 1;
        int y = position.getY();
        return new Position(x, y);
    }

    /**
     * Make a new {@link Position} to the right of the {@link MovableEntity}.
     * @return Returns a new {@link Position}.
     */
    public Position checkRight() {
        int x = position.getX() + 1;
        int y = position.getY();
        return new Position(x, y);
    }

}