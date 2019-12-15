import { FailureLevel } from 'app/shared/model/enumerations/failureLevel.model';

export interface IFailureMessage {
  failureLevel: FailureLevel;
  msg: string;
  parameter: string;
}
