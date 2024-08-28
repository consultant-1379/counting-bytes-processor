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
package com.ericsson.oss.mediaiton.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.camel.EndpointInject;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;

import com.ericsson.oss.mediation.components.CountingBytesProcessor;
import com.ericsson.oss.mediation.components.CountingInputStream;

public class CountingBytesProcessorTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    private final File testFile = new File(
            "src/test/resources/ByteCountProcessorTestFile.txt");

    /**
     * Verify that we have file to read, and it is not an empty file
     */
    @Test
    public void testAssertTestFileIsInPlace() {
        Assert.assertTrue(testFile.exists());
        Assert.assertTrue(testFile.length() > 0);
    }

    @Test
    public void testSendCorrectMessageExchangeCount() throws Exception {
        final FileInputStream fis = new FileInputStream(testFile);
        final InputStream is = (InputStream) fis;
        template.sendBodyAndHeaders(is, new HashMap<String, Object>());
        Assert.assertTrue(resultEndpoint.getExchanges().size() == 1);

    }

    @Test
    public void testSendCorrectMessage() throws Exception {
        final FileInputStream fis = new FileInputStream(testFile);
        final InputStream is = (InputStream) fis;
        final Long expectedFileSize = testFile.length();
        template.sendBodyAndHeaders(is, new HashMap<String, Object>());
        final Message message = resultEndpoint.getExchanges().get(0).getIn();
        Assert.assertNotNull(message.getBody());
        Assert.assertTrue(message.getBody() instanceof CountingInputStream);
        final CountingInputStream cis = message
                .getBody(CountingInputStream.class);
        while (cis.read() > 0) {

        }
        Assert.assertEquals(Long.valueOf(expectedFileSize),
                Long.valueOf(cis.getCount()));

    }

    @Test
    public void testSendCorrectMessageAfterProcessorInputStreamIsStillOpen()
            throws Exception {
        final FileInputStream fis = new FileInputStream(testFile);
        final InputStream is = (InputStream) fis;
        template.sendBodyAndHeaders(is, new HashMap<String, Object>());
        final Message message = resultEndpoint.getExchanges().get(0).getIn();
        final InputStream actualInputStream = message
                .getBody(CountingInputStream.class);
        while (actualInputStream.read() > 0) {

        }

    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() throws IllegalStateException {
                final Processor processor = new CountingBytesProcessor();
                from("direct:start").process(processor).to("mock:result")
                        .setId("testRoute");

            }
        };
    }
}
