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
	        singletons.add(new MainService());
	        singletons.add(new AuthService());
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
