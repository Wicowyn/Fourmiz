package fourmiz.abillity;

import fourmiz.collision.Entity;

public class Nymph extends Level{

	public Nymph(Entity owner) {
		super(owner);
		//ajout du type NYMPH à l'entité
		this.setState(LifeState.NYMPH);
		System.out.println("Passage à l'état nymphe");
	}

}
