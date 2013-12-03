package fourmiz.abillity;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.abillity.Egg;

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
		this.owner.setPosition(posQueen);
	}
	
	@Override
	public void update(int delta)
	{
		if(eggReady) spawn();
	}
	
	//crée un oeuf et l'ajoute à la collection et met eggReady à false une fois pondu
	public void spawn()
	{
		//TODO changer addEntityToBuff vers createEntity
		this.owner.getEngine().addEntityToBuff(new Egg(owner));
		this.eggReady = false;
	}
	
	//met eggReady à true si l'oeuf est prêt à être pondu
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
