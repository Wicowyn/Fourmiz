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

package fourmiz.engine;

import fourmiz.collision.Entity;

/**
 * Interface definition for callback when {@link Entity} is added or removed
 *  @author Nicolas
 *
 */
public interface EngineListener {
	/**
	 * {@link Entity} has added
	 * @param entity
	 */
	public void entityAdded(Entity entity);
	/**
	 * {@link Entity} has removed
	 * @param entity
	 */
	public void entityRemoved(Entity entity);
	
}
