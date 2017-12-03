package cn.xlink.data.metadata.datasetMetadata;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class DatasetMetadataRepository
extends MongodbEntityRepository<DatasetMetadata>
{
	@SuppressWarnings("unchecked")
    public DatasetMetadataRepository(MongoClient mongo, String dbName)
	{
		super(mongo, dbName, DatasetMetadata.class);
	}
}
