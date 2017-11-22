package com.trainline.testing.fixture;

import com.flextrade.jfixture.SpecimenBuilder;
import com.flextrade.jfixture.SpecimenContext;
import java.util.Stack;


public class RecursionCounter implements SpecimenBuilder {

    private final Stack<Object> monitoredRequests = new Stack<>();
    private final SpecimenBuilder builder;
    private final int depth;

    public RecursionCounter(SpecimenBuilder builder, int depth) {
        this.builder = builder;
        this.depth = depth;
    }

    @Override
    public Object create(Object request, SpecimenContext context) {
        int counter = 0;
        for (Object monitoredRequest : this.monitoredRequests) {
            if (request.equals(monitoredRequest)) {
                counter ++;

            }
        }
        if (counter >= depth) {
            return null;
        }

        this.monitoredRequests.push(request);
        try {
            return this.builder.create(request, context);
        } finally {
            this.monitoredRequests.pop();
        }
    }

}
