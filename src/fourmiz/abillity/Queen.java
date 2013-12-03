package fourmiz.abillity;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;

public class Queen extends Abillity
{
	private int timeBeforeSpawn = 1000;
	private boolean eggReady = false;
	Vector2f posQueen;
	
	public Queen(Entity owner)
	{
		super(owner);
		this.readyToSpawn();
		this.posQueen = new Vector2f(0, 0);
		getOwner().setPosition(posQueen);
	}
	
	@Override
	public void update(int delta)
	{
		if(eggReady) spawn();
	}
	
	//cr�e un oeuf et l'ajoute � la collection et met eggReady � false une fois pondu
	public void spawn()
	{
		//TODO changer addEntityToBuff vers createEntity
		//this.owner.getEngine().addEntityToBuff(new Egg(owner));
		this.eggReady = false;
	}
	
	//met eggReady � true si l'oeuf est pr�t � �tre pondu
	private void readyToSpawn()
	{
		while(true)
		{
			Timer timer = new Timer();
	        timer.schedule (new TimerTask()
	        {
	            public void run()
	            {
	            	eggReady = true;
	            }
	        }, 0, this.timeBeforeSpawn);
		}
	}

	@Override
	public Collection<TouchMarker> getTouchMarker()
	{
		return null;
	}

	@Override
	public Collection<TouchHandle> getTouchHandle()
	{
		return null;
	}
}
