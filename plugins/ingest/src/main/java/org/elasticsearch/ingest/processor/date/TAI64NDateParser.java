/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.ingest.processor.date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class TAI64NDateParser implements DateParser {
    private DateTimeZone timezone;

    public TAI64NDateParser(DateTimeZone timezone) {
        this.timezone = timezone;
    }

    @Override
    public long parseMillis(String date) {
        if (date.startsWith("@")) {
           date = date.substring(1);
        }
        long base = Long.parseLong(date.substring(1, 16), 16);
        // 1356138046000
        long rest = Long.parseLong(date.substring(16, 24), 16);

        return ((base * 1000) - 10000) + (rest/1000000);
    }

    @Override
    public DateTime parseDateTime(String date) {
        return new DateTime(parseMillis(date), timezone);
    }
}
