package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // Task 2: Complete this class
    private final BreedFetcher fetcher;
    private final Map<String, List<String>> cache = new HashMap<>();
    private int callsMade = 0;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedFetcher.BreedNotFoundException {
        // return statement included so that the starter code can compile and run.
        if (cache.containsKey(breed)) {
            return cache.get(breed);
        }
        try {
            List<String> result = fetcher.getSubBreeds(breed);
            callsMade++;
            cache.put(breed, result);
            return result;
        } catch (BreedFetcher.BreedNotFoundException ex) {
            throw ex;
        }
    }
    public int getCallsMade() {
        return callsMade;
    }
}