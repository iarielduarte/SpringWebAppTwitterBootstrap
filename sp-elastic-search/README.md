# Spring Boot + Spring Data + Elasticsearch 


### Setup
--------------------------------------

`Note :` SpringBoot 1.5.8.RELEASE and Spring Data Elasticsearch 2.1.8.RELEASE supports only ElasticSearch 2.4.6. They don’t support the latest version of ElasticSearch 5.x version. - Read this [ Spring Data Elasticsearch Spring Boot version matrix](https://github.com/spring-projects/spring-data-elasticsearch/wiki/Spring-Data-Elasticsearch---Spring-Boot---version-matrix)

1. Download [Elasticsearch 2.4.0](https://www.elastic.co/downloads/past-releases/elasticsearch-2-4-0).
2. Set ELASTICSEARCH_HOME variable e.g C:\elasticsearch-2.4.0
3. Configure ElasticSearch Cluster
	 Open ${ELASTICSEARCH_HOME}\config\elasticsearch.yml and add the following property
 	`cluster.name: os-cluster`
4. Start Elasticsearch Instance
	`$ cd C:\elasticsearch-2.4.0`
	`$ bin\elasticsearch`

 ### Project uses
--------------------------------------

- [Spring Boot](https://spring.io/guides/gs/spring-boot/) - 1.5.8.RELEASE
- [Spring Data REST](https://spring.io/guides/gs/accessing-data-rest/) - 2.6.8.RELEASE
- [Spring Data Mongo](https://spring.io/guides/gs/accessing-mongodb-data-rest/) - 1.10.8.RELEASE
- [Spring Data Elasticsearch](https://github.com/spring-projects/spring-data-elasticsearch/blob/master/README.md) - 2.1.8.RELEASE
- [Lombok](http://projectlombok.org) to reduce the amount of boilerplate code to be written for Java entities and value objects.