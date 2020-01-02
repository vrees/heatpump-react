import { FailureLevel } from 'app/shared/model/enumerations/failureLevel.model';
import { States } from 'app/shared/model/enumerations/states.model';
import { IProcessdata } from 'app/shared/model/processdata.model';

export interface IFailureMessage {
  failureLevel: FailureLevel;
  msg: string;
  parameter: string;
}

export const defaultValue: Readonly<IFailureMessage> = {
  failureLevel: FailureLevel.INFO,
  msg: '<msg>',
  parameter: '<parameter>'
};
