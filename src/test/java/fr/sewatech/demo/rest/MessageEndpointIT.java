package fr.sewatech.demo.rest;

import static fr.sewatech.demo.rest.HttpResponseAssert.*;
import static java.util.Arrays.*;
import static org.apache.http.HttpStatus.*;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicHeader;
import org.junit.*;


public class MessageEndpointIT {

	private static final String APPLICATION_XML = "application/xml";
	private static final String APPLICATION_JSON = "application/json";

	private static final int PORT = 7000;
	
	@Rule
	public RestEasyRule server = new RestEasyRule.Builder()
										.withPort(PORT)
										.withEntityClass(MessageEndpoint.class)
										.build();
	
	@Test
	public void should_GET_message_return_json_content() throws IOException {
		assertThat( requestTo("/message") )
			.hasStatusCode(SC_OK)
			.hasContentType(APPLICATION_JSON);		
	}

	@Test
	public void should_GET_message_return_xml_content() throws IOException {
		assertThat( requestTo("/message", APPLICATION_XML) )
			.hasStatusCode(SC_OK)
			.hasContentType(APPLICATION_XML);		
	}

	@Test
	public void should_GET_message_array_return_json_content() throws IOException {
		assertThat( requestTo("/message/array") )
			.hasStatusCode(SC_OK)
			.hasContentType(APPLICATION_JSON);
	}

	@Test
	public void should_GET_message_array_return_xml_content() throws IOException {
		assertThat( requestTo("/message/array", APPLICATION_XML) )
			.hasStatusCode(SC_OK)
			.hasContentType(APPLICATION_XML);
	}

	@Test
	public void should_GET_message_list_return_json_content() throws IOException {
		assertThat( requestTo("/message/list") )
			.hasStatusCode(SC_OK)
			.hasContentType(APPLICATION_JSON);
	}

	@Test
	public void should_GET_message_list_return_xml_content() throws IOException {
		assertThat( requestTo("/message/list", APPLICATION_XML) )
			.hasStatusCode(SC_OK)
			.hasContentType(APPLICATION_XML);
	}

	@Test
	public void should_GET_xxx_return_404() throws IOException {
		assertThat( requestTo("/xxx") )
			.hasStatusCode(SC_NOT_FOUND);		
	}


	private HttpResponse requestTo(String path, String... acceptHeaders) throws IOException {
		return Request.Get("http://localhost:" + PORT + path)
					.setHeaders(
							stream( acceptHeaders )
								.map( x -> new BasicHeader("Accept", x) )
								.toArray( Header[]::new )
						)
					.execute()
					.returnResponse();
	}
}
