import { Moment } from 'moment';
import { States } from 'app/shared/model/enumerations/states.model';
import {FailureLevel} from "app/shared/model/enumerations/failureLevel.model";

export interface IFailureMessage {
  failureLevel: FailureLevel;
  msg: string;
  parameter: string;
}
