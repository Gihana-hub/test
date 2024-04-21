package fr.ubx.poo.ugarden.go.bonus;

import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.Takeable;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.personage.Gardener;

public class Insecticide extends Bonus implements Takeable {
    public Insecticide(Position position, Decor decor) {
        super(position, decor);
    }
    @Override
    public void takenBy(Gardener gardener) {

    }
}
