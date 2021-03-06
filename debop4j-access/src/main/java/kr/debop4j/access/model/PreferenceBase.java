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

import com.google.common.base.Objects;
import kr.debop4j.core.Guard;
import kr.debop4j.core.tools.HashTool;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 설정 정보의 기본 클래스
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 13. 3. 12.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class PreferenceBase extends AccessEntityBase {

    private static final long serialVersionUID = -6774316561760796681L;

    protected PreferenceBase() {}

    protected PreferenceBase(String key, String value) {
        Guard.shouldNotBeEmpty(key, "key");
        this.key = key;
        this.value = value;
    }

    @Column(name = "PrefKey", nullable = false, length = 255)
    private String key;

    @Column(name = "PrefValue", length = 2000)
    private String value;

    @Column(length = 2000)
    private String defaultValue;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String exAttr;

    @Override
    public int hashCode() {
        return HashTool.compute(key);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("key", key)
                .add("value", value)
                .add("defaultValue", defaultValue);
    }
}
