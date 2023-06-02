package es.urjc.etsii.grafo.BMSSC.improve;


import es.urjc.etsii.grafo.BMSSC.model.BMSSCInstance;
import es.urjc.etsii.grafo.BMSSC.model.sol.BMSSCSolution;
import es.urjc.etsii.grafo.BMSSC.model.sol.SwapMove;
import es.urjc.etsii.grafo.annotations.AutoconfigConstructor;
import es.urjc.etsii.grafo.improve.Improver;
import es.urjc.etsii.grafo.solution.metrics.Metrics;
import es.urjc.etsii.grafo.solution.metrics.MetricsManager;
import es.urjc.etsii.grafo.solver.Mork;
import es.urjc.etsii.grafo.util.TimeControl;

import static es.urjc.etsii.grafo.util.DoubleComparator.isLess;
import static es.urjc.etsii.grafo.util.DoubleComparator.isNegative;

public class BestImpLS extends Improver<BMSSCSolution, BMSSCInstance> {

    @AutoconfigConstructor
    public BestImpLS() {
        super(Mork.getFMode());
    }

    public boolean iteration(BMSSCSolution s) {

        var ins = s.getInstance();
        SwapMove bestMove = null;
        for (int i = 0; i < ins.n - 1; i++) {
            for (int j = i + 1; j < ins.n; j++) {
                if (s.clusterOf(i) == s.clusterOf(j))
                    continue;
                var swap = new SwapMove(s, i, j);
                if(bestMove == null || isLess(swap.getValue(), bestMove.getValue())){
                    bestMove = swap;
                }
            }
        }

        if (bestMove != null && isNegative(bestMove.getValue())){
            bestMove.execute(s);
            MetricsManager.addDatapoint(Metrics.BEST_OBJECTIVE_FUNCTION, s.getScore());
        }
        return bestMove != null;
    }

    @Override
    protected BMSSCSolution _improve(BMSSCSolution solution) {
        int rounds = 0;
        while (!TimeControl.isTimeUp() && iteration(solution)){
            rounds++;
        }
        return solution;
    }
}
