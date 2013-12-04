package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;

public class Fourmiz extends Abillity{
	
	//private Type type;

	public Fourmiz(Entity owner) {
		super(owner);
	}

	@Override
	public void update(int delta) {
		
	}
	
	public enum Type{
		WORKER, SOLDIER, MALE, FEMAL
	}

	@Override
	public Collection<TouchMarker> getTouchMarker() {
		return new ArrayList<TouchMarker>(0);
	}
	@Override
	public Collection<TouchHandle> getTouchHandle() {
		return new ArrayList<TouchHandle>(0);
	}
	
}
