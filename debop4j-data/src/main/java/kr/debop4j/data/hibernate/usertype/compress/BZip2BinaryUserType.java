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

import kr.debop4j.core.compress.BZip2Compressor;
import kr.debop4j.core.compress.ICompressor;
import lombok.extern.slf4j.Slf4j;

/**
 * GZip 알고리즘 ({@link BZip2Compressor} 으로 이진 데이터 값을 압축하여 Binary로 저장합니다.
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 12. 9. 18
 */
@Slf4j
public class BZip2BinaryUserType extends AbstractCompressedBinaryUserType {

    private static final ICompressor compressor = new BZip2Compressor();

    @Override
    public ICompressor getCompressor() {
        return compressor;
    }

    private static final long serialVersionUID = 5894112998214016162L;
}
