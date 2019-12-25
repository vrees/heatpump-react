package de.vrees.heatpump.slaves.beckhoff;

import de.vrees.heatpump.slaves.beckhoff.converter.IoRange;
import de.vrees.heatpump.slaves.beckhoff.converter.RawConverterAnalog16Bit;
import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;
import us.ihmc.etherCAT.master.TxPDO;

import java.util.StringJoiner;

/**
 * EL3152 | 2-Kanal-Analog-Eingangsklemme 4…20 mA, single-ended, 16 Bit
 * <p>
 * https://www.beckhoff.de/default.asp?ethercat/EL3152.htm
 * <p>
 * Measure High and Low Pressure in refrigerant circuit (Kältemittelkreislauf)
 */
public class EL3152 extends Slave {
    static final long vendorID = 0x00000002L;
    static final long productCode = 0x0c503052L;

    RawConverterAnalog16Bit converterHigh = new RawConverterAnalog16Bit(new IoRange(0.0f, 18.0f));
    RawConverterAnalog16Bit converterLow = new RawConverterAnalog16Bit(new IoRange(-0.5f, 7.0f));

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

    private final Input pressureHigh = new Input(0x1a00);
    private final Input pressureLow = new Input(0x1a01);

    public EL3152(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(2, true));
        registerSyncManager(new SyncManager(3, true));

        sm(3).registerPDO(pressureHigh);
        sm(3).registerPDO(pressureLow);
    }

    public float getPressureHigh() {
        return converterHigh.toFloatRounded(pressureHigh.value.get(), 10);
    }

    public boolean getPressureHighUnderrange() {
        return pressureHigh.underrange.get();
    }

    public boolean getPressureHighOverrange() {
        return pressureHigh.overrange.get();
    }

    public boolean getPressureHighError() {
        return pressureHigh.error.get();
    }

    public float getPressureLow() {
        return converterLow.toFloatRounded(pressureLow.value.get(), 10);
    }

    public boolean getPressureLowUnderrange() {
        return pressureLow.underrange.get();
    }

    public boolean getPressureLowOverrange() {
        return pressureLow.overrange.get();
    }

    public boolean getPressureLowError() {
        return pressureLow.error.get();
    }

    public String toProcessdataString() {
        return new StringJoiner(", ", EL3152.class.getSimpleName() + "[", "]")
            .add("pressureHigh=" + getPressureHigh())
            .add("pressureHighError=" + getPressureHighError())
            .add("pressureHighUnderrange=" + getPressureHighUnderrange())
            .add("pressureHigh=Overrange" + getPressureHighOverrange())
            .add("pressureLow=" + getPressureLow())
            .add("pressureLowError=" + getPressureLowError())
            .add("pressureLowUnderrange=" + getPressureLowUnderrange())
            .add("pressureLow=Overrange" + getPressureLowOverrange())
            .toString();
    }
}

/* Slaveinfo:

2 - 5:1 EL3152
	Manufacturer: 0x00000002
	Product code: 0x0c503052
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
			0x3102:0x02 INTEGER16	Value
*/
