package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.engine.Abillity;
import fourmiz.touch.marker.LifeMarker;

public class Life extends Abillity{
	private static Logger log=LogManager.getLogger(Life.class);
	private static int INTERVAL_TIME=1000;
	private MyLifeMarker lifeMarker=new MyLifeMarker();
	private int life_current;
	private int life_max;
	private int life_tic;
	private int time=0;
	
	
	public Life(Entity owner) {
		super(owner);	
	}

	@Override
	public void update(int delta) {
		time+=delta;
		
		if(time>=INTERVAL_TIME){
			life_current=life_current-life_tic;
			time-=INTERVAL_TIME;
		}
		//You are dead, life empty
		if(life_current<=0){
			log.info("entity ID: "+getOwner().getID()+" die cause to has no health");
			getOwner().getEngine().removeEntityToBuff(getOwner());		
		}
	}

	public int getCurrentLife() {
		return life_current;
	}

	public void setCurrentLife(int life_current) {
		if(life_current<0 || life_current>life_max) throw new IllegalArgumentException();
		
		this.life_current = life_current;
	}

	public int getMaxLife() {
		if(life_max<=0) throw new IllegalArgumentException();
		
		return life_max;
	}

	public void setMaxLife(int life_max) {
		this.life_max = life_max;
	}

	public int getUptake() {
		return life_tic;
	}

	public void setUptake(int uptake) {
		this.life_tic = uptake;
	}

	@Override
	public Collection<LifeMarker> getTouchMarker() {
		Collection<LifeMarker> list=new ArrayList<LifeMarker>(1);
		
		list.add(lifeMarker);
		
		return list;
	}

	@Override
	public Collection<TouchHandle> getTouchHandle() {
		return new ArrayList<TouchHandle>(0);
	}


	private class MyLifeMarker extends LifeMarker{

		@Override
		public int getLife() {
			return life_current;
		}

		@Override
		public int getMaxLife() {
			return life_max;
		}

		@Override
		public void setLife(int life) {
			
			setCurrentLife(life);
		}

		@Override
		public void addLife(int life) {
			life_current+=life;
			
			if(life_current>life_max) life_current=life_max;
		}

		@Override
		public void withdrawLife(int life) {
			life_current-=life;
			
			if(life_current<0) life_current=0;
		}

		@Override
		public Entity getOwner() {
			return Life.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return Life.this.getOwner().getCollisionShape();
		}
		
	}
}
