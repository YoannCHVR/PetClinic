/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.samples.petclinic.model.Memo;
import org.springframework.samples.petclinic.repository.MemoRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the ClinicService interface using EntityManager.
 * <p/>
 * <p>The mappings are defined in "orm.xml" located in the META-INF directory.
 *
 * @author Helena Berger
 */
@Repository
public class JpaMemoRepositoryImpl implements MemoRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(Memo memo) {
        
    	if (memo.getId() == null) {
    		this.em.persist(memo);     		
    	}
    	else {
    		this.em.merge(memo);    
    	}
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Memo> findByVetId(Integer vetId) {
        Query query = this.em.createQuery("SELECT m FROM Memo m where m.vet.id= :id");
        query.setParameter("id", vetId);
        return query.getResultList();
    }

}