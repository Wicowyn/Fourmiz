package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.engine.EntityFactory;
import fourmiz.engine.EntityName;

public class Queen extends Abillity{
	private int time;
	private int timeToLay;
	
	public Queen(Entity owner){
		super(owner);
		
		time=0;
		generateTimeToLay();
	}
	
	@Override
	public void update(int delta){
		time+=delta;
		
		if(time>=timeToLay){
			time=0;
			generateTimeToLay();
			
			lay();
		}
	}
	
	private void lay(){
		Entity egg=EntityFactory.createEntity(EntityName.Egg, getOwner().getEngine());
		
		Vector2f position=getOwner().getPosition();
		position.x+=(float) (Engine.SIZE_CASE*Math.cos(Math.toRadians(getOwner().getDirection()-180)));
		position.y+=(float) (Engine.SIZE_CASE*Math.sin(Math.toRadians(getOwner().getDirection()-180)));
		
		egg.setPosition(position);
		egg.setDirection(getOwner().getDirection());
		
		getOwner().getEngine().addEntityToBuff(egg);
	}
	
	private void generateTimeToLay(){
		timeToLay=3000+((int) Math.random()%3000)-1000;
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
