package cn.xlink.data.metadata.druidMetadata;

import cn.xlink.data.metadata.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.strategicgains.hyperexpress.annotation.BindToken;
import com.strategicgains.hyperexpress.annotation.TokenBindings;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.*;
import org.restexpress.common.query.FilterComponent;
import org.restexpress.common.query.FilterOperator;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.plugin.hyperexpress.Linkable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */
@Entity("druid_metadata")
@TokenBindings({
	@BindToken(value=Constants.Url.SAMPLE_ID, field="id")
})
@JsonPOJOBuilder
@JsonIgnoreProperties(value = { "objectId", "uniqueFilter", "id", "deleted"})
public class DruidMetadata
extends AbstractMongodbEntity
implements Linkable
{
	@StringValidation(name="Dataset Name", required=true)
	private String dataset;

	@StringValidation(name="Corporator Id", required=true)
	@Property("corp_id")
	@JsonProperty("corp_id")
	private String corpId;

	@StringValidation(name="Product Id")
	@Property("product_id")
	@JsonProperty("product_id")
	private String productId;

	private List<DruidMetadataColumnEntity> columns;

	private DruidMetadataConfigEntity config = new DruidMetadataConfigEntity();

	@Property("is_deleted")
	@JsonProperty("is_deleted")
	private Boolean isDeleted;

	@Property("created_at")
	@JsonProperty("created_at")
	private Date createdAt;

	@Property("updated_at")
	@JsonProperty("updated_at")
	private Date updatedAt;

	public QueryFilter getUniqueFilter(){
		List<FilterComponent> list = new ArrayList<FilterComponent>();
		list.add(new FilterComponent("dataset", FilterOperator.EQUALS, dataset));
		list.add(new FilterComponent("corp_id", FilterOperator.EQUALS, corpId));
		list.add(new FilterComponent("product_id", FilterOperator.EQUALS, productId));
		return new QueryFilter(list);
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<DruidMetadataColumnEntity> getColumns() {
		return columns;
	}

	public void setColumns(List<DruidMetadataColumnEntity> columns) {
		this.columns = columns;
	}

	public DruidMetadataConfigEntity getConfig() {
		return config;
	}

	public void setConfig(DruidMetadataConfigEntity config) {
		this.config = config;
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
		return "QueryMetadataEntity{" +
				"dataset='" + dataset + '\'' +
				", corp_id='" + corpId + '\'' +
				", product_id='" + productId + '\'' +
				", columns=" + columns +
				", config=" + config +
				", is_deleted=" + isDeleted +
				'}';
	}
}
