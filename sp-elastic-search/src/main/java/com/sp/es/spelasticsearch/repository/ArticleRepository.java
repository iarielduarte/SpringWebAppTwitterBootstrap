package com.sp.es.spelasticsearch.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sp.es.spelasticsearch.model.Article;
import com.sp.es.spelasticsearch.model.StatusEnum;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, String>{
	
	//public Page<Article> findAll(Pageable pageRequest);
	
	@Query("{cpreMediaType : {$in : ?1}, humanFiltered : { $elemMatch: { category: { $in : ?0 }, status : { $in : ?2}, routingOption :\"HumanMonitoring\" } } }")
    public Page<Article> findByCategoriesAndCpreMediaTypeAndStatuses(List<String> categories, List<Integer> cpreMediaIds, List<StatusEnum> statuses, Pageable pageRequest);
	
	@Query("{cpreMediaType : {$in : ?2}, humanFiltered : { $elemMatch: { category: { $in : ?0 }, dataGroupId: { $in : ?1 }, status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")
    public Page<Article> findByCategoriesAndDataGroupIdsAndCpreMediaTypeAndStatuses(List<String> categories, List<Integer> dgIds, List<Integer> cpreMediaIds, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{cpreMediaType : {$in : ?1}, humanFiltered : { $elemMatch: { dataGroupId : { $in : ?0 }, status : { $in : ?2}, routingOption : \"HumanMonitoring\" } } }")
    public Page<Article> findByDataGroupIdsAndCpreMediaTypeAndStatuses(List<Integer> dgIds, List<Integer> cpreMediaIds, List<StatusEnum> statuses, Pageable pageRequest);
	
	@Query("{cpreMediaType : {$in : ?0}, humanFiltered : { $elemMatch: { status : { $in : ?1}, routingOption : \"HumanMonitoring\" } } }")
    public Page<Article> findByCpreMediaTypeAndStatuses(List<Integer> cpreMediaIds, List<StatusEnum> statuses, Pageable pageRequest);
	
	@Query("{cpreMediaType : {$in : ?2}, vtKey: ?0, humanFiltered : { $elemMatch : {category: { $in : ?1 }, status : { $in : ?3}, routingOption : \"HumanMonitoring\" } } }")
    public Page<Article> findByVTKeyAndCategoriesAndCpreMediaTypeAndStatuses(String vtKey, String[] categories, Integer[] cpreMediaIds, StatusEnum[] statuses, Pageable pageRequest);

	@Query("{humanFiltered : { $elemMatch : { searchId: ?0, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findBySearchId(String searchId, Pageable pageRequest);
	
	@Query("{humanFiltered : { $elemMatch : { searchId: ?0, routingOption :\"HumanMonitoring\" } } }")
    public List<Article> findBySearchId(String searchId);
	
	@Query("{vtKey : ?0, humanFiltered : { $elemMatch : { workspaceId : ?1, routingOption : \"HumanMonitoring\" } } }")
	public Article findByVtKeyAndWorkspaceId(String vtKey, Integer workspaceId);
	
	@Query("{vtKey : ?0, \"humanFiltered.routingOption\" :\"HumanMonitoring\"}")
	public Article findByVtKey(String vtKey);
	
	@Query("{vtKey : ?0, humanFiltered : { $elemMatch : { status : { $in : [\"unread\",\"held\",\"rejected\",\"readyToPublish\"]}, routingOption : \"HumanMonitoring\" } } }")
    public List<Article> findByVtKeyAndStatus(String vtKey, Pageable pageRequest);
	
	@Query("{cpreMediaType : {$in : ?3}, publicationNameLowercase: {$in: ?2},  humanFiltered : { $elemMatch: { category: { $in : ?0 }, dataGroupId: { $in : ?1 }, status : { $in : ?4}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCategoriesAndDataGroupIdsAndPublicationNameAndCpreMediaTypeAndStatuses(
			List<String> categories, List<Integer> dataGroupIds, List<String> outlets, List<Integer> mediaIds, List<StatusEnum> statuses,
			Pageable pageRequest);
	
	@Query("{cpreMediaType : {$in : ?2}, publicationNameLowercase: {$in: ?1},  humanFiltered : { $elemMatch: { category: { $in : ?0 }, status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCategoriesAndPublicationNameAndCpreMediaTypeAndStatuses(List<String> categories, List<String> outlets,
			List<Integer> mediaIds, List<StatusEnum> statuses, Pageable pageRequest);
	
	@Query("{cpreMediaType : {$in : ?2}, publicationNameLowercase: {$in: ?1}, humanFiltered : { $elemMatch: { dataGroupId: { $in : ?0 }, status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByDataGroupIdAndPublicationNameAndCpreMediaTypeAndStatuses(List<Integer> dataGroupIds, List<String> outlets,
			List<Integer> mediaIds, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{cpreMediaType : {$in : ?1}, publicationNameLowercase: {$in: ?0},  humanFiltered : { $elemMatch: { status : { $in : ?2}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByPublicationNameAndCpreMediaTypeAndStatuses(List<String> outlets, List<Integer> mediaIds,
			List<StatusEnum> statuses, Pageable pageRequest);
	
	@Query("{humanFiltered : { $elemMatch: { status : { $in : ?0}, routingOption : \"HumanMonitoring\" } } }")
	public Page<Article> findByStatuses(List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{publicationNameLowercase: {$in: ?0},  humanFiltered : { $elemMatch: { status : { $in : ?1}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByPublicationNameAndStatuses(List<String> outlets, List<StatusEnum> statuses, Pageable pageRequest);
	
	@Query("{humanFiltered : { $elemMatch: { dataGroupId : { $in : ?0 }, status : { $in : ?1}, routingOption : \"HumanMonitoring\" } } }")
	public Page<Article> findByDataGroupIdsAndStatuses(List<Integer> dataGroupIds, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{publicationNameLowercase: {$in: ?1},  humanFiltered : { $elemMatch: { dataGroupId: { $in : ?0 }, status : { $in : ?2}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByDataGroupIdsAndPublicationNameAndStatuses(List<Integer> dataGroupIds, List<String> outlets,
			List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{humanFiltered : { $elemMatch: { category: { $in : ?0 }, status : { $in : ?1}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCategoriesAndStatuses(List<String> categories, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{publicationNameLowercase: {$in: ?1},  humanFiltered : { $elemMatch: { category: { $in : ?0 }, status : { $in : ?2}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCategoriesAndPublicationNameAndStatuses(List<String> categories, List<String> outlets,
			List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{humanFiltered : { $elemMatch: { category: { $in : ?0 }, dataGroupId: { $in : ?1 }, status : { $in : ?2}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCategoriesAndDataGroupIdsAndStatuses(List<String> categories, List<Integer> dataGroupIds,
			List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{publicationNameLowercase: {$in: ?2},  humanFiltered : { $elemMatch: { category: { $in : ?0 }, dataGroupId: { $in : ?1 }, status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCategoriesAndDataGroupIdsAndPublicationNameAndStatuses(List<String> categories,
			List<Integer> dataGroupIds, List<String> outlets, List<StatusEnum> statuses, Pageable pageRequest);
	
	@Query(value = "{humanFiltered : { $elemMatch : { publishedUser : ?0, status : \"published\" } }}", count = true)
	public long countPublishedArticlesByUser(String user);
	
	@Query(value = "{humanFiltered : { $elemMatch : { rejectedUser : ?0, status : \"rejected\" } }}", count = true)
	public long countRejectedArticlesByUser(String user);

	@Query("{bestCountry: {$in : ?0}, humanFiltered: { $elemMatch: { status : { $in : ?1}, routingOption : \"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndStatuses(List<Integer> countries, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, cpreMediaType : {$in : ?1}, humanFiltered : { $elemMatch: { status : { $in : ?2}, routingOption : \"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCpreMediaTypeAndStatuses(List<Integer> countries, List<Integer> mediaIds,
			List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, publicationNameLowercase: {$in: ?1},  humanFiltered : { $elemMatch: { status : { $in : ?2}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndPublicationNameAndStatuses(List<Integer> countries, List<String> outlets,
			List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, cpreMediaType : {$in : ?2}, publicationNameLowercase: {$in: ?1},  humanFiltered : { $elemMatch: { status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndPublicationNameAndCpreMediaTypeAndStatuses(List<Integer> countries,
			List<String> outlets, List<Integer> mediaIds, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, humanFiltered : { $elemMatch: { dataGroupId : { $in : ?1 }, status : { $in : ?2}, routingOption : \"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndDataGroupIdsAndStatuses(List<Integer> countries, List<Integer> dataGroupIds,
			List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, cpreMediaType : {$in : ?2}, humanFiltered : { $elemMatch: { dataGroupId : { $in : ?1 }, status : { $in : ?3}, routingOption : \"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndDataGroupIdsAndCpreMediaTypeAndStatuses(List<Integer> countries,
			List<Integer> dataGroupIds, List<Integer> mediaIds, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, publicationNameLowercase: {$in: ?2},  humanFiltered : { $elemMatch: { dataGroupId: { $in : ?1 }, status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")	
	public Page<Article> findByCountriesAndDataGroupIdsAndPublicationNameAndStatuses(List<Integer> countries,
			List<Integer> dataGroupIds, List<String> outlets, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, cpreMediaType : {$in : ?3}, publicationNameLowercase: {$in: ?2}, humanFiltered : { $elemMatch: { dataGroupId: { $in : ?1 }, status : { $in : ?4}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndDataGroupIdAndPublicationNameAndCpreMediaTypeAndStatuses(List<Integer> countries,
			List<Integer> dataGroupIds, List<String> outlets, List<Integer> mediaIds, List<StatusEnum> statuses,
			Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, humanFiltered : { $elemMatch: { category: { $in : ?1 }, status : { $in : ?2}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCategoriesAndStatuses(List<Integer> countries, List<String> categories,
			List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, cpreMediaType : {$in : ?2}, humanFiltered : { $elemMatch: { category: { $in : ?1 }, status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCategoriesAndCpreMediaTypeAndStatuses(List<Integer> countries,
			List<String> categories, List<Integer> mediaIds, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, publicationNameLowercase: {$in: ?2},  humanFiltered : { $elemMatch: { category: { $in : ?1 }, status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCategoriesAndPublicationNameAndStatuses(List<Integer> countries,
			List<String> categories, List<String> outlets, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, cpreMediaType : {$in : ?3}, publicationNameLowercase: {$in: ?2},  humanFiltered : { $elemMatch: { category: { $in : ?1 }, status : { $in : ?4}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCategoriesAndPublicationNameAndCpreMediaTypeAndStatuses(List<Integer> countries,
			List<String> categories, List<String> outlets, List<Integer> mediaIds, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, humanFiltered : { $elemMatch: { category: { $in : ?1 }, dataGroupId: { $in : ?2 }, status : { $in : ?3}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCategoriesAndDataGroupIdsAndStatuses(List<Integer> countries, List<String> categories,
			List<Integer> dataGroupIds, List<StatusEnum> statuses, Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, cpreMediaType : {$in : ?3}, humanFiltered : { $elemMatch: { category: { $in : ?1 }, dataGroupId: { $in : ?2 }, status : { $in : ?4}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCategoriesAndDataGroupIdsAndCpreMediaTypeAndStatuses(List<Integer> countries,
			List<String> categories, List<Integer> dataGroupIds, List<Integer> mediaIds, List<StatusEnum> statuses,
			Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, publicationNameLowercase: {$in: ?3},  humanFiltered : { $elemMatch: { category: { $in : ?1 }, dataGroupId: { $in : ?2 }, status : { $in : ?4}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCategoriesAndDataGroupIdsAndPublicationNameAndStatuses(List<Integer> countries,
			List<String> categories, List<Integer> dataGroupIds, List<String> outlets, List<StatusEnum> statuses,
			Pageable pageRequest);

	@Query("{bestCountry: {$in : ?0}, cpreMediaType : {$in : ?4}, publicationNameLowercase: {$in: ?3},  humanFiltered : { $elemMatch: { category: { $in : ?1 }, dataGroupId: { $in : ?2 }, status : { $in : ?5}, routingOption :\"HumanMonitoring\" } } }")
	public Page<Article> findByCountriesAndCategoriesAndDataGroupIdsAndPublicationNameAndCpreMediaTypeAndStatuses(
			List<Integer> countries, List<String> categories, List<Integer> dataGroupIds, List<String> outlets, List<Integer> mediaIds,
			List<StatusEnum> statuses, Pageable pageRequest);


}
