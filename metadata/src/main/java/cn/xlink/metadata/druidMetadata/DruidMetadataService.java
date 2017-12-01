package cn.xlink.metadata.druidMetadata;

import com.strategicgains.repoexpress.domain.Identifier;
import com.strategicgains.syntaxe.ValidationEngine;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the 'service' or 'business logic' layer, where business logic, syntactic and semantic
 * domain validation occurs, along with calls to the persistence layer.
 */
public class DruidMetadataService
{
	private DruidMetadataRepository queryMetadata;
	
	public DruidMetadataService(DruidMetadataRepository druidMetadataRepository)
	{
		super();
		this.queryMetadata = druidMetadataRepository;
	}

	public DruidMetadata create(DruidMetadata entity)
	{
		entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);
		List<DruidMetadata> list = queryMetadata.find(entity.getUniqueFilter());

		if (list.size() > 0) {
			entity.setId(list.get(0).getId());
			queryMetadata.update(entity);
			return queryMetadata.read(entity.getId());
		} else {
			return queryMetadata.create(entity);
		}
	}

	public DruidMetadata read(Identifier id)
    {
		return queryMetadata.read(id);
    }

	public void update(DruidMetadata entity)
    {
    	entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);
		queryMetadata.update(entity);
    }

	public void delete(Identifier id)
    {
		DruidMetadata entity = this.read(id);
		entity.setDeleted(true);
		queryMetadata.update(entity);
    }

	public List<DruidMetadata> readAll(QueryFilter filter, QueryRange range, QueryOrder order)
    {
		return queryMetadata.readAll(filter, range, order);
    }

	public long count(QueryFilter filter)
    {
		return queryMetadata.count(filter);
    }

    // get name type map
	public Map<String, String> getTypeMap(DruidMetadata entity) {
		List<DruidMetadata> list = queryMetadata.find(entity.getUniqueFilter());

		if (list.size() > 0) {
			Map<String, String> typeMap = new HashMap<>();
			for (DruidMetadataColumnEntity column: list.get(0).getColumns()) {
				if (column.isDimension()) {
					typeMap.put(column.getName(), column.getType()==null?"String":column.getType());
				}
			}
			return typeMap;
		}
		return null;
	}
}
