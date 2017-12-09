package cn.xlink.data.metadata.jdbcMetadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.Property;
import org.restexpress.plugin.hyperexpress.Linkable;

import java.util.Map;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */
//@TokenBindings({
//	@BindToken(value=Constants.Url.SAMPLE_ID, field="id")
//})
public class JdbcMetadataTableEntity
extends AbstractMongodbEntity
implements Linkable
{
	/*图表ID*/
/*	@StringValidation(name="Layers id", required=true)
	@JsonProperty("name")
	@Property("name")
	private String name;*/

	/*表名*/
	private String name;

	/*描述*/
	private String describe;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
