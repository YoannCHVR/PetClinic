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

import org.springframework.samples.petclinic.model.Operation;
import org.springframework.samples.petclinic.repository.OperationRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the ClinicService interface using EntityManager.
 * <p/>
 * <p>The mappings are defined in "orm.xml" located in the META-INF directory.
 *
 * @author Helena Berger
 */
@Repository
public class JpaOperationRepositoryImpl implements OperationRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(Operation operation) {
        
    	if (operation.getId() == null) {
    		this.em.persist(operation);     		
    	}
    	else {
    		this.em.merge(operation);    
    	}
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Operation> findByVetId(Integer vetId) {
        Query query = this.em.createQuery("SELECT o FROM Operation o where o.vet.id= :id");
        query.setParameter("id", vetId);
        return query.getResultList();
    }
	
	@Override
    @SuppressWarnings("unchecked")
    public List<Operation> findByPetId(Integer petId) {
        Query query = this.em.createQuery("SELECT o FROM Operation o where o.pet.id= :id");
        query.setParameter("id", petId);
        return query.getResultList();
    }

}