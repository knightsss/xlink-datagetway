package cn.xlink.data.metadata.druidMetadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.IntegerValidation;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.Property;
import org.restexpress.plugin.hyperexpress.Linkable;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */

//@TokenBindings({
//	@BindToken(value= Constants.Url.SAMPLE_ID, field="id")
//})
public class DruidMetadataConfigEntity
extends AbstractMongodbEntity
implements Linkable
{
	private static final String SEGMENT_GRANULARITY_MINUTE = "minute";
	private static final String SEGMENT_GRANULARITY_HOUR = "hour";
	private static final String SEGMENT_GRANULARITY_DAY = "day";

	@StringValidation(name="segment granularity, Druid calc")
	@Property("segment_granularity")
	@JsonProperty("segment_granularity")
	private String segmentGranularity = SEGMENT_GRANULARITY_HOUR;

	@IntegerValidation(name="calc partition num")
	private int partitions = 1;

	@IntegerValidation(name="calc replicant num")
	private int replicants = 1;

	public String getSegmentGranularity() {
		return segmentGranularity;
	}

	public void setSegmentGranularity(String segmentGranularity) {
		this.segmentGranularity = segmentGranularity;
	}

	public int getPartitions() {
		return partitions;
	}

	public void setPartitions(int partitions) {
		this.partitions = partitions;
	}

	public int getReplicants() {
		return replicants;
	}

	public void setReplicants(int replicants) {
		this.replicants = replicants;
	}
}
