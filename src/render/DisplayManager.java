package render;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import system.InputSystem;
import system.InputSystem.Click;
import tests.MainGameLoop;

public class DisplayManager {

	private static final int width = 1500, height = 900;
	public static long window;
	
	private static GLFWErrorCallback errorCallback; 
	private static GLFWFramebufferSizeCallback framebufferSizeCallback; 
	private static GLFWKeyCallback keyCallback;
	private static GLFWCursorPosCallback mousePosCallback;
	private static GLFWMouseButtonCallback mouseButtonCallback;
	public boolean fullscreen = false;

	private static MainGameLoop main;
	
	public static void createDisplay(MainGameLoop gameLoop)
	{
		main = gameLoop;
		glfwSetErrorCallback(errorCallback = Callbacks.errorCallbackPrint(System.err));

		glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); 
		window = glfwCreateWindow(1600, 900, "Confusion", 0, 0);
		glfwMakeContextCurrent(window);
		GLContext.createFromCurrent();
		glfwShowWindow(window);
		if (window == 0)
			throw new RuntimeException("Failed to create window");

		glfwSetFramebufferSizeCallback(window, (framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
		    @Override
		    public void invoke(long window, int width, int height) {
		        onResize(width, height);
		    }
		}));
		onResize(width, height);
		
		glfwSetCursorPosCallback(window, (mousePosCallback = new GLFWCursorPosCallback() {
		    public void invoke(long window, double xpos, double ypos) {
		        //cursorPos.x = xpos;
		        //cursorPos.y = framebuffer.height - ypos;
		    	Mouse.setMouse((float)xpos, (float)ypos);
		    }
		}));
		
		glfwSetMouseButtonCallback(window, (mouseButtonCallback = new GLFWMouseButtonCallback() {
		    public void invoke(long window, int button, int action, int mods) {
		    	main.inputSystem.clicks.add(main.inputSystem.new Click(Mouse.getX(), Mouse.getY(), action, button));
		    }
		}));
		
		//GL11.glViewport(0, 0, width, height);
	}
	
	public static double currentTimeMillis() {
	    return glfwGetTime() * 1000;
	}
	
	public static void onResize(int width, int height) {
		GL11.glViewport(0, 0, width, height);
	}

	public static void closeDisplay() 
	{
		glfwDestroyWindow(window);
	}

	public static void updateDisplay()
	{
		glfwPollEvents();
		glfwSwapBuffers(window);
	}

	public static boolean requestClose()
	{
		return glfwWindowShouldClose(DisplayManager.window) == GL11.GL_TRUE;
	}	

}
