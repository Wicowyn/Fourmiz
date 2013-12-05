package fourmiz.abillity;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;

public class Level extends Abillity {	
	private static final int EGGS_TIME=30*1000;
	private static final int LARVA_TIME=EGGS_TIME+100*1000;
	private static final int NYMPH_TIME=LARVA_TIME+170*1000;
	private static final int FOURMIZ_TIME=NYMPH_TIME+7300*1000;
	private LifeState state;
	private int time;
	
	
	public Level(Entity owner) {
		super(owner);
	}

	@Override
	public void update(int delta) {
		time+=delta;
		//si l'entité est un oeuf et que son temps est écoulé, elle évolue en larve
		if(state==LifeState.EGGS && time>=EGGS_TIME){
			state=LifeState.LARVA;
		}
		//si l'entité est une larve et que son temps est écoulé, elle évolue en nymphe
		else if(state==LifeState.LARVA && time>=LARVA_TIME){
			state=LifeState.NYMPH;
		}
		//si l'entité est un nymphe et que son temps est écoulé, elle évolue en fourmi
		else if(state==LifeState.NYMPH && time>=NYMPH_TIME){
			state=LifeState.FOURMIZ;
		}
		//si l'entité est une fourmi et que son temps est écoulé, elle meurt
		else if(state==LifeState.FOURMIZ && time>=FOURMIZ_TIME){
			state=LifeState.DEAD;
		}

	}
	
	//définit l'age en fonction de l'état de l'entité
	public void setState(LifeState state) {
		this.state = state;
		
		switch (state){
		case EGGS:
			time=0;
			break;
		case LARVA:
			time=EGGS_TIME;
			break;
		case NYMPH:
			time=LARVA_TIME;
			break;
		case FOURMIZ:
			time=NYMPH_TIME;
		default:
			break;
		}
	}

	//retourne l'état de l'entité
	public LifeState getState() {
		return state;
	}

	//retourne l'age de l'entité
	public int getTime() {
		return time;
	}

	//enumération des différents états possibles
	public enum LifeState{
		EGGS, LARVA, NYMPH, FOURMIZ, DEAD, ANTHILL
	}
}
