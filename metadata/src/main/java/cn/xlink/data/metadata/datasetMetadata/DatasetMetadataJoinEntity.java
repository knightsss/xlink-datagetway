package cn.xlink.data.metadata.datasetMetadata;

import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.IntegerValidation;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.restexpress.plugin.hyperexpress.Linkable;

import java.util.List;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */

//@TokenBindings({
//	@BindToken(value= Constants.Url.SAMPLE_ID, field="id")
//})
public class DatasetMetadataJoinEntity
extends AbstractMongodbEntity
implements Linkable
{

	@StringValidation(name="join left")
	private String left;

	@StringValidation(name="join right")
	private String right;

	private List<String> sources;

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
	}
}
