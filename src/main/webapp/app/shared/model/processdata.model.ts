import { Moment } from 'moment';

export interface IProcessdata {
  id?: string;
  timestamp?: Moment;
  highPressure?: number;
  lowPressure?: number;
  evaporatingTemperatureIn?: number;
  evaporatingTemperatureOut?: number;
  pressureDiffenceEvaporator?: number;
  flowTemperature?: number;
  returnTemperature?: number;
  switchOnSensorTemperature?: number;
  overheatTemperature?: number;
  evaporatorOutTemperature?: number;
  heatRequest?: boolean;
  userConfirmation?: boolean;
  incidentFlow?: boolean;
  incidentCompressor?: boolean;
  incidentLowPressure?: boolean;
  incidentHighPressure?: boolean;
  operatingStateWaterPump?: boolean;
  operatingStateCompressor?: boolean;
  warningLowPressure?: boolean;
  warningHighPressure?: boolean;
  alarmExpansionValve?: boolean;
}

export const defaultValue: Readonly<IProcessdata> = {
  heatRequest: false,
  userConfirmation: false,
  incidentFlow: false,
  incidentCompressor: false,
  incidentLowPressure: false,
  incidentHighPressure: false,
  operatingStateWaterPump: false,
  operatingStateCompressor: false,
  warningLowPressure: false,
  warningHighPressure: false,
  alarmExpansionValve: false
};
