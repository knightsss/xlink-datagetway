package cn.xlink.metadata.druidMetadata;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class DruidMetadataRepository
extends MongodbEntityRepository<DruidMetadata>
{
	@SuppressWarnings("unchecked")
    public DruidMetadataRepository(MongoClient mongo, String dbName)
	{
		super(mongo, dbName, DruidMetadata.class);
	}
}
