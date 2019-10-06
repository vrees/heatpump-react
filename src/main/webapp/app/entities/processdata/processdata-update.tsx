import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './processdata.reducer';
import { IProcessdata } from 'app/shared/model/processdata.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProcessdataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProcessdataUpdateState {
  isNew: boolean;
}

export class ProcessdataUpdate extends React.Component<IProcessdataUpdateProps, IProcessdataUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    values.timestamp = convertDateTimeToServer(values.timestamp);

    if (errors.length === 0) {
      const { processdataEntity } = this.props;
      const entity = {
        ...processdataEntity,
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
    this.props.history.push('/entity/processdata');
  };

  render() {
    const { processdataEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="heatpumpApp.processdata.home.createOrEditLabel">Create or edit a Processdata</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : processdataEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="processdata-id">ID</Label>
                    <AvInput id="processdata-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="timestampLabel" for="processdata-timestamp">
                    Timestamp
                  </Label>
                  <AvInput
                    id="processdata-timestamp"
                    type="datetime-local"
                    className="form-control"
                    name="timestamp"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.processdataEntity.timestamp)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="highPressureLabel" for="processdata-highPressure">
                    High Pressure
                  </Label>
                  <AvField id="processdata-highPressure" type="string" className="form-control" name="highPressure" />
                  <UncontrolledTooltip target="highPressureLabel">Hochdruck Kältekreis in bar</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="lowPressureLabel" for="processdata-lowPressure">
                    Low Pressure
                  </Label>
                  <AvField id="processdata-lowPressure" type="string" className="form-control" name="lowPressure" />
                  <UncontrolledTooltip target="lowPressureLabel">Niederdruck Kältekreis in bar</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="evaporatingTemperatureInLabel" for="processdata-evaporatingTemperatureIn">
                    Evaporating Temperature In
                  </Label>
                  <AvField
                    id="processdata-evaporatingTemperatureIn"
                    type="string"
                    className="form-control"
                    name="evaporatingTemperatureIn"
                  />
                  <UncontrolledTooltip target="evaporatingTemperatureInLabel">Verdampfungstemperatur in °C</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="evaporatingTemperatureOutLabel" for="processdata-evaporatingTemperatureOut">
                    Evaporating Temperature Out
                  </Label>
                  <AvField
                    id="processdata-evaporatingTemperatureOut"
                    type="string"
                    className="form-control"
                    name="evaporatingTemperatureOut"
                  />
                  <UncontrolledTooltip target="evaporatingTemperatureOutLabel">Verdampfungstemperatur out in °C</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="pressureDiffenceEvaporatorLabel" for="processdata-pressureDiffenceEvaporator">
                    Pressure Diffence Evaporator
                  </Label>
                  <AvField
                    id="processdata-pressureDiffenceEvaporator"
                    type="string"
                    className="form-control"
                    name="pressureDiffenceEvaporator"
                  />
                  <UncontrolledTooltip target="pressureDiffenceEvaporatorLabel">Druckdifferenz Verdampfer in mbar</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="flowTemperatureLabel" for="processdata-flowTemperature">
                    Flow Temperature
                  </Label>
                  <AvField id="processdata-flowTemperature" type="string" className="form-control" name="flowTemperature" />
                  <UncontrolledTooltip target="flowTemperatureLabel">VorlaufTemperatur in °C</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="returnTemperatureLabel" for="processdata-returnTemperature">
                    Return Temperature
                  </Label>
                  <AvField id="processdata-returnTemperature" type="string" className="form-control" name="returnTemperature" />
                  <UncontrolledTooltip target="returnTemperatureLabel">Rücklauf-Temperatur in °C</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="switchOnSensorTemperatureLabel" for="processdata-switchOnSensorTemperature">
                    Switch On Sensor Temperature
                  </Label>
                  <AvField
                    id="processdata-switchOnSensorTemperature"
                    type="string"
                    className="form-control"
                    name="switchOnSensorTemperature"
                  />
                  <UncontrolledTooltip target="switchOnSensorTemperatureLabel">
                    schaltFuehlerTemperatur: Ein-/Aus-SchaltFühler misst die Temperatur in °C
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="overheatTemperatureLabel" for="processdata-overheatTemperature">
                    Overheat Temperature
                  </Label>
                  <AvField id="processdata-overheatTemperature" type="string" className="form-control" name="overheatTemperature" />
                  <UncontrolledTooltip target="overheatTemperatureLabel">
                    Überhitzung: Kühlmittel-Temperatur am Ausgang des Verdampfers (berechnet ?)
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="evaporatorOutTemperatureLabel" for="processdata-evaporatorOutTemperature">
                    Evaporator Out Temperature
                  </Label>
                  <AvField
                    id="processdata-evaporatorOutTemperature"
                    type="string"
                    className="form-control"
                    name="evaporatorOutTemperature"
                  />
                  <UncontrolledTooltip target="evaporatorOutTemperatureLabel">
                    SaugTemperatur: Kühlmittel-Temperatur am Ausgang des Verdampfers vor dem Eingang des Verdichters. Wird zusammen mit dem
                    Druck im Verdampfer zur Bestimmung der Überhitzung benötigt
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="heatRequestLabel" check>
                    <AvInput id="processdata-heatRequest" type="checkbox" className="form-control" name="heatRequest" />
                    Heat Request
                  </Label>
                  <UncontrolledTooltip target="heatRequestLabel">Wärme Anforderung</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="userConfirmationLabel" check>
                    <AvInput id="processdata-userConfirmation" type="checkbox" className="form-control" name="userConfirmation" />
                    User Confirmation
                  </Label>
                  <UncontrolledTooltip target="userConfirmationLabel">EinAusQuittierung</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="incidentFlowLabel" check>
                    <AvInput id="processdata-incidentFlow" type="checkbox" className="form-control" name="incidentFlow" />
                    Incident Flow
                  </Label>
                  <UncontrolledTooltip target="incidentFlowLabel">
                    Stoerung Durchfluss - minimale Druchlussmenge unterschritten
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="incidentCompressorLabel" check>
                    <AvInput id="processdata-incidentCompressor" type="checkbox" className="form-control" name="incidentCompressor" />
                    Incident Compressor
                  </Label>
                  <UncontrolledTooltip target="incidentCompressorLabel">Stoerung Verdichter / Motorschutzschalter</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="incidentLowPressureLabel" check>
                    <AvInput id="processdata-incidentLowPressure" type="checkbox" className="form-control" name="incidentLowPressure" />
                    Incident Low Pressure
                  </Label>
                  <UncontrolledTooltip target="incidentLowPressureLabel">
                    Stoerung Niederdruck: Ranco-Thermostat meldet zu niedrigen Niederdruck
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="incidentHighPressureLabel" check>
                    <AvInput id="processdata-incidentHighPressure" type="checkbox" className="form-control" name="incidentHighPressure" />
                    Incident High Pressure
                  </Label>
                  <UncontrolledTooltip target="incidentHighPressureLabel">
                    Stoerung Hochdruck Ranco-Thermostat meldet zu hohen Hochdruck
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="operatingStateWaterPumpLabel" check>
                    <AvInput
                      id="processdata-operatingStateWaterPump"
                      type="checkbox"
                      className="form-control"
                      name="operatingStateWaterPump"
                    />
                    Operating State Water Pump
                  </Label>
                  <UncontrolledTooltip target="operatingStateWaterPumpLabel">Status Heizungspume: Ein/Aus</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="operatingStateCompressorLabel" check>
                    <AvInput
                      id="processdata-operatingStateCompressor"
                      type="checkbox"
                      className="form-control"
                      name="operatingStateCompressor"
                    />
                    Operating State Compressor
                  </Label>
                  <UncontrolledTooltip target="operatingStateCompressorLabel">Status Verdichter: Ein/Aus</UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="warningLowPressureLabel" check>
                    <AvInput id="processdata-warningLowPressure" type="checkbox" className="form-control" name="warningLowPressure" />
                    Warning Low Pressure
                  </Label>
                  <UncontrolledTooltip target="warningLowPressureLabel">
                    Warung Niederdruck (Soft-Wert falls gemessener Niederdruck unter konfigurierte Grenze fällt)
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="warningHighPressureLabel" check>
                    <AvInput id="processdata-warningHighPressure" type="checkbox" className="form-control" name="warningHighPressure" />
                    Warning High Pressure
                  </Label>
                  <UncontrolledTooltip target="warningHighPressureLabel">
                    Warnung Hochdruck (Soft-Wert falls gemessener Hochdruck über konfigurierte Grenze steigt)
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="alarmExpansionValveLabel" check>
                    <AvInput id="processdata-alarmExpansionValve" type="checkbox" className="form-control" name="alarmExpansionValve" />
                    Alarm Expansion Valve
                  </Label>
                  <UncontrolledTooltip target="alarmExpansionValveLabel">Alarm des Elektronischen Expansionsentils EEV</UncontrolledTooltip>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/processdata" replace color="info">
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
  processdataEntity: storeState.processdata.entity,
  loading: storeState.processdata.loading,
  updating: storeState.processdata.updating,
  updateSuccess: storeState.processdata.updateSuccess
});

const mapDispatchToProps = {
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
)(ProcessdataUpdate);
