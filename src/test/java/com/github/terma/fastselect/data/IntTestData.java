/*
Copyright 2015-2016 Artem Stasiuk

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.github.terma.fastselect.data;

import java.util.Objects;

public class IntTestData {

    public int value;

    // empty constructor for database to be able restore object
    public IntTestData() {
        this(0);
    }

    public IntTestData(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntTestData that = (IntTestData) o;
        return value == that.value;
    }

    @Override
    public String toString() {
        return "TestLongShort {value: " + value + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}