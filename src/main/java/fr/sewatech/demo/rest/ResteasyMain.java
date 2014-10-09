package fr.sewatech.demo.rest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

import org.jboss.resteasy.plugins.server.sun.http.HttpContextBuilder;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class ResteasyMain {
	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(7000), 1);
		HttpContextBuilder contextBuilder = new HttpContextBuilder();
		contextBuilder.getDeployment()
					.getActualResourceClasses()
					.addAll( Arrays.asList(MessageEndpoint.class) );
		HttpContext context = contextBuilder.bind(server);
		server.start();
	}
}
