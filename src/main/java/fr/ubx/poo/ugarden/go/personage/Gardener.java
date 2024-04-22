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
import fr.ubx.poo.ugarden.go.decor.Hedgehog;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gardener extends GameObject implements Movable, TakeVisitor, WalkVisitor {

    private int energy;
    private Direction direction;
    private boolean moveRequested = false;
    private int insecticideCount;
    private int diseaseLevel;
    private List<Key> keys;
    private Label keysLabel;
    private long lastMoveTime;
    private final long IDLE_TIME_FOR_ENERGY_RECOVERY = 1000;
    private final int ENERGY_RECOVERY_AMOUNT = 1;


    public Gardener(Game game, Position position) {

        super(game, position);
        this.direction = Direction.DOWN;
        this.energy = game.configuration().gardenerEnergy();  // Initialiser avec la configuration du jeu
        this.insecticideCount = 0;
        this.diseaseLevel = 0;
        this.keys = new ArrayList<>();
        this.keysLabel = keysLabel;
        this.lastMoveTime = System.currentTimeMillis();  // Initialiser le temps du dernier mouvement

    }



    @Override
    public void take(Key key) {
        keys.add(key); // Add the key to the gardener's list of keys.
        System.out.println("Key taken! You now have " + keys.size() + " keys.");
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
                lastMoveTime = System.currentTimeMillis();  // Mettre à jour le temps du dernier mouvement
            }
        } else {
            long timeIdle = System.currentTimeMillis() - lastMoveTime;
            if (timeIdle >= IDLE_TIME_FOR_ENERGY_RECOVERY) {
                energy += ENERGY_RECOVERY_AMOUNT;
                energy = Math.min(energy, getGardenerMaxEnergy());
                lastMoveTime = System.currentTimeMillis();  // Réinitialiser le temps après la récupération d'énergie


            }
        }
        moveRequested = false;
    }

    private int getGardenerMaxEnergy() {
        // Supposons que 100 est le maximum d'énergie que le jardinier peut avoir
        return 100;
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

    // Add the new insecticide count methods
    public int getInsecticideCount() {
        return this.insecticideCount;
    }
    public int getDiseaseLevel() {
        return this.diseaseLevel;
    }

    public List<Key> getKeys() {
        return this.keys;
    }

    public void increaseEnergy(int i) {
    }

    public void addKey() {

    }
}
