package kz.edu.nu.cs.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/mainServices")
public class MainAppilication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	private Logger logger;
	private int port = 10001;


	public MainAppilication() {
		logger = LoggerFactory.getLogger(MainAppilication.class);
		System.out.println("pooooooooooooooooooooooooooooooooooort" );
		singletons.add(new AuthService());
		singletons.add(new EventService());
		System.out.println("pooooooooooooooooooooooooooooooooooort" );
		//int port = Integer.parseInt(System.getenv("PORT"));
		//System.out.println("pooooooooooooooooooooooooooooooooooort" + port);
		try {
			new ChatServer(port).start();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("MainApplication has started");

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
