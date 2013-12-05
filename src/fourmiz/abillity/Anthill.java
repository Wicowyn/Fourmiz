package fourmiz.abillity;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;

public class Anthill extends Abillity{

	public Anthill(Entity owner) {
		super(owner);
		this.getOwner().addAbillity(new Queen(getOwner()));
	}

	@Override
	public void update(int delta) {
		
	}
	
}
