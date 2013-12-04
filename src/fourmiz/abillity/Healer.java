package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;
import fourmiz.engine.CollisionType;
import fourmiz.engine.EntityListener;
import fourmiz.touch.marker.LifeMarker;

public class Healer extends Abillity implements EntityListener{
	private static final int RADIUS=100;
	private Shape baseShape=new Circle(0, 0, RADIUS);
	private Shape currentShape=null;
	private boolean positionUpdated=true;
	private MyHeal heal=new MyHeal();
	
	public Healer(Entity owner) {
		super(owner);
	}

	@Override
	public void update(int delta) {

	}

	@Override
	public Collection<? extends TouchMarker> getTouchMarker() {
		return new ArrayList<TouchMarker>(0);
	}

	@Override
	public Collection<? extends TouchHandle> getTouchHandle() {
		ArrayList<TouchHandle> list=new ArrayList<TouchHandle>(1);
		
		list.add(heal);
		
		return list;
	}
	
	private class MyHeal extends TouchHandle{
		private int priority;

		@Override
		public int getType() {
			return CollisionType.LIFE;
		}

		@Override
		public Entity getOwner() {
			return Healer.this.getOwner();
		}

		@Override
		public Shape getArea() {
			if(positionUpdated){
				Vector2f pos=Healer.this.getOwner().getPosition();
				
				currentShape=baseShape.transform(Transform.createTranslateTransform(pos.getX(), pos.getY()));
				positionUpdated=false;
			}
			
			return currentShape;
		}

		@Override
		public void setPriority(int priority) {
			this.priority=priority;
		}

		@Override
		public int getPriority() {
			return priority;
		}

		@Override
		public void perform(TouchMarker marker) {
			@SuppressWarnings("unused")
			LifeMarker lifeMarker=(LifeMarker) marker;
			
		}
		
	}

	@Override
	public void abillityAdded(Abillity abillity) {
		//nothing to do
	}

	@Override
	public void abillityRemoved(Abillity abillity) {
		//nothing to do
	}

	@Override
	public void positionUpdated() {
		positionUpdated=true;
	}

}