import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Processdata from './processdata';
import ProcessdataDetail from './processdata-detail';
import ProcessdataUpdate from './processdata-update';
import ProcessdataDeleteDialog from './processdata-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProcessdataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProcessdataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProcessdataDetail} />
      <ErrorBoundaryRoute path={match.url} component={Processdata} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProcessdataDeleteDialog} />
  </>
);

export default Routes;
