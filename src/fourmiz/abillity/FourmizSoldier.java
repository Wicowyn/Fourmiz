package fourmiz.abillity;

import fourmiz.collision.Entity;
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
		//TODO (wolf) sinon d�placement al�atoire dans le rayon de la fourmili�re
	}
	
	/**
	 * Defendre le nid en cas d'attaque
	 */
	public void defend(){
		//FourmizSoldier perd 1 point de vie
		this.currentLife.setCurrentLife(this.currentLife.getCurrentLife()-1);
		//TODO (wolf) Attaque ennemi qui approche le nid
		
	}

	
}
