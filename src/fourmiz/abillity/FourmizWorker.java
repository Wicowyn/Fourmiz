package fourmiz.abillity;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;

public class FourmizWorker extends Abillity
{
	public boolean buzy = false;
	
	public FourmizWorker(Entity owner) 
	{
		super(owner);
	}
	
	public boolean isBusy()
	{
		return buzy;
	}
	
	//Recherche de nourriture � l'ext�rieur de la fourmili�re
	public void foodResearch()
	{
		//TODO (jcamenen) d�placement al�atoire jusqu'a rencontre ph�romone ou nourriture
		//tuer animal
		//rapporter nourriture
	}
	
	@Override
	public void update(int delta)
	{
		
	}
}
