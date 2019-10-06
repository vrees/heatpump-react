package de.vrees.heatpump.master;


import de.vrees.heatpump.slaves.beckhoff.EL1008;
import de.vrees.heatpump.slaves.beckhoff.EL2008;
import de.vrees.heatpump.slaves.beckhoff.EL3122;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import us.ihmc.etherCAT.master.EtherCATRealtimeThread;
import us.ihmc.etherCAT.slaves.beckhoff.EK1100;
import us.ihmc.etherCAT.slaves.beckhoff.EL3356;
import us.ihmc.realtime.MonotonicTime;
import us.ihmc.realtime.PriorityParameters;


@Component
@Getter
public class HeatpumpMaster extends EtherCATRealtimeThread implements ApplicationRunner {

    private final EK1100 ek1100 = new EK1100(0, 0); // Coupler
    private final EL1008 el1008 = new EL1008(0, 1); // 8-fach Digital Input
    private final EL2008 el2008 = new EL2008(0, 2); // 8-fach Digital Output
    private final EL3122 el3122 = new EL3122(0, 3); // EL3122 | 2-Kanal-Analog-Eingangsklemme 4…20 mA, Differenzeingang, 16 Bit


    private int counter = 0;

    public HeatpumpMaster() {
        super("enp3s0", PriorityParameters.MAXIMUM_PRIORITY, new MonotonicTime(0, 1000000), true, 100000);
        registerSlave(ek1100);
        registerSlave(el1008);
        registerSlave(el2008);
        registerSlave(el3122);
        setRequireAllSlaves(false);
        enableTrace();
    }

    @Override
    protected void deadlineMissed() {
//        System.out.println("deadlineMissed()");
    }

    @Override
    protected void doControl() {
        if (counter++ % 10000 == 0) {

            // ProcessdataResource resource = mapper.map(el1008,el3122)
//            sender.sendProcessdata(el1008, el3122);

//            System.out.println(el1008 + ": " + el1008.toProcessdataString());
            System.out.println(el3122 + ": " + el3122.toProcessdataString());

            el2008.setOut1(true);
            el2008.setOut2(true);
            el2008.setOut3(true);
            el2008.setOut4(true);
        }
    }

    @Override
    protected void workingCounterMismatch(int expected, int actual) {
        System.out.println("workingCounterMismatch() actual=" + actual + ", expected=" + expected);
    }

    @Override
    protected void datagramLost() {
//        System.out.println("DATAGRAM LOST");
    }

    @Override
    protected void doReporting() {
        // TODO Auto-generated method stub

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
        join();
    }

//    public static void main(String[] args) {
//        HeatPumpMaster heatpumpExample = new HeatPumpMaster();
//        heatpumpExample.start();
//        heatpumpExample.join();
//    }
}
