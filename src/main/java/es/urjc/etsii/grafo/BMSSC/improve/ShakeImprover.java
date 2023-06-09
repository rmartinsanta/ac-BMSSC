package es.urjc.etsii.grafo.BMSSC.improve;

import es.urjc.etsii.grafo.BMSSC.model.BMSSCInstance;
import es.urjc.etsii.grafo.BMSSC.model.sol.BMSSCSolution;
import es.urjc.etsii.grafo.algorithms.FMode;
import es.urjc.etsii.grafo.annotations.AutoconfigConstructor;
import es.urjc.etsii.grafo.improve.Improver;
import es.urjc.etsii.grafo.shake.Shake;

import static es.urjc.etsii.grafo.util.DoubleComparator.*;

public class ShakeImprover extends Improver<BMSSCSolution, BMSSCInstance> {

    private final Improver<BMSSCSolution, BMSSCInstance> improver;
    private final Shake<BMSSCSolution, BMSSCInstance> shake;

    @AutoconfigConstructor
    public ShakeImprover(Improver<BMSSCSolution, BMSSCInstance> improver, Shake<BMSSCSolution, BMSSCInstance> shake) {
        super(FMode.MINIMIZE);
        this.improver = improver;
        this.shake = shake;
    }

    @Override
    protected BMSSCSolution _improve(BMSSCSolution solution) {
        solution = improver.improve(solution);
        var copy = solution.cloneSolution();
        copy = shake.shake(copy, 1);
        copy = improver.improve(copy);
        if(isLess(copy.getScore(), solution.getScore())) {
            return copy;
        } else {
            return solution;
        }
    }

    @Override
    public String toString() {
        return "ShakeImprover{" +
                "improver=" + improver +
                ", shake=" + shake +
                '}';
    }
}
