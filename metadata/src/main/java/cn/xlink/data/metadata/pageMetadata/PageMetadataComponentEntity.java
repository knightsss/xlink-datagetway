package cn.xlink.data.metadata.pageMetadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.Property;
import org.restexpress.plugin.hyperexpress.Linkable;

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

public class PageMetadataComponentEntity
extends AbstractMongodbEntity
implements Linkable
{
	/*图表Id*/
	@StringValidation(name="Component id", required=true)
	@JsonProperty("id")
	@Property("id")
	private String componentId;

	/*图标名称*/
	@StringValidation(name="Chart name")
	private String name;

	/*类型空间*/
	@JsonProperty("type_space")
	@Property("type_space")
	private String typeSpace;

	/*图表类型*/
	private String type;

	/*图表描述 */
	private String description;

	/*图表布局设置*/
	/*private Map<String, Object> layout;*/

	/*数据查询*/
	private List<PageMetadataDataEntity> datas;

	/*图表内容配置*/
	/*private Map<String, Object> content;*/

	/*图表样式配置*/
	private Map<String, Object> style;

	/*图表扩展配置*/
	private Map<String, Object> options;

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

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

	public String getTypeSpace() {
		return typeSpace;
	}

	public void setTypeSpace(String typeSpace) {
		this.typeSpace = typeSpace;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

/*	public Map<String, Object> getLayout() {
		return layout;
	}

	public void setLayout(Map<String, Object> layout) {
		this.layout = layout;
	}*/

	public List<PageMetadataDataEntity> getDatas() {
		return datas;
	}

	public void setDatas(List<PageMetadataDataEntity> datas) {
		this.datas = datas;
	}

/*	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}*/

	public Map<String, Object> getStyle() {
		return style;
	}

	public void setStyle(Map<String, Object> style) {
		this.style = style;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
}
