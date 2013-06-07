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

package kr.debop4j.search;

/**
 * 루씬 조회 방법
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 13. 4. 26. 오후 4:29
 */
public enum QueryMethod {

    /** Term (단어) */
    Term("Term"),

    /** 문장 */
    Phrase("Phrase"),

    /** Wildcard */
    Wildcard("Wildcard"),

    /** 접두사 */
    Prefix("Prefix"),

    /** 퍼지 */
    Fuzzy("Fuzzy"),

    /** 범위 */
    Range("Range"),

    /** Boolean */
    Boolean("Boolean");

    final String queryMethod;

    QueryMethod(String queryMethod) {
        this.queryMethod = queryMethod;
    }

    public String getValue() {
        return this.queryMethod;
    }
}
