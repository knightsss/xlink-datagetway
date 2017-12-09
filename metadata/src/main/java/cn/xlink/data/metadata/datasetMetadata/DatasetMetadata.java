package cn.xlink.data.metadata.datasetMetadata;

import cn.xlink.data.metadata.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.strategicgains.hyperexpress.annotation.BindToken;
import com.strategicgains.hyperexpress.annotation.TokenBindings;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.restexpress.common.query.FilterComponent;
import org.restexpress.common.query.FilterOperator;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.plugin.hyperexpress.Linkable;

import java.util.*;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */
@Entity("dataset_metadata")
@TokenBindings({
	@BindToken(value= Constants.Url.SAMPLE_ID, field="id")
})
@JsonPOJOBuilder
@JsonIgnoreProperties(value = { "objectId", "uniqueFilter", "id", "deleted" })
public class DatasetMetadata
extends AbstractMongodbEntity
implements Linkable
{
	@StringValidation(name="Dataset Name", required=true)
	private String name;

	@StringValidation(name="Dataset Desc")
	private String description;

	private String engine;

	private List<DatasetMetadataFieldEntity> times;

	private List<DatasetMetadataFieldEntity> fields;

	private List<DatasetMetadataSourceEntity> sources;

	private List<DatasetMetadataJoinEntity> joins;

	private List<DatasetMetadataFieldEntity> filters;

	// 动态数据集属性
	@JsonProperty("jdbc_id")
	@Property("jdbc_id")
	private String jdbcId;

	@JsonProperty("is_deleted")
	@Property("is_deleted")
	private Boolean isDeleted;

	@Property("created_at")
	@JsonProperty("created_at")
	private Date createdAt;

	@Property("updated_at")
	@JsonProperty("updated_at")
	private Date updatedAt;

	public DatasetMetadata(){}

	public DatasetMetadata(String name) {
		this.name = name;
	}

	public QueryFilter getUniqueFilter(){
		List<FilterComponent> list = new ArrayList<FilterComponent>();
		list.add(new FilterComponent("name", FilterOperator.EQUALS, name));
		return new QueryFilter(list);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public List<DatasetMetadataFieldEntity> getTimes() {
		return times;
	}

	public void setTimes(List<DatasetMetadataFieldEntity> times) {
		this.times = times;
	}

	public List<DatasetMetadataFieldEntity> getFields() {
		return fields;
	}

	public void setFields(List<DatasetMetadataFieldEntity> fields) {
		this.fields = fields;
	}

	public List<DatasetMetadataSourceEntity> getSources() {
		return sources;
	}

	public void setSources(List<DatasetMetadataSourceEntity> sources) {
		this.sources = sources;
	}

	public List<DatasetMetadataJoinEntity> getJoins() {
		return joins;
	}

	public void setJoins(List<DatasetMetadataJoinEntity> joins) {
		this.joins = joins;
	}

	public List<DatasetMetadataFieldEntity> getFilters() {
		return filters;
	}

	public void setFilters(List<DatasetMetadataFieldEntity> filters) {
		this.filters = filters;
	}

	public String getJdbcId() {
		return jdbcId;
	}

	public void setJdbcId(String jdbcId) {
		this.jdbcId = jdbcId;
	}

	public Boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean deleted) {
		isDeleted = deleted;
	}

	public Date getCreatedAt() {
		return this.createdAt == null?null:new Date(this.createdAt.getTime());
	}

	public Date getUpdatedAt() {
		return this.updatedAt == null?null:new Date(this.updatedAt.getTime());
	}

	public void setCreatedAt(Date date) {
		this.createdAt = date == null?new Date():new Date(date.getTime());
	}

	public void setUpdatedAt(Date date) {
		this.updatedAt = date == null?new Date():new Date(date.getTime());
	}

	@Override
	public String toString() {
		return "DatasetMetadata{" +
				"name='" + name + '\'' +
				", fields=" + fields +
				", sources=" + sources +
				", joins=" + joins +
				", isDeleted=" + isDeleted +
				'}';
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("description", description);
		return map;
	}

}
