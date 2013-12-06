package fourmiz.abillity;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.CollideType;
import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;
import fourmiz.engine.CollisionType;
import fourmiz.engine.Engine;
import fourmiz.engine.EngineListener;
import fourmiz.engine.EntityListener;
import fourmiz.touch.marker.PreyMarker;

public class Attack extends Abillity implements EntityListener, EngineListener{
	private static Logger log=LogManager.getLogger(Attack.class);
	private List<SearchStatic> attackAreaStatic=new ArrayList<SearchStatic>();
	private Search searchAttack=new Search();
	private Fight fight=new Fight();
	private static final int RADIUS=Engine.SIZE_CASE*4;
	private Shape baseAttackArea=new Circle(Engine.SIZE_CASE/2, Engine.SIZE_CASE/2, RADIUS);
	private Shape currentAttackArea;
	private boolean positionUpdated=true;
	
	private State state=null;
	private Entity focused;
	float speed;
	
	private static final int INTERVAL_TIME=1000;
	private int attack;
	private int time;
	private boolean canHit;

	public Attack(Entity owner) {
		super(owner);
		
		setState(State.SEARCH);
	}

	@Override
	public void update(int delta) {
		
		switch(state){
		case FOLLOW:
			time+=delta;
			
			if(time>=INTERVAL_TIME){
				time-=INTERVAL_TIME;
				canHit=true;
			}
			Vector2f position=focused.getPosition();
			position.sub(getOwner().getPosition());
			getOwner().setDirection((float) position.getTheta());
	        
			position=getOwner().getPosition();
			float hip=(speed*Engine.SIZE_CASE)/delta;
	        
	        position.x+=hip*Math.cos(Math.toRadians(getOwner().getDirection()));
	        position.y+=hip*Math.sin(Math.toRadians(getOwner().getDirection()));        
	        
	        getOwner().setPosition(position);
			break;
		case SEARCH:
			break;
		default:
			break;
		
		}
	}
	
	@Override
	public void setOwner(Entity owner){
		if(getOwner()!=null){
			getOwner().getEngine().removeListener(this);
			getOwner().removeEntityListener(this);
		}
		
		super.setOwner(owner);
		
		getOwner().getEngine().addListener(this);
		getOwner().addEntityListener(this);
	}
	
	public void addStaticArea(Shape area){
		SearchStatic search=new SearchStatic();
		search.setArea(area);
		
		attackAreaStatic.add(search);
		addTouchHandle(search);
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getAttack() {
		return attack;
	}

	/**
	 * Set the attack
	 * @param attack in point  by second
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	public boolean removeStaticArea(Shape area){
		SearchStatic searchFinded=null;
		
		for(SearchStatic search : attackAreaStatic){
			if(search.getArea()==area){
				searchFinded=search;
				break;
			}
		}
		
		boolean removed=attackAreaStatic.remove(searchFinded);
		
		if(removed)
			if(!removeTouchHandle(searchFinded)) throw new IllegalStateException();
		
		return removed;
	}

	@Override
	public void abillityAdded(Abillity abillity) {
		
	}

	@Override
	public void abillityRemoved(Abillity abillity) {
		
	}

	@Override
	public void positionUpdated() {
		positionUpdated=true;
	}
	

	@Override
	public void entityAdded(Entity entity) {
		//nothing to do
	}

	@Override
	public void entityRemoved(Entity entity) {
		if(entity==focused){
			setState(State.SEARCH);
			focused=null;
		}
		
	}

	public Shape getAttackArea() {
		if(positionUpdated){
			Vector2f pos=getOwner().getPosition();
			
			currentAttackArea=baseAttackArea.transform(Transform.createTranslateTransform(pos.getX(), pos.getY()));
			positionUpdated=false;
		}
		
		return currentAttackArea;
	}
	
	private void preyFinded(PreyMarker marker){
		focused=marker.getOwner();
		setState(State.FOLLOW);
	}
	
	private void setState(State state){
		log.info("Jump to mode "+state);
		
		switch(state){
		case FOLLOW:
			for(SearchStatic search : attackAreaStatic) removeTouchHandle(search);
			removeTouchHandle(searchAttack);
			addTouchHandle(fight);
			break;
		case SEARCH:
			for(SearchStatic search : attackAreaStatic) addTouchHandle(search);
			addTouchHandle(searchAttack);
			removeTouchHandle(fight);
			break;
		default:
			break;
		}
		
		this.state=state;
	}
	
	private enum State{
		FOLLOW, SEARCH
	}
	
	private class SearchStatic extends TouchHandle{
		private int priority;
		private Shape area;
		
		public void setArea(Shape area) {
			this.area = area;
		}

		@Override
		public int getType() {
			return CollisionType.TO_DESTROY;
		}

		@Override
		public Entity getOwner() {
			return Attack.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return area;
		}

		@Override
		public CollideType getCollideType() {
			return CollideType.CONTAIN;
		}

		@Override
		public int maxCollideByCycle() {
			return 1;
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
			preyFinded((PreyMarker) marker);
		}
		
	}
	
	private class Search extends TouchHandle{
		private int priority;

		@Override
		public int getType() {
			return CollisionType.TO_DESTROY;
		}

		@Override
		public Entity getOwner() {
			return Attack.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return Attack.this.getAttackArea();
		}

		@Override
		public CollideType getCollideType() {
			return CollideType.CONTAIN;
		}

		@Override
		public int maxCollideByCycle() {
			return 1;
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
			preyFinded((PreyMarker) marker);
		}
		
	}
	
	private class Fight extends TouchHandle{
		private int priority;
		
		@Override
		public int getType() {
			return CollisionType.TO_DESTROY;
		}

		@Override
		public Entity getOwner() {
			return Attack.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return Attack.this.getOwner().getCollisionShape();
		}
		
		@Override
		public CollideType getCollideType() {
			return CollideType.INTERSECT;
		}

		@Override
		public int maxCollideByCycle() {
			return TouchHandle.NO_COLLIDE_LIMIT;
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
			if(marker.getOwner()==focused && canHit){
				PreyMarker preyMarker=(PreyMarker) marker;
				
				preyMarker.hit(attack);
				canHit=false;
			}
			
		}
		
	}


}
