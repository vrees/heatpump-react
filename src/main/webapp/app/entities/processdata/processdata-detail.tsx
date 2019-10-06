import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './processdata.reducer';
import { IProcessdata } from 'app/shared/model/processdata.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcessdataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProcessdataDetail extends React.Component<IProcessdataDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { processdataEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Processdata [<b>{processdataEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="timestamp">Timestamp</span>
            </dt>
            <dd>
              <TextFormat value={processdataEntity.timestamp} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="highPressure">High Pressure</span>
              <UncontrolledTooltip target="highPressure">Hochdruck Kältekreis in bar</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.highPressure}</dd>
            <dt>
              <span id="lowPressure">Low Pressure</span>
              <UncontrolledTooltip target="lowPressure">Niederdruck Kältekreis in bar</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.lowPressure}</dd>
            <dt>
              <span id="evaporatingTemperatureIn">Evaporating Temperature In</span>
              <UncontrolledTooltip target="evaporatingTemperatureIn">Verdampfungstemperatur in °C</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.evaporatingTemperatureIn}</dd>
            <dt>
              <span id="evaporatingTemperatureOut">Evaporating Temperature Out</span>
              <UncontrolledTooltip target="evaporatingTemperatureOut">Verdampfungstemperatur out in °C</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.evaporatingTemperatureOut}</dd>
            <dt>
              <span id="pressureDiffenceEvaporator">Pressure Diffence Evaporator</span>
              <UncontrolledTooltip target="pressureDiffenceEvaporator">Druckdifferenz Verdampfer in mbar</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.pressureDiffenceEvaporator}</dd>
            <dt>
              <span id="flowTemperature">Flow Temperature</span>
              <UncontrolledTooltip target="flowTemperature">VorlaufTemperatur in °C</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.flowTemperature}</dd>
            <dt>
              <span id="returnTemperature">Return Temperature</span>
              <UncontrolledTooltip target="returnTemperature">Rücklauf-Temperatur in °C</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.returnTemperature}</dd>
            <dt>
              <span id="switchOnSensorTemperature">Switch On Sensor Temperature</span>
              <UncontrolledTooltip target="switchOnSensorTemperature">
                schaltFuehlerTemperatur: Ein-/Aus-SchaltFühler misst die Temperatur in °C
              </UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.switchOnSensorTemperature}</dd>
            <dt>
              <span id="overheatTemperature">Overheat Temperature</span>
              <UncontrolledTooltip target="overheatTemperature">
                Überhitzung: Kühlmittel-Temperatur am Ausgang des Verdampfers (berechnet ?)
              </UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.overheatTemperature}</dd>
            <dt>
              <span id="evaporatorOutTemperature">Evaporator Out Temperature</span>
              <UncontrolledTooltip target="evaporatorOutTemperature">
                SaugTemperatur: Kühlmittel-Temperatur am Ausgang des Verdampfers vor dem Eingang des Verdichters. Wird zusammen mit dem
                Druck im Verdampfer zur Bestimmung der Überhitzung benötigt
              </UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.evaporatorOutTemperature}</dd>
            <dt>
              <span id="heatRequest">Heat Request</span>
              <UncontrolledTooltip target="heatRequest">Wärme Anforderung</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.heatRequest ? 'true' : 'false'}</dd>
            <dt>
              <span id="userConfirmation">User Confirmation</span>
              <UncontrolledTooltip target="userConfirmation">EinAusQuittierung</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.userConfirmation ? 'true' : 'false'}</dd>
            <dt>
              <span id="incidentFlow">Incident Flow</span>
              <UncontrolledTooltip target="incidentFlow">Stoerung Durchfluss - minimale Druchlussmenge unterschritten</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.incidentFlow ? 'true' : 'false'}</dd>
            <dt>
              <span id="incidentCompressor">Incident Compressor</span>
              <UncontrolledTooltip target="incidentCompressor">Stoerung Verdichter / Motorschutzschalter</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.incidentCompressor ? 'true' : 'false'}</dd>
            <dt>
              <span id="incidentLowPressure">Incident Low Pressure</span>
              <UncontrolledTooltip target="incidentLowPressure">
                Stoerung Niederdruck: Ranco-Thermostat meldet zu niedrigen Niederdruck
              </UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.incidentLowPressure ? 'true' : 'false'}</dd>
            <dt>
              <span id="incidentHighPressure">Incident High Pressure</span>
              <UncontrolledTooltip target="incidentHighPressure">
                Stoerung Hochdruck Ranco-Thermostat meldet zu hohen Hochdruck
              </UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.incidentHighPressure ? 'true' : 'false'}</dd>
            <dt>
              <span id="operatingStateWaterPump">Operating State Water Pump</span>
              <UncontrolledTooltip target="operatingStateWaterPump">Status Heizungspume: Ein/Aus</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.operatingStateWaterPump ? 'true' : 'false'}</dd>
            <dt>
              <span id="operatingStateCompressor">Operating State Compressor</span>
              <UncontrolledTooltip target="operatingStateCompressor">Status Verdichter: Ein/Aus</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.operatingStateCompressor ? 'true' : 'false'}</dd>
            <dt>
              <span id="warningLowPressure">Warning Low Pressure</span>
              <UncontrolledTooltip target="warningLowPressure">
                Warung Niederdruck (Soft-Wert falls gemessener Niederdruck unter konfigurierte Grenze fällt)
              </UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.warningLowPressure ? 'true' : 'false'}</dd>
            <dt>
              <span id="warningHighPressure">Warning High Pressure</span>
              <UncontrolledTooltip target="warningHighPressure">
                Warnung Hochdruck (Soft-Wert falls gemessener Hochdruck über konfigurierte Grenze steigt)
              </UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.warningHighPressure ? 'true' : 'false'}</dd>
            <dt>
              <span id="alarmExpansionValve">Alarm Expansion Valve</span>
              <UncontrolledTooltip target="alarmExpansionValve">Alarm des Elektronischen Expansionsentils EEV</UncontrolledTooltip>
            </dt>
            <dd>{processdataEntity.alarmExpansionValve ? 'true' : 'false'}</dd>
          </dl>
          <Button tag={Link} to="/entity/processdata" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/processdata/${processdataEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ processdata }: IRootState) => ({
  processdataEntity: processdata.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProcessdataDetail);
