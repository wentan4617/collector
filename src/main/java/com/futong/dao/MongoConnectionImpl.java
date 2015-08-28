package com.futong.dao;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * MongoDB connection singleton
 */
public class MongoConnectionImpl implements MongoConnection {
    private static final Logger LOG = LoggerFactory.getLogger(MongoConnectionImpl.class);
        
    private final String hostIp;
    private final String dbName;
    private MongoClient m = null;
    private MongoDatabase db = null;


    public MongoConnectionImpl(String hostIp,String dataBase) {
        this.hostIp = checkNotNull(hostIp);
        this.dbName = checkNotNull(dataBase);
    }

    /**
     * Connect the instance.
     */
    @Override
    public synchronized Mongo connect() {
        if (m == null) {
            if (isNullOrEmpty(dbName)) {
                LOG.error("The MongoDB database name must not be null or empty (mongodb_uri was: {})", hostIp);
                throw new RuntimeException("MongoDB database name is missing.");
            }

            try {
                m = new MongoClient(hostIp);
                db = m.getDatabase(dbName);
            } catch (Exception e) {
                throw new RuntimeException("Cannot resolve host name for MongoDB", e);
            }
        }


        return m;
    }

    /**
     * Returns the raw database object.
     *
     * @return database
     */
    @Override
    public MongoDatabase getDatabase() {
        return db;
    }
}
