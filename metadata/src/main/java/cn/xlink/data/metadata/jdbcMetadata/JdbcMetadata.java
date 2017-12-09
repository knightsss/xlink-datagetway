package cn.xlink.data.metadata.jdbcMetadata;

import cn.xlink.data.metadata.Constants;
import cn.xlink.data.metadata.pageMetadata.PageMetadataComponentEntity;
import cn.xlink.data.metadata.pageMetadata.PageMetadataLayersEntity;
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
@Entity("jdbc_metadata")
@TokenBindings({
	@BindToken(value= Constants.Url.PAGE_ID, field="id")
})
@JsonPOJOBuilder
@JsonIgnoreProperties(value = { "objectId", "uniqueFilter", "id", "deleted" })
/*@JsonIgnoreProperties(value = { "objectId", "uniqueFilter", "deleted" })*/
public class JdbcMetadata
extends AbstractMongodbEntity
implements Linkable
{

	/*企业ID*/
/*	@StringValidation(name="corpId")*/
	@JsonProperty("corp_id")
	@Property("corp_id")
	private String corpId;

	/*JDBC名称 配置名称*/
	@StringValidation(name="Jdbc Name")
	private String name;

	/*JDBC驱动名称*/
	@StringValidation(name="JDBC Driver Name")
	private String drivername;

	/*用户名称*/
	private String username;

	/*数据库连接密码*/
	private String password;

	/*数据库域名地址或ip地址*/
	private String host;

	/*数据库连接端口*/
	private String port;

	/*数据库名*/
	private String database;

	/*对象，连接属性*/
	private String query;

	/*表*/
//	private ArrayList<String> tables;
	private List<JdbcMetadataTableEntity> tables;

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

/*	@NotSaved
	DateFormat ISO8601UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");*/

	public JdbcMetadata(){}

	public JdbcMetadata(String name) {
		this.name = name;
	}

/*	public QueryFilter getUniqueFilter(){
		List<FilterComponent> list = new ArrayList<FilterComponent>();
		list.add(new FilterComponent("corp_id", FilterOperator.EQUALS, corpId));
		System.out.println("list size is " + list.size());
		return new QueryFilter(list);
	}*/

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<JdbcMetadataTableEntity> getTables() {
		return tables;
	}

	public void setTables(List<JdbcMetadataTableEntity> tables) {
		this.tables = tables;
	}

/*	public DateFormat getISO8601UTC() {
		return ISO8601UTC;
	}

	public void setISO8601UTC(DateFormat ISO8601UTC) {
		this.ISO8601UTC = ISO8601UTC;
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	/*public QueryFilter getUniqueFilter(){
		List<FilterComponent> list = new ArrayList<FilterComponent>();
		list.add(new FilterComponent("corp_id", FilterOperator.EQUALS, corpId));
		System.out.println("list size is " + list.size());
		return new QueryFilter(list);
	}*/

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("id", getId().toString());
		/*map.put("update_time", ISO8601UTC.format(getUpdatedAt()));*/
		return map;
	}

}
