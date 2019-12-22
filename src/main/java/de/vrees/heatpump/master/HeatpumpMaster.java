package de.vrees.heatpump.master;


import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.limitcheck.LimitCheckResult;
import de.vrees.heatpump.limitcheck.LimitChecker;
import de.vrees.heatpump.simulate.EcatToProcessdataMapper;
import de.vrees.heatpump.slaves.beckhoff.*;
import de.vrees.heatpump.statemachine.StateMachineWrapper;
import de.vrees.heatpump.web.websocket.WebsocketService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import us.ihmc.etherCAT.master.EtherCATRealtimeThread;
import us.ihmc.etherCAT.slaves.beckhoff.EK1100;
import us.ihmc.realtime.MonotonicTime;
import us.ihmc.realtime.PriorityParameters;

import java.util.List;


@Component
@Profile("!simulate")
@Getter
@Slf4j
public class HeatpumpMaster extends EtherCATRealtimeThread implements ApplicationRunner {

    private final EK1100 ek1100 = new EK1100(0, 0); // Coupler

    // EL3152
    private final EL3122 el3122 = new EL3122(0, 1); // EL3122 | 2-Kanal-Analog-Eingangsklemme 4…20 mA, Differenzeingang, 16 Bit

    // EL2004
    private final EL2008 el2008 = new EL2008(0, 2); // 8-fach Digital Output

    private final EL3204_1 el3204_1 = new EL3204_1(0, 3); // EL3204 | PT100

    // EL3102
    private final EL3064 eL3064 = new EL3064(0, 4); // EL3064 | 4-Kanal-Analog-Eingangsklemme 0…10 V, single-ended, 12 Bit

    private final EL1008 el1008 = new EL1008(0, 5); // 8-fach Digital Input

    private final EL3204_2 el3204_2 = new EL3204_2(0, 6); // EL3204 | PT100

    private final de.vrees.heatpump.statemachine.StateMachineWrapper stateMachineWrapper;
    private final EcatToProcessdataMapper mapper = new EcatToProcessdataMapper();


    public HeatpumpMaster(StateMachineWrapper stateMachineWrapper, WebsocketService websocketService, LimitChecker limitChecker) {
        super("enp3s0", PriorityParameters.MAXIMUM_PRIORITY, new MonotonicTime(0, 100_000_000), false, 100_000);

        this.stateMachineWrapper = stateMachineWrapper;

        registerSlave(ek1100);
        registerSlave(el3122);
        registerSlave(el2008);
        registerSlave(el3204_1);
        registerSlave(eL3064);
        registerSlave(el1008);
        registerSlave(el3204_2);

        setRequireAllSlaves(false);
        enableTrace();

//        log.info("getJitterEstimate(): ", getJitterEstimate());
//        setMaximumExecutionJitter(100000);
    }

    @Override
    protected void deadlineMissed() {
//        System.out.println("deadlineMissed()");
    }

    @Override
    protected void doControl() {
        Processdata processdata = mapper.map(el3122, el2008, el3204_1, eL3064, el1008, el3204_2);
        stateMachineWrapper.storeProcessdataInStatemachine(processdata);

        List<LimitCheckResult> failedChecks = stateMachineWrapper.checkLimits(processdata);
        stateMachineWrapper.processOutgoingValues(processdata, failedChecks);

        stateMachineWrapper.sendData(processdata);
    }



/*    private void logValues() {
        System.out.println("******************************************************************");
        System.out.println(el3122 + ": " + el3122.toProcessdataString());
        System.out.println(el3204_2 + ": " + el3204_2.toProcessdataString());
        System.out.println(eL3064 + ": " + eL3064.toProcessdataString());
    }*/

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

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
        join();
    }
}
