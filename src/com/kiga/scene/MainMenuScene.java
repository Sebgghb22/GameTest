package com.kiga.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.kiga.manager.SceneManager;
import com.kiga.manager.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{

	private MenuScene menuChildScene;
	private final int MENU_PLAY=0;
	private final int MENU_OPTIONS=1;
	private Sprite mainMenu;

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	public void createBackground(){
		mainMenu=new Sprite(0, 0, resourcesManager.menu_background_region, vbom){

			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				// TODO Auto-generated method stub
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		mainMenu.setScale(camera.getWidth()/mainMenu.getWidth());
		mainMenu.setPosition((camera.getWidth()-mainMenu.getWidth())/2, 
				(camera.getHeight()-mainMenu.getHeight())/2);
		attachChild(mainMenu);
	}
	
	private void createMenuChildScene(){
		menuChildScene=new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		final IMenuItem playMenuItem=new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_PLAY,resourcesManager.menu_play_region, vbom), 0.8f, 1);
		final IMenuItem optionsMenuItem=new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_OPTIONS,resourcesManager.menu_options_region,vbom),1.2f,1);
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY()-10);
		optionsMenuItem.setPosition(optionsMenuItem.getX(), optionsMenuItem.getY()+110);
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		switch(pMenuItem.getID()){
			case MENU_PLAY:
				SceneManager.getInstance().loadGameScene(engine);
				return true;
			case MENU_OPTIONS:
				return true;
			default:
				return false;
		}
	}
}
