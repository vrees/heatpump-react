import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FailureMessage from './failure-message';
import FailureMessageDetail from './failure-message-detail';
import FailureMessageUpdate from './failure-message-update';
import FailureMessageDeleteDialog from './failure-message-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FailureMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FailureMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FailureMessageDetail} />
      <ErrorBoundaryRoute path={match.url} component={FailureMessage} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FailureMessageDeleteDialog} />
  </>
);

export default Routes;
