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

 Name:EL3122
 Output size: 0bits
 Input size: 48bits
 State: 4
 Delay: 0[ns]
 Has DC: 0
 Activeports:1.0.0.0
 Configured address: 1004
 Man: 00000002 ID: 0c323052 Rev: 00000000
 SM0 A:1800 L: 246 F:00010026 Type:1
 SM1 A:18f6 L: 246 F:00010022 Type:2
 SM2 A:1000 L:   0 F:00000024 Type:3
 SM3 A:1100 L:   6 F:00010020 Type:4
 FMMU0 Ls:00000002 Ll:   6 Lsb:0 Leb:7 Ps:1100 Psb:0 Ty:01 Act:01
 FMMUfunc 0:3 1:2 2:0 3:0
 MBX length wr: 246 rd: 246 MBX protocols : 0c
 CoE details: 0f FoE details: 01 EoE details: 00 SoE details: 00
 Ebus current: 180[mA]
 only LRD/LWR:0
PDO mapping according to CoE :
  SM2 outputs
     addr b   index: sub bitl data_type    name
  SM3 inputs
     addr b   index: sub bitl data_type    name
  [0x0002.0] 0x3101:0x01 0x08 UNSIGNED8    Status
  [0x0003.0] 0x3101:0x02 0x10 INTEGER16    Value
  [0x0005.0] 0x3102:0x01 0x08 UNSIGNED8    Status
  [0x0006.0] 0x3102:0x02 0x10 INTEGER16    Value

 */

}
