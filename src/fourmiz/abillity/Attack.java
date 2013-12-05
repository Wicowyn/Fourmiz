package fourmiz.abillity;

import java.util.Collection;

import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;
import fourmiz.engine.EntityListener;

public class Attack extends Abillity implements EntityListener{
	private Shape attackArea=null;

	public Attack(Entity owner) {
		super(owner);
	}

	@Override
	public void abillityAdded(Abillity abillity) {
		
	}

	@Override
	public void abillityRemoved(Abillity abillity) {
		
	}

	@Override
	public void positionUpdated() {
		
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

	public Shape getAttackArea() {
		return attackArea;
	}

	public void setAttackArea(Shape attackArea) {
		this.attackArea = attackArea;
	}

}
