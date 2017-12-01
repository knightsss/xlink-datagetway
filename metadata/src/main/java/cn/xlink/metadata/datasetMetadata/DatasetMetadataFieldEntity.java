package cn.xlink.metadata.datasetMetadata;

import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.restexpress.plugin.hyperexpress.Linkable;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */
//@TokenBindings({
//	@BindToken(value=Constants.Url.SAMPLE_ID, field="id")
//})
public class DatasetMetadataFieldEntity
extends AbstractMongodbEntity
implements Linkable
{
	@StringValidation(name="Field name", required=true)
	private String name;

	@StringValidation(name="Field sql name")
	private String field;

	private int type;

	private String description;

	private String source;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
