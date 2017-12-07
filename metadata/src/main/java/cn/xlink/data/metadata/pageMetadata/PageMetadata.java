package cn.xlink.data.metadata.pageMetadata;

import cn.xlink.data.metadata.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.strategicgains.hyperexpress.annotation.BindToken;
import com.strategicgains.hyperexpress.annotation.TokenBindings;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.Property;
import org.restexpress.common.query.FilterComponent;
import org.restexpress.common.query.FilterOperator;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.plugin.hyperexpress.Linkable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */

/*数据库中集合名称*/
@Entity("page_metadata")
@TokenBindings({
	@BindToken(value= Constants.Url.PAGE_ID, field="id")
})
@JsonPOJOBuilder
@JsonIgnoreProperties(value = { "objectId", "uniqueFilter", "id", "deleted" })
/*@JsonIgnoreProperties(value = { "objectId", "uniqueFilter", "deleted" })*/
public class PageMetadata
extends AbstractMongodbEntity
implements Linkable
{
	/*战图名称 必须字段*/
	@StringValidation(name="Page Name")
	private String name;

	/*战图描述*/
	@StringValidation(name="Page Desc")
	private String description;

	/*缩略图*/
	private String thumbnail;

	/*企业ID*/
/*	@StringValidation(name="corpId")*/
	@JsonProperty("corp_id")
	@Property("corp_id")
	private String corpId;

	/*分享密码*/
	private String password;

	/*站图样式*/
	private Map<String, Object> style;

	/*图层样式列表*/
	private List<PageMetadataLayersEntity> layers;

	/*components 组件*/
	private List<PageMetadataComponentEntity>  components;

	/*战图扩展配置*/
	private Map<String, Object> options;

	@JsonProperty("is_deleted")
	@Property("is_deleted")
	private Boolean isDeleted;

	@Property("created_at")
	@JsonProperty("created_at")
	private Date createdAt;

	@Property("updated_at")
	@JsonProperty("updated_at")
	private Date updatedAt;

	@NotSaved
	DateFormat ISO8601UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public PageMetadata(){}

	public PageMetadata(String name) {
		this.name = name;
	}

	public QueryFilter getUniqueFilter(){
		List<FilterComponent> list = new ArrayList<FilterComponent>();
		list.add(new FilterComponent("corp_id", FilterOperator.EQUALS, corpId));
		System.out.println("list size is " + list.size());
		return new QueryFilter(list);
	}


/*	@Override
	public String getId() {
		return getId().toString();
	}

	public void setId(String id) {
		this.id = id;
	}*/

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

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public Map<String, Object> getStyle() {
		return style;
	}

	public void setStyle(Map<String, Object> style) {
		this.style = style;
	}

	public List<PageMetadataLayersEntity> getLayers() {
		return layers;
	}

	public void setLayers(List<PageMetadataLayersEntity> layers) {
		this.layers = layers;
	}

	public List<PageMetadataComponentEntity> getComponents() {
		return components;
	}

	public void setComponents(List<PageMetadataComponentEntity> components) {
		this.components = components;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
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

/*	@Override
	public String toString() {
		return "DatasetFigureMetadata{" +
				"name='" + name + '\'' +
				", fields=" + fields +
				", sources=" + sources +
				", joins=" + joins +
				", isDeleted=" + isDeleted +
				'}';
	}*/

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("description", description);
		map.put("thumbnail", thumbnail);
		map.put("id", getId().toString());
		map.put("update_time", ISO8601UTC.format(getUpdatedAt()));
		return map;
	}

}
