/*
 * Attach.java — macOS dynamic-attach helper for the v4-chat agent.
 *
 * Loads agent.jar into an already-running JVM (e.g. Minecraft) via the
 * Attach API. Requires a full JDK (module jdk.attach) on the same user.
 *
 *   javac --add-modules jdk.attach -d . Attach.java
 *   java  --add-modules jdk.attach -cp . Attach <pid> [agent.jar] [agentArgs]
 *
 * Or just use attach.sh, which finds the PID for you.
 */

import com.sun.tools.attach.VirtualMachine;

public final class Attach {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("usage: Attach <pid> [agent.jar] [agentArgs]");
            System.exit(2);
        }
        String pid = args[0];
        String jar = args.length > 1 ? args[1] : "agent.jar";
        String agentArgs = args.length > 2 ? args[2] : "";

        VirtualMachine vm = VirtualMachine.attach(pid);
        try {
            vm.loadAgent(jar, agentArgs);
        } finally {
            vm.detach();
        }
        System.out.println("[Attach] loaded " + jar + " into pid " + pid);
    }
}
