package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
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
	
	//Recherche de nourriture à l'extérieur de la fourmilière
	public void foodResearch()
	{
		//TODO (jcamenen) déplacement aléatoire jusqu'a rencontre phéromone ou nourriture
		//tuer animal
		//rapporter nourriture
	}
	
	@Override
	public void update(int delta)
	{
		
	}
	
	@Override
	public Collection<TouchMarker> getTouchMarker() {
		return new ArrayList<TouchMarker>(0);
	}

	@Override
	public Collection<TouchHandle> getTouchHandle() {
		return new ArrayList<TouchHandle>(0);
	}	
}
