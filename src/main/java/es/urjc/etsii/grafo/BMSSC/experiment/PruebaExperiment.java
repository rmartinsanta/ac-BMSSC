package es.urjc.etsii.grafo.BMSSC.experiment;

import es.urjc.etsii.grafo.BMSSC.model.BMSSCInstance;
import es.urjc.etsii.grafo.BMSSC.model.sol.BMSSCSolution;
import es.urjc.etsii.grafo.algorithms.Algorithm;
import es.urjc.etsii.grafo.algorithms.multistart.MultiStartAlgorithm;
import es.urjc.etsii.grafo.autoconfig.irace.AutomaticAlgorithmBuilder;
import es.urjc.etsii.grafo.experiment.AbstractExperiment;

import java.util.List;
import java.util.stream.Collectors;

public class PruebaExperiment extends AbstractExperiment<BMSSCSolution, BMSSCInstance> {

    private final AutomaticAlgorithmBuilder<BMSSCSolution, BMSSCInstance> builder;

    public PruebaExperiment(AutomaticAlgorithmBuilder<BMSSCSolution, BMSSCInstance> builder) {
        this.builder = builder;
    }

    @Override
    public List<Algorithm<BMSSCSolution, BMSSCInstance>> getAlgorithms() {

        List<Algorithm<BMSSCSolution, BMSSCInstance>> algorithms = List.of(
                builder.buildFromStringParams("ROOT=VNS ROOT_VNS.constructive=RandomGreedyGRASPConstructive ROOT_VNS.constructive_RandomGreedyGRASPConstructive.alpha=0.21 ROOT_VNS.constructive_RandomGreedyGRASPConstructive.candidateListManager=BMSSCListManager ROOT_VNS.improver=VND ROOT_VNS.improver_VND.improver1=ShakeDatLS ROOT_VNS.improver_VND.improver1_ShakeDatLS.improver=NullImprover ROOT_VNS.improver_VND.improver1_ShakeDatLS.shake=StrategicOscillation ROOT_VNS.improver_VND.improver1_ShakeDatLS.shake_StrategicOscillation.increment=0.50 ROOT_VNS.improver_VND.improver2=VND ROOT_VNS.improver_VND.improver2_VND.improver1=FirstImpLS ROOT_VNS.improver_VND.improver2_VND.improver2=FirstImpLS   ROOT_VNS.improver_VND.improver2_VND.improver3=FirstImpLS ROOT_VNS.improver_VND.improver3=BestImpLS ROOT_VNS.maxK=38 ROOT_VNS.shake=StrategicOscillation ROOT_VNS.shake_StrategicOscillation.increment=0.79"),
                builder.buildFromStringParams("ROOT=VNS ROOT_VNS.constructive=RandomGreedyGRASPConstructive ROOT_VNS.constructive_RandomGreedyGRASPConstructive.alpha=0.75 ROOT_VNS.constructive_RandomGreedyGRASPConstructive.candidateListManager=BMSSCListManager ROOT_VNS.improver=VND ROOT_VNS.improver_VND.improver1=ShakeDatLS ROOT_VNS.improver_VND.improver1_ShakeDatLS.improver=NullImprover ROOT_VNS.improver_VND.improver1_ShakeDatLS.shake=StrategicOscillation ROOT_VNS.improver_VND.improver1_ShakeDatLS.shake_StrategicOscillation.increment=0.33 ROOT_VNS.improver_VND.improver2=VND ROOT_VNS.improver_VND.improver2_VND.improver1=FirstImpLS ROOT_VNS.improver_VND.improver2_VND.improver2=NullImprover ROOT_VNS.improver_VND.improver2_VND.improver3=FirstImpLS ROOT_VNS.improver_VND.improver3=BestImpLS ROOT_VNS.maxK=20 ROOT_VNS.shake=StrategicOscillation ROOT_VNS.shake_StrategicOscillation.increment=0.45"), // <-- ganadora segun Excel
                builder.buildFromStringParams("ROOT=VNS ROOT_VNS.constructive=RandomGreedyGRASPConstructive ROOT_VNS.constructive_RandomGreedyGRASPConstructive.alpha=0.32 ROOT_VNS.constructive_RandomGreedyGRASPConstructive.candidateListManager=BMSSCListManager ROOT_VNS.improver=VND ROOT_VNS.improver_VND.improver1=ShakeDatLS ROOT_VNS.improver_VND.improver1_ShakeDatLS.improver=NullImprover ROOT_VNS.improver_VND.improver1_ShakeDatLS.shake=StrategicOscillation ROOT_VNS.improver_VND.improver1_ShakeDatLS.shake_StrategicOscillation.increment=0.36 ROOT_VNS.improver_VND.improver2=VND ROOT_VNS.improver_VND.improver2_VND.improver1=FirstImpLS ROOT_VNS.improver_VND.improver2_VND.improver2=NullImprover ROOT_VNS.improver_VND.improver2_VND.improver3=FirstImpLS ROOT_VNS.improver_VND.improver3=BestImpLS ROOT_VNS.maxK=97 ROOT_VNS.shake=StrategicOscillation ROOT_VNS.shake_StrategicOscillation.increment=0.36")
//

                // El que tenemos publicado como referencia
//                new MultistartOnlyBestAppliesLS("Reimplementation", 100, new BMSSCGRASPConstructor(0.75), new ShakeDatLS(new FirstImpLS(), new StrategicOscillation(0.75)))
        );

        // Wrap algorithms as multistart with "infinite" iterations, so we are consistent with the autoconfig engine.
        // Algorithms will automatically stop when they reach the timelimit for a given instance
        return algorithms.stream().map(a -> new MultiStartAlgorithm<>(a.getShortName(), a, 1_000_000, 1_000_000, 1_000_000)).collect(Collectors.toList());
    }

}
