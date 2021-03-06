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

package kr.debop4j.timeperiod;

import com.google.common.base.Objects;
import kr.debop4j.core.ValueObjectBase;
import kr.debop4j.core.tools.HashTool;
import lombok.Getter;

/**
 * 1일 범위 내에서의 시간 단위의 범위를 표현합니다. (예: 09시 ~ 17시).
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 5. 12. 오후 2:21
 */
public class HourRangeInDay extends ValueObjectBase implements Comparable<HourRangeInDay> {
    private static final long serialVersionUID = 6958950354517975186L;

    @Getter
    private final Timepart start;
    @Getter
    private final Timepart end;

    /**
     * Instantiates a new Hour range in day.
     *
     * @param hourOfDay the hour of day
     */
    public HourRangeInDay(int hourOfDay) {
        this(hourOfDay, hourOfDay);
    }

    /**
     * Instantiates a new Hour range in day.
     *
     * @param startHourOfDay the start hour of day
     * @param endHourOfDay   the end hour of day
     */
    public HourRangeInDay(int startHourOfDay, int endHourOfDay) {
        this(new Timepart(startHourOfDay), new Timepart(endHourOfDay));
    }

    /**
     * Instantiates a new Hour range in day.
     *
     * @param start the start
     * @param end   the end
     */
    public HourRangeInDay(Timepart start, Timepart end) {
        if (start.compareTo(end) <= 0) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }
    }

    /**
     * start 값으로 비교합니다.
     */
    @Override
    public int compareTo(HourRangeInDay o) {
        return start.compareTo(o.getStart());
    }

    @Override
    public int hashCode() {
        return HashTool.compute(start, end);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("start", start)
                .add("end", end);
    }
}
