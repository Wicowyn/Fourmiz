package fourmiz.abillity;

import fourmiz.collision.Entity;

public class Nymph extends Level{

	public Nymph(Entity owner) {
		super(owner);
		//ajout du type NYMPH � l'entit�
		this.setState(LifeState.NYMPH);
		System.out.println("Passage � l'�tat nymphe");
	}

}
