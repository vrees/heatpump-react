import React from 'react';
import {connect} from 'react-redux';
import {websocketConnect, websocketDisconnect} from '../../config/websocket-middleware';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, UncontrolledTooltip, FormGroup, Row, Col, Label} from 'reactstrap';
import {ICrudGetAction, TextFormat} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getLatestProcessdata} from '../../entities/processdata/processdata.reducer';
import {IProcessdata} from 'app/shared/model/processdata.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface IDataViewProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export class DataView extends React.Component<IDataViewProps> {
  componentDidMount() {
    this.props.getLatestProcessdata();
  }

  handleConnect = () => {
    websocketConnect();
  };

  handleDisconnect = () => {
    websocketDisconnect();
  };

  render() {
    const {processdataEntity} = this.props;
    return (
      <div>

        <p>
          <Button color="primary" onClick={this.handleConnect}>Connect</Button>
          <Button color="primary" onClick={this.handleDisconnect}>Disconnect</Button>
        </p>

        <h2>
          Processdata [<b>{processdataEntity.id}</b>]
        </h2>

        <h4>
          <Row>
            <Label md={4}>Verdampfungstemperatur in °C</Label>
            <Label md={2}>{processdataEntity.temperatureEvaporatingIn}</Label>
            <Label md={4}>Verdampfungstemperatur out in °C</Label>
            <Label md={2}>{processdataEntity.temperatureEvaporatingOut}</Label>
          </Row>

          <FormGroup row>
            <Label md={4}>Vorlauf-Temperatur in °C</Label>
            <Label md={2}>{processdataEntity.temperatureFlow}</Label>
            <Label md={4}>Rücklauf-Temperatur in °C</Label>
            <Label md={2}>{processdataEntity.temperatureReturn}</Label>
          </FormGroup>
        </h4>


        <Row>
          <Col md="8">
            <dl className="jh-entity-details">
              <dt>
                <span id="timestamp">Timestamp</span>
              </dt>
              <dd>
                <TextFormat value={processdataEntity.timestamp} type="date" format={APP_DATE_FORMAT}/>
              </dd>
              <dt>
                <span id="state">State</span>
                <UncontrolledTooltip target="state">Status der Statemachine = Betrriebszustand</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.state}</dd>
              <dt>
                <span id="temperatureEvaporatingIn">Temperature Evaporating In</span>
                <UncontrolledTooltip target="temperatureEvaporatingIn">Verdampfungstemperatur in
                  °C</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.temperatureEvaporatingIn}</dd>
              <dt>
                <span id="temperatureEvaporatingOut">Temperature Evaporating Out</span>
                <UncontrolledTooltip target="temperatureEvaporatingOut">Verdampfungstemperatur out in
                  °C</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.temperatureEvaporatingOut}</dd>


              <dt>
                <span id="temperatureFlow">Temperature Flow</span>
                <UncontrolledTooltip target="temperatureFlow">Vorlauf-Temperatur in °C</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.temperatureFlow}</dd>
              <dt>
                <span id="temperatureReturn">Temperature Return</span>
                <UncontrolledTooltip target="temperatureReturn">Rücklauf-Temperatur in °C</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.temperatureReturn}</dd>
              <dt>
                <span id="temperatureSwitchOnSensor">Temperature Switch On Sensor</span>
                <UncontrolledTooltip target="temperatureSwitchOnSensor">
                  SchaltFuehlerTemperatur: Ein-/Aus-SchaltFühler misst die Temperatur am Puffer oben in °C
                </UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.temperatureSwitchOnSensor}</dd>
              <dt>
                <span id="temperatureOverheatedGas">Temperature Overheated Gas</span>
                <UncontrolledTooltip target="temperatureOverheatedGas">
                  SaugTemperatur: Kühlmittel-Temperatur am Ausgang des Verdampfers vor dem Eingang des Verdichters, also
                  auf der
                  Niederdruck-Seite.\nWird zusammen mit dem Druck im Verdampfer zur Berechnung der Überhitzung
                  benötigt\nGesättigteVerdampfungstemperatur = Druck mal TemperaturKonstante des
                  Kühlmittels\nUeberhitzung
                  = Temperatur des
                  ueberhitzten Gases - GesättigteVerdampfungstemperatur
                </UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.temperatureOverheatedGas}</dd>
              <dt>
                <span id="pressureHigh">Pressure High</span>
                <UncontrolledTooltip target="pressureHigh">Hochdruck Kältekreis in bar</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.pressureHigh}</dd>
              <dt>
                <span id="pressureLow">Pressure Low</span>
                <UncontrolledTooltip target="pressureLow">Niederdruck Kältekreis in bar</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.pressureLow}</dd>
              <dt>
                <span id="pressureDiffenceEvaporator">Pressure Diffence Evaporator</span>
                <UncontrolledTooltip target="pressureDiffenceEvaporator">
                  Druckdifferenz Verdampfer in mbar, minVal=0 maxVal=200
                </UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.pressureDiffenceEvaporator}</dd>
              <dt>
                <span id="heatRequest">Heat Request</span>
                <UncontrolledTooltip target="heatRequest">Wärme Anforderung</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.heatRequest ? 'true' : 'false'}</dd>
              <dt>
                <span id="userConfirmation">User Confirmation</span>
                <UncontrolledTooltip target="userConfirmation">Taster Ein-/Aus- Quittierung</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.userConfirmation ? 'true' : 'false'}</dd>
              <dt>
                <span id="alarmExpansionValve">Alarm Expansion Valve</span>
                <UncontrolledTooltip target="alarmExpansionValve">Alarm des Elektronischen Expansionsentils
                  EEV</UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.alarmExpansionValve ? 'true' : 'false'}</dd>
              <dt>
                <span id="incidentFlow">Incident Flow</span>
                <UncontrolledTooltip target="incidentFlow">
                  Stoerung Durchfluss - minimale Druchlussmenge im Verdampfer unterschritten
                </UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.incidentFlow ? 'true' : 'false'}</dd>
              <dt>
                <span id="incidentCompressor">Incident Compressor</span>
                <UncontrolledTooltip target="incidentCompressor">Stoerung Verdichter /
                  Motorschutzschalter</UncontrolledTooltip>
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
                <span id="calculatedOverheatTemperature">Calculated Overheat Temperature</span>
                <UncontrolledTooltip target="calculatedOverheatTemperature">
                  Überhitzung: Berechnet aus Kühlmittel-Temperatur am Ausgang des Verdampfers und dem Druck mal
                  TemperaturKonstante des
                  Kühlmittels
                </UncontrolledTooltip>
              </dt>
              <dd>{processdataEntity.calculatedOverheatTemperature}</dd>
              <dt>
                <span id="warningLowPressure">Warning Low Pressure</span>
                <UncontrolledTooltip target="warningLowPressure">
                  Warnung Niederdruck (Soft-Wert falls gemessener Niederdruck unter konfigurierte Grenze fällt)
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
            </dl>
            <Button tag={Link} to="/entity/processdata" replace color="info">
              <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
            </Button>
            &nbsp;
            <Button tag={Link} to={`/entity/processdata/${processdataEntity.id}/edit`} replace color="primary">
              <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
            </Button>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({processdata}: IRootState) => {

  /* eslint-disable no-console */
  console.log("DataView mapStateToProps called");
  /* eslint-enable no-console */

  return {processdataEntity: processdata.latestEntity};
};

const mapDispatchToProps = {getLatestProcessdata};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DataView);
