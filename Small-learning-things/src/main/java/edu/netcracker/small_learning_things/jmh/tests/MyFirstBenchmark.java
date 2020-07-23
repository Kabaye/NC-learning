package edu.netcracker.small_learning_things.jmh.tests;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Timeout;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Timeout(time = 10, timeUnit = TimeUnit.NANOSECONDS)
public class MyFirstBenchmark {
    private Map<String, String> map;

    @Setup
    public void prepare() {
        map = new HashMap<>();
        map.put("1", "Test 1");
        map.put("2", "Test 2");
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.INLINE)
    public String testMethod1() {
        return map.get("1");
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void testMethod2() {
        map.get("1");
    }
}
