/**
 * Custom implementation of both java {@link java.util.concurrent.TimeUnit} class and Bukkit {@link org.bukkit.scheduler.BukkitRunnable}.
 * <p>The intention is to make it easier when specifying units of time when using BukkitRunnable. Also to
 * do it in a way that standardise time units. While at the same time only adding functionality to the Bukkit API keeping it fully backwards compatible and not changing the internals of BukkitRunnable.</p>
 * <p>Currently when using a BukkitRunnable all periods of time are inputted in server ticks. While this seems the most logical way
 * a tick is not a unit of time. It is stated that there are 20 ticks per second. So you can concur a tick is 1/20 of a second</p>
 * <p>But this is very inflexible when actually using Ticks to specify delays and periods in a BukkitRunnable. For example if you wish the period for the task to run to be
 * every 5 seconds you can either use 100l, or (20l * 5l). While this is very simple maths it is very easy to make a mistake and makes it a lot harder to read the code.
 * Especially for example if the period is say every 20 minutes the task is to run which is 24000l ticks. Looking at 24000 will take even the best maths brains a second to calculate it back to be 20 minutes.</p>
 *
 * <h1>Possible solution</h1>
 * <p>Java already has a solution which they use in their java.utils.concurrent package called {@link java.util.concurrent.TimeUnit} this is used in a lot of the class in that package.
 * The problem is the as a Tick is not a unit of time it is not in the package. Also as the class is an enum it is not expendable, even if enum where A TimeUnit represents time durations at a given unit of granularity.
 * Means you can't just slot in a minecraft Tick. A decided to refactor the {@link java.util.concurrent.TimeUnit} to include the minecraft tick as a unit of time altering all the relevant maths to allow conversions between any of the following time units.
 * <ol>
 *     <li>Millisecond</li>
 *     <li>Tick</li>
 *     <li>Second</li>
 *     <li>Minute</li>
 *     <li>Hour</li>
 *     <li>Day</li>
 * </ol>
 *
 * <p>I also removed micro and nano time and some of the timeout methods to keep things simple for now.</p>
 * Full details of this refactored class and usage details can be found here <tt>{@link org.codemine.schedule.TimeUnit}</tt>.
 *
 * @version 0.0.1
 */
package org.codemine.schedule;
