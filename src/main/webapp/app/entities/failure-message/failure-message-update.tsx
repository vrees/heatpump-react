import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProcessdata } from 'app/shared/model/processdata.model';
import { getEntities as getProcessdata } from 'app/entities/processdata/processdata.reducer';
import { getEntity, updateEntity, createEntity, reset } from './failure-message.reducer';
import { IFailureMessage } from 'app/shared/model/failure-message.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFailureMessageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFailureMessageUpdateState {
  isNew: boolean;
  processdataId: string;
}

export class FailureMessageUpdate extends React.Component<IFailureMessageUpdateProps, IFailureMessageUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      processdataId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getProcessdata();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { failureMessageEntity } = this.props;
      const entity = {
        ...failureMessageEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/failure-message');
  };

  render() {
    const { failureMessageEntity, processdata, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="heatpumpApp.failureMessage.home.createOrEditLabel">Create or edit a FailureMessage</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : failureMessageEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="failure-message-id">ID</Label>
                    <AvInput id="failure-message-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="failureLevelLabel" for="failure-message-failureLevel">
                    Failure Level
                  </Label>
                  <AvInput
                    id="failure-message-failureLevel"
                    type="select"
                    className="form-control"
                    name="failureLevel"
                    value={(!isNew && failureMessageEntity.failureLevel) || 'ERROR'}
                  >
                    <option value="ERROR">ERROR</option>
                    <option value="WARNING">WARNING</option>
                    <option value="INFO">INFO</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="msgLabel" for="failure-message-msg">
                    Msg
                  </Label>
                  <AvField id="failure-message-msg" type="text" name="msg" />
                </AvGroup>
                <AvGroup>
                  <Label id="parameterLabel" for="failure-message-parameter">
                    Parameter
                  </Label>
                  <AvField id="failure-message-parameter" type="text" name="parameter" />
                </AvGroup>
                <AvGroup>
                  <Label for="failure-message-processdata">Processdata</Label>
                  <AvInput id="failure-message-processdata" type="select" className="form-control" name="processdata.id">
                    <option value="" key="0" />
                    {processdata
                      ? processdata.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/failure-message" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  processdata: storeState.processdata.entities,
  failureMessageEntity: storeState.failureMessage.entity,
  loading: storeState.failureMessage.loading,
  updating: storeState.failureMessage.updating,
  updateSuccess: storeState.failureMessage.updateSuccess
});

const mapDispatchToProps = {
  getProcessdata,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FailureMessageUpdate);
