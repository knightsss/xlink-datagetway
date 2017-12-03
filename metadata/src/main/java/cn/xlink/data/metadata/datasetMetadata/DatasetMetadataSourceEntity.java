package cn.xlink.data.metadata.datasetMetadata;

import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.restexpress.plugin.hyperexpress.Linkable;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */

//@TokenBindings({
//	@BindToken(value= Constants.Url.SAMPLE_ID, field="id")
//})
public class DatasetMetadataSourceEntity
extends AbstractMongodbEntity
implements Linkable
{
	@StringValidation(name="source name", required=true)
	private String name;

	@StringValidation(name="source sql name")
	private String source;

	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "DatasetMetadataSourceEntity{" +
				"name='" + name + '\'' +
				", source='" + source + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
