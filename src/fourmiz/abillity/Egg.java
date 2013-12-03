package fourmiz.abillity;

import fourmiz.collision.Entity;

public class Egg extends Level{
	
	public Egg(Entity owner) {
		super(owner);
		this.setOwner(owner);
		this.setState(LifeState.EGGS);
		System.out.println("Naissance oeuf");
	}

}
