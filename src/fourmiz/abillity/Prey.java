package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.touch.marker.PreyMarker;

public class Prey extends Abillity {
	private int packetFood=50;
	private int life;
	private int food;
	private MyPrey prey=new MyPrey();

	public Prey(Entity owner) {
		super(owner);
	}

	@Override
	public void update(int delta) {

	}
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		if(life<0) throw new IllegalArgumentException();
		this.life = life;
	}
	
	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		if(food<0) throw new IllegalArgumentException();
		this.food = food;
	}

	public int getPacketFood() {
		return packetFood;
	}

	public void setPacketFood(int packetFood) {
		this.packetFood = packetFood;
	}

	private void popFood(){
		Vector2f position=getOwner().getPosition();
		
		while(food>0){
			Shape shape=new Rectangle(0, 0, Engine.SIZE_CASE, Engine.SIZE_CASE);
			Entity entity=new Entity(getOwner().getEngine(), shape);
			
			Food food=new Food(entity);
			food.setFood(getFood(packetFood));
			entity.addAbillity(food);
			
			int dir=(int) ((Math.random()*360)%360);
			
			position.x+=(float) (2*Engine.SIZE_CASE*Math.cos(Math.toRadians(dir)));
			position.y+=(float) (2*Engine.SIZE_CASE*Math.sin(Math.toRadians(dir)));
			
			entity.setPosition(position);
			entity.setDirection(dir);
			
			getOwner().getEngine().addEntityToBuff(entity);
		}
		
		getOwner().getEngine().removeEntityToBuff(getOwner());
	}
	
	private int getFood(int amount) {
		int foodTaked=0;
		
		if(amount>food){
			foodTaked=food;
			food=0;				
		}
		else{
			foodTaked=amount;
			food-=amount;
		}
		
		return foodTaked;
	}

	@Override
	public Collection<? extends TouchMarker> getTouchMarker() {
		List<TouchMarker> list=new ArrayList<TouchMarker>(1);
		
		list.add(prey);
		
		return list;
	}

	@Override
	public Collection<? extends TouchHandle> getTouchHandle() {
		return new ArrayList<TouchHandle>();
	}
	
	private class MyPrey extends PreyMarker{

		@Override
		public Entity getOwner() {
			return Prey.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return Prey.this.getOwner().getCollisionShape();
		}

		@Override
		public int getLife() {
			return life;
		}

		@Override
		public boolean hit(int attackLife) {
			life-=attackLife;
			
			if(life<=0){
				popFood();
				return true;
			}
			else return false;
		}

		@Override
		public int getFood() {
			return food;
		}
		
	}

}
