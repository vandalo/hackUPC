package com.noxer.games.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.noxer.games.wii.Partida;


public class Car extends Sprite {
	protected Vector2 velocity = new Vector2();
	protected float spriteW, spriteH;
	private float velocidadActual;
	private float turbo;
	public Body body;
	public static int VEL_MAX = 50;
	
	
	
	//Per BORRAR
	private float gir;
	private boolean freno;
	private Sprite spriteCenter, spriteGiroI1, spriteGiroI2, spriteGiroD1, spriteGiroD2;
	
	public Car(World world, Partida game, Sprite centrado, Sprite giroI1,
			Sprite giroI2, Sprite giroD1, Sprite giroD2){
		spriteW = getWidth(); 
		spriteH = getHeight();
		velocidadActual = 0;
		turbo = 0;
		spriteCenter = centrado;
		spriteGiroI1 = giroI1;
		spriteGiroI2 = giroI2;
		spriteGiroD1 = giroD1;
		spriteGiroD2 = giroD2;
		initBody(world);
		
	}
	
	public void draw(Batch spriteBatch){
			update(Gdx.graphics.getDeltaTime());
			super.draw(spriteBatch);
	}

	protected void update(float deltaTime) {		
		//setNivelPez();
		//collisionX = false;
		//collisionY = false;
		turbo -= deltaTime;
		if(freno)velocidadActual -= deltaTime*3;
		else if(velocidadActual < VEL_MAX) velocidadActual += deltaTime;
		
		if(gir < -30){
			set(this.spriteGiroI2);
		}
		else if(gir < -15){
			set(this.spriteGiroI1);
		}
		else if(gir < 15){
			set(this.spriteCenter);
		}
		else if(gir < 30){
			set(this.spriteGiroD1);
		}
		else{
			set(this.spriteGiroD2);
		}
		
		body.setLinearVelocity(body.getLinearVelocity().x, -body.getLinearVelocity().y);
		setPosition(body.getPosition().x - spriteW/2, body.getPosition().y - spriteH/2);
		
		
	}
	
	public void initBody(World world) {
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 0.0f;
        bodyDef.position.set(getX() + getWidth()/2, getY() + getHeight()/2);
        body = world.createBody(bodyDef);
        body.setUserData(this);
        //((Sprite)body.getUserData()).setPosition(body.getPosition().x,body.getPosition().y);
        
        //PolygonShape shape = new PolygonShape();
        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth()/2);
        //shape.setAsBox(player.getWidth()/2 / 1, player.getHeight()/2 / 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.1f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.friction = 0.0f;
      
        //fixtureDef.isSensor = true; --> use it on towers not to react but detect collision
        body.createFixture(fixtureDef);
        shape.dispose();
	}
	
}
