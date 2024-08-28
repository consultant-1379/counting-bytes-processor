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

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor used to count number of bytes in the body of the exchange. This
 * processor will take input stream from message body and replace it in the
 * message body with <code>CountingInputStream</code>, CountingInputStream class
 * has getCount() method that will return number of bytes read from this stream.
 * 
 * @see com.ericsson.nms.mediation.camel.components.eftp.CountingInputStream
 * @see com.ericsson.nms.mediation.camel.components.eftp.CountingInputStream#getCount
 * 
 * @author edejket
 * 
 */
public class CountingBytesProcessor implements Processor {

    private static final Logger logger = LoggerFactory
            .getLogger(CountingBytesProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(final Exchange exchange)
            throws CountingBytesProcessorException {

        logger.debug("ByteCountProcessor processing exchange...");
        final Message message = exchange.getIn();
        final InputStream is = message.getBody(InputStream.class);
        final CountingInputStream cis = new CountingInputStream(is);
        message.setBody(cis);
        exchange.setIn(message);
        logger.debug("ByteCountProcessor -done: Counting input stream created.");
    }

}
