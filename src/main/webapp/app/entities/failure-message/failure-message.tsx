import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './failure-message.reducer';
import { IFailureMessage } from 'app/shared/model/failure-message.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFailureMessageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class FailureMessage extends React.Component<IFailureMessageProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { failureMessageList, match } = this.props;
    return (
      <div>
        <h2 id="failure-message-heading">
          Failure Messages
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Failure Message
          </Link>
        </h2>
        <div className="table-responsive">
          {failureMessageList && failureMessageList.length > 0 ? (
            <Table responsive aria-describedby="failure-message-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Failure Level</th>
                  <th>Msg</th>
                  <th>Parameter</th>
                  <th>Processdata</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {failureMessageList.map((failureMessage, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${failureMessage.id}`} color="link" size="sm">
                        {failureMessage.id}
                      </Button>
                    </td>
                    <td>{failureMessage.failureLevel}</td>
                    <td>{failureMessage.msg}</td>
                    <td>{failureMessage.parameter}</td>
                    <td>
                      {failureMessage.processdata ? (
                        <Link to={`processdata/${failureMessage.processdata.id}`}>{failureMessage.processdata.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${failureMessage.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${failureMessage.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${failureMessage.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Failure Messages found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ failureMessage }: IRootState) => ({
  failureMessageList: failureMessage.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FailureMessage);
