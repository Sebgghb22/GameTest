package com.kiga.scene;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.kiga.manager.ResourcesManager;
import com.kiga.manager.SceneManager;
import com.kiga.manager.SceneManager.SceneType;

public class GameScene extends BaseScene{

	private HUD gameHUD;
	private Text scoreText;
	private int score=0;
	private FixedStepPhysicsWorld physicsWorld;

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createHUD();
		createPhysics();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		SceneManager.getInstance().loadMenuScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		camera.setHUD(null);
		camera.setCenter(camera.getWidth()/2, camera.getHeight()/2);
	}
	
	private void createBackground(){
		setBackground(new Background(Color.GREEN));
	}
	
	private void createHUD(){
		gameHUD=new HUD();

		scoreText = new Text(20, 420, resourcesManager.font, 
				"Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setPosition(0, 0) ;  
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);

		camera.setHUD(gameHUD);
	}
	
	private void addScore(int i){
		score+=i;
		scoreText.setText("Score:"+score);
	}
	
	private void createPhysics(){
		physicsWorld=new FixedStepPhysicsWorld(60, new Vector2(0,-17), false);
		registerUpdateHandler(physicsWorld);
	}
}
