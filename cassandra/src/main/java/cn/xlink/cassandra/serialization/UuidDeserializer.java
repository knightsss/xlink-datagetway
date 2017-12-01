package cn.xlink.cassandra.serialization;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.strategicgains.repoexpress.util.UuidConverter;

public class UuidDeserializer
extends JsonDeserializer<UUID>
{
	@Override
	public UUID deserialize(JsonParser json, DeserializationContext context)
	throws IOException
	{
		return UuidConverter.parse(json.getText());
	}
}
