/*//////////////////////////////////////////////////////////////////////
	This file is part of Fourmiz, an simulation of ant live.
	Copyright (C) 2013  Nicolas Barranger <wicowyn@gmail.com>
						Jean-Baptiste Le Henaff <jb.le.henaff@gmail.com>
						Antoine Fouque <antoine.fqe@gmail.com>
						Julien Camenen <jcamenen@gmail.Com>
    Fourmiz is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Fourmiz is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Fourmiz.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

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
