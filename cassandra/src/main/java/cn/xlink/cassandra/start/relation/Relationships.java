package cn.xlink.cassandra.start.relation;

import java.util.Map;

import org.restexpress.RestExpress;
import org.restexpress.common.exception.ConfigurationException;

public abstract class Relationships
{
    protected Relationships() {
        throw new IllegalStateException("Utility class");
    }

	private static Map<String, String> ROUTES;

	public static void define(RestExpress server)
	{
		ROUTES = server.getRouteUrlsByName();
		
	}

	@SuppressWarnings("unused")
	private static String href(String name)
	{
		String href = ROUTES.get(name);
		if (href == null) throw new ConfigurationException("Route name not found: " + name);
		return href;
	}

}
