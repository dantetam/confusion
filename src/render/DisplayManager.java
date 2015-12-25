package render;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.Display;

import tests.MainGameLoop;

public class DisplayManager {

	private static final int width = 1500, height = 900;
	private static GLFWErrorCallback errorCallback; 
	private static GLFWFramebufferSizeCallback framebufferSizeCallback; 
	public static long window;
	public boolean fullscreen = false;

	public static void createDisplay()
	{
		glfwSetErrorCallback(errorCallback = Callbacks.errorCallbackPrint(System.err));

		glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); 
		window = glfwCreateWindow(1600, 900, "Pong - LWJGL3", 0, 0);
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
		
		//GL11.glViewport(0, 0, width, height);
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
