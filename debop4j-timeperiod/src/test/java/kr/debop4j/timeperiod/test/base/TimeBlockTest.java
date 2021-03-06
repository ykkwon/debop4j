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

package kr.debop4j.timeperiod.test.base;

import kr.debop4j.timeperiod.PeriodRelation;
import kr.debop4j.timeperiod.TimeBlock;
import kr.debop4j.timeperiod.test.samples.TimeBlockPeriodRelationTestData;
import kr.debop4j.timeperiod.tools.Durations;
import kr.debop4j.timeperiod.tools.TimeSpec;
import kr.debop4j.timeperiod.tools.Times;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.debop4j.timeperiod.test.base.TimeBlockTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 5. 18. 오후 11:22
 */
@Slf4j
public class TimeBlockTest {

    private final Duration duration = new Duration(Durations.hours(1));
    private final Duration offset = Durations.Second;

    private DateTime start = DateTime.now();
    private DateTime end = start.plus(duration);
    private TimeBlockPeriodRelationTestData testData = new TimeBlockPeriodRelationTestData(start, end, offset);

    @Test
    public void anytimeTest() throws Exception {
        assertThat(TimeBlock.Anytime.getStart()).isEqualTo(TimeSpec.MinPeriodTime);
        assertThat(TimeBlock.Anytime.getEnd()).isEqualTo(TimeSpec.MaxPeriodTime);

        assertThat(TimeBlock.Anytime.isAnytime()).isTrue();
        assertThat(TimeBlock.Anytime.isReadonly()).isTrue();

        assertThat(TimeBlock.Anytime.hasPeriod()).isFalse();
        assertThat(TimeBlock.Anytime.hasStart()).isFalse();
        assertThat(TimeBlock.Anytime.hasEnd()).isFalse();
        assertThat(TimeBlock.Anytime.isMoment()).isFalse();
    }

    @Test
    public void defaultContructorTest() throws Exception {
        TimeBlock block = new TimeBlock();

        assertThat(block).isNotEqualTo(TimeBlock.Anytime);
        assertThat(Times.getRelation(block, TimeBlock.Anytime)).isEqualTo(PeriodRelation.ExactMatch);

        assertThat(block.isAnytime()).isTrue();
        assertThat(block.isReadonly()).isFalse();

        assertThat(block.hasPeriod()).isFalse();
        assertThat(block.hasStart()).isFalse();
        assertThat(block.hasEnd()).isFalse();
        assertThat(block.isMoment()).isFalse();
    }

    @Test
    public void momentTest() throws Exception {
        DateTime moment = Times.now();
        TimeBlock block = new TimeBlock(moment);

        assertThat(block.hasStart()).isTrue();
        assertThat(block.hasEnd()).isTrue();
        assertThat(block.getDuration()).isEqualTo(TimeSpec.MinDuration);

        assertThat(block.isAnytime()).isFalse();
        assertThat(block.isMoment()).isTrue();
        assertThat(block.hasPeriod()).isTrue();
    }

    @Test
    public void momentByPeriod() {
        TimeBlock block = new TimeBlock(Times.now(), Duration.ZERO);
        assertThat(block.isMoment()).isTrue();
    }

    @Test
    public void nonMomentTest() {
        TimeBlock block = new TimeBlock(Times.now(), TimeSpec.MinPositiveDuration);
        assertThat(block.isMoment()).isFalse();
        assertThat(block.getDuration()).isEqualTo(TimeSpec.MinPositiveDuration);
    }

    @Test
    public void hasStartTest() {
        // 현재부터 ~
        TimeBlock block = new TimeBlock(Times.now(), (DateTime) null);
        assertThat(block.hasStart()).isTrue();
        assertThat(block.hasEnd()).isFalse();
    }

    @Test
    public void hasEndTest() {
        //  ~ 현재까지
        TimeBlock range = new TimeBlock((DateTime) null, Times.now());
        assertThat(range.hasStart()).isFalse();
        assertThat(range.hasEnd()).isTrue();
    }

