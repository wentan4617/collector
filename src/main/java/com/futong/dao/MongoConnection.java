package com.futong.dao;

import com.mongodb.Mongo;
import com.mongodb.client.MongoDatabase;

public interface MongoConnection {
    Mongo connect();

    MongoDatabase getDatabase();
}
