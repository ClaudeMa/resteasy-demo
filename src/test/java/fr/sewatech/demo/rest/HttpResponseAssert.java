package fr.sewatech.demo.rest;

import java.util.Objects;

import org.apache.http.HttpResponse;
import org.assertj.core.api.AbstractAssert;

public class HttpResponseAssert extends
		AbstractAssert<HttpResponseAssert, HttpResponse> {

	public static HttpResponseAssert assertThat(HttpResponse actual) {
		return new HttpResponseAssert(actual);
	}

	protected HttpResponseAssert(HttpResponse actual) {
		super(actual, HttpResponseAssert.class);
	}

	public HttpResponseAssert hasStatusCode(int expectedStatusCode) {
		int actualStatusCode = actual.getStatusLine().getStatusCode();
		if ( actualStatusCode != expectedStatusCode ) {
			failWithMessage("Expected status code to be %s but was %s", expectedStatusCode, actualStatusCode);
		}
		return this;		
	}

	public HttpResponseAssert hasContentType(String expectedContentType) {
		String actualContentType = actual.getHeaders("Content-type")[0].getValue();
		if ( ! Objects.equals(actualContentType, expectedContentType) ) {
			failWithMessage("Expected content type to be %s but was %s", expectedContentType, actualContentType);
		}
		return this;		
	}
}
