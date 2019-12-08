package de.vrees.heatpump.master;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("!simulate")
@Getter
@Slf4j
public class TestMaster implements ApplicationRunner {



    private int counter = 0;

    public TestMaster() {
//        super("enp3s0", PriorityParameters.MAXIMUM_PRIORITY, new MonotonicTime(0, 1000000), true, 100000);
//        registerSlave(ek1100);
//        registerSlave(el1008);
//        registerSlave(el2008);
//        registerSlave(el3122);
//        setRequireAllSlaves(false);
//        enableTrace();

        log.debug("constructor called");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("rund called");
    }

}
