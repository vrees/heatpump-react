import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFailureMessage, defaultValue } from 'app/shared/model/failure-message.model';

export const ACTION_TYPES = {
  FETCH_FAILUREMESSAGE_LIST: 'failureMessage/FETCH_FAILUREMESSAGE_LIST',
  FETCH_FAILUREMESSAGE: 'failureMessage/FETCH_FAILUREMESSAGE',
  CREATE_FAILUREMESSAGE: 'failureMessage/CREATE_FAILUREMESSAGE',
  UPDATE_FAILUREMESSAGE: 'failureMessage/UPDATE_FAILUREMESSAGE',
  DELETE_FAILUREMESSAGE: 'failureMessage/DELETE_FAILUREMESSAGE',
  RESET: 'failureMessage/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFailureMessage>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FailureMessageState = Readonly<typeof initialState>;

// Reducer

export default (state: FailureMessageState = initialState, action): FailureMessageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FAILUREMESSAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FAILUREMESSAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FAILUREMESSAGE):
    case REQUEST(ACTION_TYPES.UPDATE_FAILUREMESSAGE):
    case REQUEST(ACTION_TYPES.DELETE_FAILUREMESSAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FAILUREMESSAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FAILUREMESSAGE):
    case FAILURE(ACTION_TYPES.CREATE_FAILUREMESSAGE):
    case FAILURE(ACTION_TYPES.UPDATE_FAILUREMESSAGE):
    case FAILURE(ACTION_TYPES.DELETE_FAILUREMESSAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FAILUREMESSAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FAILUREMESSAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FAILUREMESSAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_FAILUREMESSAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FAILUREMESSAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/failure-messages';

// Actions

export const getEntities: ICrudGetAllAction<IFailureMessage> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FAILUREMESSAGE_LIST,
  payload: axios.get<IFailureMessage>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFailureMessage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FAILUREMESSAGE,
    payload: axios.get<IFailureMessage>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFailureMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FAILUREMESSAGE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFailureMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FAILUREMESSAGE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFailureMessage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FAILUREMESSAGE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
