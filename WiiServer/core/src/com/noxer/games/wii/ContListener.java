package com.noxer.games.wii;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.noxer.games.entities.Box;
import com.noxer.games.entities.auxCar;

public class ContListener implements com.badlogic.gdx.physics.box2d.ContactListener{
	private Partida game;
	public ContListener(Partida game){
		this.game = game;
	}
	
	@Override
    public void beginContact(Contact contact) {
    	contact.setRestitution(0);
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    	Box box = null;
    	auxCar car = null;
    	if (contact.getFixtureA().getBody().getUserData() instanceof Box){
    		box = (Box) contact.getFixtureA().getBody().getUserData();
    		if (!box.pintar) contact.setEnabled(false);
    		/*if (contact.getFixtureB().getBody().getUserData() instanceof auxCar){
    			
    		}*/
    	}
    	else if (contact.getFixtureB().getBody().getUserData() instanceof Box){
    		box = (Box) contact.getFixtureB().getBody().getUserData();
    		if (!box.pintar) contact.setEnabled(false);
    		/*if (contact.getFixtureA().getBody().getUserData() instanceof auxCar){
    			
    		}*/
    	}

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    	Box box = null;
    	auxCar car = null;
    	if (contact.getFixtureA().getBody().getUserData() instanceof Box){
    		if (contact.getFixtureB().getBody().getUserData() instanceof auxCar){
    			box = (Box) contact.getFixtureA().getBody().getUserData();
    			car = (auxCar) contact.getFixtureB().getBody().getUserData();
    			box.tiempo = 10;
    			box.pintar = false;
    		}
    	}
    	else if (contact.getFixtureB().getBody().getUserData() instanceof Box){
    		if (contact.getFixtureA().getBody().getUserData() instanceof auxCar){
    			box = (Box) contact.getFixtureB().getBody().getUserData();
    			car = (auxCar) contact.getFixtureA().getBody().getUserData();
    			box.tiempo = 10;
    			box.pintar = false;
    		}
    	}
    }
}
