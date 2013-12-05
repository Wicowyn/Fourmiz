package fourmiz.touch.marker;

import fourmiz.collision.TouchMarker;
import fourmiz.engine.CollisionType;

public abstract class PreyMarker extends TouchMarker {

	@Override
	public final int getType() {
		return CollisionType.TO_DESTROY;
	}
	
	public abstract int getLife();
	public abstract boolean hit(int attackLife);
	public abstract int getFood();

}
