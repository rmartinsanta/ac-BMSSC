package es.urjc.etsii.grafo.BMSSC.experiment;

import es.urjc.etsii.grafo.BMSSC.model.BMSSCInstance;
import es.urjc.etsii.grafo.BMSSC.model.sol.BMSSCSolution;
import es.urjc.etsii.grafo.algorithms.Algorithm;
import es.urjc.etsii.grafo.algorithms.multistart.MultiStartAlgorithm;
import es.urjc.etsii.grafo.autoconfig.irace.AutomaticAlgorithmBuilder;
import es.urjc.etsii.grafo.experiment.AbstractExperiment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FinalExperiment extends AbstractExperiment<BMSSCSolution, BMSSCInstance> {

    private final AutomaticAlgorithmBuilder<BMSSCSolution, BMSSCInstance> builder;

    public FinalExperiment(AutomaticAlgorithmBuilder<BMSSCSolution, BMSSCInstance> builder) {
        this.builder = builder;
    }

    @Override
    public List<Algorithm<BMSSCSolution, BMSSCInstance>> getAlgorithms() {
        var algorithms = new ArrayList<Algorithm<BMSSCSolution, BMSSCInstance>>();

        String[] iraceOutput = """
                                
                """.split("\n");

        algorithms.add(sotaAlgorithm());
        for (int i = 0; i < iraceOutput.length; i++) {
            if (!iraceOutput[i].isBlank()) {
                var algorithm = builder.buildFromStringParams(iraceOutput[i].trim());
                // Wrap algorithms as multistart with "infinite" iterations, so we are consistent with the autoconfig engine.
                // Algorithms will automatically stop when they reach the timelimit for a given instance
                var multistart = new MultiStartAlgorithm<>("ac" + i, algorithm, 1_000_000, 1_000_000, 1_000_000);
                algorithms.add(multistart);
            }
        }

        return algorithms;
    }

    public Algorithm<BMSSCSolution, BMSSCInstance> sotaAlgorithm(){
        throw new UnsupportedOperationException();
    }

}
