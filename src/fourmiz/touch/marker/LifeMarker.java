package fourmiz.touch.marker;

import fourmiz.collision.TouchMarker;
import fourmiz.engine.CollisionType;

public abstract class LifeMarker extends TouchMarker {
	
	public final int getType(){
		return CollisionType.LIFE;
	}
	
	public abstract int getLife();
	public abstract int getMaxLife();
	public abstract void setLife(int life);
	public abstract void addLife(int life);
	public abstract void withdrawLife(int life);
}
