package kz.edu.nu.cs.Services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/mainServices")
public class MainAppilication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();



	public MainAppilication() {
		System.out.println("pooooooooooooooooooooooooooooooooooort" );
		singletons.add(new AuthService());
		singletons.add(new EventService());
		System.out.println("pooooooooooooooooooooooooooooooooooort" );
		//int port = Integer.parseInt(System.getenv("PORT"));
		//System.out.println("pooooooooooooooooooooooooooooooooooort" + port);
		new ChatServer(10001).start();

	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	    }

}
