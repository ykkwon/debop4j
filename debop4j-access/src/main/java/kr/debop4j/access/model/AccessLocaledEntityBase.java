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

package kr.debop4j.access.model;

import kr.debop4j.access.AccessContext;
import kr.debop4j.data.model.AnnotatedLocaleEntityBase;
import kr.debop4j.data.model.ILocaleValue;
import kr.debop4j.data.model.IUpdateTimestampedEntity;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 다국어를 지원하는 Entity를 나타냅니다.
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 13. 3. 8 오후 3:46
 */
@MappedSuperclass
public abstract class AccessLocaledEntityBase<TLocaleValue extends ILocaleValue>
        extends AnnotatedLocaleEntityBase implements IUpdateTimestampedEntity {

    @Getter
    @Type(type = "kr.debop4j.data.hibernate.usertype.JodaDateTimeUserType")
    @Column(name = AccessContext.UPDATE_TIMESTAMP)
    private DateTime updateTimestamp;

    public void updateUpdateTimestamp() {
        updateTimestamp = DateTime.now();
    }

    private static final long serialVersionUID = 4105817032363041651L;
}
