package es.urjc.etsii.grafo.BMSSC.create;

import es.urjc.etsii.grafo.BMSSC.model.BMSSCInstance;
import es.urjc.etsii.grafo.BMSSC.model.sol.AssignMove;
import es.urjc.etsii.grafo.BMSSC.model.sol.BMSSCSolution;
import es.urjc.etsii.grafo.create.Constructive;
import es.urjc.etsii.grafo.solution.metrics.Metrics;
import es.urjc.etsii.grafo.solution.metrics.MetricsManager;
import es.urjc.etsii.grafo.util.random.RandomManager;

import java.util.ArrayList;

public class RandomConstructor extends Constructive<BMSSCSolution, BMSSCInstance> {

    @Override
    public BMSSCSolution construct(BMSSCSolution solution) {

        var r = RandomManager.getRandom();
        var ins = solution.getInstance();

        ArrayList<Counter> counters = new ArrayList<>(ins.k);
        for (int i = 0; i < ins.k; i++) {
            counters.add(new Counter(i, ins.getClusterSize(i)));
        }

        for (int i = 0; i < ins.n; i++) {
            // nextInt [0, k) --> [1, k]
            int randomNumber = r.nextInt(counters.size());
            Counter c = counters.get(randomNumber);
            var move = new AssignMove(solution, i, c.n);
            move.execute(solution);

            if (--c.counter == 0) {
                counters.remove(randomNumber);
            }
        }

        solution.notifyUpdate();
        MetricsManager.addDatapoint(Metrics.BEST_OBJECTIVE_FUNCTION, solution.getScore());
        solution.generateCachedScore();
        return solution;
    }

    private static class Counter {
        /**
         * Cluster id
         */
        public int n;

        /**
         * Number of points until cluster is full
         */
        public int counter;

        public Counter(int n, int p) {
            this.n = n;
            this.counter = p;
        }
    }
}
