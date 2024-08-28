/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.mediation.components;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream extends FilterInputStream {

    private long count = 0;

    /**
     * Creates a <code>CountingInputStream</code>
     * 
     * This calls the parent constructor in FilterInputStream
     * 
     * 
     * @param source
     *            the underlying input stream, or <code>null</code> if this
     *            instance is to be created without an underlying stream.
     */
    public CountingInputStream(final InputStream source) {
        super(source);
    }

    /**
     * Reads the next byte of data from this input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream has
     * been reached, the value <code>-1</code> is returned. This method blocks
     * until input data is available, the end of the stream is detected, or an
     * exception is thrown.
     * <p>
     * This method simply performs <code>super.read()</code> and returns the
     * result.
     * 
     * This method also increments the class variable count (total number of bytes
     * read from stream)
     * 
     * @return the next byte of data, or <code>-1</code> if the end of the stream
     *         is reached.
     * @exception IOException
     *                if an I/O error occurs.
     */
    @Override
    public int read() throws IOException {
        final int result = super.read();
        if (result > 0) {
            count++;
        }

        return result;
    }

    /**
     * Reads up to <code>byte.length</code> bytes of data from this input stream
     * into an array of bytes. This method blocks until some input is available.
     * <p>
     * This method simply performs the call <code>read(b, 0, b.length)</code> and
     * returns the result.
     * 
     * @param bytes
     *            the buffer into which the data is read.
     * @return the total number of bytes read into the buffer, or <code>-1</code>
     *         if there is no more data because the end of the stream has been
     *         reached.
     * @exception IOException
     *                if an I/O error occurs.
     */
    @Override
    public int read(final byte bytes[]) throws IOException {
        return read(bytes, 0, bytes.length);
    }

    /**
     * Reads up to <code>len</code> bytes of data from this input stream into an
     * array of bytes. This method blocks until some input is available.
     * <p>
     * This method simply performs <code>in.read(b, off, len)</code> and returns
     * the result.
     * 
     * This method also increments the class variable count (total number of bytes
     * read from stream) with the number of bytes read in this method
     * 
     * @param bytes
     *            the buffer into which the data is read.
     * @param offset
     *            the start offset of the data.
     * @param length
     *            the maximum number of bytes read.
     * @return the total number of bytes read into the buffer, or <code>-1</code>
     *         if there is no more data because the end of the stream has been
     *         reached.
     * @exception IOException
     *                if an I/O error occurs.
     */
    @Override
    public int read(final byte bytes[], final int offset, final int length)
            throws IOException {
        final int result = super.read(bytes, offset, length);
        if (result > 0) {
            count += result;
        }
        return result;
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from the input stream.
     * The <code>skip</code> method may, for a variety of reasons, end up skipping
     * over some smaller number of bytes, possibly <code>0</code>. The actual
     * number of bytes skipped is returned.
     * <p>
     * This method simply performs <code>in.skip(n)</code>.
     * 
     * This method also increments the class variable count (total number of bytes
     * read from stream) with the number of bytes skipped in this method
     * 
     * @param length
     *            the number of bytes to be skipped.
     * @return the actual number of bytes skipped.
     * @exception IOException
     *                if an I/O error occurs.
     */
    @Override
    public long skip(final long length) throws IOException {
        final long result = super.skip(length);
        if (result > 0) {
            count += result;
        }
        return result;
    }

    /**
     * get the total number of bytes read/skipped from this stream
     * 
     * @return long number of bytes read/skipped from this stream
     */
    public long getCount() {
        return count;
    }

}
