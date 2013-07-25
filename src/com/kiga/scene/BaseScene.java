package com.kiga.scene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.kiga.manager.ResourcesManager;
import com.kiga.manager.SceneManager.SceneType;

import android.app.Activity;

public abstract class BaseScene extends Scene{
	/*
	 * Varibles
	 * */
	protected Engine engine;
	protected Activity activity;
	protected ResourcesManager resourcesManager;
	protected VertexBufferObjectManager vbom;
	protected BoundCamera camera;
	
	public BaseScene(){
		this.resourcesManager=ResourcesManager.getInstance();
		this.engine=resourcesManager.engine;
		this.activity=resourcesManager.activity;
		this.camera=resourcesManager.camera;
		this.vbom=resourcesManager.vbom;
		createScene();
	}
	
	/*
	 * Abstractions
	 */
	public abstract void createScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
	public abstract void disposeScene();
}
