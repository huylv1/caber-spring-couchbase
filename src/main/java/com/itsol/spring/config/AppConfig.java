/**
 * 
 */
package com.itsol.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;

/**
 * @author huylv
 *
 */
@Configuration
@ComponentScan(basePackages={"com.itsol.couchbase.service"})
public class AppConfig {
	@Value("${couchbase.hostname}")
	private String hostname;
	
	@Value("${couchbase.bucket}")
	private String bucket;
	
	public @Bean Cluster cluster() {
		return CouchbaseCluster.create(hostname);
	}
	
	public @Bean Bucket bucket() {
		return cluster().openBucket(bucket);
	}
}
