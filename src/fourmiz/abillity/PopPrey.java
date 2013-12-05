package fourmiz.abillity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.engine.EngineListener;

public class PopPrey extends Abillity implements EngineListener{
	private List<Entity> list=new ArrayList<Entity>();
	private int maxPrey=5;
	private int popDelay=5;
	private int time=0;
	

	public PopPrey(Entity owner) {
		super(owner);
	}
	
	@Override
	public void setOwner(Entity owner){
		if(getOwner()!=null) getOwner().getEngine().removeListener(this);
		
		super.setOwner(owner);
		
		getOwner().getEngine().addListener(this);
	}

	
	@Override
	public void update(int delta) {
		time+=delta;
		
		if(time>=popDelay){
			popPrey();
			time-=popDelay;
		}
	}
	
	private void popPrey(){
		if(list.size()>=maxPrey) return;
		
		Entity prey=new Entity(getOwner().getEngine(), Engine.getDefaultShape());
		Prey preyA=new Prey(prey);
		preyA.setLife(160);
		preyA.setFood(356);
		prey.addAbillity(preyA);
		
		ShapeMove move=new ShapeMove(prey);
		move.setSpeed(2);
		move.setArea(getOwner().getCollisionShape());
		prey.addAbillity(move);
		
		RealRender render=new RealRender(prey);
		render.setAnimation(getOwner().getEngine().getRessources().getAnimation("Prey"));
		prey.addAbillity(render);
		
		Vector2f position=new Vector2f(
				getOwner().getCollisionShape().getCenterX(),
				getOwner().getCollisionShape().getCenterY());
		
		prey.setPosition(position);
		prey.setDirection(270);
		
		getOwner().getEngine().addEntityToBuff(prey);
		list.add(prey);
	}

	public int getMaxPrey() {
		return maxPrey;
	}

	public void setMaxPrey(int maxPrey) {
		this.maxPrey = maxPrey;
	}

	public int getPopDelay() {
		return popDelay;
	}

	public void setPopDelay(int popDelay) {
		this.popDelay = popDelay;
	}

	@Override
	public void entityAdded(Entity entity) {
		//nothing to do		
	}

	@Override
	public void entityRemoved(Entity entity) {
		list.remove(entity); //can be our prey		
	}

}
