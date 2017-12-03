package cn.xlink.data.metadata.pageMetadata;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class PageMetadataRepository
extends MongodbEntityRepository<PageMetadata>
{
	@SuppressWarnings("unchecked")
    public PageMetadataRepository(MongoClient mongo, String dbName)
	{
		super(mongo, dbName, PageMetadata.class);
	}
}
