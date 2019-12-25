package de.vrees.heatpump.slaves.beckhoff;

import de.vrees.heatpump.slaves.beckhoff.converter.IoRange;
import de.vrees.heatpump.slaves.beckhoff.converter.RawConverterAnalog16Bit;
import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;
import us.ihmc.etherCAT.master.TxPDO;

import java.util.StringJoiner;

/**
 * EL3102 | 2-Kanal-Analog-Eingangsklemme -10…+10 V, Differenzeingang, 16 Bit
 * <p>
 * https://www.beckhoff.de/default.asp?ethercat/el3102.htm
 */
public class EL3102 extends Slave {
    static final long vendorID = 0x00000002L;
    static final long productCode = 0x0C1E3052;

    RawConverterAnalog16Bit converter = new RawConverterAnalog16Bit(new IoRange(0.0f, 200.0f));


    public class Input extends TxPDO {
        protected Input(int address) {
            super(address);
        }

        Bool underrange = new Bool();
        Bool overrange = new Bool();
        Member limit1 = new Bit2();
        Member limit2 = new Bit2();
        Bool error = new Bool();

        Signed16 value = new Signed16();
    }

    private final Input pressureDiffenceEvaporator = new Input(0x1a00);
    private final Input reserve1 = new Input(0x1a01);

    public EL3102(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(2, false));
        registerSyncManager(new SyncManager(3, false));

        sm(3).registerPDO(pressureDiffenceEvaporator);
        sm(3).registerPDO(reserve1);
    }

    public float getPressureDiffenceEvaporator() {
        return converter.toFloatRounded(pressureDiffenceEvaporator.value.get(), 10);
    }

    public boolean getPressureDiffenceEvaporatorUnderrange() {
        return pressureDiffenceEvaporator.underrange.get();
    }

    public boolean getPressureDiffenceEvaporatorOverrange() {
        return pressureDiffenceEvaporator.overrange.get();
    }

    public boolean getPressureDiffenceEvaporatorError() {
        return pressureDiffenceEvaporator.error.get();
    }


    public int getReserve1() {
        return reserve1.value.get();
    }

    public boolean getReserve1Underrange() {
        return reserve1.underrange.get();
    }

    public boolean getReserve1Overrange() {
        return reserve1.overrange.get();
    }

    public boolean getReserve1Error() {
        return reserve1.error.get();
    }


    public String toProcessdataString() {
        return new StringJoiner(", ", EL3102.class.getSimpleName() + "[", "]")
            .add("PressureDiffenceEvaporator=" + getPressureDiffenceEvaporator())
            .add("PressureDiffenceEvaporatorError=" + getPressureDiffenceEvaporatorError())
            .add("PressureDiffenceEvaporatorUnderrange=" + getPressureDiffenceEvaporatorUnderrange())
            .add("PressureDiffenceEvaporator=Overrange" + getPressureDiffenceEvaporatorOverrange())

            .add("Reserve1=" + getReserve1())
            .add("Reserve1Error=" + getReserve1Error())
            .add("Reserve1Underrange=" + getReserve1Underrange())
            .add("Reserve1=Overrange" + getReserve1Overrange())

            .toString();
    }


/* Slaveinfo:
5 - 0:4 EL3102
	Manufacturer: 0x00000002
	Product code: 0x0c1e3052
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

}
