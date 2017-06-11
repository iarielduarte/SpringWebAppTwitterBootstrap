package ar.com.api.ibera.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import ar.com.api.ibera.models.Product;
import ar.com.api.ibera.models.QProduct;

@Repository("queryDSLExampleRepository")
public class QueryDSLExampleRepository {

	@PersistenceContext
	private EntityManager em;
	
	private QProduct qProduct = QProduct.product;
	
	public Product find(boolean exist){
		JPAQuery<Product> query = new JPAQuery<Product>(em);
		
		BooleanBuilder predicateBuilder = new BooleanBuilder(qProduct.description.endsWith("Litros"));
		
		if(exist){
			Predicate predicate2 = qProduct.id.eq(3);
			predicateBuilder.and(predicate2);
		}else{
			Predicate predicate3 = qProduct.name.endsWith("2L");
			predicateBuilder.or(predicate3);
		}
		
		
		return query.select(qProduct).from(qProduct).where(predicateBuilder).fetchOne();
		
		//List<Product> products = query.select(qProduct).from(qProduct).where(qProduct.price.between(10, 100)).fetch();
	}
}
