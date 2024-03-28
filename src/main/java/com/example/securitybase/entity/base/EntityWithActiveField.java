package com.example.securitybase.entity.base;

import javax.persistence.Basic;
import javax.persistence.Column;

/**
 * @author caunv
 * @created_date 08/04/2021 - 4:25 PM
 */
public interface EntityWithActiveField {
    @Basic
    @Column(name = "IS_ACTIVE")
    Boolean getActive();
    void setActive(Boolean active);
}

