package org.codemine.schedule;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * A <tt>BukkitRun</tt> is the same as a BukkitRunnable with 4 extra overloaded methods.
 * <p>This Class is a direct copy code by the Bukkit Dev Team, I can't claim any credit at all. My overloaded methods, require about
 * 10 lines of code. The reason for copying the entire class was due to the scope of some of the properties. I have included the
 * GPL 3 license and have followed the requirements. The main reason for this class is to demonstrate my customised {@link org.codemine.schedule.TimeUnit} class.
 * <p>This class is provided as an easy way to handle scheduling tasks, with the extra feature of
 * being able to accept {@link org.codemine.schedule.TimeUnit} as a valid input.</p>
 * <p>The 4 extra methods <tt>BukkitRun</tt> has are overload methods already in the default class.
 * <ol>
 *     <li>{@link org.codemine.schedule.BukkitRun#runTaskLater(org.bukkit.plugin.Plugin, long, TimeUnit)}</li>
 *     <li>{@link org.codemine.schedule.BukkitRun#runTaskLaterAsynchronously(org.bukkit.plugin.Plugin, long, TimeUnit)}</li>
 *     <li>{@link org.codemine.schedule.BukkitRun#runTaskTimer(org.bukkit.plugin.Plugin, long, long, TimeUnit)}</li>
 *     <li>{@link org.codemine.schedule.BukkitRun#runTaskTimerAsynchronously(org.bukkit.plugin.Plugin, long, long, TimeUnit)}</li>
 * </ol>
 * <hr>
 * Am example usage can be seen below. This sets the task to be delayed by 1 minute.
 * <tt>
 * <pre>
 * {@code
 * new BukkitRun() {
 *
 *       public void run() {
 *           System.out.println("Hello I was delayed 1 minute");
 *       }
 *    }.runTaskLater(this,1l,TimeUnit.MINUTES);
 * }
 * </pre></tt>
 * <p>
 * You can also use it with the Bukkit scheduler without any modifications.The following will delay the task by 15 seconds.
 * <tt>
 * <pre>
 * {@code
 *         Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this,new Runnable() {
 *             public void run() {
 *                 System.out.println("Hi I was delayed by 15 seconds")
 *             }
 *         },TimeUnit.SECONDS.toTicks(15l));
 * }
 * </pre>
 * </tt>
 * </p>
 * @version 1.0-SNAPSHOT
 */
public abstract class BukkitRun implements Runnable {

    private int taskId = -1;
    /**
     * Attempts to cancel this task.
     *
     * @throws IllegalStateException if task was not scheduled yet
     */
    public synchronized void cancel() throws IllegalStateException{
        Bukkit.getScheduler().cancelTask(getTaskId());
    }
    /**
     * Schedules this in the Bukkit scheduler to run on next tick.
     *
     * @param plugin the reference to the plugin scheduling task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     * @see org.bukkit.scheduler.BukkitScheduler#runTask(org.bukkit.plugin.Plugin,Runnable)
     */
    public synchronized BukkitTask runTask(Plugin plugin) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTask(plugin,this));
    }
    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p/>
     * Schedules this in the Bukkit scheduler to run asynchronously.
     *
     * @param plugin the reference to the plugin scheduling task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     * @see org.bukkit.scheduler.BukkitScheduler#runTaskAsynchronously(Plugin,Runnable)
     */
    public synchronized BukkitTask runTaskAsynchronously(Plugin plugin) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskAsynchronously(plugin,this));
    }
    /**
     * Schedules this to run after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay  the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     * @see org.bukkit.scheduler.BukkitScheduler#runTaskLater(Plugin,Runnable,long)
     */
    public synchronized BukkitTask runTaskLater(Plugin plugin,long delay) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskLater(plugin,this,delay));
    }
    /**
     * Schedules this to run after the specified delay value and the {@link org.codemine.schedule.TimeUnit} .
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay  the delay value to wait before running the task
     * @param unit   the unit of time the delay value relates to
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     */
    public synchronized BukkitTask runTaskLater(Plugin plugin,long delay,TimeUnit unit) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskLater(plugin,this,unit.toTicks(delay)));
    }
    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p/>
     * Schedules this to run asynchronously after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay  the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     * @see org.bukkit.scheduler.BukkitScheduler#runTaskLaterAsynchronously(Plugin,Runnable,long)
     */
    public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin,long delay) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin,this,delay));
    }
    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p/>
     * Schedules this to run asynchronously after the specified delay value and the {@link org.codemine.schedule.TimeUnit} .
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay the delay value to wait before running the task
     * @param unit  the unit of time the delay value relates to
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     */
    public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin,long delay,TimeUnit unit) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin,this,unit.convert(delay,TimeUnit.TICKS)));
    }
    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay  the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     * @see org.bukkit.scheduler.BukkitScheduler#runTaskTimer(Plugin,Runnable,long,long)
     */
    public synchronized BukkitTask runTaskTimer(Plugin plugin,long delay,long period) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskTimer(plugin,this,delay,period));
    }
    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified delay value and period value and the {@link org.codemine.schedule.TimeUnit} .
     *
     * @param plugin the reference to the plugin scheduling task
     * @param period the period to wait between runs
     * @param delay  the delay value to wait before running the task
     * @param unit   the unit of time the delay value relates to
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     */
    public synchronized BukkitTask runTaskTimer(Plugin plugin,long delay,long period,TimeUnit unit) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskTimer(plugin,this,unit.toTicks(delay),unit.toTicks(period)));
    }
    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p/>
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay  the ticks to wait before running the task for the first time
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     * @see org.bukkit.scheduler.BukkitScheduler#runTaskTimerAsynchronously(Plugin,Runnable,long,long)
     */
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin,long delay,long period) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,this,delay,period));
    }
    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p/>
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified delay value and period value and the {@link org.codemine.schedule.TimeUnit} .
     *
     * @param plugin the reference to the plugin scheduling task
     * @param period the period to wait between runs
     * @param delay  the delay value to wait before running the task
     * @param unit   the unit of time the delay value relates to
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if plugin is null
     * @throws IllegalStateException    if this was already scheduled
     */
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin,long delay,long period,TimeUnit unit) throws IllegalArgumentException, IllegalStateException{
        checkState();
        return setupId(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,this,unit.toTicks(delay),unit.toTicks(period)));
    }
    /**
     * Gets the task id for this runnable.
     *
     * @return the task id that this runnable was scheduled as
     * @throws IllegalStateException if task was not scheduled yet
     */
    public synchronized int getTaskId() throws IllegalStateException{
        final int id = taskId;
        if (id == -1) {
            throw new IllegalStateException("Not scheduled yet");
        }
        return id;
    }
    private void checkState(){
        if (taskId != -1) {
            throw new IllegalStateException("Already scheduled as " + taskId);
        }
    }
    private BukkitTask setupId(final BukkitTask task){
        this.taskId = task.getTaskId();
        return task;
    }
}
