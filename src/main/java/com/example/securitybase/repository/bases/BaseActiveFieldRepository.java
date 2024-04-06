package com.example.securitybase.repository.bases;


import com.example.securitybase.entity.base.EntityWithActiveField;

import java.util.List;

/**
 * @author caunv
 * @created_date 08/04/2021 - 4:29 PM
 */
public interface BaseActiveFieldRepository<T extends EntityWithActiveField>{
    List<T> findAllByActive(Boolean active);
}