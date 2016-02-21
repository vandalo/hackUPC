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


public class Box extends ModelInstance {
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
	public boolean pintar;
	public float tiempo;
	

	
	//Per BORRAR
	private float gir;
	private boolean freno;
	private Vector3 pos;
	
	public Box(Partida game, Model model, Vector3 pos){
		super(model, pos);
		firstUpdate = true;
		trans = new Vector3();
		bb = new BoundingBox();
		velocidadY = velocidadX = 5f;
		velocidadActual = (float) Math.sqrt((velocidadX*velocidadX)+(velocidadY*velocidadY)+1);

		this.pos = pos;
		angleGir = 0;
		pintar = true;
		tiempo = 0;
		
		
	}
	
		
	public void update(float deltaTime) {
		
		tiempo -= deltaTime;
		if (tiempo <= 0) pintar = true;
		transform.rotate(0,0,1,(float) Math.toDegrees(1.6*deltaTime));		
	}
	
	
	public void initBody(World world) {
		bb = new BoundingBox();
		calculateBoundingBox(bb);
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
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