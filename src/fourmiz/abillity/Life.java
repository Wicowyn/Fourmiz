package fourmiz.abillity;

import java.util.Collection;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;

public class Life extends Abillity{
	public int life_current;
	public int life_max;//life max for the entity
	public int life_tic;//life loose every seconds
	public boolean live_or_dead = false;//à supprimer ?
	private int time;
	
	
	public Life(Entity owner) {
		super(owner);
		// TODO Auto-generated constructor stub		
	}

	@Override
	public void update(int delta) {
		life_current=life_max;
		time+=delta;
		
		if(time>=1000){
			life_current=life_current-life_tic;
			time=time-1000;
		}
		//You are dead, life empty
		if(life_current<=0){
			live_or_dead = true;//à supprimer ?
			owner.getEngine().removeEntity(owner);		
		}
	}
	

	@Override
	public Collection<TouchMarker> getTouchMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TouchHandle> getTouchHandle() {
		// TODO Auto-generated method stub
		return null;
	}



}
