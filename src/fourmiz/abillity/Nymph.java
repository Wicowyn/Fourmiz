package fourmiz.abillity;

import fourmiz.collision.Entity;

public class Nymph extends Level{

	public Nymph(Entity owner) {
		super(owner);
		this.setState(LifeState.NYMPH);
		System.out.println("Passage à l'état nymphe");
	}

}
