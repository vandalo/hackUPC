package com.noxer.games.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.noxer.games.wii.Partida;
import com.noxer.games.wii.Start;


public class auxCar extends ModelInstance {
	protected Vector2 velocity = new Vector2();
	private float velocidadActual;
	private float turbo;
	public Body body;
	public static int VEL_MAX = 200;
	final short COCHE = 0x1;
	private float factorDeGiro;
	public BoundingBox bb;
	public Vector3 trans;
	private float velocidadX, velocidadY;
	boolean firstUpdate;
	public float angleGir;
	

	
	//Per BORRAR
	private float gir;
	private boolean freno;
	private Vector3 pos;
	private int player;
	
	public auxCar(Partida game, Model model, Vector3 pos, int player){
		super(model, pos);
		firstUpdate = true;
		trans = new Vector3();
		bb = new BoundingBox();
		velocidadY = velocidadX = 5f;
		velocidadActual = (float) Math.sqrt((velocidadX*velocidadX)+(velocidadY*velocidadY)+1);

		gir = 0;
		turbo = 0;
		factorDeGiro = 0;
		this.player = player;
		this.pos = pos;
		angleGir = 0;
		
		
	}
	

	private float velocitatTotal(){
		return velocidadActual;//(float) Math.sqrt((velocidadX*velocidadX)+(velocidadY*velocidadY)+1);
	}
		
	public void update(float deltaTime) {
		if(firstUpdate){
			//velocidadX = 0.1f;
			firstUpdate = false;
		}

		turbo -= deltaTime;
		float velTotal = velocitatTotal();
		gir = (float) ((player == 0) ? Math.toRadians(Start.s.giro[0]) : Math.toRadians(Start.s.giro[1]));
		angleGir += (gir*deltaTime);
		transform.rotate(0,1,0,(float) Math.toDegrees(gir*deltaTime));
		velocidadY = (float) (Math.cos(angleGir)*velTotal);
		velocidadX = (float) (Math.sin(angleGir)*velTotal);

		
		if(freno){
			velocidadY -= deltaTime*4;
			velocidadX -= deltaTime*4;
		}

		body.setLinearVelocity(velocidadX, velocidadY);
		//body.setAngularVelocity(0.1f);
		/*if (body.getAngle() >= 60){
			body.setTransform(body.getPosition().x, body.getPosition().y, 60);
		}
		else if (body.getAngle() <= -60){
			body.setTransform(body.getPosition().x, body.getPosition().y, -60);
		}*/
		//transform.set(new Vector3(body.getPosition().x,body.getPosition().y,0), new Quaternion(new Vector3(0,0,0),gir));
		transform.setTranslation(body.getPosition().x, body.getPosition().y, 0);
		transform.getTranslation(trans);
		
		//setPosition(body.getPosition().x - spriteW/2, body.getPosition().y - spriteH/2);
		if ( velocidadActual < VEL_MAX ) {
			velocidadActual += 25 * deltaTime;
			if(velocidadActual > VEL_MAX)
				velocidadActual = VEL_MAX;
		}
		
	}
	
	
	public void initBody(World world) {
		bb = new BoundingBox();
		calculateBoundingBox(bb);
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 0.0f;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(pos.x + bb.getWidth()/2, pos.y + bb.getDepth()/2);
        body = world.createBody(bodyDef);
        body.setUserData(this);
        //((Sprite)body.getUserData()).setPosition(body.getPosition().x,body.getPosition().y);
        
        PolygonShape shape = new PolygonShape();
        //CircleShape shape = new CircleShape();
        //shape.setRadius(getWidth()/2);
        shape.setAsBox( bb.getWidth()*15/2, bb.getDepth()*15/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.1f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.friction = 0.0f;

        	fixtureDef.filter.categoryBits = COCHE;
        	fixtureDef.filter.maskBits = COCHE;
        
        //fixtureDef.isSensor = true; --> use it on towers not to react but detect collision
        body.createFixture(fixtureDef);
        shape.dispose();
	}
	
}