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

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import fourmiz.collision.Entity;
import fourmiz.engine.Engine;

public class RealRender extends Render {
	private Animation animation;
	private Image img;
	
	public RealRender(Entity owner) {
		super(owner);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		Engine engine=getOwner().getEngine();		
		Vector2f position=getOwner().getPosition();
		position.x=position.x*engine.getxScale();
		position.y=position.y*engine.getyScale();
		
		//if(getOwner().getID()==0)
		//	log.info("owner rotate: "+getOwner().getDirection()+"/ frame rotate: "+animation.getCurrentFrame().getRotation());
		
		if(img!=null){
			gr.drawImage(img, position.x, position.y);
		}
		else if(animation!=null){
			animation.getCurrentFrame().setRotation(getOwner().getDirection());
			
			gr.drawAnimation(animation, position.x, position.y);		
		} else throw new IllegalStateException();
		
		
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

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

}