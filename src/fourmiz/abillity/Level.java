package fourmiz.abillity;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;

public class Level extends Abillity {	
	private static final int EGGS_TIME=30*1000;
	private static final int LARVA_TIME=EGGS_TIME+100*1000;
	private static final int NYMPH_TIME=LARVA_TIME+170*1000;
	private LifeState state;
	private int time;
	
	
	public Level(Entity owner) {
		super(owner);
	}

	@Override
	public void update(int delta) {
		time+=delta;
		
		if(state==LifeState.EGGS && time>=EGGS_TIME){
			state=LifeState.LARVA;
		}
		else if(state==LifeState.LARVA && time>=LARVA_TIME){
			state=LifeState.NYMPH;
		}
		else if(state==LifeState.NYMPH && time>=NYMPH_TIME){
			state=LifeState.FOURMIZ;
			
			
		}

	}
	
	public LifeState getState() {
		return state;
	}

	public int getTime() {
		return time;
	}

	public enum LifeState{
		EGGS, LARVA, NYMPH, FOURMIZ
	}
}
