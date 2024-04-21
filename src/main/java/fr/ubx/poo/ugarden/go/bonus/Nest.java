package fr.ubx.poo.ugarden.go.bonus;

import fr.ubx.poo.ugarden.engine.Timer;
import fr.ubx.poo.ugarden.game.Direction;
import fr.ubx.poo.ugarden.game.Position;
import fr.ubx.poo.ugarden.go.decor.Decor;
import fr.ubx.poo.ugarden.go.decor.ground.Grass;
import fr.ubx.poo.ugarden.go.personage.Hornet;

public class Nest extends Bonus {
    private final fr.ubx.poo.ugarden.engine.Timer timer = new Timer(10);
    public Nest(Position position, Decor decor) {
        super(position, decor);
        timer.start();
    }
    @Override
    public void update(long now) {
        if(!timer.isRunning()){
            Position pos = Direction.randomPos(getPosition().level(), game);
            Hornet hornet = new Hornet(game, this.getPosition());
            while(!(game.world().getGrid().get(pos) instanceof Grass && game.world().getGrid().get(pos).getBonus() == null)){
                pos = Direction.randomPos(getPosition().level(), game);
            }
            Insecticide insecticide = new Insecticide(pos, game.world().getGrid().get(pos));
            timer.start();
        }
    }
}
