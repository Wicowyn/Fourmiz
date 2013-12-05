package fourmiz.abillity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.touch.marker.LifeMarker;

public class Life extends Abillity{
	private static Logger log=LogManager.getLogger(Life.class);
	private static int INTERVAL_TIME=1000;
	private MyLifeMarker lifeMarker=new MyLifeMarker();
	//vie instantanée
	private int life_current;
	//vie maximum
	private int life_max;
	//quantité de vie perdue à chaque actualisation
	private int life_tic;
	private int time=0;
	
	
	public Life(Entity owner) {
		super(owner);
		
		addTouchMarker(lifeMarker);
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

	//retourne la vie actuelle
	public int getCurrentLife() {
		return life_current;
	}

	//met à jour la vie actuelle en vérifiant qu'elle est conforme aux bornes
	public void setCurrentLife(int life_current) {
		if(life_current<0 || life_current>life_max) throw new IllegalArgumentException();
		
		this.life_current = life_current;
	}

	//retourne la vie maximum
	public int getMaxLife() {
		if(life_max<=0) throw new IllegalArgumentException();
		
		return life_max;
	}

	//met à jour la valeur maximale de vie possible
	public void setMaxLife(int life_max) {
		this.life_max = life_max;
	}

	//retourne la perte de vie périodique
	public int getUptake() {
		return life_tic;
	}

	//met à jour la perte de vie périodique
	public void setUptake(int uptake) {
		this.life_tic = uptake;
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
		//augmente la vie actuelle de la valeur de life
		public void addLife(int life) {
			life_current+=life;
			
			if(life_current>life_max) life_current=life_max;
		}

		@Override
		//diminue la vie actuelle de la valeur de life
		public void withdrawLife(int life) {
			life_current-=life;
			
			if(life_current<0) life_current=0;
		}

		@Override
		//retourne l'entité à qui appartient cette vie
		public Entity getOwner() {
			return Life.this.getOwner();
		}

		//retourne la zone d'influence autour de l'entité 
		@Override
		public Shape getArea() {
			return Life.this.getOwner().getCollisionShape();
		}
		
	}
}
