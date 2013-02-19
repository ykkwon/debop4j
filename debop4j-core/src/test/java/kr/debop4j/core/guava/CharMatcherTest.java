package kr.debop4j.core.guava;

import com.google.common.base.CharMatcher;
import kr.debop4j.core.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * kr.nsoft.commons.guava.CharMatcherTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 22.
 */
@Slf4j
public class CharMatcherTest extends AbstractTest {

    @Test
    public void charMatcherTest() {
        Assert.assertFalse(CharMatcher.is('x').apply('a'));
    }
}
