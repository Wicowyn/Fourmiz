package fourmiz.abillity;

import java.util.Collection;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;

public class Anthill extends Abillity{

	public Anthill(Entity owner) {
		super(owner);
		this.getOwner().addAbillity(new Queen(getOwner()));
	}

	@Override
	public void update(int delta) {
		
	}

	@Override
	public Collection<? extends TouchMarker> getTouchMarker() {
		return null;
	}

	@Override
	public Collection<? extends TouchHandle> getTouchHandle() {
		return null;
	}
	
}
