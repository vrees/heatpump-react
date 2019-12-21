package de.vrees.heatpump.slaves.beckhoff;

import us.ihmc.etherCAT.master.RxPDO;
import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;

/**
 * EL2008 | 8-Kanal-Digital-Ausgangsklemme 24 V DC, 0,5 A
 *
 * https://www.beckhoff.de/default.asp?ethercat/el2008.htm
 *
 */
public class EL2008 extends Slave {
    private static final long vendorID = 0x00000002L;
    private static final long productCode = 0x07d83052L;


    public class Output extends RxPDO {
        protected Output(int address) {
            super(address);
        }

        Bool output = new Bool();
    }

    private final Output operatingStateWaterPump = new Output(0x1600);
    private final Output operatingStateCompressor = new Output(0x1601);
    private final Output out3 = new Output(0x1602);
    private final Output out4 = new Output(0x1603);
    private final Output out5 = new Output(0x1604);
    private final Output out6 = new Output(0x1605);
    private final Output out7 = new Output(0x1606);
    private final Output out8 = new Output(0x1607);

    public EL2008(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(0, false));

        sm(0).registerPDO(operatingStateWaterPump);
        sm(0).registerPDO(operatingStateCompressor);
        sm(0).registerPDO(out3);
        sm(0).registerPDO(out4);
        sm(0).registerPDO(out5);
        sm(0).registerPDO(out6);
        sm(0).registerPDO(out7);
        sm(0).registerPDO(out8);
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

    public void setOut5(boolean val) {
        out5.output.set(val);
    }

    public void setOut6(boolean val) {
        out6.output.set(val);
    }

    public void setOut7(boolean val) {
        out7.output.set(val);
    }

    public void setOut8(boolean val) {
        out8.output.set(val);
    }

}
