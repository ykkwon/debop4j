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

package kr.debop4j.core.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.debop4j.core.spring.AutowiredSpringsTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 5. 8. 오후 9:39
 */
@Slf4j
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { AnnotatedBeanConfig.class } )
public class AutowiredSpringsTest {

    @Test
    public void initializeTest() {
        // AnnotatedBeanConfig 에서 ComponentScan으로 Springs를 Scan 할 때,
        // Application Context 를 Injection 합니다.
        //
        assertThat(Springs.getContext()).isNotNull();
        assertThat(Springs.isInitialized()).isTrue();
    }
}
