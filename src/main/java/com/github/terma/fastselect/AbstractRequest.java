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

package com.github.terma.fastselect;

import java.util.BitSet;

public abstract class AbstractRequest {

    public final String name;

    /**
     * Field for internal usage by framework you don't need to fill it.
     */
    public FastSelect.Column column;

    public AbstractRequest(String name) {
        this.name = name;
    }

    /**
     * @param bitSet - block bitmap (we use it only for numeric types)
     * @return - true if block could contains data good for request
     */
    public boolean inBlock(BitSet bitSet) {
        return true;
    }

    public boolean inBlock(Range range) {
        return true;
    }

    /**
     * @param position - absolute position in data set
     * @return - true if value in requested position good for requested
     */
    public abstract boolean checkValue(int position);

    /**
     * Prepare request state to scan through data set
     */
    public void prepare() {
    }

}
