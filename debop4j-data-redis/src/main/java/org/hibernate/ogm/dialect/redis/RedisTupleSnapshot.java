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

package org.hibernate.ogm.dialect.redis;

import lombok.Getter;
import org.hibernate.ogm.datastore.spi.TupleSnapshot;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * {@link TupleSnapshot} for Redis
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 5. 3. 오후 9:15
 */
public class RedisTupleSnapshot implements TupleSnapshot, Serializable {

    private static final long serialVersionUID = 9136662772359532463L;

    @Getter
    private final Map<String, Object> map;

    public RedisTupleSnapshot(Map<String, Object> map) {
        assert map != null;
        this.map = map;
    }

    @Override
    public Object get(String column) {
        return map.get(column);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Set<String> getColumnNames() {
        return map.keySet();
    }
}
