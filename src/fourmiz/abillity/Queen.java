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

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.engine.EngineListener;
import fourmiz.engine.EntityFactory;
import fourmiz.engine.EntityName;

public class Queen extends Abillity implements EngineListener{
	private int time;
	private int timeToLay;
	private int interval=10000;
	private int intervalOffset=2000;
	private int nbMaxEgg = 50;
	private ArrayList<Entity> oeufPondu = new ArrayList<Entity>(nbMaxEgg);
	
	public int getNbMaxEgg() {
		return nbMaxEgg;
	}

	public void setNbMaxEgg(int nbMaxEgg) {
		this.nbMaxEgg = nbMaxEgg;
	}

	public Queen(Entity owner){
		super(owner);
		
		time=0;
		generateTimeToLay();
	}
	
	@Override
	public void update(int delta){
		time+=delta;
		
		//si l'age de la reine � d�pass� ou est �gal � la date de ponte suivante 
		if(time>=timeToLay && nbMaxEgg>oeufPondu.size()){
			time=0;
			generateTimeToLay();
			lay();
		}
	}
	
	//ponte d'un oeuf
	private void lay(){
		//cr�ation d'une nouvelle entit� de type oeuf
		Entity egg=EntityFactory.createEntity(EntityName.Egg, getOwner().getEngine());
		//on lui donne la position de la reine au moment de la ponte
		Vector2f position=getOwner().getPosition();
		position.x+=(float) (Engine.SIZE_CASE*Math.cos(Math.toRadians(getOwner().getDirection()-180)));
		position.y+=(float) (Engine.SIZE_CASE*Math.sin(Math.toRadians(getOwner().getDirection()-180)));
		
		egg.setOwner(getOwner());
		egg.setPosition(position);
		egg.setDirection(0);
		
		//on ajoute l'oeuf � la liste des entit�s
		getOwner().getEngine().addEntityToBuff(egg);
		oeufPondu.add(egg);
	}
	
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		if(interval<=0) throw new IllegalArgumentException();
		this.interval = interval;
	}

	public int getIntervalOffset() {
		return intervalOffset;
	}

	public void setIntervalOffset(int intervalOffset) {
		if(intervalOffset<=0) throw new IllegalArgumentException();
		this.intervalOffset = intervalOffset;
	}

	//g�n�ration du prochain temps de ponte
	private void generateTimeToLay(){
		timeToLay=interval+((int) Math.random()*intervalOffset*2)-intervalOffset;
	}

	@Override
	public void entityAdded(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entityRemoved(Entity entity) {
			oeufPondu.remove(entity);
	}
	
	@Override
	public void setOwner(Entity owner){
		if(getOwner()!=null){
			getOwner().getEngine().removeListener(this);
		}
		
		super.setOwner(owner);
		
		getOwner().getEngine().addListener(this);
	}
}
