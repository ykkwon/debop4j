/*
 * Copyright 2011-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.debop4j.access.repository.organization;

import kr.debop4j.access.model.organization.Company;
import kr.debop4j.access.model.organization.CompanyCode;
import kr.debop4j.core.Guard;
import kr.debop4j.core.tools.StringTool;
import kr.debop4j.data.hibernate.repository.impl.HibernateRepository;
import kr.debop4j.data.hibernate.tools.CriteriaTool;
import kr.debop4j.data.hibernate.unitofwork.IUnitOfWork;
import kr.debop4j.data.hibernate.unitofwork.UnitOfWorkNestingOptions;
import kr.debop4j.data.hibernate.unitofwork.UnitOfWorks;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link CompanyCode}에 대한 Repository 클래스입니다.
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 13. 3. 12.
 */
@Repository
@Transactional
@Slf4j
public class CompanyCodeRepository extends HibernateRepository<CompanyCode> {

    public CompanyCodeRepository() {
        super(CompanyCode.class);
    }

    public DetachedCriteria buildCriteria(Company company, String code, String name, Boolean active) {

        if (log.isDebugEnabled())
            log.debug("Criteria를 빌드합니다. company=[{}], code=[{}], name=[{}], active=[{}]",
                      company, code, name, active);

        DetachedCriteria dc = DetachedCriteria.forClass(CompanyCode.class);

        if (company != null) {
            CriteriaTool.addEq(dc, "company", company);
        }

        if (StringTool.isNotEmpty(code)) {
            CriteriaTool.addEq(dc, "code", code);
        }
        if (StringTool.isNotEmpty(name)) {
            CriteriaTool.addEq(dc, "name", name);
        }
        if (active != null) {
            CriteriaTool.addEq(dc, "active", active);
        }
        return dc;
    }

    public CompanyCode getOrCreate(Company company, String code) {
        CompanyCode companyCode = findUniqueByCode(company, code);

        if (companyCode == null) {
            synchronized (this) {
                try (IUnitOfWork unitOfWork = UnitOfWorks.start(UnitOfWorkNestingOptions.CreateNewOrNestUnitOfWork)) {
                    UnitOfWorks.getCurrentSession().saveOrUpdate(new CompanyCode(company, code));
                    UnitOfWorks.getCurrent().transactionalFlush();
                }
            }
            if (log.isDebugEnabled())
                log.debug("새로운 CompanyCode 정보를 생성했습니다. CompanyCode=[{}]", companyCode);

            companyCode = findUniqueByCode(company, code);
        }

        return companyCode;
    }

    public CompanyCode findUniqueByCode(Company company, String code) {
        Guard.shouldNotBeNull(company, "company");
        Guard.shouldNotBeEmpty(code, "code");

        return findUnique(buildCriteria(company, code, null, null));
    }

    public List<CompanyCode> findByCompany(Company company) {
        return find(buildCriteria(company, null, null, null));
    }
}
