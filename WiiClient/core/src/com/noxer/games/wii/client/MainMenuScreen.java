package com.noxer.games.wii.client;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen implements Screen{

	final Client game;
	final MainMenuScreen mmScreen;
	private TextureAtlas atlas, gameUI;
	protected Skin skin, skin2;
	private Stage stage;
	private Table table;
	protected Sprite background;
	private ImageButton menu;
	private SpriteBatch batch;
	public float n;
	private boolean started;
	
    public MainMenuScreen(final Client gam){
        game = gam;
        mmScreen = this; 
        batch = new SpriteBatch();
        n = 0;
        started = false;
    }
    
	@Override
	public void show() {
		//stage = new Stage(new FitViewport(800, 480)); //CON STRETCH Y BACKGROUND EN LA TABLA SE VE BN
		stage = new Stage(new StretchViewport(800, 480));
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("skins/userInterface.pack");
		gameUI = new TextureAtlas("skins/gameUI.pack");
		skin = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		skin2 = new Skin(Gdx.files.internal("skins/gameUI.json"), gameUI);
		table = new Table(skin);
		table.setBounds(0, 0, 800, 480);
		
		//menu = new ImageButton(skin2, "timon");
		
		//buttonPlay = new TextButton("PLAY", skin, "mainMenuBlack");
		//SpriteDrawable
		ImageButtonStyle b = new ImageButtonStyle();
		b.imageUp = skin2.getDrawable("options");
		//b.imageDown = skin2.getDrawable("options");
		b.pressedOffsetX = 1;
		b.pressedOffsetY = -1;
		b.imageUp.setMinHeight(65);
		b.imageUp.setMinWidth(65);
		menu = new ImageButton(b);
		//heading = new Label("Aquarium Deluxe", skin, "default");
		//heading.setFontScale(2);
		
		//table.add(heading);
		//table.getCell(heading).padBottom(30);
		menu.setPosition(700, 400);
		//menu.setSize(150, 150);
		menu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				SocketSender.Connect("10.192.213.124", 5556); 
				started = true;
				//gameUI.dispose();
				//game.setScreen(new StageSelector(mmScreen, game));
			}
		});
		
		//table.add(menu);
		stage.addActor(table);
		table.addActor(menu);
		
		background = new Sprite(new Texture("CONECT-01.png"));
		background.setBounds(0, 0, Client.width, Client.height);
		//background.setBounds(0, 0, 800, 480);
		
	}

	@Override
	public void render(float delta) {
      //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      		Gdx.gl.glClearColor(255, 255, 255, 1);
      		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      		//Gdx.app.log("HOLAAA", "PPEEEPIIITOO");
      		//if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer) && n == 5 && started){
      			if(started && n > 0.1){
      				SocketSender.Snd_txt_Msg(String.valueOf(Gdx.input.getPitch()));
      				Gdx.app.log("Eix X: ", String.valueOf(Gdx.input.getPitch()));
      				n = 0;
      				//background = new Sprite(new Texture("POWER-01.png"));
      				//Gdx.app.log("started", "datoooooooos");
      			}
      			else {
      				//Gdx.app.log("started", "false");
      			}
      			n+=delta;
      			//Gdx.app.log("Eix Y: ", String.valueOf(Gdx.input.getRoll()));
      			//Gdx.app.log("Eix Z: ", String.valueOf(Gdx.input.getAzimuth()));
      			
      			/*Gdx.app.log("Eix X2: ", String.valueOf(Gdx.input.getAccelerometerX()));
              	Gdx.app.log("Eix Y2: ", String.valueOf(Gdx.input.getAccelerometerY()));
              	Gdx.app.log("Eix Z2: ", String.valueOf(Gdx.input.getAccelerometerZ()));*/
      			//n = 0;
      		//}
      		batch.begin();
      			background.draw(batch);
      		batch.end();
              //table.debugTable();
      		//stage.getViewport().apply();
              stage.act(delta);
              stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		//stage.getViewport().update(width, height);
		//table.invalidateHierarchy();
		stage.getCamera().update();
		//background.setBounds(0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		atlas.dispose();
		skin.dispose();
		skin2.dispose();
		stage.dispose();
		gameUI.dispose();
		batch.dispose();
		background.getTexture().dispose();
	}

}
	
	/*
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		*/


/*private ScheduledExecutorService programador;
programador = Executors.newSingleThreadScheduledExecutor();
programador.schedule(accion, timepohastalaprimeraejecucion, TimeUnit.MILLISECONDS);
programador.schedule(accion, 2000, TimeUnit.MILLISECONDS);
 private Runnable accion = new Runnable() {
        @Override
        public void run() {}};
        ---------
        *Timer timer = new Timer();
        *TimerTask tas = new TimerTask(){
	        public void run(){
	        }
	    };
	    
	 	timer.schedule(tas, 2900);        */
