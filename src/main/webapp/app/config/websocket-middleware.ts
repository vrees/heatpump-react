import SockJS from 'sockjs-client';

import Stomp from 'webstomp-client';
import { Observable } from 'rxjs';
import { Storage } from 'react-jhipster';
import { store } from 'app/index';

import { ACTION_TYPES as PD_ACTIONS } from 'app/entities/processdata/processdata.reducer';
import { ACTION_TYPES as AUTH_ACTIONS } from 'app/shared/reducers/authentication';
import { SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import processdata from 'app/entities/processdata/processdata';

let stompClient = null;

let subscriber = null;
let connection: Promise<any>;
let connectedPromise: any = null;
let listener: Observable<any>;
let listenerObserver: any;
let alreadyConnectedOnce = false;

const createConnection = (): Promise<any> => new Promise((resolve, reject) => (connectedPromise = resolve));

const createListener = (): Observable<any> =>
  new Observable(observer => {
    listenerObserver = observer;
  });

const sendActivity = () => {
  connection.then(() => {
    stompClient.send(
      '/topic/activity', // destination
      JSON.stringify({ page: window.location.hash }), // body
      {} // header
    );
  });
};

const subscribe = () => {
  connection.then(() => {
    subscriber = stompClient.subscribe('/topic/processdata', data => {
      console.log('data:', data);
      // listenerObserver.next(JSON.parse(data.body));

      store.dispatch({
        type: PD_ACTIONS.WEBSOCKET_RECEIVE_PROCESSDATA,
        payload: JSON.parse(data.body)
      });
    });
  });
};

export const websocketConnect = () => {
  if (connectedPromise !== null || alreadyConnectedOnce) {
    // the connection is already being established
    return;
  }
  connection = createConnection();
  listener = createListener();

  // building absolute path so that websocket doesn't fail when deploying with a context path
  const loc = window.location;
  const baseHref = document
    .querySelector('base')
    .getAttribute('href')
    .replace(/\/$/, '');

  const headers = {};
  let url = '//' + loc.host + baseHref + '/websocket/tracker';
  const authToken = Storage.local.get('jhi-authenticationToken') || Storage.session.get('jhi-authenticationToken');
  if (authToken) {
    url += '?access_token=' + authToken;
  }
  const socket = new SockJS(url);
  stompClient = Stomp.over(socket);

  stompClient.connect(headers, () => {
    connectedPromise('success');
    connectedPromise = null;
    subscribe();
    sendActivity();
    if (!alreadyConnectedOnce) {
      window.onhashchange = () => {
        sendActivity();
      };
      alreadyConnectedOnce = true;
    }
  });
};

export const websocketDisconnect = () => {
  if (stompClient !== null) {
    stompClient.disconnect();
    stompClient = null;
  }
  window.onhashchange = () => {};
  alreadyConnectedOnce = false;
};

const receive = () => listener;

const unsubscribe = () => {
  if (subscriber !== null) {
    subscriber.unsubscribe();
  }
  listener = createListener();
};

export default store => next => action => {
  // console.log("store-next-action:", action);
  //
  // if (action.type === SUCCESS(AUTH_ACTIONS.GET_SESSION)) {
  //   connect();
  //   if (!alreadyConnectedOnce) {
  //     receive().subscribe(processdata => {
  //       return store.dispatch({
  //         type: PD_ACTIONS.WEBSOCKET_RECEIVE_PROCESSDATA,
  //         payload: processdata
  //       });
  //     });
  //   }
  // } else if (action.type === FAILURE(AUTH_ACTIONS.GET_SESSION)) {
  //   unsubscribe();
  //   disconnect();
  // }
  return next(action);
};
