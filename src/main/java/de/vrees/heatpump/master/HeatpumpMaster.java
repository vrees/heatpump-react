package de.vrees.heatpump.master;


import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.limitcheck.LimitCheckResult;
import de.vrees.heatpump.simulate.EcatToProcessdataMapper;
import de.vrees.heatpump.slaves.beckhoff.*;
import de.vrees.heatpump.statemachine.StateMachineWrapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import us.ihmc.etherCAT.master.EtherCATRealtimeThread;
import us.ihmc.etherCAT.slaves.beckhoff.EK1100;
import us.ihmc.realtime.MonotonicTime;
import us.ihmc.realtime.PriorityParameters;

import java.util.Arrays;
import java.util.List;


@Component
@Profile("!simulate")
@Getter
@Slf4j
public class HeatpumpMaster extends EtherCATRealtimeThread implements ApplicationRunner {

    private final EK1100 ek1100 = new EK1100(0, 0); // Coupler

    // Evaluation unit only
    private final EL3122 el3122 = new EL3122(0, 1);   // EL3122 | 2-Kanal-Analog-Eingangsklemme 4…20 mA, Differenzeingang, 16 Bit
    private final EL3152 el3152 = new EL3152(0, 1); // EL3152 | 2-Kanal-Analog-Eingangsklemme 4…20 mA, single-ended, 16 Bit

    // Evaluation unit only
    private final EL2008 el2008 = new EL2008(0, 2); // 8-fach Digital Output
    private final EL2004 el2004 = new EL2004(0, 2); // EL2004 | 4-Kanal-Digital-Ausgangsklemme 24 V DC, 0,5 A

    private final EL3204_1 el3204_1 = new EL3204_1(0, 3); // EL3204 | PT100

    // Evaluation unit only
    private final EL3064 eL3064 = new EL3064(0, 4); // EL3064 | 4-Kanal-Analog-Eingangsklemme 0…10 V, single-ended, 12 Bit
    private final EL3102 el3102 = new EL3102(0, 4); // EL3102 | 2-Kanal-Analog-Eingangsklemme -10…+10 V, Differenzeingang, 16 Bit

    private final EL1008 el1008 = new EL1008(0, 5); // 8-fach Digital Input

    private final EL3204_2 el3204_2 = new EL3204_2(0, 6); // EL3204 | PT100

    private final de.vrees.heatpump.statemachine.StateMachineWrapper stateMachineWrapper;
    private final EcatToProcessdataMapper mapper = new EcatToProcessdataMapper();
    private Environment environment;

    private boolean useEvaluationUnit = false;

    public HeatpumpMaster(StateMachineWrapper stateMachineWrapper, Environment environment) {
        super("enp3s0", PriorityParameters.MAXIMUM_PRIORITY, new MonotonicTime(0, 50_000_000), false, 100_000);

        this.stateMachineWrapper = stateMachineWrapper;
        this.environment = environment;

        useEvaluationUnit = Arrays.stream(environment.getActiveProfiles()).filter(p -> "useEvaluationUnit".equals(p)).findFirst().isPresent();

        if (useEvaluationUnit) {
            // test eavluation unit on desk in office
            registerSlave(ek1100);
            registerSlave(el3122);
            registerSlave(el2008);
            registerSlave(el3204_1);
            registerSlave(eL3064);
            registerSlave(el1008);
            registerSlave(el3204_2);
        } else {
            // real Heatpump Hardware
            registerSlave(ek1100);
            registerSlave(el3152);
            registerSlave(el2004);
            registerSlave(el3204_1);
            registerSlave(el3102);
            registerSlave(el1008);
            registerSlave(el3204_2);
        }

        setRequireAllSlaves(false);
        enableTrace();
    }

    @Override
    protected void deadlineMissed() {
//        System.out.println("deadlineMissed()");
    }

    @Override
    protected void doControl() {
        Processdata processdata;

        if (useEvaluationUnit)
            processdata = mapper.map(el3122, el2008, el3204_1, eL3064, el1008, el3204_2);
        else
            processdata = mapper.map(el3152, el2004, el3204_1, el3102, el1008, el3204_2);

        stateMachineWrapper.storeAndPreProcess(processdata);

        List<LimitCheckResult> failedChecks = stateMachineWrapper.checkLimits(processdata);

        if (useEvaluationUnit)
            stateMachineWrapper.writeOutgoingValuesToEcatBus(el2008);
        else
            stateMachineWrapper.writeOutgoingValuesToEcatBus(el2004);

        stateMachineWrapper.processOutgoingValues(processdata, failedChecks);
        stateMachineWrapper.sendData(processdata);
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

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
        join();
    }
}
