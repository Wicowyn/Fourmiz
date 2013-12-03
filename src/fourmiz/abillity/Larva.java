package fourmiz.abillity;

import fourmiz.collision.Entity;

public class Larva extends Level{

	public Larva(Entity owner) {
		super(owner);
		this.setState(LifeState.LARVA);
		System.out.println("Passage � l'�tat larve");
	}

}

