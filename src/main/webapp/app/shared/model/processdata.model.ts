import {Moment} from 'moment';
import {States} from 'app/shared/model/enumerations/states.model';
import {IFailureMessage} from "app/shared/model/failureMessage.model";

export interface IProcessdata {
  id?: string;
  timestamp?: Moment;
  state?: States;
  temperatureEvaporatingIn?: number;
  temperatureEvaporatingOut?: number;
  temperatureFlow?: number;
  temperatureReturn?: number;
  temperatureSwitchOnSensor?: number;
  temperatureOverheatedGas?: number;
  pressureHigh?: number;
  pressureLow?: number;
  pressureDiffenceEvaporator?: number;
  heatRequest?: boolean;
  userConfirmation?: boolean;
  alarmExpansionValve?: boolean;
  incidentFlow?: boolean;
  incidentCompressor?: boolean;
  incidentLowPressure?: boolean;
  incidentHighPressure?: boolean;
  operatingStateWaterPump?: boolean;
  operatingStateCompressor?: boolean;
  calculatedOverheatTemperature?: number;
  warningLowPressure?: boolean;
  warningHighPressure?: boolean;
  messages?: IFailureMessage[];
  waitCounter?: number;
}

export const defaultValue: Readonly<IProcessdata> = {
  heatRequest: false,
  userConfirmation: false,
  alarmExpansionValve: false,
  incidentFlow: false,
  incidentCompressor: false,
  incidentLowPressure: false,
  incidentHighPressure: false,
  operatingStateWaterPump: false,
  operatingStateCompressor: false,
  warningLowPressure: false,
  warningHighPressure: false,
  state: States.UNDEFINED
};
