package com.si.api.repositories;

import java.io.Serializable;

/**
 * @author arielduarte
 * @since 30/10/2016
 */

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.si.api.models.Bookmark;

@RepositoryRestResource(collectionResourceRel = "bookmarks", path = "bookmarks")
public interface BookmarkRepository extends CrudRepository<Bookmark, Serializable> {

	/**
	 * URL /bookmarks/search/findByType?type=Blog
	 * @param type
	 * @return
	 */
	@Query(value = "{ type : ?0 }")
	List<Bookmark> findByType(@Param("type") String type);
	
	/**
	 * URL /bookmarks/search/findByAuthor?author=a
	 * @param author
	 * @return
	 */
	@Query("{ author : { $regex : ?0, $options: 'i'}}")
	List<Bookmark> findByAuthor(@Param("author") String author);
	
	/**
	 * URL /bookmarks/search/findByName?name=Routing
	 * @param name
	 * @return
	 */
	@Query("{ name : { $regex : ?0, $options: 'i'}}")
	List<Bookmark> findByName(@Param("name") String name);
	
	/**
	 * URL /bookmarks/search/findBySearches?name=Routing&type=Blog&author=&page=0&size=1
	 * @param name
	 * @param type
	 * @param author
	 * @param pageRequest
	 * @return
	 */
	@Query("{$or:[ { name : {$regex: ?0 }},{ type : {$regex: ?1 }},{ author : {$regex: ?2 }} ]}")
	Page<Bookmark> findByFilters (@Param("name") String name, @Param("type") String type, @Param("author") String author, Pageable pageRequest);
	
	/**
	 * URL /bookmarks/search/findBySearches?term=&page=1&size=10
	 * @param term
	 * @param pageRequest
	 * @return
	 */
	@Query(value = "{$or:[ { 'name' : {$regex: ?0, $options:'i' }},{ 'type' : {$regex: ?0, $options:'i' }},{ 'author' : {$regex: ?0, $options:'i' }} ]}")
	Page<Bookmark> findBySearches(@Param("term") String term, Pageable pageRequest);
	
}
