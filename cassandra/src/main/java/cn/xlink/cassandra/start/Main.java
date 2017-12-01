package cn.xlink.cassandra.start;

import org.restexpress.util.Environment;

import cn.xlink.cassandra.start.config.Configuration;
import cn.xlink.cassandra.start.server.Server;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		Configuration config = Environment.load(args, Configuration.class);
		Server server = new Server(config);
		server.start().awaitShutdown();
	}
}
