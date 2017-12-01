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
	private DruidMetadataRepository druidMetadata;
	
	public DruidMetadataService(DruidMetadataRepository druidMetadataRepository)
	{
		super();
		this.druidMetadata = druidMetadataRepository;
	}

	public DruidMetadata create(DruidMetadata entity)
	{
		entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);
		List<DruidMetadata> list = druidMetadata.find(entity.getUniqueFilter());

		if (list.size() > 0) {
			entity.setId(list.get(0).getId());
			druidMetadata.update(entity);
			return druidMetadata.read(entity.getId());
		} else {
			return druidMetadata.create(entity);
		}
	}

	public DruidMetadata read(Identifier id)
    {
		DruidMetadata entity = druidMetadata.read(id);
		if (entity.getDeleted()) return null;
		return entity;
    }

	public void update(DruidMetadata entity)
    {
    	entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);
		druidMetadata.update(entity);
    }

	public void delete(Identifier id)
    {
		DruidMetadata entity = this.read(id);
		entity.setDeleted(true);
		druidMetadata.update(entity);
    }

	public List<DruidMetadata> readAll(QueryFilter filter, QueryRange range, QueryOrder order)
    {
		return druidMetadata.readAll(filter, range, order);
    }

	public long count(QueryFilter filter)
    {
		return druidMetadata.count(filter);
    }

    // get name type map
	public Map<String, String> getTypeMap(DruidMetadata entity) {
		List<DruidMetadata> list = druidMetadata.find(entity.getUniqueFilter());

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
