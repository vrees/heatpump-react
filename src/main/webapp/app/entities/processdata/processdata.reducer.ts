import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProcessdata, defaultValue } from 'app/shared/model/processdata.model';
import { IPayload } from 'react-jhipster/src/type/redux-action.type';

export const ACTION_TYPES = {
  FETCH_PROCESSDATA_LIST: 'processdata/FETCH_PROCESSDATA_LIST',
  FETCH_PROCESSDATA: 'processdata/FETCH_PROCESSDATA',
  FETCH_LATEST_PROCESSDATA: 'processdata/FETCH_LATEST_PROCESSDATA',
  CREATE_PROCESSDATA: 'processdata/CREATE_PROCESSDATA',
  UPDATE_PROCESSDATA: 'processdata/UPDATE_PROCESSDATA',
  DELETE_PROCESSDATA: 'processdata/DELETE_PROCESSDATA',
  RESET: 'processdata/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProcessdata>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ProcessdataState = Readonly<typeof initialState>;

// Reducer

export default (state: ProcessdataState = initialState, action): ProcessdataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROCESSDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROCESSDATA):
    case REQUEST(ACTION_TYPES.FETCH_LATEST_PROCESSDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROCESSDATA):
    case REQUEST(ACTION_TYPES.UPDATE_PROCESSDATA):
    case REQUEST(ACTION_TYPES.DELETE_PROCESSDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROCESSDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROCESSDATA):
    case FAILURE(ACTION_TYPES.FETCH_LATEST_PROCESSDATA):
    case FAILURE(ACTION_TYPES.CREATE_PROCESSDATA):
    case FAILURE(ACTION_TYPES.UPDATE_PROCESSDATA):
    case FAILURE(ACTION_TYPES.DELETE_PROCESSDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROCESSDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROCESSDATA):
    case SUCCESS(ACTION_TYPES.FETCH_LATEST_PROCESSDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROCESSDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_PROCESSDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROCESSDATA):
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

const apiUrl = 'api/processdata';

// Actions

export const getEntities: ICrudGetAllAction<IProcessdata> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PROCESSDATA_LIST,
    payload: axios.get<IProcessdata>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IProcessdata> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROCESSDATA,
    payload: axios.get<IProcessdata>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProcessdata> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROCESSDATA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProcessdata> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROCESSDATA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProcessdata> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROCESSDATA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const getLatestProcessdata: IGetLatestProcessdataAction<IProcessdata> = () => {
  const requestUrl = `${apiUrl}/latest`;
  return {
    type: ACTION_TYPES.FETCH_PROCESSDATA,
    payload: axios.get<IProcessdata>(requestUrl)
  };
};

export type IGetLatestProcessdataAction<T> = () => IPayload<T> | ((dispatch: any) => IPayload<T>);

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
