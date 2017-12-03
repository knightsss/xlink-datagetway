package cn.xlink.data.metadata.pageMetadata;

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
public class PageMetadataService
{
	private PageMetadataRepository pageMetadata;

	public PageMetadataService(PageMetadataRepository pageMetadataRepository)
	{
		super();
		this.pageMetadata = pageMetadataRepository;
	}

	public PageMetadata create(PageMetadata entity)
	{
		entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);

		return pageMetadata.create(entity);

	}

	public PageMetadata read(Identifier id)
    {
		return pageMetadata.read(id);
    }

	public void update(PageMetadata entity)
    {
    	entity.setDeleted(false);
		ValidationEngine.validateAndThrow(entity);
		pageMetadata.update(entity);
    }

	public void delete(Identifier id)
    {
    	pageMetadata.delete(id);
//		DatasetFigureMetadata entity = this.read(id);
//		entity.setDeleted(true);
//		pageMetadata.update(entity);
    }

	public List<PageMetadata> readAll(QueryFilter filter, QueryRange range, QueryOrder order)
    {
		return pageMetadata.readAll(filter, range, order);
    }

	public long count(QueryFilter filter)
    {
		return pageMetadata.count(filter);
    }

    public PageMetadata findByEntity(PageMetadata entity)
	{
		List<PageMetadata> list = pageMetadata.find(entity.getUniqueFilter());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
