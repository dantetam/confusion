package render;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class DisplayManager {

	private static final int width = 1500, height = 900;
	public static GLFWErrorCallback errorCallback; 
	public static long window; 

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
