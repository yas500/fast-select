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

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @see com.github.terma.fastselect.MultiByteRequest
 */
public class MultiByteData implements Data {

    public final IntData index;
    public final ByteData data;

    public MultiByteData(final int inc) {
        index = new IntData(inc);
        data = new ByteData(inc);
    }

    public MultiByteData(MultiByteData data, byte[] needToCopy) {
        this.index = new IntData(data.inc());
        this.data = new ByteData(data.inc());

        for (int i = 0; i < needToCopy.length; i++) {
            if (needToCopy[i] == 1) {
                add((byte[]) data.get(i));
            }
        }
    }

    public void add(byte[] values) {
        index.add(data.size); // store index of first element of data
        for (byte v : values) data.add(v);
    }

    public int getDataStart(int position) {
        return index.data[position];
    }

    public int getDataEnd(int position) {
        return index.size == position + 1 ? data.size : index.data[position + 1];
    }

    @Override
    public int getDiskSpace() {
        return index.getDiskSpace() + data.getDiskSpace();
    }

    @Override
    public void save(final ByteBuffer buffer) throws IOException {
        index.save(buffer);
        data.save(buffer);
    }

    @Override
    public void load(String dataClass, ByteBuffer buffer, int size) throws IOException {
        index.load(IntData.class.getName(), buffer, size);

        // tricky point, as data size doesn't depend really on count of items (size) in cache
        // we just take rest of buffer as size of byte data block as size of buffer is
        // size of index data and real data in bytes
        final int dataSize = buffer.remaining();
        data.load(ByteData.class.getName(), buffer, dataSize);
    }

    @Override
    public Object get(int position) {
        int start = getDataStart(position);
        int end = getDataEnd(position);
        byte[] values = new byte[end - start];
        System.arraycopy(data.data, start, values, 0, values.length);
        return values;
    }

    @Override
    public int compare(int position1, int position2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void init(int size) {
        index.init(size);
    }

    @Override
    public void compact() {
        index.compact();
        data.compact();
    }

    @Override
    public int size() {
        return index.size();
    }

    @Override
    public int allocatedSize() {
        return index.allocatedSize();
    }

    @Override
    public long mem() {
        return OBJECT_HEADER_BYTES + 2 * REFERENCE_BYTES + index.mem() + data.mem();
    }

    @Override
    public int inc() {
        return index.inc();
    }

    @Override
    public Data copy(byte[] needToCopy) {
        return new MultiByteData(this, needToCopy);
    }

}
