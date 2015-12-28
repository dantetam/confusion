package tests;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import models.RawModel;
import models.TexturedModel;

import render.*;
import shaders.StaticShader;
import system.BaseSystem;
import system.InputSystem;
import textures.ModelTexture;

public class MainGameLoop {

	public ArrayList<BaseSystem> systems = new ArrayList<BaseSystem>();
	public InputSystem inputSystem;
	
	public static void main(String[] args)
	{
		new MainGameLoop();
	}

	public MainGameLoop()
	{
		GLFW.glfwInit();
		
		inputSystem = new InputSystem(this);

		DisplayManager.createDisplay(this);
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		//counter clockwise vertices
		float[] vertices = {
				//Left bottom and top right, resp.
				-0.5f, 0.5f, 0f,	
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f
		};

		//order in which to transverse the vertices
		int[] indices = {0,1,3,3,1,2};

		//respective u,v vertex of texture to map to
		float[] textureCoords = {0,0,0,1,1,1,1,0};

		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("blueplasma"));
		TexturedModel texturedModel = new TexturedModel(model, texture);

		//Keep updating the display until the user exits
		while (!DisplayManager.requestClose())
		{
			renderer.prepare();
			shader.start(); //Enable shader
			renderer.render(texturedModel);
			shader.stop(); //Disable shader when the draw is done
			DisplayManager.updateDisplay();
		}

		//Clean up data
		shader.cleanUp();
		loader.cleanData();
		DisplayManager.closeDisplay();
	}

}
