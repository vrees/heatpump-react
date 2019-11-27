import {States} from "app/shared/model/enumerations/states.model";

export const enum ActionButton {
  SWITCH_ON = 'SWITCH_ON',
  SWITCH_OFF = 'SWITCH_OFF',
  ACKNOWLEDGE = 'ACKNOWLEDGE',
}

export interface IActionButtonProps {
  label: string;
  color: string;
}

export const ActionButtonInfo = new Map<ActionButton, IActionButtonProps>([
  [ActionButton.SWITCH_ON, {
    label: 'einschalten',
    color: 'primary'
  }],
  [ActionButton.SWITCH_OFF, {
    label: 'ausschalten',
    color: 'dark'
  }],
  [ActionButton.ACKNOWLEDGE, {
    label: 'quittieren',
    color: 'success'
  }]
]);
