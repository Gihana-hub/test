/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.go.personage;

import fr.ubx.poo.ugarden.game.Direction;
import fr.ubx.poo.ugarden.game.Game;
import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.GameObject;
import fr.ubx.poo.ugarden.go.Movable;
import fr.ubx.poo.ugarden.go.TakeVisitor;
import fr.ubx.poo.ugarden.go.WalkVisitor;
import fr.ubx.poo.ugarden.go.bonus.Key;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.decor.Door;
import fr.ubx.poo.ugarden.go.decor.Hedgehog;
import fr.ubx.poo.ugarden.go.decor.Tree;
import fr.ubx.poo.ugarden.go.decor.ground.Carrots;
import fr.ubx.poo.ugarden.go.decor.ground.Flowers;
import fr.ubx.poo.ugarden.go.decor.ground.Grass;
import fr.ubx.poo.ugarden.go.decor.ground.Land;

public class Gardener extends GameObject implements Movable, TakeVisitor, WalkVisitor {

    private int energy;
    private Direction direction;
    private boolean moveRequested = false;

    public Gardener(Game game, Position position) {

        super(game, position);
        this.direction = Direction.DOWN;
        this.energy = game.configuration().gardenerEnergy();
    }

    @Override
    public void take(Key key) {
// TODO
        System.out.println("I am taking the key, I should do something ...");

    }


    public int getEnergy() {
        return this.energy;
    }


    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }

    @Override
    public final boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        int height = game.world().getGrid().height() - 1;
        int width = game.world().getGrid().width() - 1;
        if(nextPos.x() < 0 || nextPos.x() > width || nextPos.y() < 0 || nextPos.y() > height)
            return false;
        Decor next = game.world().getGrid().get(nextPos);
        return next.walkableBy(this);
    }

    @Override
    public void doMove(Direction direction) {
        // Restart the timer
        Position nextPos = direction.nextPosition(getPosition());
        Decor next = game.world().getGrid().get(nextPos);
        setPosition(nextPos);
        if (next != null)
            next.takenBy(this);

        assert next != null;
        energy -= next.energyConsumptionWalk();
    }

    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    public void hurt(int damage) {
        energy-= damage;
    }

    public void hurt() {
        hurt(20);
    }

    public Direction getDirection() {
        return direction;
    }
    public boolean isWon(){return game.world().getGrid().get(getPosition()) instanceof Hedgehog;}

}
