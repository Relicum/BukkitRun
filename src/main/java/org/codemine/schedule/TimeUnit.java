package org.codemine.schedule;

/**
 * A <tt>TimeUnit</tt> represents time durations at a given unit of
 * granularity and provides utility methods to convert across units,
 * this now includes the minecraft Tick as a unit of time.
 * A <tt>TimeUnit</tt> does not maintain time information, but only
 * helps organize and use time representations that may be maintained
 * separately across various contexts.  A millisecond is defined as one
 * fiftieth of a tick, a tick is 1 twentieth of a second, a second is 1 sixtieth of a minute
 * a minute is 1 sixtieth of an hour, an hour is a twenty fourth of a day.
 * <p/>
 * <p>A <tt>TimeUnit</tt> is mainly used to inform time-based methods
 * how a given timing parameter should be interpreted. For example,
 * the following code the developer wants a 25 second delay before the task starts. The example does not include
 * any modifications to BukkitRunnable, what will come next. But it does indicate how it can be used
 * <p/>
 * <tt>
 * <pre>
 * {@code
 * new BukkitRunnable() {
 *
 *       public void run() {
 *           System.out.println("Hello I was delayed 20 seconds");
 *       }
 *    }.runTaskLater(this,TimeUnit.SECONDS.toTicks(20l));
 * }
 * </pre></tt>
 * <p>If using modified BukkitRunnable you can pass the time value and the time unit</p>
 * <tt>
 * <pre>
 * {@code
 * new BukkitRunnable() {
 *
 *       public void run() {
 *           System.out.println("Hello I was delayed 1 minute");
 *       }
 *    }.runTaskLater(this,1l,TimeUnit.MINUTES);
 * }
 * </pre></tt>
 * <p/>
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
 * </tt></p>
 * Note however, that there is no guarantee that a particular timeout
 * implementation will be able to notice the passage of time at the
 * same granularity as the given <tt>TimeUnit</tt>.
 *
 * @author Doug Lea and Relicum
 */
