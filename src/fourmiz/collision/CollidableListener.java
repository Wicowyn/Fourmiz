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

/**
 * Interface definition for callback when {@link TouchHandle} or {@link TouchMarker} is added or removed
 * @author Nicolas
 */
public interface CollidableListener {
	/**
	 * {@link TouchHandle} has added
	 * @param handle
	 */
	public void touchHandleAdded(TouchHandle handle);
	/**
	 * {@link TouchHandle} has removed
	 * @param handle
	 */
	public void touchHandleRemoved(TouchHandle handle);
	
	/**
	 * {@link TouchMarker} has added
	 * @param marker
	 */
	public void touchMarkerAdded(TouchMarker marker);
	/**
	 * {@link TouchMarker} has removed
	 * @param marker
	 */
	public void touchMarkerRemoved(TouchMarker marker);
}
