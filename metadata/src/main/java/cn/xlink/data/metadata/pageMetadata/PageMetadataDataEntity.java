package cn.xlink.data.metadata.pageMetadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.Property;
import org.restexpress.plugin.hyperexpress.Linkable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */
//@TokenBindings({
//	@BindToken(value=Constants.Url.SAMPLE_ID, field="id")
//})
public class PageMetadataDataEntity
extends AbstractMongodbEntity
implements Linkable
{
	/*数据集名称*/
	@StringValidation(name="dataset name", required=true)
	private String dataset;
	
	/*维度度量列表fields*/
	private List<String> fields;

	/*筛选对象filters*/
	private Map<String, Object> filters;

	/*维度度量排序列表*/
	private List<String> sorts;

	/*扩展属性*/
	private Map<String, Object> options;

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public List<String> getSorts() {
		return sorts;
	}

	public void setSorts(List<String> sorts) {
		this.sorts = sorts;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
}
