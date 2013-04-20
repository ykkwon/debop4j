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

package kr.debop4j.core.json;

import kr.debop4j.core.Guard;
import kr.debop4j.core.spring.Springs;
import lombok.extern.slf4j.Slf4j;

/**
 * Json 직렬화/역직렬화 등을 지원하는 Utility Class 입니다.
 *
 * @author sunghyouk.bae@gmail.com
 * @since 12. 12. 5.
 */
@Slf4j
public class JsonTool {

    private static IJsonSerializer serializer = new GsonSerializer();

    synchronized static IJsonSerializer getDefaultSerializer() {
        if (serializer == null) {
            // IJsonSerializer 를 검색하고, 없다면 GsonSerializer 를 등록하고 반환한다.
            serializer = Springs.getOrRegisterBean(IJsonSerializer.class, JacksonSerializer.class);
        }
        return serializer;
    }

    /**
     * 지정된 객체를 Json 직렬화를 수행하여 byte 배열로 변환합니다.
     */
    public static <T> byte[] serializeAsBytes(T graph) {
        return serializer.serialize(graph);
    }

    /**
     * 지정된 객체를 Json 직렬화를 수행ㅎ여 문자열로 변환합니다.
     */
    public static <T> String serializeAsText(T graph) {
        Guard.shouldNotBeNull(graph, "graph");
        return serializer.serializeToText(graph);
    }

    /**
     * 지정된 바이트 배열을 JSON 역직렬화를 수행하여, 대상 객체로 빌드합니다.
     */
    public static <T> T deserializeFromBytes(byte[] jsonBytes, Class<T> targetType) {
        return serializer.deserialize(jsonBytes, targetType);
    }

    /**
     * 지정된 JSON TEXT 를 역직렬화하여, 대상 객체로 빌드합니다.
     */
    public static <T> T deserializeFromText(String jsonText, Class<T> targetType) {
        return serializer.deserializeFromText(jsonText, targetType);
    }
}
