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

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fourmiz.collision.Entity;
import fourmiz.engine.Engine;

public class RealRender extends Render {
	private Animation animation;
	
	
	public RealRender(Entity owner) {
		super(owner);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		animation.getCurrentFrame().setRotation(getOwner().getDirection());
		
		Vector2f position=getOwner().getPosition();
		Engine engine=getOwner().getEngine();
		
		gr.drawAnimation(animation, position.x*engine.getxScale(), position.y*engine.getyScale());		
	}

	@Override
	public void update(int delta) {
		//nothing to do
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

}