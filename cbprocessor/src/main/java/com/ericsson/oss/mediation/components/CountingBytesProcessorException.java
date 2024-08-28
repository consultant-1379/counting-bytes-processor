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

/**
 * Exception that will be thrown during processing
 * 
 * @author edejket
 * 
 */
public class CountingBytesProcessorException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2755154919783213959L;

    public CountingBytesProcessorException(final Exception e) {
        super(e);
    }

}
