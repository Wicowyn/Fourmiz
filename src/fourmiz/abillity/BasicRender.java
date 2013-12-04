/*//////////////////////////////////////////////////////////////////////
	This file is part of Bomberton, an Bomberman-like.
	Copyright (C) 2012-2013  Nicolas Barranger <wicowyn@gmail.com>

    Bomberton is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Bomberton is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Bomberton.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

package fourmiz.abillity;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;
import fourmiz.engine.Engine;


public class BasicRender extends Render {

	
	public BasicRender(Entity owner){
		super(owner);
		
	}
	@Override
	public void update(int delta) {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr){
		gr.setColor(Color.blue);
		
		Engine engine=getOwner().getEngine();
		gr.draw(getOwner().getCollisionShape().transform(
				Transform.createScaleTransform(engine.getxScale(), engine.getyScale())));
	}
	
	@Override
	public Collection<TouchMarker> getTouchMarker() {
		return new ArrayList<TouchMarker>(0);
	}
	@Override
	public Collection<TouchHandle> getTouchHandle() {
		return new ArrayList<TouchHandle>(0);
	}

}
