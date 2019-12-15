import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './failure-message.reducer';
import { IFailureMessage } from 'app/shared/model/failure-message.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFailureMessageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FailureMessageDetail extends React.Component<IFailureMessageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { failureMessageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            FailureMessage [<b>{failureMessageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="failureLevel">Failure Level</span>
            </dt>
            <dd>{failureMessageEntity.failureLevel}</dd>
            <dt>
              <span id="msg">Msg</span>
            </dt>
            <dd>{failureMessageEntity.msg}</dd>
            <dt>
              <span id="parameter">Parameter</span>
            </dt>
            <dd>{failureMessageEntity.parameter}</dd>
            <dt>Processdata</dt>
            <dd>{failureMessageEntity.processdata ? failureMessageEntity.processdata.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/failure-message" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/failure-message/${failureMessageEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ failureMessage }: IRootState) => ({
  failureMessageEntity: failureMessage.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FailureMessageDetail);
