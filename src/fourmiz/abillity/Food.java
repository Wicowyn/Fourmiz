package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Abillity;
import fourmiz.touch.marker.FoodMarker;

public class Food extends Abillity {
	public Food(Entity owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	private int food;
	private MyFood foodMarker=new MyFood();

	@Override
	public void update(int delta) {

	}
	
	public int getFood() {
		return food;
	}
	
	public void setFood(int food) {
		this.food = food;
	}

	@Override
	public Collection<? extends TouchMarker> getTouchMarker() {
		List<TouchMarker> list=new ArrayList<TouchMarker>(1);
		
		list.add(foodMarker);
		
		return list;
	}

	@Override
	public Collection<? extends TouchHandle> getTouchHandle() {
		return new ArrayList<TouchHandle>(0);
	}

	private class MyFood extends FoodMarker{

		@Override
		public int getTotalFood() {
			return food;
		}

		@Override
		public int getFood(int amount) {
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
		public Entity getOwner() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Shape getArea() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
