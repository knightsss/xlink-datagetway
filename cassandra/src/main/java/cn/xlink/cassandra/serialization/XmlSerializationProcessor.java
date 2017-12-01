package cn.xlink.cassandra.serialization;

import org.restexpress.serialization.xml.XstreamXmlProcessor;

public class XmlSerializationProcessor
extends XstreamXmlProcessor
{
	public XmlSerializationProcessor()
    {
	    super();
		registerConverter(new XstreamUuidConverter());
    }
}
