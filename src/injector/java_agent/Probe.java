/*
 * Probe.java — tiny main used by run-agent.sh so `java -javaagent:agent.jar`
 * has a class to launch. The agent's premain output is the interesting part;
 * main just prints a line and exits.
 */
public final class Probe {
    public static void main(String[] args) {
        System.out.println("[Probe] main reached — agent premain already ran.");
    }
}