public enum TimeUnit {
    MILLISECONDS {
        public long toMillis(long d){return d;}
        public long toTicks(long d){return d/(C1/C0);}
        public long toSeconds(long d){return d/(C2/C0);}
        public long toMinutes(long d){return d/(C3/C0);}
        public long toHours(long d){return d/(C4/C0);}
        public long toDays(long d){return d/(C5/C0);}
        public long convert(long d,TimeUnit u){return u.toMillis(d);}
    },
    TICKS {
        public long toMillis(long d){return x(d,C1/C0,MAX/(C1/C0));}
        public long toTicks(long d){return d;}
        public long toSeconds(long d){return d/(C2/C1);}
        public long toMinutes(long d){return d/(C3/C1);}
        public long toHours(long d){return d/(C4/C1);}
        public long toDays(long d){return d/(C5/C1);}
        public long convert(long d,TimeUnit u){return u.toTicks(d);}
    },
    SECONDS {
        public long toMillis(long d){return x(d,C2/C0,MAX/(C2/C0));}
        public long toTicks(long d){return x(d,C2/C1,MAX/(C2/C1));}
        public long toSeconds(long d){return d;}
        public long toMinutes(long d){return d/(C3/C2);}
        public long toHours(long d){return d/(C4/C2);}
        public long toDays(long d){return d/(C5/C2);}
        public long convert(long d,TimeUnit u){return u.toSeconds(d);}
    },
    MINUTES {
        public long toMillis(long d){return x(d,C3/C0,MAX/(C3/C0));}
        public long toTicks(long d){return x(d,C3/C1,MAX/(C3/C1));}
        public long toSeconds(long d){return x(d,C3/C2,MAX/(C3/C2));}
        public long toMinutes(long d){return d;}
        public long toHours(long d){return d/(C4/C3);}
        public long toDays(long d){return d/(C5/C3);}
        public long convert(long d,TimeUnit u){return u.toMinutes(d);}
    },
    HOURS {
        public long toMillis(long d){return x(d,C4/C0,MAX/(C4/C0));}
        public long toTicks(long d){return x(d,C4/C1,MAX/(C4/C1));}
        public long toSeconds(long d){return x(d,C4/C2,MAX/(C4/C2));}
        public long toMinutes(long d){return x(d,C4/C3,MAX/(C4/C3));}
        public long toHours(long d){return d;}
        public long toDays(long d){return d/(C5/C4);}
        public long convert(long d,TimeUnit u){return u.toHours(d);}
    },
    DAYS {
        public long toMillis(long d){return x(d,C5/C0,MAX/(C4/C0));}
        public long toTicks(long d){return x(d,C5/C1,MAX/(C4/C1));}
        public long toSeconds(long d){return x(d,C5/C2,MAX/(C4/C2));}
        public long toMinutes(long d){return x(d,C5/C3,MAX/(C4/C3));}
        public long toHours(long d){return x(d,C5/C4,MAX/(C5/C4));}
        public long toDays(long d){return d;}
        public long convert(long d,TimeUnit u){return u.toDays(d);}
    };
    private static final long C0 = 1L;
    private static final long C1 = C0*50L;
    private static final long C2 = C1*20L;
    private static final long C3 = C2*60L;
    private static final long C4 = C3*60L;
    private static final long C5 = C4*24L;
    private static final long MAX = Long.MAX_VALUE;
    /**
     * Scale duration by Multiplier.
     * Checks for overflows
     */
    private static long x(long d,long m,long over){
        if (d > over) return Long.MAX_VALUE;
        if (d < -over) return Long.MIN_VALUE;
        return d*m;
        // To maintain full signature compatibility with 1.5, and to improve the
        // clarity of the generated javadoc (see 6287639: Abstract methods in
        // enum classes should not be listed as abstract), method convert
        // etc. are not declared abstract but otherwise act as abstract methods.
    }
    /**
     * Convert the given time duration in the given unit to this
     * unit.  Conversions from finer to coarser granularities
     * truncate, so lose precision. For example converting
     * <tt>999</tt> milliseconds to seconds results in
     * <tt>0</tt>. Conversions from coarser to finer granularities
     * with arguments that would numerically overflow saturate to
     * <tt>Long.MIN_VALUE</tt> if negative or <tt>Long.MAX_VALUE</tt>
     * if positive.
     * <p/>
     * <p>For example, to convert 10 minutes to ticks, use:
     * <tt>TimeUnit.MINUTES.convert(10L, TimeUnit.TICKS)</tt>
     *
     * @param sourceDuration the time duration in the given <tt>sourceUnit</tt>
     * @param sourceUnit     the unit of the <tt>sourceDuration</tt> argument
     * @return the converted duration in this unit,
     * or <tt>Long.MIN_VALUE</tt> if conversion would negatively
     * overflow, or <tt>Long.MAX_VALUE</tt> if it would positively overflow.
     */
    public long convert(long sourceDuration,TimeUnit sourceUnit){
        throw new AbstractMethodError();
    }
    /**
     * Equivalent to <tt>MILLISECONDS.convert(duration, this)</tt>.
     *
     * @param duration the duration
     * @return the converted duration,
     * or <tt>Long.MIN_VALUE</tt> if conversion would negatively
     * overflow, or <tt>Long.MAX_VALUE</tt> if it would positively overflow.
     * @see #convert
     */
    public long toMillis(long duration){
        throw new AbstractMethodError();
    }
    /**
     * A Tick is not a unit of time, but could be called an "occurrence".
     * This class defines a Tick as the frequency the minecraft, game loop runs.
     * Under normal conditions this runs 20 times a second. It can now be defined
     * as a unit of time which is 1/20 of a second.
     * <p/>
     * Equivalent to <tt>TICKS.convert(duration, this)</tt>.
     *
     * @param duration the duration
     * @return the converted duration,
     * or <tt>Long.MIN_VALUE</tt> if conversion would negatively
     * overflow, or <tt>Long.MAX_VALUE</tt> if it would positively overflow.
     * @see #convert
     */
    public long toTicks(long duration){
        throw new AbstractMethodError();
    }
    /**
     * Equivalent to <tt>SECONDS.convert(duration, this)</tt>.
     *
     * @param duration the duration
     * @return the converted duration,
     * or <tt>Long.MIN_VALUE</tt> if conversion would negatively
     * overflow, or <tt>Long.MAX_VALUE</tt> if it would positively overflow.
     * @see #convert
     */
    public long toSeconds(long duration){
        throw new AbstractMethodError();
    }
    /**
     * Equivalent to <tt>SECONDS.convert(duration, this)</tt>.
     *
     * @param duration the duration
     * @return the converted duration,
     * or <tt>Long.MIN_VALUE</tt> if conversion would negatively
     * overflow, or <tt>Long.MAX_VALUE</tt> if it would positively overflow.
     * @see #convert
     */
    public long toMinutes(long duration){
        throw new AbstractMethodError();
    }
    /**
     * Equivalent to <tt>HOURS.convert(duration, this)</tt>.
     *
     * @param duration the duration
     * @return the converted duration,
     * or <tt>Long.MIN_VALUE</tt> if conversion would negatively
     * overflow, or <tt>Long.MAX_VALUE</tt> if it would positively overflow.
     * @see #convert
     */
    public long toHours(long duration){
        throw new AbstractMethodError();
    }
    /**
     * Equivalent to <tt>DAYS.convert(duration, this)</tt>.
     *
     * @param duration the duration
     * @return the converted duration
     * @see #convert
     */
    public long toDays(long duration){
        throw new AbstractMethodError();
    }
}
