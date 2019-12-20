package de.vrees.heatpump.slaves.beckhoff;

import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;
import us.ihmc.etherCAT.master.TxPDO;

import java.util.StringJoiner;

/**
 * EL3204 | 4-Kanal PD100
 * <p>
 * https://www.beckhoff.de/default.asp?ethercat/el3204.htm
 */
public class EL3204_1 extends Slave {
    static final long vendorID = 0x00000002L;
    static final long productCode = 0x0c843052;

    public class Input extends TxPDO {
        protected Input(int address) {
            super(address);
        }

        Bool underrange = new Bool();
        Bool overrange = new Bool();
        Member limit1 = new Bit2();
        Member limit2 = new Bit2();
        Bool error = new Bool();
        Bit7 gap = new Bit7();
        Bool txPDOState = new Bool();
        Bool txPDOToggle = new Bool();
        Signed16 value = new Signed16();
    }

    private final Input temperatureEvaporatingIn = new Input(0x1a00);
    private final Input temperatureFlow = new Input(0x1a01);
    private final Input temperatureEvaporatingOut = new Input(0x1a02);
    private final Input temperatureReturn = new Input(0x1a03);


    public EL3204_1(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(2, false));
        registerSyncManager(new SyncManager(3, false));

        sm(3).registerPDO(temperatureEvaporatingIn);
        sm(3).registerPDO(temperatureFlow);
        sm(3).registerPDO(temperatureEvaporatingOut);
        sm(3).registerPDO(temperatureReturn);
    }


    public int getTemperatureEvaporatingIn() {
        return temperatureEvaporatingIn.value.get();
    }

    public boolean getTemperatureEvaporatingInUnderrange() {
        return temperatureEvaporatingIn.underrange.get();
    }

    public boolean getTemperatureEvaporatingInOverrange() {
        return temperatureEvaporatingIn.overrange.get();
    }

    public boolean getTemperatureEvaporatingInError() {
        return temperatureEvaporatingIn.error.get();
    }


    public int getTemperatureFlow() {
        return temperatureFlow.value.get();
    }

    public boolean getTemperatureFlowUnderrange() {
        return temperatureFlow.underrange.get();
    }

    public boolean getTemperatureFlowOverrange() {
        return temperatureFlow.overrange.get();
    }

    public boolean getTemperatureFlowError() {
        return temperatureFlow.error.get();
    }


    public int getTemperatureEvaporatingOut() {
        return temperatureEvaporatingOut.value.get();
    }

    public boolean getTemperatureEvaporatingOutUnderrange() {
        return temperatureEvaporatingOut.underrange.get();
    }

    public boolean getTemperatureEvaporatingOutOverrange() {
        return temperatureEvaporatingOut.overrange.get();
    }

    public boolean getTemperatureEvaporatingOutError() {
        return temperatureEvaporatingOut.error.get();
    }


    public int getTemperatureReturn() {
        return temperatureReturn.value.get();
    }

    public boolean getTemperatureReturnUnderrange() {
        return temperatureReturn.underrange.get();
    }

    public boolean getTemperatureReturnOverrange() {
        return temperatureReturn.overrange.get();
    }

    public boolean getTemperatureReturnError() {
        return temperatureReturn.error.get();
    }

    public String toProcessdataString() {
        return new StringJoiner(", ", EL3204_1.class.getSimpleName() + "[", "]")
            .add("TemperatureEvaporatingIn=" + getTemperatureEvaporatingIn())
            .add("TemperatureEvaporatingInError=" + getTemperatureEvaporatingInError())
            .add("TemperatureEvaporatingInUnderrange=" + getTemperatureEvaporatingInUnderrange())
            .add("TemperatureEvaporatingIn=Overrange" + getTemperatureEvaporatingInOverrange())

            .add("TemperatureFlow=" + getTemperatureFlow())
            .add("TemperatureFlowError=" + getTemperatureFlowError())
            .add("TemperatureFlowUnderrange=" + getTemperatureFlowUnderrange())
            .add("TemperatureFlow=Overrange" + getTemperatureFlowOverrange())

            .add("TemperatureEvaporatingOut=" + getTemperatureEvaporatingOut())
            .add("TemperatureEvaporatingOutError=" + getTemperatureEvaporatingOutError())
            .add("TemperatureEvaporatingOutUnderrange=" + getTemperatureEvaporatingOutUnderrange())
            .add("TemperatureEvaporatingOut=Overrange" + getTemperatureEvaporatingOutOverrange())

            .add("TemperatureReturn=" + getTemperatureReturn())
            .add("TemperatureReturnError=" + getTemperatureReturnError())
            .add("TemperatureReturnUnderrange=" + getTemperatureReturnUnderrange())
            .add("TemperatureReturn=Overrange" + getTemperatureReturnOverrange())

            .toString();
    }


