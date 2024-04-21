package fr.ubx.poo.ugarden.go.decor;

import fr.ubx.poo.ugarden.game.Position;

public class Door extends Decor{
    private boolean isOpen = false;
    public Door(Position position) {
        super(position);
    }
}
