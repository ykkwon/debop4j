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

package kr.debop4j.data.hibernate.forTesting;

/**
 * kr.debop4j.data.hibernate.forTesting.DatabaseEngine
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 13. 2. 19.
 */
public enum DatabaseEngine {

    HSql("HSql"),
    HSqlWithFile("HSqlWithFile"),
    H2("H2"),
    H2WithFile("H2WithFile"),
    SQLite("SQLite"),
    SQLiteWithFile("SQLitewithFile"),
    MySQL("MySQL"),
    PostgreSQL("PostgresQL"),
    SQLServer("SQLServer"),
    Oracle("Oracle");

    private final String databaseName;

    DatabaseEngine(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getValue() {
        return databaseName;
    }
}
