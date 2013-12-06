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

package fourmiz.abillity;

import fourmiz.collision.Entity;
import fourmiz.engine.Ability;

public class FourmizSoldier extends Ability{
	private Life currentLife = new Life(getOwner());

	public FourmizSoldier(Entity owner) {
		super(owner);
	}

	@Override
	public void update(int delta) {
		
		//TODO (wolf) Si rencontre avec un ennemi 
		//TODO (wolf)   alors combat 
		//TODO (wolf) sinon d�placement al�atoire dans le rayon de la fourmili�re
	}
	
	/**
	 * Defendre le nid en cas d'attaque
	 */
	public void defend(){
		//FourmizSoldier perd 1 point de vie
		this.currentLife.setCurrentLife(this.currentLife.getCurrentLife()-1);
		//TODO (wolf) Attaque ennemi qui approche le nid
		
	}

	
}
