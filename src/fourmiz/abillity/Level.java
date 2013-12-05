package fourmiz.abillity;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.engine.EntityFactory;
import fourmiz.engine.EntityName;

public class Level extends Abillity {	
	private static final int EGGS_TIME=20*1000;
	private static final int LARVA_TIME=EGGS_TIME+20*1000;
	private static final int NYMPH_TIME=LARVA_TIME+20*1000;
	private static final int FOURMIZ_TIME=NYMPH_TIME+7300*1000;
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
			Engine engine=getOwner().getEngine();
			
			Entity entity=EntityFactory.createEntity(EntityName.Larva, engine);
			entity.setOwner(getOwner().getOwner());
			entity.setPosition(getOwner().getPosition());
			
			engine.removeEntityToBuff(getOwner());
			engine.addEntityToBuff(entity);
		}
		else if(state==LifeState.LARVA && time>=LARVA_TIME){
			state=LifeState.NYMPH;
			Engine engine=getOwner().getEngine();
			
			Entity entity=EntityFactory.createEntity(EntityName.Nymph, engine);
			entity.setOwner(getOwner().getOwner());
			entity.setPosition(getOwner().getPosition());
			
			engine.removeEntityToBuff(getOwner());
			engine.addEntityToBuff(entity);
		}
		else if(state==LifeState.NYMPH && time>=NYMPH_TIME){
			state=LifeState.FOURMIZ;
			Engine engine=getOwner().getEngine();
			Entity entity=null;
			
			int value=(int) (Math.random()*100);
			if(value<40){
				entity=EntityFactory.createEntity(EntityName.FourmizWorker, engine);
			}
			else if(value<80){
				entity=EntityFactory.createEntity(EntityName.FourmizSoldier, engine);
			}
			else{
				entity=EntityFactory.createEntity(EntityName.FourmizSex, engine);
			}
			entity.setOwner(getOwner().getOwner());
			entity.setPosition(getOwner().getPosition());
			
			engine.removeEntityToBuff(getOwner());
			engine.addEntityToBuff(entity);
		}
		
		else if(state==LifeState.FOURMIZ && time>=FOURMIZ_TIME){
			state=LifeState.DEAD;
		}

	}
	
	//d�finit l'age en fonction de l'�tat de l'entit�
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

	//retourne l'�tat de l'entit�
	public LifeState getState() {
		return state;
	}

	//retourne l'age de l'entit�
	public int getTime() {
		return time;
	}

	//enum�ration des diff�rents �tats possibles
	public enum LifeState{
		EGGS, LARVA, NYMPH, FOURMIZ, DEAD, ANTHILL
	}
}
