package fourmiz.abillity;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import fourmiz.engine.EngineListener;
import fourmiz.engine.EntityListener;
import fourmiz.touch.marker.FoodMarker;
import fourmiz.touch.marker.LifeMarker;

public class Healer extends Abillity implements EntityListener, EngineListener{
	private static Logger log=LogManager.getLogger(Healer.class);
	private List<SearchStatic> searchAreaStatic=new ArrayList<SearchStatic>();
	private List<HealStatic> healAreaStatic=new ArrayList<HealStatic>();
	private Search search=new Search();
	private Heal heal=new Heal();
	private static final int RADIUS=Engine.SIZE_CASE*4;
	private Shape baseSearchArea=new Circle(0, 0, RADIUS);
	private Shape baseHealArea=new Circle(0, 0, RADIUS);
	private Shape currentSearchArea;
	private Shape currentHealArea;
	private boolean updateSearchArea=true;
	private boolean updateHealArea=true;
	private Touched touch;
	
	private State state=null;
	private Entity focused;
	float speed;
	
	private static final int INTERVAL_TIME=1000;
	private int foodStock=0;
	private int maxFoodStock=500;
	private int time;
	private boolean canHit;

	public Healer(Entity owner) {
		super(owner);
		
		setState(State.SEARCH);
	}

