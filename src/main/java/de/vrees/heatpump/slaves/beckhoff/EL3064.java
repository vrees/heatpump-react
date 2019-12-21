package de.vrees.heatpump.slaves.beckhoff;

import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;
import us.ihmc.etherCAT.master.TxPDO;

import java.util.StringJoiner;

/**
 * EL3064 | 4-Kanal-Analog-Eingangsklemme 0…10 V, single-ended, 12 Bit
 * <p>
 * https://www.beckhoff.de/default.asp?ethercat/el3064.htm
 */
public class EL3064 extends Slave {
    static final long vendorID = 0x00000002L;
    static final long productCode = 0x0bf83052;

    public class Input extends TxPDO {
        protected Input(int address) {
            super(address);
        }


        Bool underrange = new Bool();
        Bool overrange = new Bool();
        Member limit1 = new Bit2();
        Member limit2 = new Bit2();
        Bool error = new Bool();
        Bool gap1 = new Bool();
        Bit5 gap2 = new Bit5();
        Bool txPDOState = new Bool();
        Bool txPDOToggle = new Bool();
        Bool gap3 = new Bool();
        Signed16 value = new Signed16();
    }

    private final Input pressureDiffenceEvaporator = new Input(0x1a00);
    private final Input reserve1 = new Input(0x1a02);
    private final Input reserve2 = new Input(0x1a04);
    private final Input reserve3 = new Input(0x1a06);


    public EL3064(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(2, true));
        registerSyncManager(new SyncManager(3, true));

        sm(3).registerPDO(pressureDiffenceEvaporator);
        sm(3).registerPDO(reserve1);
        sm(3).registerPDO(reserve2);
        sm(3).registerPDO(reserve3);
    }


    public float getPressureDiffenceEvaporator() {
        return pressureDiffenceEvaporator.value.get();
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


    public int getReserve2() {
        return reserve2.value.get();
    }

    public boolean getReserve2Underrange() {
        return reserve2.underrange.get();
    }

    public boolean getReserve2Overrange() {
        return reserve2.overrange.get();
    }

    public boolean getReserve2Error() {
        return reserve2.error.get();
    }


    public int getReserve3() {
        return reserve3.value.get();
    }

    public boolean getReserve3Underrange() {
        return reserve3.underrange.get();
    }

    public boolean getReserve3Overrange() {
        return reserve3.overrange.get();
    }

    public boolean getReserve3Error() {
        return reserve3.error.get();
    }

    public String toProcessdataString() {
        return new StringJoiner(", ", EL3064.class.getSimpleName() + "[", "]")
            .add("PressureDiffenceEvaporator=" + getPressureDiffenceEvaporator())
            .add("PressureDiffenceEvaporatorError=" + getPressureDiffenceEvaporatorError())
            .add("PressureDiffenceEvaporatorUnderrange=" + getPressureDiffenceEvaporatorUnderrange())
            .add("PressureDiffenceEvaporator=Overrange" + getPressureDiffenceEvaporatorOverrange())

            .add("Reserve1=" + getReserve1())
            .add("Reserve1Error=" + getReserve1Error())
            .add("Reserve1Underrange=" + getReserve1Underrange())
            .add("Reserve1=Overrange" + getReserve1Overrange())

            .add("Reserve2=" + getReserve2())
            .add("Reserve2Error=" + getReserve2Error())
            .add("Reserve2Underrange=" + getReserve2Underrange())
            .add("Reserve2=Overrange" + getReserve2Overrange())

            .add("Reserve3=" + getReserve3())
            .add("Reserve3Error=" + getReserve3Error())
            .add("Reserve3Underrange=" + getReserve3Underrange())
            .add("Reserve3=Overrange" + getReserve3Overrange())

            .toString();
    }


/* Slaveinfo:

5 - 0:4 EL3064
	Manufacturer: 0x00000002
	Product code: 0x0bf83052
	Revision: 1048576
	Distributed Clocks: yes
	SM(0) Address: 0x1000, length: 128	Flags: 65574	Type: Mailbox messages receive
	SM(1) Address: 0x1080, length: 128	Flags: 65570	Type: Mailbox messages transmit
	SM(2) Address: 0x1100, length: 0	Flags: 4	Type: Cyclic process data receive
	SM(3) Address: 0x1180, length: 16	Flags: 65568	Type: Cyclic process data transmit
		TxPDO 0x1a00	AI TxPDO-Map Standard Ch.1
			0x6000:0x01 BOOLEAN	Underrange
			0x6000:0x02 BOOLEAN	Overrange
			0x6000:0x03 BIT2	Limit 1
			0x6000:0x05 BIT2	Limit 2
			0x6000:0x07 BOOLEAN	Error
			0x0000:0x00 (1 bit)
			0x0000:0x00 (5 bit)
			0x1800:0x07 BOOLEAN	TxPDO State
			0x1800:0x09 BOOLEAN	TxPDO Toggle
			0x0000:0x00 (1 bit)
			0x6000:0x11 INTEGER16	Value
		TxPDO 0x1a02	AI TxPDO-Map Standard Ch.2
			0x6010:0x01 BOOLEAN	Underrange
			0x6010:0x02 BOOLEAN	Overrange
			0x6010:0x03 BIT2	Limit 1
			0x6010:0x05 BIT2	Limit 2
			0x6010:0x07 BOOLEAN	Error
			0x0000:0x00 (1 bit)
			0x0000:0x00 (5 bit)
			0x1802:0x07 BOOLEAN	TxPDO State
			0x1802:0x09 BOOLEAN	TxPDO Toggle
			0x0000:0x00 (1 bit)
			0x6010:0x11 INTEGER16	Value
		TxPDO 0x1a04	AI TxPDO-Map Standard Ch.3
			0x6020:0x01 BOOLEAN	Underrange
			0x6020:0x02 BOOLEAN	Overrange
			0x6020:0x03 BIT2	Limit 1
			0x6020:0x05 BIT2	Limit 2
			0x6020:0x07 BOOLEAN	Error
			0x0000:0x00 (1 bit)
			0x0000:0x00 (5 bit)
			0x1804:0x07 BOOLEAN	TxPDO State
			0x1804:0x09 BOOLEAN	TxPDO Toggle
			0x0000:0x00 (1 bit)
			0x6020:0x11 INTEGER16	Value
		TxPDO 0x1a06	AI TxPDO-Map Standard Ch.4
			0x6030:0x01 BOOLEAN	Underrange
			0x6030:0x02 BOOLEAN	Overrange
			0x6030:0x03 BIT2	Limit 1
			0x6030:0x05 BIT2	Limit 2
			0x6030:0x07 BOOLEAN	Error
			0x0000:0x00 (1 bit)
			0x0000:0x00 (5 bit)
			0x1806:0x07 BOOLEAN	TxPDO State
			0x1806:0x09 BOOLEAN	TxPDO Toggle
			0x0000:0x00 (1 bit)
			0x6030:0x11 INTEGER16	Value

5 - 0:4 EL3064
	Manufacturer: 0x00000002
	Product code: 0x0bf83052
	Revision: 1048576
	Distributed Clocks: yes
	SM(0) Address: 0x1000, length: 128	Flags: 65574	Type: Mailbox messages receive
	SM(1) Address: 0x1080, length: 128	Flags: 65570	Type: Mailbox messages transmit
	SM(2) Address: 0x1100, length: 0	Flags: 4	Type: Cyclic process data receive
	SM(3) Address: 0x1180, length: 16	Flags: 65568	Type: Cyclic process data transmit

 */

}
