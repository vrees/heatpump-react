import { ActionButton } from 'app/shared/model/enumerations/actionButton.model';

export const enum States {
  UNDEFINED = 'UNDEFINED',
  OFF = 'OFF',
  READY = 'READY',
  RUNNING = 'RUNNING',
  BACKLASH = 'BACKLASH',
  ERROR = 'ERROR'
}

export interface IStateProps {
  label: string;
  buttons: ActionButton[];
  color: string;
}

export const StateInfo = new Map<States, IStateProps>([
  [
    States.UNDEFINED,
    {
      label: 'Aus',
      buttons: [],
      color: 'light'
    }
  ],
  [
    States.OFF,
    {
      label: 'Aus',
      buttons: [ActionButton.SWITCH_ON],
      color: 'secondary'
    }
  ],
  [
    States.READY,
    {
      label: 'Bereit',
      buttons: [ActionButton.SWITCH_OFF],
      color: 'success'
    }
  ],
  [
    States.RUNNING,
    {
      label: 'Ein',
      buttons: [ActionButton.SWITCH_OFF],
      color: 'primary'
    }
  ],
  [
    States.BACKLASH,
    {
      label: 'Nachlauf',
      buttons: [ActionButton.SWITCH_OFF],
      color: 'warning'
    }
  ],
  [
    States.ERROR,
    {
      label: 'Error',
      buttons: [ActionButton.SWITCH_OFF],
      color: 'danger'
    }
  ]
]);
