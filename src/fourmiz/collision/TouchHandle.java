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

package fourmiz.collision;

import org.newdawn.slick.geom.Shape;

/**
 * Class to define an area where we went to be alerted via {@link #perform(TouchMarker)} of specific {@link TouchMarker}
 * @author Nicolas
 */
public abstract class TouchHandle implements Comparable<TouchHandle> {
	/**
	 * Special value to indicate no limit of alert. See {@link #maxCollideByCycle()}
	 */
	public static final int NO_COLLIDE_LIMIT=-1;
	/**
	 * Give the marker type wanted
	 * @return the type
	 */
	public abstract int getType();
	/**
	 * Give the owner entity which the handler is attached
	 * @return the owner entity
	 */
	public abstract Entity getOwner();
	/**
	 * Give the area where we want to be alerted
	 * @return The area
	 */
	public abstract Shape getArea();
	
	/**
	 * Set the priority. {@link TouchHandle} with the upper priority will be execute before other during 
	 * the resolution phase of conflict collision
	 * @param priority
	 */
	@Deprecated
	public abstract void setPriority(int priority);
	/**
	 * Return the priority. See {@link #setPriority(int)}
	 * @return
	 */
	@Deprecated
	public abstract int getPriority();
	/**
	 * Return the {@link CollideType} that we want to be alerted
	 * @return the type
	 */
	public abstract CollideType getCollideType();
	/**
	 * Return the max number of alert by cycle that we want to be alerted
	 * @return max number of alert by cycle or {@link #NO_COLLIDE_LIMIT}
	 */
	public abstract int maxCollideByCycle();
	
	/**
	 * Callback method, is call when handler collide with a marker
	 * @param marker the {@link TouchMarker} collided
	 */
	public abstract void perform(TouchMarker marker);
	
	@Override
	public int compareTo(TouchHandle handle){
		return getPriority()-handle.getPriority();
	}
}
