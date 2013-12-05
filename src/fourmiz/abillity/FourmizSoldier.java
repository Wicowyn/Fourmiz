package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;

public class FourmizSoldier extends Abillity{
	private Life currentLife = new Life(getOwner());

	public FourmizSoldier(Entity owner) {
		super(owner);
	}

	@Override
	public void update(int delta) {
		
		//TODO (wolf) Si rencontre avec un ennemi 
		//TODO (wolf)   alors combat 
		//TODO (wolf) sinon déplacement aléatoire dans le rayon de la fourmilière
	}
	
	/**
	 * Defendre le nid en cas d'attaque
	 */
	public void defend(){
		//FourmizSoldier perd 1 point de vie
		this.currentLife.setCurrentLife(this.currentLife.getCurrentLife()-1);
		//TODO (wolf) Attaque ennemi qui approche le nid
		
	}

	@Override
	public Collection<TouchMarker> getTouchMarker() {
		return new ArrayList<>(0);
	}

	@Override
	public Collection<TouchHandle> getTouchHandle() {
		return new ArrayList<>(0);
	}
	

	
}
