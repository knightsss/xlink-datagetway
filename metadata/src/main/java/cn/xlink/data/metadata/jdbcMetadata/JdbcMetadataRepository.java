package cn.xlink.data.metadata.jdbcMetadata;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class JdbcMetadataRepository
extends MongodbEntityRepository<JdbcMetadata>
{
	@SuppressWarnings("unchecked")
    public JdbcMetadataRepository(MongoClient mongo, String dbName)
	{
		super(mongo, dbName, JdbcMetadata.class);
	}
}
