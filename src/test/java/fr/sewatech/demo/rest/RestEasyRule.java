package fr.sewatech.demo.rest;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.resteasy.plugins.server.sun.http.HttpContextBuilder;
import org.junit.rules.ExternalResource;

import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class RestEasyRule extends ExternalResource {

	private final Class<?>[] entityClasses;
	private final int port;

	private HttpServer server;

	private RestEasyRule(int port, Class<?>... entityClasses) {
		this.entityClasses = entityClasses;
		this.port = port;
	}

	@Override
	protected void before() throws Throwable {
		server = startServer(port, entityClasses);
	}
	
	@Override
	protected void after() {
		server.stop(0);
		server = null;
	}
	
	
	private HttpServer startServer(int port, Class<?>... entityClasses)
			throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		HttpContextBuilder contextBuilder = new HttpContextBuilder();
		contextBuilder.getDeployment()
					.getActualResourceClasses()
					.addAll( asList(entityClasses) );
		contextBuilder.bind(server);
		server.start();
		return server;
	}
	
	public static class Builder {
		private final List<Class<?>> entityClasses = new ArrayList<Class<?>>();
		private int port;

		public RestEasyRule build() {
			return new RestEasyRule(port, entityClasses.toArray(new Class<?>[entityClasses.size()]));
			
		}
		public Builder withEntityClass(Class<?> clazz) {
			entityClasses.add(clazz);
			return this;
		}
		public Builder withEntityClasses(Class<?>... clazzes) {
			entityClasses.addAll(Arrays.asList(clazzes));
			return this;
		}	
		public Builder withPort(int port) {
			this.port = port;
			return this;
		}		
	}

}