    @Test
    public void startEndTest() {
        TimeBlock range = new TimeBlock(start, end);

        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.getEnd()).isEqualTo(end);
        assertThat(range.getDuration()).isEqualTo(duration);

        assertThat(range.hasPeriod()).isTrue();
        assertThat(range.isAnytime()).isFalse();
        assertThat(range.isMoment()).isFalse();
        assertThat(range.isReadonly()).isFalse();
    }

    @Test
    public void startEndSwapTest() {
        TimeBlock range = new TimeBlock(end, start);

        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.getEnd()).isEqualTo(end);
        assertThat(range.getDuration()).isEqualTo(duration);

        assertThat(range.hasPeriod()).isTrue();
        assertThat(range.isAnytime()).isFalse();
        assertThat(range.isMoment()).isFalse();
        assertThat(range.isReadonly()).isFalse();
    }

    @Test
    public void startAndDurationTest() {
        TimeBlock range = new TimeBlock(start, duration);

        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.getEnd()).isEqualTo(end);
        assertThat(range.getDuration()).isEqualTo(duration);

        assertThat(range.hasPeriod()).isTrue();
        assertThat(range.isAnytime()).isFalse();
        assertThat(range.isMoment()).isFalse();
        assertThat(range.isReadonly()).isFalse();
    }

    @Test(expected = AssertionError.class)
    public void startAndNegateDurationTest() {
        TimeBlock block = new TimeBlock(start, Durations.negate(duration));
    }

    @Test
    public void copyConstructorTest() {
        TimeBlock source = new TimeBlock(start, start.plusHours(1), true);
        TimeBlock copy = new TimeBlock(source);

        assertThat(copy.getStart()).isEqualTo(source.getStart());
        assertThat(copy.getEnd()).isEqualTo(source.getEnd());
        assertThat(copy.getDuration()).isEqualTo(source.getDuration());

        assertThat(copy.isReadonly()).isEqualTo(source.isReadonly());

        assertThat(copy.hasPeriod()).isTrue();
        assertThat(copy.isAnytime()).isFalse();
        assertThat(copy.isMoment()).isFalse();
    }

    @Test
    public void startTest() {
        TimeBlock block = new TimeBlock(start, start.plusHours(1));
        assertThat(block.getStart()).isEqualTo(start);
        assertThat(block.getDuration().getStandardHours()).isEqualTo(1);

        DateTime chanedStart = start.plusHours(1);
        block.setStart(chanedStart);
        assertThat(block.getStart()).isEqualTo(chanedStart);
    }

    @Test(expected = AssertionError.class)
    public void startReadonlyTest() {
        TimeBlock block = new TimeBlock(Times.now(), Durations.hours(1), true);
        block.setStart(block.getStart().minusHours(1));
    }

    @Test
    public void endTest() throws Exception {
        TimeBlock block = new TimeBlock(end.minusHours(1), end);
        assertThat(block.getEnd()).isEqualTo(end);

        DateTime changedEnd = end.plusHours(1);
        block.setEnd(changedEnd);
        assertThat(block.getEnd()).isEqualTo(changedEnd);
    }

    @Test(expected = AssertionError.class)
    public void endReadonlyTest() {
        TimeBlock range = new TimeBlock(Times.now(), Durations.hours(1), true);
        range.setEnd(range.getEnd().plusHours(1));
    }

    @Test
    public void durationTest() {
        TimeBlock block = new TimeBlock(start, duration);
        assertThat(block.getStart()).isEqualTo(start);
        assertThat(block.getEnd()).isEqualTo(end);
        assertThat(block.getDuration()).isEqualTo(duration);

        Duration delta = Durations.hours(1);
        Duration newDuration = block.getDuration().plus(delta);
        block.setDuration(newDuration);

        assertThat(block.getStart()).isEqualTo(start);
        assertThat(block.getEnd()).isEqualTo(end.plus(delta));
        assertThat(block.getDuration()).isEqualTo(newDuration);

        block.setDuration(TimeSpec.MinDuration);
        assertThat(block.getDuration()).isEqualTo(TimeSpec.MinDuration);
    }

    @Test(expected = AssertionError.class)
    public void durationOutOfRangeTest() {
        TimeBlock block = new TimeBlock(start, duration);
        block.setDuration(Durations.negate(Durations.millis(1)));
    }

    @Test
    public void durationFromStartTest() {
        TimeBlock block = new TimeBlock(start, duration);
        assertThat(block.getStart()).isEqualTo(start);
        assertThat(block.getEnd()).isEqualTo(end);
        assertThat(block.getDuration()).isEqualTo(duration);

        Duration delta = Durations.hours(1);
        Duration newDuration = block.getDuration().plus(delta);
        block.durationFromStart(newDuration);

        assertThat(block.getStart()).isEqualTo(start);
        assertThat(block.getEnd()).isEqualTo(end.plus(delta));
        assertThat(block.getDuration()).isEqualTo(newDuration);

        block.setDuration(TimeSpec.MinDuration);
        assertThat(block.getDuration()).isEqualTo(TimeSpec.MinDuration);
    }

    @Test
    public void durationFromEndTest() {
        TimeBlock block = new TimeBlock(start, duration);
        assertThat(block.getStart()).isEqualTo(start);
        assertThat(block.getEnd()).isEqualTo(end);
        assertThat(block.getDuration()).isEqualTo(duration);

        Duration delta = Durations.hours(1);
        Duration newDuration = block.getDuration().plus(delta);
        block.durationFromEnd(newDuration);

        assertThat(block.getStart()).isEqualTo(start.minus(delta));
        assertThat(block.getEnd()).isEqualTo(end);
        assertThat(block.getDuration()).isEqualTo(newDuration);

        block.setDuration(TimeSpec.MinDuration);
        assertThat(block.getDuration()).isEqualTo(TimeSpec.MinDuration);
    }

    @Test
    public void hasInsideDateTimeTest() {
        TimeBlock range = new TimeBlock(start, end);

        assertThat(range.getEnd()).isEqualTo(end);

        assertThat(range.hasInside(start.minus(duration))).isFalse();
        assertThat(range.hasInside(start)).isTrue();
        assertThat(range.hasInside(start.plus(duration))).isTrue();

        assertThat(range.hasInside(end.minus(duration))).isTrue();
        assertThat(range.hasInside(end)).isTrue();
        assertThat(range.hasInside(end.plus(duration))).isFalse();
    }

    @Test
    public void hasInsidePeriodTest() {
        TimeBlock range = new TimeBlock(start, duration);

        assertThat(range.getEnd()).isEqualTo(end);

        // before
        TimeBlock before1 = new TimeBlock(start.minusHours(2), start.minusHours(1));
        TimeBlock before2 = new TimeBlock(start.minusMillis(1), end);
        TimeBlock before3 = new TimeBlock(start.minusMillis(1), start);

        assertThat(range.hasInside(before1)).isFalse();
        assertThat(range.hasInside(before2)).isFalse();
        assertThat(range.hasInside(before3)).isFalse();

        // after
        TimeBlock after1 = new TimeBlock(start.plusHours(1), end.plusHours(1));
        TimeBlock after2 = new TimeBlock(start, end.plusMillis(1));
        TimeBlock after3 = new TimeBlock(end, end.plusMillis(1));

        assertThat(range.hasInside(after1)).isFalse();
        assertThat(range.hasInside(after2)).isFalse();
        assertThat(range.hasInside(after3)).isFalse();

        // inside
        assertThat(range.hasInside(range)).isTrue();

        TimeBlock inside1 = new TimeBlock(start.plusMillis(1), end);
        TimeBlock inside2 = new TimeBlock(start.plusMillis(1), end.minusMillis(1));
        TimeBlock inside3 = new TimeBlock(start, end.minusMillis(1));

        assertThat(range.hasInside(inside1)).isTrue();
        assertThat(range.hasInside(inside2)).isTrue();
        assertThat(range.hasInside(inside3)).isTrue();
    }

    @Test
    public void copyTest() {
        TimeBlock readonlyTimeBlock = new TimeBlock(start, duration);
        assertThat(readonlyTimeBlock.copy()).isEqualTo(readonlyTimeBlock);
        assertThat(readonlyTimeBlock.copy(Duration.ZERO)).isEqualTo(readonlyTimeBlock);

        TimeBlock range = new TimeBlock(start, end);

        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.getEnd()).isEqualTo(end);

        TimeBlock noMove = range.copy(Durations.Zero);

        assertThat(noMove.getStart()).isEqualTo(range.getStart());
        assertThat(noMove.getEnd()).isEqualTo(range.getEnd());
        assertThat(noMove.getDuration()).isEqualTo(range.getDuration());
        assertThat(noMove).isEqualTo(noMove);

        Duration forwardOffset = Durations.hours(2, 30, 15);
        TimeBlock forward = range.copy(forwardOffset);

        assertThat(forward.getStart()).isEqualTo(start.plus(forwardOffset));
        assertThat(forward.getEnd()).isEqualTo(end.plus(forwardOffset));
        assertThat(forward.getDuration()).isEqualTo(duration);

        Duration backwardOffset = Durations.hours(-1, 10, 30);
        TimeBlock backward = range.copy(backwardOffset);

        assertThat(backward.getStart()).isEqualTo(start.plus(backwardOffset));
        assertThat(backward.getEnd()).isEqualTo(end.plus(backwardOffset));
        assertThat(backward.getDuration()).isEqualTo(duration);
    }

    @Test
    public void moveTest() {
        TimeBlock moveZero = new TimeBlock(start, end);
        moveZero.move(Durations.Zero);
        assertThat(moveZero.getStart()).isEqualTo(start);
        assertThat(moveZero.getEnd()).isEqualTo(end);
        assertThat(moveZero.getDuration()).isEqualTo(duration);

        TimeBlock forward = new TimeBlock(start, end);
        Duration forwardOffset = Durations.hours(2, 30, 15);
        forward.move(forwardOffset);

        assertThat(forward.getStart()).isEqualTo(start.plus(forwardOffset));
        assertThat(forward.getEnd()).isEqualTo(end.plus(forwardOffset));
        assertThat(forward.getDuration()).isEqualTo(duration);

        TimeBlock backward = new TimeBlock(start, end);
        Duration backwardOffset = Durations.hours(-1, 10, 30);
        backward.move(backwardOffset);

        assertThat(backward.getStart()).isEqualTo(start.plus(backwardOffset));
        assertThat(backward.getEnd()).isEqualTo(end.plus(backwardOffset));
        assertThat(backward.getDuration()).isEqualTo(duration);
    }


    @Test
    public void isSamePeriodTest() {
        TimeBlock range1 = new TimeBlock(start, end);
        TimeBlock range2 = new TimeBlock(start, end);

        assertThat(range1.isSamePeriod(range1)).isTrue();
        assertThat(range2.isSamePeriod(range2)).isTrue();

        assertThat(range1.isSamePeriod(range2)).isTrue();
        assertThat(range2.isSamePeriod(range1)).isTrue();

        assertThat(range1.isSamePeriod(TimeBlock.Anytime)).isFalse();
        assertThat(range2.isSamePeriod(TimeBlock.Anytime)).isFalse();

        range1.move(Durations.Millisecond);
        assertThat(range1.isSamePeriod(range2)).isFalse();
        assertThat(range2.isSamePeriod(range1)).isFalse();

        range1.move(Durations.millis(-1));
        assertThat(range1.isSamePeriod(range2)).isTrue();
        assertThat(range2.isSamePeriod(range1)).isTrue();
    }

    @Test
    public void hasInsideTest() {

        assertThat(testData.getReference().hasInside(testData.getBefore())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getStartTouching())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getStartInside())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getInsideStartTouching())).isFalse();

        assertThat(testData.getReference().hasInside(testData.getEnclosingStartTouching())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getEnclosing())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getEnclosingEndTouching())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getExactMatch())).isTrue();

        assertThat(testData.getReference().hasInside(testData.getInside())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getInsideEndTouching())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getEndTouching())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getAfter())).isFalse();
    }

    @Test
    public void intersectsWithTest() {

        assertThat(testData.getReference().intersectsWith(testData.getBefore())).isFalse();
        assertThat(testData.getReference().intersectsWith(testData.getStartTouching())).isTrue();
        assertThat(testData.getReference().intersectsWith(testData.getStartInside())).isTrue();
        assertThat(testData.getReference().intersectsWith(testData.getInsideStartTouching())).isTrue();

        assertThat(testData.getReference().intersectsWith(testData.getEnclosingStartTouching())).isTrue();
        assertThat(testData.getReference().intersectsWith(testData.getEnclosing())).isTrue();
        assertThat(testData.getReference().intersectsWith(testData.getEnclosingEndTouching())).isTrue();
        assertThat(testData.getReference().intersectsWith(testData.getExactMatch())).isTrue();

        assertThat(testData.getReference().intersectsWith(testData.getInside())).isTrue();
        assertThat(testData.getReference().intersectsWith(testData.getInsideEndTouching())).isTrue();
        assertThat(testData.getReference().intersectsWith(testData.getEndTouching())).isTrue();
        assertThat(testData.getReference().intersectsWith(testData.getAfter())).isFalse();
    }

    @Test
    public void overlapsWithTest() {

        assertThat(testData.getReference().overlapsWith(testData.getBefore())).isFalse();
        assertThat(testData.getReference().overlapsWith(testData.getStartTouching())).isFalse();
        assertThat(testData.getReference().overlapsWith(testData.getStartInside())).isTrue();
        assertThat(testData.getReference().overlapsWith(testData.getInsideStartTouching())).isTrue();

        assertThat(testData.getReference().overlapsWith(testData.getEnclosingStartTouching())).isTrue();
        assertThat(testData.getReference().overlapsWith(testData.getEnclosing())).isTrue();
        assertThat(testData.getReference().overlapsWith(testData.getEnclosingEndTouching())).isTrue();
        assertThat(testData.getReference().overlapsWith(testData.getExactMatch())).isTrue();

        assertThat(testData.getReference().overlapsWith(testData.getInside())).isTrue();
        assertThat(testData.getReference().overlapsWith(testData.getInsideEndTouching())).isTrue();
        assertThat(testData.getReference().overlapsWith(testData.getEndTouching())).isFalse();
        assertThat(testData.getReference().overlapsWith(testData.getAfter())).isFalse();
    }

    @Test
    public void intersectsWithDateTimeTest() {
        TimeBlock range = new TimeBlock(start, end);

        // before
        assertThat(range.intersectsWith(new TimeBlock(start.minusHours(2), start.minusHours(1)))).isFalse();
        assertThat(range.intersectsWith(new TimeBlock(start.minusHours(1), start))).isTrue();
        assertThat(range.intersectsWith(new TimeBlock(start.minusHours(1), start.plusMillis(1)))).isTrue();

        // after
        assertThat(range.intersectsWith(new TimeBlock(end.plusHours(1), end.plusHours(2)))).isFalse();
        assertThat(range.intersectsWith(new TimeBlock(end, end.plusMillis(1)))).isTrue();
        assertThat(range.intersectsWith(new TimeBlock(end.minusMillis(1), end.plusMillis(1)))).isTrue();

        // intersect
        assertThat(range.intersectsWith(range)).isTrue();
        assertThat(range.intersectsWith(new TimeBlock(start.minusMillis(1), end.plusHours(2)))).isTrue();
        assertThat(range.intersectsWith(new TimeBlock(start.minusMillis(1), start.plusMillis(1)))).isTrue();
        assertThat(range.intersectsWith(new TimeBlock(end.minusMillis(1), end.plusMillis(1)))).isTrue();
    }

    @Test
    public void getIntersectionTest() {
        TimeBlock range = new TimeBlock(start, end);

        // before
        assertThat(range.getIntersection(new TimeBlock(start.minusHours(2), start.minusHours(1)))).isNull();
        assertThat(range.getIntersection(new TimeBlock(start.minusMillis(1), start))).isEqualTo(new TimeBlock(start));
        assertThat(range.getIntersection(new TimeBlock(start.minusHours(1), start.plusMillis(1)))).isEqualTo(new TimeBlock(start, start.plusMillis(1)));

        // after
        assertThat(range.getIntersection(new TimeBlock(end.plusHours(1), end.plusHours(2)))).isNull();
        assertThat(range.getIntersection(new TimeBlock(end, end.plusMillis(1)))).isEqualTo(new TimeBlock(end));
        assertThat(range.getIntersection(new TimeBlock(end.minusMillis(1), end.plusMillis(1)))).isEqualTo(new TimeBlock(end.minusMillis(1), end));

        // intersect
        assertThat(range.getIntersection(range)).isEqualTo(range);
        assertThat(range.getIntersection(new TimeBlock(start.minusMillis(1), end.plusMillis(1)))).isEqualTo(range);
        assertThat(range.getIntersection(new TimeBlock(start.plusMillis(1), end.minusMillis(1)))).isEqualTo(new TimeBlock(start.plusMillis(1), end.minusMillis(1)));
    }

    @Test
    public void getRelationTest() {
        assertThat(testData.getReference().getRelation(testData.getBefore())).isEqualTo(PeriodRelation.Before);
        assertThat(testData.getReference().getRelation(testData.getStartTouching())).isEqualTo(PeriodRelation.StartTouching);
        assertThat(testData.getReference().getRelation(testData.getStartInside())).isEqualTo(PeriodRelation.StartInside);
        assertThat(testData.getReference().getRelation(testData.getInsideStartTouching())).isEqualTo(PeriodRelation.InsideStartTouching);
        assertThat(testData.getReference().getRelation(testData.getEnclosing())).isEqualTo(PeriodRelation.Enclosing);
        assertThat(testData.getReference().getRelation(testData.getExactMatch())).isEqualTo(PeriodRelation.ExactMatch);
        assertThat(testData.getReference().getRelation(testData.getInside())).isEqualTo(PeriodRelation.Inside);
        assertThat(testData.getReference().getRelation(testData.getInsideEndTouching())).isEqualTo(PeriodRelation.InsideEndTouching);
        assertThat(testData.getReference().getRelation(testData.getEndInside())).isEqualTo(PeriodRelation.EndInside);
        assertThat(testData.getReference().getRelation(testData.getEndTouching())).isEqualTo(PeriodRelation.EndTouching);
        assertThat(testData.getReference().getRelation(testData.getAfter())).isEqualTo(PeriodRelation.After);

        // reference
        assertThat(testData.getReference().getStart()).isEqualTo(start);
        assertThat(testData.getReference().getEnd()).isEqualTo(end);
        assertThat(testData.getReference().isReadonly()).isTrue();

        // after
        assertThat(testData.getAfter().isReadonly()).isTrue();
        assertThat(testData.getAfter().getStart().compareTo(start)).isLessThan(0);
        assertThat(testData.getAfter().getEnd().compareTo(start)).isLessThan(0);

        assertThat(testData.getReference().hasInside(testData.getAfter().getStart())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getAfter().getEnd())).isFalse();
        assertThat(testData.getReference().getRelation(testData.getAfter())).isEqualTo(PeriodRelation.After);

        // start touching
        assertThat(testData.getStartTouching().isReadonly()).isTrue();
        assertThat(testData.getStartTouching().getStart().getMillis()).isLessThan(start.getMillis());
        assertThat(testData.getStartTouching().getEnd()).isEqualTo(start);

        assertThat(testData.getReference().hasInside(testData.getStartTouching().getStart())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getStartTouching().getEnd())).isTrue();
        assertThat(testData.getReference().getRelation(testData.getStartTouching())).isEqualTo(PeriodRelation.StartTouching);

        // start inside
        assertThat(testData.getStartInside().isReadonly()).isTrue();
        assertThat(testData.getStartInside().getStart().getMillis()).isLessThan(start.getMillis());
        assertThat(testData.getStartInside().getEnd().getMillis()).isLessThan(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getStartInside().getStart())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getStartInside().getEnd())).isTrue();
        assertThat(testData.getReference().getRelation(testData.getStartInside())).isEqualTo(PeriodRelation.StartInside);

        // inside start touching
        assertThat(testData.getInsideStartTouching().isReadonly()).isTrue();
        assertThat(testData.getInsideStartTouching().getStart().getMillis()).isEqualTo(start.getMillis());
        assertThat(testData.getInsideStartTouching().getEnd().getMillis()).isGreaterThan(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getInsideStartTouching().getStart())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getInsideStartTouching().getEnd())).isFalse();
        assertThat(testData.getReference().getRelation(testData.getInsideStartTouching())).isEqualTo(PeriodRelation.InsideStartTouching);

        // enclosing start touching
        assertThat(testData.getInsideStartTouching().isReadonly()).isTrue();
        assertThat(testData.getInsideStartTouching().getStart().getMillis()).isEqualTo(start.getMillis());
        assertThat(testData.getInsideStartTouching().getEnd().getMillis()).isGreaterThan(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getInsideStartTouching().getStart())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getInsideStartTouching().getEnd())).isFalse();
        assertThat(testData.getReference().getRelation(testData.getInsideStartTouching())).isEqualTo(PeriodRelation.InsideStartTouching);

        // enclosing
        assertThat(testData.getEnclosing().isReadonly()).isTrue();
        assertThat(testData.getEnclosing().getStart().getMillis()).isGreaterThan(start.getMillis());
        assertThat(testData.getEnclosing().getEnd().getMillis()).isLessThan(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getEnclosing().getStart())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getEnclosing().getEnd())).isTrue();
        assertThat(testData.getReference().getRelation(testData.getEnclosing())).isEqualTo(PeriodRelation.Enclosing);

        // enclosing end touching
        assertThat(testData.getEnclosingEndTouching().isReadonly()).isTrue();
        assertThat(testData.getEnclosingEndTouching().getStart().getMillis()).isGreaterThan(start.getMillis());
        assertThat(testData.getEnclosingEndTouching().getEnd().getMillis()).isEqualTo(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getEnclosingEndTouching().getStart())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getEnclosingEndTouching().getEnd())).isTrue();
        assertThat(testData.getReference().getRelation(testData.getEnclosingEndTouching())).isEqualTo(PeriodRelation.EnclosingEndTouching);

        // exact match
        assertThat(testData.getExactMatch().isReadonly()).isTrue();
        assertThat(testData.getExactMatch().getStart().getMillis()).isEqualTo(start.getMillis());
        assertThat(testData.getExactMatch().getEnd().getMillis()).isEqualTo(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getExactMatch().getStart())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getExactMatch().getEnd())).isTrue();
        assertThat(testData.getReference().getRelation(testData.getExactMatch())).isEqualTo(PeriodRelation.ExactMatch);

        // inside
        assertThat(testData.getInside().isReadonly()).isTrue();
        assertThat(testData.getInside().getStart().getMillis()).isLessThan(start.getMillis());
        assertThat(testData.getInside().getEnd().getMillis()).isGreaterThan(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getInside().getStart())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getInside().getEnd())).isFalse();
        assertThat(testData.getReference().getRelation(testData.getInside())).isEqualTo(PeriodRelation.Inside);

        // inside end touching
        assertThat(testData.getInsideEndTouching().isReadonly()).isTrue();
        assertThat(testData.getInsideEndTouching().getStart().getMillis()).isLessThan(start.getMillis());
        assertThat(testData.getInsideEndTouching().getEnd().getMillis()).isEqualTo(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getInsideEndTouching().getStart())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getInsideEndTouching().getEnd())).isTrue();
        assertThat(testData.getReference().getRelation(testData.getInsideEndTouching())).isEqualTo(PeriodRelation.InsideEndTouching);

        // end inside
        assertThat(testData.getEndInside().isReadonly()).isTrue();
        assertThat(testData.getEndInside().getStart().getMillis()).isGreaterThan(start.getMillis());
        assertThat(testData.getEndInside().getStart().getMillis()).isLessThan(end.getMillis());
        assertThat(testData.getEndInside().getEnd().getMillis()).isGreaterThan(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getEndInside().getStart())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getEndInside().getEnd())).isFalse();
        assertThat(testData.getReference().getRelation(testData.getEndInside())).isEqualTo(PeriodRelation.EndInside);

        // end touching
        assertThat(testData.getEndTouching().isReadonly()).isTrue();
        assertThat(testData.getEndTouching().getStart().getMillis()).isEqualTo(end.getMillis());
        assertThat(testData.getEndTouching().getEnd().getMillis()).isGreaterThan(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getEndTouching().getStart())).isTrue();
        assertThat(testData.getReference().hasInside(testData.getEndTouching().getEnd())).isFalse();
        assertThat(testData.getReference().getRelation(testData.getEndTouching())).isEqualTo(PeriodRelation.EndTouching);

        // before
        assertThat(testData.getBefore().isReadonly()).isTrue();
        assertThat(testData.getBefore().getStart().getMillis()).isGreaterThan(end.getMillis());
        assertThat(testData.getBefore().getEnd().getMillis()).isGreaterThan(end.getMillis());

        assertThat(testData.getReference().hasInside(testData.getBefore().getStart())).isFalse();
        assertThat(testData.getReference().hasInside(testData.getBefore().getEnd())).isFalse();
        assertThat(testData.getReference().getRelation(testData.getBefore())).isEqualTo(PeriodRelation.Before);
    }

    @Test
    public void resetTest() {
        TimeBlock range = new TimeBlock(start, duration);

        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.hasStart()).isTrue();
        assertThat(range.getEnd()).isEqualTo(end);
        assertThat(range.hasEnd()).isTrue();

        range.reset();

        assertThat(range.getStart()).isEqualTo(TimeSpec.MinPeriodTime);
        assertThat(range.hasStart()).isFalse();
        assertThat(range.getEnd()).isEqualTo(TimeSpec.MaxPeriodTime);
        assertThat(range.hasEnd()).isFalse();
    }

    @Test
    public void equalsTest() {
        TimeBlock range1 = new TimeBlock(start, end);
        TimeBlock range2 = new TimeBlock(start, end);
        TimeBlock range3 = new TimeBlock(start.plusMillis(-1), end.plusMillis(1));
        TimeBlock range4 = new TimeBlock(start, end, true);

        assertThat(range1).isEqualTo(range2);
        assertThat(range1).isNotEqualTo(range3);
        assertThat(range2).isEqualTo(range1);
        assertThat(range2).isNotEqualTo(range3);

        assertThat(range1).isNotEqualTo(range4);
    }
}
