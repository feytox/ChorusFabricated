package chorusfabricated;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class DelayedTask {

    public static final ObjectArrayList<DelayedTask> tasks = new ObjectArrayList<>();

    private final Runnable runnable;
    private int age;

    private DelayedTask(Runnable runnable, int ticks) {
        this.runnable = runnable;
        this.age = ticks;
    }

    private boolean tickAge() {
        age -= 1;
        return age <= 0;
    }

    private void execute() {
        runnable.run();
    }

    public static void schedule(Runnable runnable, int ticks) {
        DelayedTask task = new DelayedTask(runnable, ticks);
        tasks.add(task);
    }

    public static void tickTasks() {
        tasks.removeIf(task -> {
            boolean result = task.tickAge();
            if (!result) return false;
            task.execute();
            return true;
        });
    }
}
