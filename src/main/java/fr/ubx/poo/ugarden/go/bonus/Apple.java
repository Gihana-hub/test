package fr.ubx.poo.ugarden.go.bonus;

import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.Takeable;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.personage.Gardener;

public class Apple extends Bonus implements Takeable {
    public Apple(Position position, Decor decor) {
            super(position, decor);
    }
    @Override
    public void takenBy(Gardener gardener) {

    }
}
