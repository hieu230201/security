package com.example.securitybase.service.systems;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.example.securitybase.repository.bases.BaseActiveFieldRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public abstract class AbstractGenericService<T, ID extends Serializable> {

	protected abstract JpaRepository<T, ID> getRepository();

	public List<T> findAllSortAscById(){
		try {
			return getRepository().findAll(Sort.by(Sort.Direction.ASC, "id"));
		} catch (Exception ex)
		{
			return findAll();
		}
	}
	public List<T> findAllSortDescById(){
		try {
			return getRepository().findAll(Sort.by(Sort.Direction.DESC, "id"));
		} catch (Exception ex)
		{
			return findAll();
		}
	}
	public List<T> findAll() {
		return getRepository().findAll();
	}
	@SuppressWarnings("unchecked")
	public List<T> findAllByActive(Boolean active){
		if(getRepository() instanceof BaseActiveFieldRepository<?>){

			return (List<T>)(((BaseActiveFieldRepository<?>) getRepository()).findAllByActive(active));
		}
		return findAll();
	}

	public List<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}
	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}
	public List<T> findAllById(Iterable<ID> ids) {
		return getRepository().findAllById(ids);
	}
	
	public <S extends T> S save(S entity) {
		return getRepository().save(entity);
	}

	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		return getRepository().saveAll(entities);
	}

	public void flush() {
		getRepository().flush();
	}

	public <S extends T> S saveAndFlush(S entity) {
		return getRepository().saveAndFlush(entity);
	}
	
	public void deleteById(ID id) {
		getRepository().deleteById(id);
	}

	public void delete(T entity) {
		getRepository().delete(entity);
	}

	public void deleteInBatch(Iterable<T> entities) {
		getRepository().deleteInBatch(entities);
	}

	public void deleteAllInBatch() {
		getRepository().deleteAllInBatch();
	}

	public T findById(ID id) {
		Optional<T> t = getRepository().findById(id);
        return t.orElse(null);
    }

	public <S extends T> List<S> findAll(Example<S> example) {
		return getRepository().findAll(example);
	}

	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		return getRepository().findAll(example, sort);
	}

}