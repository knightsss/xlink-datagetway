package cn.xlink.data.metadata.pageMetadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import com.strategicgains.syntaxe.annotation.StringValidation;
import org.mongodb.morphia.annotations.Property;
import org.restexpress.plugin.hyperexpress.Linkable;

import java.util.Map;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */
//@TokenBindings({
//	@BindToken(value=Constants.Url.SAMPLE_ID, field="id")
//})
public class PageMetadataLayersEntity
extends AbstractMongodbEntity
implements Linkable
{
	/*图表ID*/
	@StringValidation(name="Layers id", required=true)
	@JsonProperty("id")
	@Property("id")
	private String layerId;

	/*图表图层*/
	@StringValidation(name="Layers level")
	private int level;

	/*图标样式 JSON*/
	@StringValidation(name="Layers style")
	private Map<String, Object> style;

	public String getLayerId() {
		return layerId;
	}

	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Map<String, Object> getStyle() {
		return style;
	}

	public void setStyle(Map<String, Object> style) {
		this.style = style;
	}
}
