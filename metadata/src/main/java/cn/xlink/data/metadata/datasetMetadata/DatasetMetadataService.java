package cn.xlink.data.metadata.datasetMetadata;

import com.strategicgains.repoexpress.domain.Identifier;
import com.strategicgains.syntaxe.ValidationEngine;
import org.restexpress.common.query.*;

import java.util.List;

/**
 * This is the 'service' or 'business logic' layer, where business logic, syntactic and semantic
 * domain validation occurs, along with calls to the persistence layer.
 */
public class DatasetMetadataService
{
	private DatasetMetadataRepository datasetMetadata;

	public DatasetMetadataService(DatasetMetadataRepository datasetMetadataRepository)
	{
		super();
		this.datasetMetadata = datasetMetadataRepository;
	}

	public DatasetMetadata create(DatasetMetadata entity)
	{
		entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);
		List<DatasetMetadata> list = datasetMetadata.find(entity.getUniqueFilter());

		if (list.size() > 0) {
			entity.setId(list.get(0).getId());
			datasetMetadata.update(entity);
			return datasetMetadata.read(entity.getId());
		} else {
			return datasetMetadata.create(entity);
		}
	}

	public DatasetMetadata read(Identifier id)
    {
		DatasetMetadata entity = datasetMetadata.read(id);
		if (entity.getDeleted()) return null;
		return entity;
    }

	public void update(DatasetMetadata entity)
    {
    	entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);
		datasetMetadata.update(entity);
    }

	public void delete(Identifier id)
    {
		DatasetMetadata entity = this.read(id);
		entity.setDeleted(true);
		datasetMetadata.update(entity);
    }

	public List<DatasetMetadata> readAll(QueryFilter filter, QueryRange range, QueryOrder order)
    {
		return datasetMetadata.readAll(filter, range, order);
    }

	public long count(QueryFilter filter)
    {
		return datasetMetadata.count(filter);
    }

	public DatasetMetadata findByEntity(DatasetMetadata entity)
	{
		List<DatasetMetadata> list = datasetMetadata.find(entity.getUniqueFilter());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<DatasetMetadata> findAll()
	{
	    return datasetMetadata.find(new QueryFilter());
	}
}
