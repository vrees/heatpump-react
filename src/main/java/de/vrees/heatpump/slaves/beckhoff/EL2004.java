package de.vrees.heatpump.slaves.beckhoff;

import us.ihmc.etherCAT.master.RxPDO;
import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;

/**
 * EL2004 | 4-Kanal-Digital-Ausgangsklemme 24 V DC, 0,5 A
 *
 * https://www.beckhoff.de/default.asp?ethercat/el2004.htm
 *
 */
public class EL2004 extends Slave {
    private static final long vendorID = 0x00000002L;
    private static final long productCode = 0x07d43052L;


    public class Output extends RxPDO {
        protected Output(int address) {
            super(address);
        }

        Bool output = new Bool();
    }

    private final Output operatingStateCompressor = new Output(0x1600);
    private final Output operatingStateWaterPump = new Output(0x1601);
    private final Output out3 = new Output(0x1602);
    private final Output out4 = new Output(0x1603);

    public EL2004(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(0, false));

        sm(0).registerPDO(operatingStateCompressor);
        sm(0).registerPDO(operatingStateWaterPump);
        sm(0).registerPDO(out3);
        sm(0).registerPDO(out4);
    }

    public void setOperatingStateWaterPump(boolean val) {
        operatingStateWaterPump.output.set(val);
    }

    public void setOperatingStateCompressor(boolean val) {
        operatingStateCompressor.output.set(val);
    }

    public void setOut3(boolean val) {
        out3.output.set(val);
    }

    public void setOut4(boolean val) {
        out4.output.set(val);
    }
}

/*
3 - 10:0 EL2004
    Manufacturer: 0x00000002
    Product code: 0x07d43052
    Revision: 1048576
    Distributed Clocks: yes
    SM(0) Address: 0x0f00, length: 0	Flags: 589892	Type: Unused
 */
