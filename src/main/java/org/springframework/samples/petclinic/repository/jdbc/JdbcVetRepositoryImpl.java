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
package org.springframework.samples.petclinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Repository;

/**
 * A simple JDBC-based implementation of the {@link VetRepository} interface. Uses @Cacheable to cache the result of the
 * {@link findAll} method
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Sam Brannen
 * @author Thomas Risberg
 * @author Mark Fisher
 * @author Michael Isvy
 */
@Repository
public class JdbcVetRepositoryImpl implements VetRepository {

    private JdbcTemplate jdbcTemplate;
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcVetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Refresh the cache of Vets that the ClinicService is holding.
     *
     * @see org.springframework.samples.petclinic.model.service.ClinicService#findVets()
     */
    @Override
    public Collection<Vet> findAll() throws DataAccessException {
        List<Vet> vets = new ArrayList<Vet>();
        // Retrieve the list of all vets.
        vets.addAll(this.jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM vets ORDER BY last_name,first_name",
                ParameterizedBeanPropertyRowMapper.newInstance(Vet.class)));

        // Retrieve the list of all possible specialties.
        final List<Specialty> specialties = this.jdbcTemplate.query(
                "SELECT id, name FROM specialties",
                ParameterizedBeanPropertyRowMapper.newInstance(Specialty.class));

        // Build each vet's list of specialties.
        for (Vet vet : vets) {
            final List<Integer> vetSpecialtiesIds = this.jdbcTemplate.query(
                    "SELECT specialty_id FROM vet_specialties WHERE vet_id=?",
                    new ParameterizedRowMapper<Integer>() {
                        @Override
                        public Integer mapRow(ResultSet rs, int row) throws SQLException {
                            return Integer.valueOf(rs.getInt(1));
                        }
                    },
                    vet.getId().intValue());
            for (int specialtyId : vetSpecialtiesIds) {
                Specialty specialty = EntityUtils.getById(specialties, Specialty.class, specialtyId);
                vet.addSpecialty(specialty);
            }
        }
        return vets;
    }
    
    /**
     * Loads the {@link Vet} with the supplied <code>id</code>; also loads the {@link Specialty Specialties} and {@link Memo Memos}
     * for the corresponding vet, if not already loaded.
     */
    @Override
    public Vet findById(int id) throws DataAccessException {
        Vet vet;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            vet = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, first_name, last_name FROM vets WHERE id= :id",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Vet.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Vet.class, id);
        }
        loadSpecialtiesAndMemos(vet);
        return vet;
    }
    
     public void loadSpecialtiesAndMemos(final Vet vet) {
        /*Map<String, Object> params = new HashMap<String, Object>();
        params.put("id",vet.getId().intValue());
        final List<JdbcSpecialties> specialties = this.namedParameterJdbcTemplate.query(
                "SELECT id, name FROM vet_specialties, specialties WHERE vet_specialties.specialty_id=specialties.id and vet_id=:id",
                params,
                new JdbcSpecialtyRowMapper()
        );
        for (JdbcSpecialty specialty : specialties) {
            vet.addSpecialty(specialty);
        
            }
            
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("id",vet.getId().intValue());
        final List<JdbcMemos> memos = this.namedParameterJdbcTemplate.query(
                "SELECT id, description FROM memos WHERE  vet_id=:id",
                params2,
                new JdbcMemoRowMapper()
        );
        for (JdbcMemo memo : memos) {
            vet.addMemo(memo);
        
            }
        }*/
    }
}