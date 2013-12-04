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
import fourmiz.engine.Engine;
import fourmiz.engine.EntityListener;
import fourmiz.touch.marker.LifeMarker;

public class Healer extends Abillity implements EntityListener{
	private static final int RADIUS=Engine.SIZE_CASE*4;
	private Shape baseShape=new Circle(0, 0, RADIUS);
	private Shape currentShape=null;
	private Shape walkArea=null;
	private boolean positionUpdated=true;
	private MyHeal heal=new MyHeal();
	private int currentFoodQuantity;
	private float speed = 2f;
	
	public Healer(Entity owner) {
		super(owner);
	}
	
	public void setWalkArea(Shape newArea)
	{
		this.walkArea = newArea;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	private boolean isInWalkArea()
	{
		if(this.walkArea.contains(this.currentShape)) return true;
		return false;
	}

	@Override
	public void update(int delta) {

        //mise à jour position
		Vector2f position=this.getOwner().getPosition();
        float hip=this.speed*delta;
        position.x+=hip*Math.cos(Math.toRadians(this.getOwner().getDirection()));
        position.y+=hip*Math.sin(Math.toRadians(this.getOwner().getDirection()));        
        this.getOwner().setPosition(position);
        
        //si il est sorti de la zone on redirige dans une direction aléatoire
        if(!this.isInWalkArea())
        {
        	this.getOwner().setDirection((float) (Math.random()*360));
        }
	}
	
	@Override
	public void setOwner(Entity owner){
		if(getOwner()!=null) getOwner().removeEntityListener(this);
		
		owner.addEntityListener(this);
		super.setOwner(owner);		
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
			
			LifeMarker lifeMarker=(LifeMarker) marker;
			//si la vie de l'entitï¿½ est infï¿½rieure ï¿½ 66%
			if(lifeMarker.getLife() < lifeMarker.getMaxLife()/1.5)
			{
				//si le stock de la fourmi ouvriï¿½re est supï¿½rieur au manque de vie de l'entitï¿½
				if((lifeMarker.getLife() + currentFoodQuantity) >= lifeMarker.getMaxLife())
				{
					lifeMarker.setLife(lifeMarker.getMaxLife());
					currentFoodQuantity -= (lifeMarker.getMaxLife()-lifeMarker.getLife());
				}
				else
				{
					lifeMarker.setLife(lifeMarker.getLife() + currentFoodQuantity);
					currentFoodQuantity = 0;
				}		
			}
			
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
