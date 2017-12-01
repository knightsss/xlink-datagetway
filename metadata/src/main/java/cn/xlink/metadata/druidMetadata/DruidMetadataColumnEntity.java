package cn.xlink.metadata.druidMetadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.Property;
import org.restexpress.plugin.hyperexpress.Linkable;

import java.util.List;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */
//@TokenBindings({
//	@BindToken(value=Constants.Url.SAMPLE_ID, field="id")
//})
public class DruidMetadataColumnEntity
extends AbstractMongodbEntity
implements Linkable
{

	@StringValidation(name="Field Name", required=true)
	private String name;

	@StringValidation(name="Field type")
	private String type;

	@Property("is_dimension")
	@JsonProperty("is_dimension")
	private boolean isDimension;

	private List<String> metrics;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDimension() {
		return isDimension;
	}

	public void setDimension(boolean dimension) {
		isDimension = dimension;
	}

	public List<String> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<String> metrics) {
		this.metrics = metrics;
	}
}