	@Override
	public void update(int delta) {
		
		switch(state){
		case FOLLOW_FOOD:
		case FOLLOW_TO_HEAL:
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
		getOwner().removeEntityListener(this);
	}
	
	public void addStaticSearchArea(Shape area){
		SearchStatic search=new SearchStatic();
		search.setArea(area);
		
		searchAreaStatic.add(search);
		addTouchHandle(search);
	}
	
	public void addStaticHealArea(Shape area){
		HealStatic heal=new HealStatic();
		heal.setArea(area);
		
		healAreaStatic.add(heal);
		addTouchHandle(heal);
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getFood() {
		return foodStock;
	}

	/**
	 * Set the food stock
	 * @param attack in point  by second
	 */
	public void setFood(int foodStock) {
		this.foodStock=foodStock;
	}

	public int getMaxFoodStock() {
		return maxFoodStock;
	}

	public void setMaxFoodStock(int maxFoodStock) {
		this.maxFoodStock = maxFoodStock;
	}

	public boolean removeStaticSearchArea(Shape area){
		SearchStatic searchFinded=null;
		
		for(SearchStatic search : searchAreaStatic){
			if(search.getArea()==area){
				searchFinded=search;
				break;
			}
		}
		
		boolean removed=healAreaStatic.remove(searchFinded);
		
		if(removed)
			if(!removeTouchHandle(searchFinded)) throw new IllegalStateException();
		
		return removed;
	}
	
	public boolean removeStaticHealArea(Shape area){
		HealStatic searchFinded=null;
		
		for(HealStatic heal : healAreaStatic){
			if(heal.getArea()==area){
				searchFinded=heal;
				break;
			}
		}
		
		boolean removed=healAreaStatic.remove(searchFinded);
		
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
		updateSearchArea=true;
		updateHealArea=true;
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

	public Shape getSearchArea() {
		if(updateSearchArea){
			Vector2f pos=getOwner().getPosition();
			
			currentSearchArea=baseSearchArea.transform(Transform.createTranslateTransform(pos.getX(), pos.getY()));
			updateSearchArea=false;
		}
		
		return currentSearchArea;
	}
	
	public Shape getHealerArea() {
		if(updateHealArea){
			Vector2f pos=getOwner().getPosition();
			
			currentHealArea=baseHealArea.transform(Transform.createTranslateTransform(pos.getX(), pos.getY()));
			updateHealArea=false;
		}
		
		return currentHealArea;
	}
	
	
	private void foodFinded(FoodMarker marker){
		focused=marker.getOwner();
		setState(State.FOLLOW_FOOD);
	}
	
	private void healFinded(LifeMarker marker){
		focused=marker.getOwner();
		setState(State.FOLLOW_TO_HEAL);
	}
	
	private void setState(State state){
		this.state=state;
		log.info("Jump to mode "+state);
		
		switch(state){
		case FOLLOW_FOOD:
			for(SearchStatic search : searchAreaStatic) removeTouchHandle(search);
			for(HealStatic heal : healAreaStatic) removeTouchHandle(heal);
			
			removeTouchHandle(search);
			removeTouchHandle(heal);
			
			touch=new Touched(CollisionType.FOOD);
			addTouchHandle(touch);
			break;
		case FOLLOW_TO_HEAL:
			for(SearchStatic search : searchAreaStatic) removeTouchHandle(search);
			for(HealStatic heal : healAreaStatic) removeTouchHandle(heal);
			
			removeTouchHandle(search);
			removeTouchHandle(heal);
			
			touch=new Touched(CollisionType.LIFE);
			addTouchHandle(touch);
			break;
		case SEARCH:
			for(SearchStatic search : searchAreaStatic) addTouchHandle(search);
			addTouchHandle(search);
			
			if(foodStock>0){
				for(HealStatic heal : healAreaStatic) addTouchHandle(heal);
				addTouchHandle(heal);
			}
			
			if(touch!=null) removeTouchHandle(touch);
			focused=null;
			break;
		default:
			break;
		}
		
	}
	
	private int getFood(int amount) {
		int foodTaked=0;
		
		if(amount>foodStock){
			foodTaked=foodStock;
			foodStock=0;				
		}
		else{
			foodTaked=amount;
			foodStock-=amount;
		}
		
		return foodTaked;
		}
	
	private enum State{
		FOLLOW_FOOD, FOLLOW_TO_HEAL, SEARCH
	}
	
	private class SearchStatic extends TouchHandle{
		private int priority;
		private Shape area;
		
		public void setArea(Shape area) {
			this.area = area;
		}

		@Override
		public int getType() {
			return CollisionType.FOOD;
		}

		@Override
		public Entity getOwner() {
			return Healer.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return area;
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
			foodFinded((FoodMarker) marker);
		}
		
	}
	
	private class Search extends TouchHandle{
		private int priority;

		@Override
		public int getType() {
			return CollisionType.FOOD;
		}

		@Override
		public Entity getOwner() {
			return Healer.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return Healer.this.getSearchArea();
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
			foodFinded((FoodMarker) marker);
		}
		
	}
	
	private class HealStatic extends TouchHandle{
		private int priority;
		private Shape area;
		
		public void setArea(Shape area) {
			this.area = area;
		}

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
			return area;
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
			healFinded((LifeMarker) marker);
		}
		
	}
	
	private class Heal extends TouchHandle{
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
			return Healer.this.getHealerArea();
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
			healFinded((LifeMarker) marker);
		}
		
	}
	
	private class Touched extends TouchHandle{
		private int priority;
		private int type;
		
		public Touched(int type){
			this.type=type;
		}
		
		@Override
		public int getType() {
			return type;
		}

		@Override
		public Entity getOwner() {
			return Healer.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return Healer.this.getOwner().getCollisionShape();
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
				switch (state) {
				case FOLLOW_FOOD:
					FoodMarker foodMarker=(FoodMarker) marker;
					foodStock+=foodMarker.getFood(maxFoodStock);
					
					setState(State.SEARCH);
					break;
				case FOLLOW_TO_HEAL:
					LifeMarker lifeMarker=(LifeMarker) marker;
					
					int heal=lifeMarker.getMaxLife()-lifeMarker.getLife();
					lifeMarker.addLife(getFood(heal));
					
					setState(State.SEARCH);
					break;
				case SEARCH:
					break;
				default:
					break;
				}
				
				canHit=false;
			}
			
		}
		
	}


}
