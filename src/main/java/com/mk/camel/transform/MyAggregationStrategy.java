package com.mk.camel.transform;

import com.mk.camel.model.StudentAllocation;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class MyAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        String allocated = newExchange.getIn().getBody(String.class);

        if (oldExchange == null) {
            StudentAllocation st = new StudentAllocation();
            updateStudentAllocation(allocated, st);
            newExchange.getIn().setBody(st);
            return newExchange;
        } else {
            StudentAllocation old = oldExchange.getIn().getBody(StudentAllocation.class);
            updateStudentAllocation(allocated, old);
            return oldExchange;
        }
    }

    private void updateStudentAllocation(String allocated, StudentAllocation stAll) {
        if (allocated.equalsIgnoreCase("nursery")) {
            stAll.setNursery(stAll.getNursery() + 1);
        } else if (allocated.equalsIgnoreCase("reception")) {
            stAll.setReception(stAll.getReception() + 1);
        } else {
            stAll.setOthers(stAll.getOthers() + 1);
        }
    }

}