/* Slaveinfo:

4 - 0:3 EL3204
	Manufacturer: 0x00000002
	Product code: 0x0c843052
	Revision: 1114112
	Distributed Clocks: yes
	SM(0) Address: 0x1000, length: 128	Flags: 65574	Type: Mailbox messages receive
	SM(1) Address: 0x1080, length: 128	Flags: 65570	Type: Mailbox messages transmit
	SM(2) Address: 0x1100, length: 0	Flags: 4	Type: Cyclic process data receive
	SM(3) Address: 0x1180, length: 16	Flags: 65568	Type: Cyclic process data transmit
		TxPDO 0x1a00	RTD TxPDO-Map Ch.1
			0x6000:0x01 BOOLEAN	Underrange
			0x6000:0x02 BOOLEAN	Overrange
			0x6000:0x03 BIT2	Limit 1
			0x6000:0x05 BIT2	Limit 2
			0x6000:0x07 BOOLEAN	Error
			0x0000:0x00 (7 bit)
			0x1800:0x07 BOOLEAN	TxPDO State
			0x1800:0x09 BOOLEAN	TxPDO Toggle
			0x6000:0x11 INTEGER16	Value
		TxPDO 0x1a01	RTD TxPDO-Map Ch.2
			0x6010:0x01 BOOLEAN	Underrange
			0x6010:0x02 BOOLEAN	Overrange
			0x6010:0x03 BIT2	Limit 1
			0x6010:0x05 BIT2	Limit 2
			0x6010:0x07 BOOLEAN	Error
			0x0000:0x00 (7 bit)
			0x1801:0x07 BOOLEAN	TxPDO State
			0x1801:0x09 BOOLEAN	TxPDO Toggle
			0x6010:0x11 INTEGER16	Value
		TxPDO 0x1a02	RTD TxPDO-Map Ch.3
			0x6020:0x01 BOOLEAN	Underrange
			0x6020:0x02 BOOLEAN	Overrange
			0x6020:0x03 BIT2	Limit 1
			0x6020:0x05 BIT2	Limit 2
			0x6020:0x07 BOOLEAN	Error
			0x0000:0x00 (7 bit)
			0x1802:0x07 BOOLEAN	TxPDO State
			0x1802:0x09 BOOLEAN	TxPDO Toggle
			0x6020:0x11 INTEGER16	Value
		TxPDO 0x1a03	RTD TxPDO-Map Ch.4
			0x6030:0x01 BOOLEAN	Underrange
			0x6030:0x02 BOOLEAN	Overrange
			0x6030:0x03 BIT2	Limit 1
			0x6030:0x05 BIT2	Limit 2
			0x6030:0x07 BOOLEAN	Error
			0x0000:0x00 (7 bit)
			0x1803:0x07 BOOLEAN	TxPDO State
			0x1803:0x09 BOOLEAN	TxPDO Toggle
			0x6030:0x11 INTEGER16	Value

4 - 0:3 EL3204
	Manufacturer: 0x00000002
	Product code: 0x0c843052
	Revision: 1114112
	Distributed Clocks: yes
	SM(0) Address: 0x1000, length: 128	Flags: 65574	Type: Mailbox messages receive
	SM(1) Address: 0x1080, length: 128	Flags: 65570	Type: Mailbox messages transmit
	SM(2) Address: 0x1100, length: 0	Flags: 4	Type: Cyclic process data receive
	SM(3) Address: 0x1180, length: 16	Flags: 65568	Type: Cyclic process data transmit




 */

}
