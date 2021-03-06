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

package kr.debop4j.data.hibernate.usertype.compress;

import kr.debop4j.core.tools.ArrayTool;
import kr.debop4j.core.tools.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BinaryType;
import org.hibernate.type.StringType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 엔티티의 속성 값을 압축하여 DB에 Hex Decimal 문자열로 저장합니다.
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 12. 9. 18
 */
@Slf4j
public abstract class AbstractCompressedStringUserType extends AbstractCompressedUserType {

    /**
     * Compress string, return byte array.
     *
     * @param value 원본 문자열
     * @return 압축된 바이트 배열
     */
    protected byte[] compress(final String value) {
        if (StringTool.isEmpty(value))
            return null;

        return getCompressor().compress(StringTool.getUtf8Bytes(value));
    }

    /**
     * Decompress byte array, return string.
     *
     * @param value 압축된 byte 배열
     * @return 원본 문자열
     */
    protected String decompress(final byte[] value) {
        if (ArrayTool.isEmpty(value))
            return StringTool.EMPTY_STR;

        return StringTool.getUtf8String(getCompressor().decompress(value));
    }

    @Override
    public Class returnedClass() {
        return String.class;
    }

    @Override
    public Object nullSafeGet(final ResultSet resultSet,
                              final String[] strings,
                              final SessionImplementor sessionImplementor,
                              final Object o) throws HibernateException, SQLException {
        try {
            byte[] value = BinaryType.INSTANCE.nullSafeGet(resultSet, strings[0], sessionImplementor);
            return decompress(value);
        } catch (Exception ex) {
            log.error("column=[" + strings[0] + "] 정보를 읽어 압축 복원하는데 실패했습니다.", ex);
            throw new HibernateException("압축된 정보를 복원하는데 실패했습니다.", ex);
        }
    }

    @Override
    public void nullSafeSet(final PreparedStatement preparedStatement,
                            final Object o,
                            final int i,
                            final SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        try {
            byte[] value = compress((String) o);
            BinaryType.INSTANCE.nullSafeSet(preparedStatement, value, i, sessionImplementor);
        } catch (Exception ex) {
            log.error("Statement=[" + preparedStatement + "], index=[" + i + "]에 해당하는 값을 압축하는데 실패했습니다.", ex);
            throw new HibernateException("압축된 정보를 복원하는데 실패했습니다.", ex);
        }
    }

    @Override
    public boolean isMutable() {
        return StringType.INSTANCE.isMutable();
    }

    private static final long serialVersionUID = -2543273769125439331L;
}
