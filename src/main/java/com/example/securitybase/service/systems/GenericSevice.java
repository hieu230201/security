package com.example.securitybase.service.systems;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;


public interface GenericSevice<T, ID extends Serializable> {

	List<T> findAll();

	List<T> findAllByActive(Boolean active);

	List<T> findAll(Sort sort);

	Page<T> findAll(Pageable pageable);

	List<T> findAllById(Iterable<ID> ids);
	
	<S extends T> S save(S entity);

	<S extends T> List<S> saveAll(Iterable<S> entities);

	/**
	 * Flushes all pending changes to the database.
	 */
	void flush();

	/**
	 * Saves an entity and flushes changes instantly.
	 *
	 * @param entity
	 * @return the saved entity
	 */
	<S extends T> S saveAndFlush(S entity);
	
	void deleteById(ID id);

	void delete(T entity);

	/**
	 * Deletes the given entities in a batch which means it will create a single
	 * {@link Query}. Assume that we will clear the
	 * {@link javax.persistence.EntityManager} after the call.
	 *
	 * @param entities
	 */
	void deleteInBatch(Iterable<T> entities);

	/**
	 * Deletes all entities in a batch call.
	 */
	void deleteAllInBatch();

	T findById(ID id);

	<S extends T> List<S> findAll(Example<S> example);

	<S extends T> List<S> findAll(Example<S> example, Sort sort);
}