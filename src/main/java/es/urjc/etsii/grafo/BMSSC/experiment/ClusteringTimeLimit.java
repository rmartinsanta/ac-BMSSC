package es.urjc.etsii.grafo.BMSSC.experiment;

import es.urjc.etsii.grafo.BMSSC.model.BMSSCInstance;
import es.urjc.etsii.grafo.BMSSC.model.sol.BMSSCSolution;
import es.urjc.etsii.grafo.algorithms.Algorithm;
import es.urjc.etsii.grafo.services.TimeLimitCalculator;
import es.urjc.etsii.grafo.util.TimeUtil;

import java.util.concurrent.TimeUnit;

import static es.urjc.etsii.grafo.experiment.reference.ReferenceResultProvider.EMPTY_REFERENCE_RESULT;

public class ClusteringTimeLimit extends TimeLimitCalculator<BMSSCSolution, BMSSCInstance> {

    private final ReimplementationResults results;

    public ClusteringTimeLimit(ReimplementationResults results){
        this.results = results;
    }

    @Override
    public long timeLimitInMillis(BMSSCInstance instance, Algorithm<BMSSCSolution, BMSSCInstance> algorithm) {
        var refResult = results.getValueFor(instance.getId());
        if(refResult == null || refResult == EMPTY_REFERENCE_RESULT){
            throw new IllegalArgumentException("invalid reference value for instance: " + instance.getId());
        }
        var nanos =  refResult.getTimeInNanos();
        // Use as timelimit twice the time of the sota algorithm, and analyze how it evolves
        return TimeUtil.convert(nanos, TimeUnit.NANOSECONDS, TimeUnit.MILLISECONDS) * 2;
    }
}
