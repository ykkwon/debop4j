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

package kr.debop4j.access.model.calendar;

import com.google.common.base.Objects;
import kr.debop4j.access.model.organization.Company;
import kr.debop4j.core.tools.HashTool;
import kr.debop4j.data.model.AnnotatedTreeEntityBase;
import kr.debop4j.data.model.ITreeEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Index;

import javax.persistence.*;

/**
 * 회사의 업무 시간을 표현하는 Working Calendar를 표시합니다.
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 13. 3. 11.
 */
@Entity
@Table(name = "WorkCalendar")
@org.hibernate.annotations.Cache(region = "Calendar", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class WorkCalendar
        extends AnnotatedTreeEntityBase<WorkCalendar> implements ITreeEntity<WorkCalendar> {
    private static final long serialVersionUID = 290331237182956620L;

    @Id
    @GeneratedValue
    @Column(name = "WorkCalendarId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CompanyId")
    @Index(name = "ix_workcalendar_code")
    private Company company;

    @Column(name = "CalendarCode", nullable = false, length = 128)
    @Index(name = "ix_workcalendar_code")
    private String code;

    @Column(name = "CalendarName", nullable = false, length = 128)
    private String name;


    @Override
    public int hashCode() {
        if (isPersisted())
            return HashTool.compute(id);

        return HashTool.compute(company, code);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("code", code)
                .add("name", name)
                .add("company", company);
    }
}
