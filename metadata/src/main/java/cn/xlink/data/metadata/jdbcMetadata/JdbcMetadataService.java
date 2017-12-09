package cn.xlink.data.metadata.jdbcMetadata;

//import cn.xlink.data.metadata.pageMetadata.PageMetadataRepository;
import com.strategicgains.repoexpress.domain.Identifier;
import com.strategicgains.syntaxe.ValidationEngine;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;

import java.util.List;

/**
 * This is the 'service' or 'business logic' layer, where business logic, syntactic and semantic
 * domain validation occurs, along with calls to the persistence layer.
 */
public class JdbcMetadataService
{
	private JdbcMetadataRepository jdbcMetadata;

	public JdbcMetadataService(JdbcMetadataRepository jdbcMetadataRepository)
	{
		super();
		this.jdbcMetadata = jdbcMetadataRepository;
	}

	public JdbcMetadata create(JdbcMetadata entity)
	{
		entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);

		return jdbcMetadata.create(entity);

	}

	public JdbcMetadata read(Identifier id)
    {
		return jdbcMetadata.read(id);
    }

	public void update(JdbcMetadata entity)
    {
    	entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);
		jdbcMetadata.update(entity);
    }

	public void delete(Identifier id)
    {
    	jdbcMetadata.delete(id);
//		DatasetFigureMetadata entity = this.read(id);
//		entity.setDeleted(true);
//		pageMetadata.update(entity);
    }

	public List<JdbcMetadata> readAll(QueryFilter filter, QueryRange range, QueryOrder order)
    {
		return jdbcMetadata.readAll(filter, range, order);
    }

	public long count(QueryFilter filter)
    {
		return jdbcMetadata.count(filter);
    }

    /*public JdbcMetadata findByEntity(JdbcMetadata entity)
	{
		List<JdbcMetadata> list = jdbcMetadata.find(entity.getUniqueFilter());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}*/
}
