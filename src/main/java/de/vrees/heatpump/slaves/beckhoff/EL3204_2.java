package de.vrees.heatpump.slaves.beckhoff;

import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;
import us.ihmc.etherCAT.master.TxPDO;

import java.util.StringJoiner;

/**
 * EL3204 | 4-Kanal PD100
 * <p>
 * EL3204 | 4-Kanal-Eingangsklemme PT100 (RTD)
 */
public class EL3204_2 extends Slave {
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
        // newer versions do have more status infos
//        Bit7 gap = new Bit7();
//        Bool txPDOState = new Bool();
//        Bool txPDOToggle = new Bool();
        Signed16 value = new Signed16();
    }

    private final Input temperatureSwitchOnSensor = new Input(0x1a00);
    private final Input temperatureOverheatedGas = new Input(0x1a01);
    private final Input temperatureReserve1 = new Input(0x1a02);
    private final Input temperatureReserve2 = new Input(0x1a03);


    public EL3204_2(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(2, true));
        registerSyncManager(new SyncManager(3, true));

        sm(3).registerPDO(temperatureSwitchOnSensor);
        sm(3).registerPDO(temperatureOverheatedGas);
        sm(3).registerPDO(temperatureReserve1);
        sm(3).registerPDO(temperatureReserve2);
    }


    public int getTemperatureSwitchOnSensor() {
        return temperatureSwitchOnSensor.value.get();
    }

    public boolean getTemperatureSwitchOnSensorUnderrange() {
        return temperatureSwitchOnSensor.underrange.get();
    }

    public boolean getTemperatureSwitchOnSensorOverrange() {
        return temperatureSwitchOnSensor.overrange.get();
    }

    public boolean getTemperatureSwitchOnSensorError() {
        return temperatureSwitchOnSensor.error.get();
    }


    public int getTemperatureOverheatedGas() {
        return temperatureOverheatedGas.value.get();
    }

    public boolean getTemperatureOverheatedGasUnderrange() {
        return temperatureOverheatedGas.underrange.get();
    }

    public boolean getTemperatureOverheatedGasOverrange() {
        return temperatureOverheatedGas.overrange.get();
    }

    public boolean getTemperatureOverheatedGasError() {
        return temperatureOverheatedGas.error.get();
    }


    public int getTemperatureReserve1() {
        return temperatureReserve1.value.get();
    }

    public boolean getTemperatureReserve1Underrange() {
        return temperatureReserve1.underrange.get();
    }

    public boolean getTemperatureReserve1Overrange() {
        return temperatureReserve1.overrange.get();
    }

    public boolean getTemperatureReserve1Error() {
        return temperatureReserve1.error.get();
    }


    public int getTemperatureReserve2() {
        return temperatureReserve2.value.get();
    }

    public boolean getTemperatureReserve2Underrange() {
        return temperatureReserve2.underrange.get();
    }

    public boolean getTemperatureReserve2Overrange() {
        return temperatureReserve2.overrange.get();
    }

    public boolean getTemperatureReserve2Error() {
        return temperatureReserve2.error.get();
    }

    public String toProcessdataString() {
        return new StringJoiner(", ", EL3204_2.class.getSimpleName() + "[", "]")
            .add("TemperatureSwitchOnSensor=" + getTemperatureSwitchOnSensor())
            .add("TemperatureSwitchOnSensorError=" + getTemperatureSwitchOnSensorError())
            .add("TemperatureSwitchOnSensorUnderrange=" + getTemperatureSwitchOnSensorUnderrange())
            .add("TemperatureSwitchOnSensor=Overrange" + getTemperatureSwitchOnSensorOverrange())

            .add("TemperatureOverheatedGas=" + getTemperatureOverheatedGas())
            .add("TemperatureOverheatedGasError=" + getTemperatureOverheatedGasError())
            .add("TemperatureOverheatedGasUnderrange=" + getTemperatureOverheatedGasUnderrange())
            .add("TemperatureOverheatedGas=Overrange" + getTemperatureOverheatedGasOverrange())

            .add("TemperatureReserve1=" + getTemperatureReserve1())
            .add("TemperatureReserve1Error=" + getTemperatureReserve1Error())
            .add("TemperatureReserve1Underrange=" + getTemperatureReserve1Underrange())
            .add("TemperatureReserve1=Overrange" + getTemperatureReserve1Overrange())

            .add("TemperatureReserve2=" + getTemperatureReserve2())
            .add("TemperatureReserve2Error=" + getTemperatureReserve2Error())
            .add("TemperatureReserve2Underrange=" + getTemperatureReserve2Underrange())
            .add("TemperatureReserve2=Overrange" + getTemperatureReserve2Overrange())

            .toString();
    }


/* Slaveinfo:

7 - 0:6 EL3204
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
7 - 0:6 EL3204
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
