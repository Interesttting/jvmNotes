package com.jvm.test.alloc;

/**
 * 说明：本类中含有多个例子；java代码都是一样的但JVM参数不一样；通过参数配置的不同来反应JVM内存空间分配策略的不同；通过以下例子不能完全明白内存分配原理也没有关系；能够有所了解就行了。遇到了此类问题再仔细研究。
 * 学习目的：合理的配置区域的内存大小；这块JVM配置完全能够影响到应用程序的性能。并且对于大对象要谨慎使用；
 * 禁用用户级别（http线程级别）的大对象原因：
 * 频繁的创建大对象和消亡大对象对性能影响比较大。大对象一般都会直接存放到老年代；但是这些大对象生命周期短；发送minor gc时无法回收这些在老年代没有再使用的对象，所以大对象大量的堆积在老年代后，一旦发现老年代内存不够用了就会进行full gc；而且这种现象出现的频率很高
 * 应用级别的大对象一般可以用；毕竟应用级别的大对象生命周期和应用的生命周期一样。会长久的存放在老年代并且使gc root可达的。用户级别不应该使用大对象；每达到一定数量的请求就会发生一次full gc。周而复始。
 *
 * 分配策略有：
 * 1、对象优先在Eden分配，如果说Eden内存空间不足，就会发生Minor GC
 * 2、大对象直接进入老年代，大对象：需要大量连续内存空间的Java对象
 * 3、长期存活的对象将进入老年代，默认15岁（15次GC后未被回收），-XX:MaxTenuringThreshold调整
 * 4、动态对象年龄判定，为了能更好地适应不同程序的内存状况，虚拟机并不是永远地要求对象的年龄必须达到了MaxTenuringThreshold才能晋升老年代，如果在Survivor空间中相同年龄所有对象大小的总和大于Survivor空间的一半，年龄大于或等于该年龄的对象就可以直接进入老年代，无须等到MaxTenuringThreshold中要求的年龄
 * 5、空间分配担保：新生代中有大量的对象存活，survivor空间不够，当出现大量对象在MinorGC后仍然存活的情况（最极端的情况就是内存回收后新生代中所有对象都存活），就需要老年代进行分配担保，把Survivor无法容纳的对象直接进入老年代.只要老年代的连续空间大于新生代对象的总大小或者历次晋升的平均大小，就进行Minor GC，否则FullGC。
 * Minor gc 只会发生在新生代；full gc则 新生代、老年代、永久代都会进行垃圾回收
 *
 *
 * * ps：日志打印的时机：
 *  * [GC (Allocation Failure) [PSYoungGen: 3794K->1520K(5632K)] gc日志会在内存回收成功/失败或者分配失败情况下打印
 *  * Heap 会在程序执行完后打印
 *  *
 * 模板：
 *****************例子n****************************************************************************************************************************************************************
 *  1、JVM参数：
 *  2、JVM参数解释：
 *  3、控制台日志：
 *  4、分析（日志内容的分析）：
 *  5、例子中使用到的策略：
 *
 *
 * ***************例子1****************************************************************************************************************************************************************************************
 * 1、JVM参数：-Xms10M -Xmx20M -XX:+PrintGCDetails -XX:NewSize=2m -XX:MaxNewSize=2m -XX:SurvivorRatio=2
 * 2、JVM参数解释：-堆最小20m -堆最大20m 打印gc详情 -新生代最大和最小都是2m -Eden:FromSurvivor:ToSurvivor= 2:1:1（每一份为512k）
 * 3、控制台日志：
     * [GC (Allocation Failure) [PSYoungGen: 1020K->496K(1536K)] 1020K->496K(19968K), 0.0016472 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * Heap
     *  PSYoungGen      total 1536K, used 1162K [0x00000007bfe00000, 0x00000007c0000000, 0x00000007c0000000)
     *   eden space 1024K, 65% used [0x00000007bfe00000,0x00000007bfea6b38,0x00000007bff00000)
     *   from space 512K, 96% used [0x00000007bff00000,0x00000007bff7c010,0x00000007bff80000)
     *   to   space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
     *  ParOldGen       total 18432K, used 10240K [0x00000007bec00000, 0x00000007bfe00000, 0x00000007bfe00000)
     *   object space 18432K, 55% used [0x00000007bec00000,0x00000007bf6000a0,0x00000007bfe00000)
     *  Metaspace       used 3286K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 361K, capacity 388K, committed 512K, reserved 1048576K
 * 4、分析（日志内容的分析）：
     * PSYoungGen      total 1536K, used 1162K：新生代大小为2m，但是新生代中s0/s1其中之一是不能被直接使用的，是用于s0、s1之间的对象拷贝用的空间。
     * 新生代可用大小计算：Eden+from或者Eden+to
     *      SurvivorRatio=2 ~  Eden:FromSurvivor:ToSurvivor= 2:1:1（每一份为512k）
     *      eden=512*2=1m
     *      from=512*1
     *      to=512*1
     *      total=512*2+512=1536K
     * 老年代大小计算 堆空间大小-新生代空间大小=20m-2m=18m=18432K
     *
     *[GC (Allocation Failure) [PSYoungGen: 1020K->496K(1536K)] 1020K->496K(19968K), 0.0016472 secs]：
     * 发生gc由于内存(分配失败)[新生代内存占用1020降低为496（新生代内存总共大小1536k）]堆空间占用大小从1020K降低为496k（堆空间内存可用大小）。堆空间内存可用大小计算：堆最大内存值-from 或者 堆最大内存值-to =20M -512K=19968K
     * 对于执行这段时 int cap =1*1024*1024;
     *              byte[] bs0 =new byte[cap]; //bs0实际大小要大于1M，不单单存放了数组 还有其他内容例如数据下标等等。
     * 在新生代Eden区尝试分配空间；分配到一半发现Eden放不下bs0就触发了minor gc
     * ParOldGen       total 18432K, used 10240K ：
     * 最终把10个数组分配到了老年代
     *
 *5、例子中使用到的策略：
     *对象优先在Eden分配，如果说Eden内存空间不足，就会发生Minor GC
     *大对象直接进入老年代，大对象：需要大量连续内存空间的Java对象
 *
 *
 *****************例子2****************************************************************************************************************************************************************
 *  1、JVM参数：-Xms10M -Xmx20M -XX:+PrintGCDetails -XX:NewSize=7m -XX:MaxNewSize=7m -XX:SurvivorRatio=2
 *  2、JVM参数解释：-堆最小20m -堆最大20m 打印gc详情 -新生代最大和最小都是7m -Eden:FromSurvivor:ToSurvivor= 2:1:1（理论上每一份为1792k，实际上由于分配值必须是512的倍数，所以每份站1.5M也就是1536K，先分配s0、s1、一共3M，剩余4M分配给Eden；比值依然是2：1：1）
 *  3、控制台日志：
     * [GC (Allocation Failure) [PSYoungGen: 3794K->1520K(5632K)] 3794K->2576K(18944K), 0.0028163 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     * [GC (Allocation Failure) [PSYoungGen: 4712K->1520K(5632K)] 5768K->5656K(18944K), 0.0046079 secs] [Times: user=0.02 sys=0.01, real=0.00 secs]
     * [GC (Allocation Failure) [PSYoungGen: 4661K->1520K(5632K)] 8797K->8728K(18944K), 0.0032453 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
     * Heap
     *  PSYoungGen      total 5632K, used 3709K [0x00000007bf900000, 0x00000007c0000000, 0x00000007c0000000)
     *   eden space 4096K, 53% used [0x00000007bf900000,0x00000007bfb23790,0x00000007bfd00000)
     *   from space 1536K, 98% used [0x00000007bfd00000,0x00000007bfe7c020,0x00000007bfe80000)
     *   to   space 1536K, 0% used [0x00000007bfe80000,0x00000007bfe80000,0x00000007c0000000)
     *  ParOldGen       total 13312K, used 7208K [0x00000007bec00000, 0x00000007bf900000, 0x00000007bf900000)
     *   object space 13312K, 54% used [0x00000007bec00000,0x00000007bf30a070,0x00000007bf900000)
     *  Metaspace       used 3166K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
 *  4、分析（日志内容的分析）：
 *      eden、from、to大小非理论值1792k和3584K原因在于分配大小必须是512的倍数。
 *      ParOldGen       total 13312K, used 7208K 老年代放了7个数据对象 所以新生代放了3个数据对象（Eden存放2个，from存放1个）
 *  5、例子中使用到的策略：
     *对象优先在Eden分配，如果说Eden内存空间不足，就会发生Minor GC
     *大对象直接进入老年代，大对象：需要大量连续内存空间的Java对象
 *
 *
 *
 *
 *****************例子3****************************************************************************************************************************************************************
 *  1、JVM参数：-Xms10M -Xmx20M -XX:+PrintGCDetails -XX:NewSize=15m -XX:MaxNewSize=15m -XX:SurvivorRatio=8
 *  2、JVM参数解释：-堆最小20m -堆最大20m 打印gc详情 -新生代最大和最小都是15m -Eden:FromSurvivor:ToSurvivor= 8:1:1（每一份为1536k：计算方式参考例子2）
 *  3、控制台日志：
     *  Heap
     *  PSYoungGen      total 13824K, used 12288K [0x00000007bf100000, 0x00000007c0000000, 0x00000007c0000000)
     *   eden space 12288K, 100% used [0x00000007bf100000,0x00000007bfd00000,0x00000007bfd00000)
     *   from space 1536K, 0% used [0x00000007bfe80000,0x00000007bfe80000,0x00000007c0000000)
     *   to   space 1536K, 0% used [0x00000007bfd00000,0x00000007bfd00000,0x00000007bfe80000)
     *  ParOldGen       total 5120K, used 0K [0x00000007bec00000, 0x00000007bf100000, 0x00000007bf100000)
     *   object space 5120K, 0% used [0x00000007bec00000,0x00000007bec00000,0x00000007bf100000)
     *  Metaspace       used 3271K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 360K, capacity 388K, committed 512K, reserved 1048576K
 *  4、分析（日志内容的分析）：
 *  eden=1536k*8=12288K
 *  eden space 12288K, 100% used  10个数组对象全部分配到了Eden区
 *  5、例子中使用到的策略：
     * 对象优先在Eden分配
 *
 *****************例子4****************************************************************************************************************************************************************
 *  1、JVM参数：-Xms10M -Xmx20M -XX:+PrintGCDetails -XX:NewRatio=2
 *  2、JVM参数解释：-堆最小20m -堆最大20m 打印gc详情 -新生代：老年代1：2 理论值为6.6M：13.4M 实际上 6M：14M
 *  3、控制台日志：
     *  [GC (Allocation Failure) [PSYoungGen: 4834K->496K(6144K)] 4834K->3608K(19968K), 0.0035026 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * [GC (Allocation Failure) [PSYoungGen: 5780K->512K(6144K)] 8893K->8752K(19968K), 0.0053276 secs] [Times: user=0.02 sys=0.01, real=0.00 secs]
     * [Full GC (Ergonomics) [PSYoungGen: 512K->0K(6144K)] [ParOldGen: 8240K->8574K(13824K)] 8752K->8574K(19968K), [Metaspace: 3165K->3165K(1056768K)], 0.0094099 secs] [Times: user=0.03 sys=0.00, real=0.01 secs]
     * Heap
     *  PSYoungGen      total 6144K, used 2355K [0x00000007bf980000, 0x00000007c0000000, 0x00000007c0000000)
     *   eden space 5632K, 41% used [0x00000007bf980000,0x00000007bfbccfe0,0x00000007bff00000)
     *   from space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
     *   to   space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
     *  ParOldGen       total 13824K, used 8574K [0x00000007bec00000, 0x00000007bf980000, 0x00000007bf980000)
     *   object space 13824K, 62% used [0x00000007bec00000,0x00000007bf45f9f0,0x00000007bf980000)
     *  Metaspace       used 3192K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 354K, capacity 388K, committed 512K, reserved 1048576K
 *  4、分析（日志内容的分析）：
 *      当Eden往from中放数组时，发现from空间太小，则直接往老年代存放数组对象。这个现象就是空间分配担保。
 *      发送一次full gc的原因：老年代中剩余总空间是够的，但是为了保证空间分配担保；但是此时老年代的连续空间小于1M所以发送了full gc。
 *  5、例子中使用到的策略：
    *空间分配担保
 */
public class SpaceAllocStrategyTest {
    public static void main(String[] args) {
        int cap =1*1024*1024;
        byte[] bs0 =new byte[cap];
        byte[] bs1 =new byte[cap];
        byte[] bs2 =new byte[cap];
        byte[] bs3 =new byte[cap];
        byte[] bs4 =new byte[cap];
        byte[] bs5 =new byte[cap];
        byte[] bs6 =new byte[cap];
        byte[] bs7 =new byte[cap];
        byte[] bs8 =new byte[cap];
        byte[] bs9 =new byte[cap];
        System.out.println();
    }
}
