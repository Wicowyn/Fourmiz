package fourmiz.touch.marker;

import fourmiz.collision.TouchMarker;
import fourmiz.engine.CollisionType;

public abstract class FoodMarker extends TouchMarker {

	@Override
	public final int getType() {
		return CollisionType.FOOD;
	}
	
	/**
	 * Total of food in
	 * @return
	 */
	public abstract int getTotalFood();
	/**
	 * To take food
	 * @param amount total wanted
	 * @return amount received
	 */
	public abstract int getFood(int amount);

}
