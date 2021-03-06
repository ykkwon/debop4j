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

package kr.debop4j.data.jpa.domain;

import kr.debop4j.data.model.StatefulEntityBase;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;

/**
 * JPA 기반의 엔티티의 추상 클래스입니다.
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 12. 11. 25.
 */
public abstract class JpaEntityBase extends StatefulEntityBase {

    /** 엔티티 저장 후에 처리해야 할 작업을 수행합니다. */
    @PostPersist
    private void postPersist() {
        onSave();
    }

    /** 엔티티 로드 후에 엔티티에 후 처리할 작업입니다. */
    @PostLoad
    private void postLoad() {
        onLoad();
    }

    private static final long serialVersionUID = 8224137297852297901L;
}
