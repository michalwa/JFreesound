package pl.michalwa.jfreesound.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple HTTP request data wrapper.
 *
 * <p> Stores information about an HTTP request to be made.
 */
public abstract class HttpRequest
{
	private final List<String> path = new ArrayList<>();
	private final Map<String, String> urlParams = new HashMap<>();
	private final Map<String, String> headers = new HashMap<>();
	
	/**
	 * Returns the method of this request
	 */
	public abstract HttpMethod method();
	
	/**
	 * Computes and returns the url of this request
	 */
	public String url()
	{
		// Remove slashes from beginning and end of path elements
		List<String> trimmedPath = path.stream()
			.map(p -> p.replaceAll("^/+", "").replaceAll("/+$", ""))
			.collect(Collectors.toList());
		
		return String.join("/", trimmedPath) + "?" + HttpUtils.encodeParams(urlParams, "UTF-8");
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " (" + method().name() + ", " + url() + ")";
	}
	
	/**
	 * Adds the given string to the path of this request
	 *
	 * @param path the string to append to the path
	 */
	public HttpRequest path(String path)
	{
		this.path.add(path);
		return this;
	}
	
	/**
	 * Sets the given URL parameter for this request.
	 */
	public HttpRequest urlParam(String key, String value)
	{
		urlParams.put(key, value);
		return this;
	}
	
	/**
	 * Returns a copy of the map of url parameters.
	 */
	public Map<String, String> urlParams()
	{
		return new HashMap<>(urlParams);
	}
	
	/**
	 * Sets the specified header to the given value.
	 */
	public HttpRequest header(String name, String value)
	{
		headers.put(name, value);
		return this;
	}
	
	/**
	 * Removes the specfied header from this request.
	 */
	public HttpRequest removeHeader(String name)
	{
		headers.remove(name);
		return this;
	}
	
	/**
	 * Returns a copy of the map of headers for this request.
	 */
	public Map<String, String> headers()
	{
		return new HashMap<>(headers);
	}
}
