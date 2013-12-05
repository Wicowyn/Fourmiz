package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;

public class ShapeMove extends Abillity {
	/**
	 * At each times where {@link ShapeMove#lastUpdate} equal {@link Engine#SIZE_CASE} we update our direction by a beam of {@value ShapeMove#beamMove} percent
	 */
	private int beamMove=20;
	private float speed=1;
	private Shape area;
	private float lastUpdate;
	private State state=State.NORMAL;

	public ShapeMove(Entity owner) {
		super(owner);		
	}

	@Override
	public void update(int delta) {		
		if(lastUpdate>=Engine.SIZE_CASE){
			if(state==State.NORMAL){
				Shape collisionShape=getOwner().getCollisionShape();
				
				if(!area.contains(collisionShape)){
					state=State.CORRECT;
					
					getOwner().setDirection(getOwner().getDirection()-180);
				}
				else{
					float dirHip=(360*beamMove)/100;
					
					getOwner().setDirection(
							(float) (getOwner().getDirection()+(((Math.random()*360)%dirHip)-dirHip/2)));
					
				}
				
			}
			
			
			lastUpdate-=Engine.SIZE_CASE;
			
		}
		
		if(state==State.CORRECT){
			Shape collisionShape=getOwner().getCollisionShape();
			if(area.contains(collisionShape)){
				state=State.NORMAL;
			}
		}
		
		Vector2f position=getOwner().getPosition();
		float hip=(speed*Engine.SIZE_CASE)/delta;
		lastUpdate+=hip;
		
		position.x+=hip*Math.cos(Math.toRadians(getOwner().getDirection()));
		position.y+=hip*Math.sin(Math.toRadians(getOwner().getDirection()));
		
		getOwner().setPosition(position);
	}
	
	public Shape getArea() {
		return area;
	}

	public void setArea(Shape area) {
		this.area = area;
	}

	public float getSpeed() {
		return speed;
	}

	/**
	 * Set the move speed
	 * @param speed in case by second
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getBeamMove() {
		return beamMove;
	}

	public void setBeamMove(int beamMove) {
		this.beamMove = beamMove;
	}

	@Override
	public Collection<? extends TouchMarker> getTouchMarker() {
		return new ArrayList<TouchMarker>(0);
	}

	@Override
	public Collection<? extends TouchHandle> getTouchHandle() {
		return new ArrayList<TouchHandle>(0);
	}

	private enum State{
		CORRECT, NORMAL
	}
}
