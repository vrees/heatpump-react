package de.vrees.heatpump.slaves.beckhoff;

import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;
import us.ihmc.etherCAT.master.TxPDO;

import java.util.StringJoiner;

/**
 * EL3122 | 2-Kanal-Analog-Eingangsklemme 4…20 mA, Differenzeingang, 16 Bit
 * EL3152 | 2-Kanal-Analog-Eingangsklemme 4…20 mA, single-ended, 16 Bit
 * EL3124 | 4-Kanal-Analog-Eingangsklemme 4…20 mA, Differenzeingang, 16 Bit
 *
 * https://www.beckhoff.de/default.asp?ethercat/el3124.htm
 *
 * Measure High and Low Pressure in refrigerant circuit (Kältemittelkreislauf)
 */
public class EL3122 extends Slave {
    static final long vendorID = 0x00000002L;
    static final long productCode = 0x0c323052L;

    public class Input extends TxPDO {
        protected Input(int address) {
            super(address);
        }

        Bool underrange = new Bool();
        Bool overrange = new Bool();
        Member limit1 = new Bit2();
        Member limit2 = new Bit2();
        Bool error = new Bool();
        // newer versions do have more status infos
//        Bit7 gap = new Bit7();
//        Bool txPDOState = new Bool();
//        Bool txPDOToggle = new Bool();
        Signed16 value = new Signed16();
    }

    private final Input highPressure = new Input(0x1a00);
    private final Input lowPressure = new Input(0x1a01);

    public EL3122(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(2, true));
        registerSyncManager(new SyncManager(3, true));

        sm(3).registerPDO(highPressure);
        sm(3).registerPDO(lowPressure);
    }

    public int getHighPressure() {
        return highPressure.value.get();
    }

    public boolean getHighPressureUnderrange() {
        return highPressure.underrange.get();
    }

    public boolean getHighPressureOverrange() {
        return highPressure.overrange.get();
    }

    public boolean getHighPressureError() {
        return highPressure.error.get();
    }

    public int getLowPressure() {
        return lowPressure.value.get();
    }

    public boolean getLowPressureUnderrange() {
        return lowPressure.underrange.get();
    }

    public boolean getLowPressureOverrange() {
        return lowPressure.overrange.get();
    }

    public boolean getLowPressureError() {
        return lowPressure.error.get();
    }

    public String toProcessdataString() {
        return new StringJoiner(", ", EL3122.class.getSimpleName() + "[", "]")
                .add("highPressure=" + getHighPressure())
                .add("highPressureError=" + getHighPressureError())
                .add("highPressureUnderrange=" + getHighPressureUnderrange())
                .add("highPressure=Overrange" + getHighPressureOverrange())
                .add("lowPressure=" + getLowPressure())
                .add("lowPressureError=" + getLowPressureError())
                .add("lowPressureUnderrange=" + getLowPressureUnderrange())
                .add("lowPressure=Overrange" + getLowPressureOverrange())
                .toString();
    }


/* Slaveinfo:

2 - 0:1 EL3122
	Manufacturer: 0x00000002
	Product code: 0x0c323052
	Revision: 0
	Distributed Clocks: no
	SM(0) Address: 0x1800, length: 246	Flags: 65574	Type: Mailbox messages receive
	SM(1) Address: 0x18f6, length: 246	Flags: 65570	Type: Mailbox messages transmit
	SM(2) Address: 0x1000, length: 0	Flags: 65572	Type: Cyclic process data receive
	SM(3) Address: 0x1100, length: 0	Flags: 65568	Type: Cyclic process data transmit
		TxPDO 0x1a00	TxPDO 001 mapping
			0x3101:0x01 UNSIGNED8	Status
			0x3101:0x02 INTEGER16	Value
		TxPDO 0x1a01	TxPDO 002 mapping
			0x3102:0x01 UNSIGNED8	Status
			0x3102:0x02 INTEGER16	Valueatus
  [0x0006.0] 0x3102:0x02 0x10 INTEGER16    Value

 */

}
